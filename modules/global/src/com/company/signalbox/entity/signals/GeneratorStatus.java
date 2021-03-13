package com.company.signalbox.entity.signals;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum GeneratorStatus implements EnumClass<Integer> {

    ENABLED(10),
    DISABLED(20),
    ERROR(30);

    private Integer id;

    GeneratorStatus(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static GeneratorStatus fromId(Integer id) {
        for (GeneratorStatus at : GeneratorStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}