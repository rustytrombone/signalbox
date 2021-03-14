package com.company.signalbox.web.screens.generator;

import com.company.signalbox.service.SigGenService
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.screen.*;
import com.company.signalbox.entity.signals.Generator;

import javax.inject.Inject;

@UiController("signalbox_Generator.edit")
@UiDescriptor("generator-edit.xml")
@EditedEntityContainer("generatorDc")
@LoadDataBeforeShow
public class GeneratorEdit extends StandardEditor<Generator> {
    @Inject
    protected SigGenService sigGenService

    @Inject
    private Notifications notifications;

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        try {
            sigGenService.checkCompilation(editedEntity.query)
        } catch (Exception ex) {
            event.preventCommit()
            notifications.create()
                    .withCaption("Query Compilation Error")
                    .withDescription(ex.message)
                    .withType(Notifications.NotificationType.ERROR)
                    .show();
        }
    }
}