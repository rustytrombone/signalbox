package com.company.signalbox.web.screens.markovlab;

import com.haulmont.cuba.gui.screen.*;
import com.company.signalbox.entity.labs.MarkovLab;

@UiController("signalbox_MarkovLab.edit")
@UiDescriptor("markov-lab-edit.xml")
@EditedEntityContainer("markovLabDc")
@LoadDataBeforeShow
public class MarkovLabEdit extends StandardEditor<MarkovLab> {
}