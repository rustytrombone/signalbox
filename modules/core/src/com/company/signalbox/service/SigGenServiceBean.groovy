package com.company.signalbox.service

import com.company.signalbox.entity.data.FxPrice
import com.company.signalbox.entity.signals.GenHandler
import com.company.signalbox.entity.signals.Generator
import com.company.signalbox.entity.signals.GeneratorStatus
import com.espertech.esper.common.client.EPCompiled
import com.espertech.esper.common.client.EventBean
import com.espertech.esper.common.client.configuration.Configuration
import com.espertech.esper.common.client.fireandforget.EPFireAndForgetQueryResult
import com.espertech.esper.compiler.client.CompilerArguments
import com.espertech.esper.compiler.client.EPCompileException
import com.espertech.esper.compiler.client.EPCompiler
import com.espertech.esper.compiler.client.EPCompilerProvider
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

    private boolean isStarted = false;
    private EPCompiler compiler
    private EPRuntime runtime
    private Configuration configuration

    @Inject
    protected Metadata metadata

    @Inject
    protected TimeSource timeSource

    private long lastReloadedGens

    @Inject
    protected DataManager dataManager

    private Map<Generator, EPDeployment> loadedGenStatements = new HashMap<>()

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

        if (gen) {
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
        def args = new CompilerArguments(configuration);
        EPCompilerProvider.getCompiler().compile(query, args);
    }

    EPDeployment compileAndDeploy(Generator generator) {
        try {

            def args = new CompilerArguments(configuration);
            def epCompiled = EPCompilerProvider.getCompiler().compile(generator.query, args);
            def deployment = runtime.getDeploymentService().deploy(epCompiled);
            def statement = runtime.getDeploymentService().getStatement(deployment.getDeploymentId(), generator.name);

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

            compiler = EPCompilerProvider.getCompiler();
            runtime = EPRuntimeProvider.getDefaultRuntime(configuration);


        }
    }

    public static int getLabValue(int inputVal) {
        return inputVal + 5;
    }

    @Override
    public EPFireAndForgetQueryResult executeQuery(String query) {
        def args = new CompilerArguments(configuration);
        def epCompiled = EPCompilerProvider.getCompiler().compileQuery(query, args)
        def output = runtime.fireAndForgetService.executeQuery(epCompiled)

        return output
    }

}