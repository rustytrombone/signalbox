package com.company.signalbox.listeners;

import com.company.signalbox.entity.signals.Generator;
import com.company.signalbox.service.SigGenService;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.inject.Inject;
import java.util.UUID;

@Component("signalbox_GeneratorChangedListener")
public class GeneratorChangedListener {

    @Inject
    protected SigGenService sigGenService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(EntityChangedEvent<Generator, UUID> event) {
        sigGenService.reloadStatements();
    }
}