package com.company.signalbox.entity.data;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TSeriesType implements EnumClass<Integer> {

    MIN1(10),
    MIN5(20),
    MIN15(30);

    private Integer id;

    TSeriesType(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static TSeriesType fromId(Integer id) {
        for (TSeriesType at : TSeriesType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}