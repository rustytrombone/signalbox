package com.company.signalbox

import com.espertech.esper.common.client.EventBean
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowContext
import com.espertech.esper.common.client.hook.vdw.VirtualDataWindowLookup

class MarkovDataWindowLookup  implements VirtualDataWindowLookup {

    private final VirtualDataWindowContext context;

    public MarkovDataWindowLookup(VirtualDataWindowContext context) {
        this.context = context;
    }

    @Override
    Set<EventBean> lookup(Object[] keys, EventBean[] eventsPerStream) {
        Map<String, Object> eventData = new HashMap<String, Object>();
        eventData.put("key1", "sample1");
        eventData.put("key2", "sample2");
        eventData.put("value1", 100);
        eventData.put("value2", 1.5d);
        EventBean theEvent = context.getEventFactory().wrap(eventData);
        return Collections.singleton(theEvent);
    }
}
