package com.company.signalbox.entity.data;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;

@MetaClass(name = "signalbox_EplResultItem")
public class EplResultItem extends BaseUuidEntity {
    private static final long serialVersionUID = 2581714123279820405L;

    @MetaProperty
    protected String eventName;

    @MetaProperty
    protected String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}