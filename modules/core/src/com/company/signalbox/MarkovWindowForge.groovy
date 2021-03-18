package com.company.signalbox

import com.espertech.esper.common.client.hook.forgeinject.InjectionStrategy
import com.espertech.esper.common.client.hook.forgeinject.InjectionStrategyClassNewInstance
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowFactoryMode
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowFactoryModeManaged
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowForge
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowForgeContext

class MarkovWindowForge implements VirtualDataWindowForge {

    @Override
    void initialize(VirtualDataWindowForgeContext initializeContext) {

    }

    @Override
    VirtualDataWindowFactoryMode getFactoryMode() {
        // The injection strategy defines how to obtain and configure the factory-factory.
        InjectionStrategy injectionStrategy = new InjectionStrategyClassNewInstance(MarkovWindowFactoryFactory.class);

        // The managed-mode is the default. It uses the provided injection strategy.
        VirtualDataWindowFactoryModeManaged managed = new VirtualDataWindowFactoryModeManaged();
        managed.setInjectionStrategyFactoryFactory(injectionStrategy);

        return managed;
    }

    @Override
    Set<String> getUniqueKeyPropertyNames() {
        return null
    }
}
