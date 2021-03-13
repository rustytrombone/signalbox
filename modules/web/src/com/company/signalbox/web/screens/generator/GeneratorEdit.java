package com.company.signalbox.web.screens.generator;

import com.haulmont.cuba.gui.screen.*;
import com.company.signalbox.entity.signals.Generator;

@UiController("signalbox_Generator.edit")
@UiDescriptor("generator-edit.xml")
@EditedEntityContainer("generatorDc")
@LoadDataBeforeShow
public class GeneratorEdit extends StandardEditor<Generator> {
}