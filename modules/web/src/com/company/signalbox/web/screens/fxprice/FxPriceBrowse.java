package com.company.signalbox.web.screens.fxprice;

import com.haulmont.cuba.gui.screen.*;
import com.company.signalbox.entity.data.FxPrice;

@UiController("signalbox_FxPrice.browse")
@UiDescriptor("fx-price-browse.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class FxPriceBrowse extends MasterDetailScreen<FxPrice> {
}