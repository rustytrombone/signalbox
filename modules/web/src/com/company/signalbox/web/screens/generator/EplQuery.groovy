package com.company.signalbox.web.screens.generator

import com.company.signalbox.entity.data.EplResultItem
import com.company.signalbox.service.SigGenService
import com.haulmont.cuba.gui.components.SourceCodeEditor
import com.haulmont.cuba.gui.model.CollectionContainer
import com.haulmont.cuba.gui.screen.Screen
import com.haulmont.cuba.gui.screen.UiController
import com.haulmont.cuba.gui.screen.UiDescriptor

import javax.inject.Inject

@UiController("signalbox_EplQuery")
@UiDescriptor("epl-query.xml")
class EplQuery extends Screen {

    @Inject
    protected SourceCodeEditor eplQuery

    @Inject
    protected CollectionContainer<EplResultItem> eplResultItemsDc

    @Inject
    protected SigGenService sigGenService

    public void onBtnExecuteClick ( ) {

        def outcome = sigGenService.executeQuery(eplQuery.value)

        eplResultItemsDc.getMutableItems().clear()

        outcome.each {
            eplResultItemsDc.replaceItem(it)
        }

    }
}