package com.company.signalbox.entity.signals;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@NamePattern("%s|name")
@Table(name = "SIGNALBOX_SIGNAL")
@Entity(name = "signalbox_Signal")
public class Signal extends StandardEntity {
    private static final long serialVersionUID = 6404766956730166234L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GENERATOR_ID")
    protected Generator generator;

    @Column(name = "NAME")
    protected String name;

    @Column(name = "VALUE_")
    protected String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }
}