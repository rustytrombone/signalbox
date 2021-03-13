package com.company.signalbox.web.screens.generator;

import com.company.signalbox.service.SigGenService;
import com.haulmont.cuba.gui.screen.*;
import com.company.signalbox.entity.signals.Generator;

import javax.inject.Inject;

@UiController("signalbox_Generator.browse")
@UiDescriptor("generator-browse.xml")
@LookupComponent("generatorsTable")
@LoadDataBeforeShow
public class GeneratorBrowse extends StandardLookup<Generator> {
    @Inject
    protected SigGenService sigGenService

    public void onBtnTestMessageClick() {
        sigGenService.sendTestMessage();
    }
}