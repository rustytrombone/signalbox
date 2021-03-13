package com.company.signalbox.entity.data;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "SIGNALBOX_FX_PRICE_TS")
@Entity(name = "signalbox_FxPriceTS")
public class FxPriceTS extends StandardEntity {
    private static final long serialVersionUID = -4757749572679255670L;

    @Column(name = "TICKER", length = 15)
    protected String ticker;

    @Column(name = "VAL", precision = 19, scale = 6)
    protected BigDecimal val;

    @Column(name = "TYPE_")
    protected Integer type;

    public TSeriesType getType() {
        return type == null ? null : TSeriesType.fromId(type);
    }

    public void setType(TSeriesType type) {
        this.type = type == null ? null : type.getId();
    }

    public BigDecimal getVal() {
        return val;
    }

    public void setVal(BigDecimal val) {
        this.val = val;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}