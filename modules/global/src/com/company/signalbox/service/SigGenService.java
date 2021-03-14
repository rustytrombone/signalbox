package com.company.signalbox.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public interface SigGenService {
    String NAME = "signalbox_SigGenService";
    void reloadStatements(UUID name);
    void checkCompilation(String query);
    com.espertech.esper.common.client.fireandforget.EPFireAndForgetQueryResult executeQuery(String query);
    void sendEntityEvent(com.haulmont.cuba.core.entity.Entity entity, String eventName);
    void reloadStatements();
    void sendTestMessage();


}