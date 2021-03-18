package com.company.signalbox

import com.espertech.esper.common.client.EventBean
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindow
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowContext
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowEvent
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowLookup
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowLookupContext

class MarkovDataWindow  implements VirtualDataWindow {
    @Override
    VirtualDataWindowLookup getLookup(VirtualDataWindowLookupContext desc) {
        return null
    }

    @Override
    void handleEvent(VirtualDataWindowEvent theEvent) {

    }

    @Override
    void update(EventBean[] newData, EventBean[] oldData) {

    }

    @Override
    void destroy() {

    }

    @Override
    Iterator<EventBean> iterator() {
        return null
    }

    private final VirtualDataWindowContext context;

    public MarkovDataWindow(VirtualDataWindowContext context) {
        this.context = context;
    }
}
