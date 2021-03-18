package com.company.signalbox

import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowFactory
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowFactoryFactory
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowFactoryFactoryContext

class MarkovWindowFactoryFactory implements VirtualDataWindowFactoryFactory{
    @Override
    VirtualDataWindowFactory createFactory(VirtualDataWindowFactoryFactoryContext ctx) {
        return new MarkovWindowFactory()
    }
}
