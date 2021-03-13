package com.company.signalbox.entity.signals;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum GenHandler implements EnumClass<String> {

    FX_PRICE("A"),
    GENERIC_SIGNAL("B");

    private String id;

    GenHandler(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static GenHandler fromId(String id) {
        for (GenHandler at : GenHandler.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}