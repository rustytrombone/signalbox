package com.company.signalbox.entity.data;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "SIGNALBOX_ITEM")
@Entity(name = "signalbox_Item")
public class Item extends StandardEntity {
    private static final long serialVersionUID = -5356859749239115790L;

    @Column(name = "TYPE_")
    protected Integer type;

    public ItemType getType() {
        return type == null ? null : ItemType.fromId(type);
    }

    public void setType(ItemType type) {
        this.type = type == null ? null : type.getId();
    }
}