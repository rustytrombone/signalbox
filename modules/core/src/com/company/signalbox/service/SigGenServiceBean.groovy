package com.company.signalbox.service

import com.company.signalbox.MarkovWindowForge
import com.company.signalbox.entity.data.EplResultItem
import com.company.signalbox.entity.data.FxPrice
import com.company.signalbox.entity.signals.GenHandler
import com.company.signalbox.entity.signals.Generator
import com.company.signalbox.entity.signals.GeneratorStatus
import com.espertech.esper.common.client.EPCompiled
import com.espertech.esper.common.client.EventBean
import com.espertech.esper.common.client.configuration.Configuration
import com.espertech.esper.common.client.fireandforget.EPFireAndForgetQueryResult
import com.espertech.esper.common.client.module.Module
import com.espertech.esper.common.client.util.EventTypeBusModifier
import com.espertech.esper.common.client.util.NameAccessModifier
import com.espertech.esper.compiler.client.CompilerArguments
import com.espertech.esper.compiler.client.EPCompileException
import com.espertech.esper.compiler.client.EPCompiler
import com.espertech.esper.compiler.client.EPCompilerProvider
import com.espertech.esper.compiler.client.option.AccessModifierContextOption
import com.espertech.esper.compiler.client.option.AccessModifierEventTypeContext
import com.espertech.esper.compiler.client.option.AccessModifierEventTypeOption
import com.espertech.esper.compiler.client.option.AccessModifierNamedWindowContext
import com.espertech.esper.compiler.client.option.AccessModifierNamedWindowOption
import com.espertech.esper.compiler.client.option.BusModifierEventTypeContext
import com.espertech.esper.compiler.client.option.BusModifierEventTypeOption
import com.espertech.esper.runtime.client.DeploymentOptions
import com.espertech.esper.runtime.client.EPDeployException
import com.espertech.esper.runtime.client.EPDeployment
import com.espertech.esper.runtime.client.EPFireAndForgetService
import com.espertech.esper.runtime.client.EPRuntime
import com.espertech.esper.runtime.client.EPRuntimeProvider
import com.espertech.esper.runtime.client.EPStatement
import com.espertech.esper.runtime.client.UpdateListener
import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.core.global.TimeSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

import javax.inject.Inject;

@Service(SigGenService.NAME)
public class SigGenServiceBean implements SigGenService {

    private Logger log = LoggerFactory.getLogger(getClass())

    protected boolean isStarted = false;
    protected EPCompiler compiler
    protected EPRuntime runtime
    protected Configuration configuration
    protected CompilerArguments compilerArguments

    @Inject
    protected Metadata metadata

    @Inject
    protected TimeSource timeSource

    private long lastReloadedGens

    @Inject
    protected DataManager dataManager

    private Map<Generator, EPDeployment> loadedGenStatements = new HashMap<>()
    private Map<Generator, EPCompiled> genCompilations = new HashMap<>()

    // Model / labe process
    // Create data structure that is based on event type, with start probabilities, transition probs and emmission probs
    // For each defined model, add a virtual data window and name it

    @Override
    public void sendTestMessage() {
        init();

        def fxPrice = metadata.create(FxPrice.class)
        fxPrice.ticker = 'GBPUSD'
        fxPrice.val = 25.55

        runtime.getEventService().sendEventBean(fxPrice, "FxPrice");
    }

    @Override
    public void sendEntityEvent(Entity entity, String eventName) {
        runtime.getEventService().sendEventBean(entity, eventName);
    }

    @Override
    public void reloadStatements() {
        reloadStatements(null)
        def gens = dataManager.load(Generator.class).view('generator-view').list()

        gens.each { gen ->
            if ((gen.status.equals(GeneratorStatus.DISABLED) && loadedGenStatements.keySet().contains(gen)) ||
                    (gen.status.equals(GeneratorStatus.ENABLED && gen.updateTs.getTime() > lastReloadedGens && loadedGenStatements.keySet().contains(gen)))) {
                runtime.getDeploymentService().undeploy(loadedGenStatements[gen].deploymentId)
                loadedGenStatements.remove(gen)
                log.info("Generator {} removed", gen.name)
            }

            if ((gen.status.equals(GeneratorStatus.ENABLED) && !loadedGenStatements.keySet().contains(gen))) {
                def deployment = compileAndDeploy(gen)
                loadedGenStatements.put(gen, deployment)
                log.info("Generator {} added", gen.name)
            }
        }

        lastReloadedGens = timeSource.currentTimeMillis()

    }

    @Override
    public void reloadStatements(UUID id) {
        init();
        def gen = loadedGenStatements.keySet().find { it.id.equals(id)}

        if (gen && loadedGenStatements.containsKey(gen)) {
            runtime.getDeploymentService().undeploy(loadedGenStatements[gen].deploymentId)
            loadedGenStatements.remove(gen)
            def newGen = dataManager.load(Generator.class).id(id).view('generator-view').one()
            def deployment = compileAndDeploy(newGen)
            loadedGenStatements.put(gen, deployment)

            log.info("Reloaded statement {}", newGen.name)
        }

        // 1. Load all statements which are inactive, if any of them exist in active statements list, shut them down and remove them
        // 2. All that are active but not in list, add them
        // 3. Any which are updated since last ran, remove from list and re-load
        // 4. Any which cause errors when loading, update status to errored and save
    }

    @Override
    void checkCompilation(String query) {
        return
        EPCompilerProvider.getCompiler().compile(query, new CompilerArguments()); // TODO this doesn't work because it won't compile named windows which already exists
    }

    EPDeployment compileAndDeploy(Generator generator) {
        init()

        try {

            def args = new CompilerArguments();

            args.getOptions().setAccessModifierEventType( new AccessModifierEventTypeOption() {
                @Override
                NameAccessModifier getValue(AccessModifierEventTypeContext env) {
                    NameAccessModifier.PUBLIC
                }
            })


            args.getOptions().setAccessModifierNamedWindow(new AccessModifierNamedWindowOption() {
                @Override
                NameAccessModifier getValue(AccessModifierNamedWindowContext env) {
                    return NameAccessModifier.PUBLIC
                }
            });
            args.getOptions().setBusModifierEventType(new BusModifierEventTypeOption() {
                @Override
                EventTypeBusModifier getValue(BusModifierEventTypeContext env) {
                    return EventTypeBusModifier.BUS
                }
            });
            args.setConfiguration(runtime.getConfigurationDeepCopy());


            def epCompiled = compiler.compile(generator.query, args);
            def deployment = runtime.getDeploymentService().deploy(epCompiled);
            def statement = runtime.getDeploymentService().getStatement(deployment.getDeploymentId(), generator.name);

            genCompilations[generator] = epCompiled

            statement.addListener(new UpdateListener() {
                @Override
                void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement stat, EPRuntime runtime) {
                    switch (generator.handler) {
                        case GenHandler.FX_PRICE:
                            String ticker = (String) newEvents[0].get("ticker");
                            BigDecimal val = new BigDecimal((String) newEvents[0].get("val"))
                            log.info("FX Event: {} {}", ticker, val)
                            break;
                        case GenHandler.GENERIC_SIGNAL:
                            // TODO implement me
                            break;
                    }

                }
            })

            return deployment
        } catch (Exception ex) {
            log.error("Error deploying statement", ex)
            generator.status = GeneratorStatus.ERROR

            return null
        }
    }

    private void init() {

        if (!isStarted) {
            lastReloadedGens = timeSource.currentTimeMillis()

            configuration = new Configuration();
            configuration.getCommon().addEventType(FxPrice.class);

            configuration.getCompiler().addPlugInSingleRowFunction('labVal', 'com.company.signalbox.service.SigGenServiceBean', 'getLabValue')
            configuration.getCompiler().addPlugInVirtualDataWindow("ml", "markov", MarkovWindowForge.class.getName());
            compiler = EPCompilerProvider.getCompiler();
            runtime = EPRuntimeProvider.getDefaultRuntime(configuration);

            compilerArguments = new CompilerArguments(configuration);

            isStarted = true

        }
    }

    public static int getLabValue(int inputVal) {
        return inputVal + 5;
    }

    @Override
    public List<EplResultItem> executeQuery(String query) {

        init()
        compilerArguments.getPath().add(runtime.getRuntimePath())
        EPCompiled compiled = EPCompilerProvider.getCompiler().compileQuery(query, compilerArguments);

        EPFireAndForgetQueryResult result = runtime.getFireAndForgetService().executeQuery(compiled);

        def events = []
        for (EventBean row : result.getArray()) {
            def item = metadata.create(EplResultItem.class)
            item.eventName = result.eventType.name
            row.getProperties().each {
                item.data = item.data + String.format("%s=%s,", it.key.toString(), it.value.toString())
            }
            events.add(item)
        }

        return events
    }

}