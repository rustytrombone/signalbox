package com.company.signalbox.entity.data;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ItemType implements EnumClass<Integer> {

    STORED(10),
    RTIME(20);

    private Integer id;

    ItemType(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static ItemType fromId(Integer id) {
        for (ItemType at : ItemType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}