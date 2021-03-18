package com.company.signalbox

import com.espertech.esper.common.client.hook.vdw.VirtualDataWindow
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowContext
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowFactory
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowFactoryContext

class MarkovWindowFactory implements VirtualDataWindowFactory {
    @Override
    void initialize(VirtualDataWindowFactoryContext initializeContext) {

    }

    @Override
    VirtualDataWindow create(VirtualDataWindowContext context) {
        return new MarkovDataWindow(context)
    }

    @Override
    void destroy() {

    }
}
