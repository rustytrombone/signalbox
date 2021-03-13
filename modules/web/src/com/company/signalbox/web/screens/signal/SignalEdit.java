package com.company.signalbox.web.screens.signal;

import com.haulmont.cuba.gui.screen.*;
import com.company.signalbox.entity.signals.Signal;

@UiController("signalbox_Signal.edit")
@UiDescriptor("signal-edit.xml")
@EditedEntityContainer("signalDc")
@LoadDataBeforeShow
public class SignalEdit extends StandardEditor<Signal> {
}