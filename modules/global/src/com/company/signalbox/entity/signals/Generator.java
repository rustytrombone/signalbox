package com.company.signalbox.entity.signals;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@PublishEntityChangedEvents
@NamePattern("%s|name")
@Table(name = "SIGNALBOX_GENERATOR")
@Entity(name = "signalbox_Generator")
public class Generator extends StandardEntity {
    private static final long serialVersionUID = -3641388484505898813L;

    @Column(name = "NAME", nullable = false)
    protected @NotNull String name;

    @Column(name = "HANDLER_", nullable = false)
    protected @NotNull String handler;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @Lob
    @Column(name = "QUERY", nullable = false)
    protected @NotNull String query;

    @Column(name = "STATUS", nullable = false)
    protected @NotNull Integer status;

    @OneToMany(mappedBy = "generator")
    protected List<Signal> signals;

    public GenHandler getHandler() {
        return handler == null ? null : GenHandler.fromId(handler);
    }

    public void setHandler(GenHandler handler) {
        this.handler = handler == null ? null : handler.getId();
    }

    public List<Signal> getSignals() {
        return signals;
    }

    public void setSignals(List<Signal> signals) {
        this.signals = signals;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeneratorStatus getStatus() {
        return status == null ? null : GeneratorStatus.fromId(status);
    }

    public void setStatus(GeneratorStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}