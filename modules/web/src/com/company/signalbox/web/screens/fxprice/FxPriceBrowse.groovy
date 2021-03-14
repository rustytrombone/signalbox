package com.company.signalbox.web.screens.fxprice

import com.company.signalbox.service.SigGenService;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import com.company.signalbox.entity.data.FxPrice;

import javax.inject.Inject;

@UiController("signalbox_FxPrice.browse")
@UiDescriptor("fx-price-browse.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class FxPriceBrowse extends MasterDetailScreen<FxPrice> {

    @Inject
    protected GroupTable<FxPrice> table

    @Inject
    protected SigGenService sigGenService

    public void onBtnSendPricesClick() {
        table.getSelected().each {
            sigGenService.sendEntityEvent(it, 'FxPrice')
        }
    }
}