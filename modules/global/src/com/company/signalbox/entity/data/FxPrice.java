package com.company.signalbox.entity.data;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Table(name = "SIGNALBOX_FX_PRICE")
@Entity(name = "signalbox_FxPrice")
public class FxPrice extends StandardEntity {
    private static final long serialVersionUID = -8176676478998503555L;

    @Column(name = "TICKER", length = 15)
    protected String ticker;

    @Column(name = "INSTANT")
    protected OffsetDateTime instant;

    @Column(name = "VAL", precision = 19, scale = 6)
    protected BigDecimal val;

    public OffsetDateTime getInstant() {
        return instant;
    }

    public void setInstant(OffsetDateTime instant) {
        this.instant = instant;
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