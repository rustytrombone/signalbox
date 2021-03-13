package com.company.signalbox.web.screens.markovlab;

import com.company.signalbox.service.LabService;
import com.haulmont.cuba.gui.screen.*;
import com.company.signalbox.entity.labs.MarkovLab;

import javax.inject.Inject;

@UiController("signalbox_MarkovLab.browse")
@UiDescriptor("markov-lab-browse.xml")
@LookupComponent("markovLabsTable")
@LoadDataBeforeShow
public class MarkovLabBrowse extends StandardLookup<MarkovLab> {
    @Inject
    protected LabService labService

    public void onBtnTestLabClick() {
        labService.testMarkov()
    }
}