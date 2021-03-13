package com.company.signalbox.entity.labs;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NamePattern("%s|name")
@Table(name = "SIGNALBOX_MARKOV_LAB")
@Entity(name = "signalbox_MarkovLab")
public class MarkovLab extends StandardEntity {
    private static final long serialVersionUID = -1914428668547704875L;

    @Column(name = "NAME")
    protected String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}