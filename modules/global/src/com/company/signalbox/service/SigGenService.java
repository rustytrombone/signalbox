package com.company.signalbox.service;

import com.company.signalbox.entity.data.EplResultItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public interface SigGenService {
    String NAME = "signalbox_SigGenService";
    void reloadStatements(UUID name);
    void checkCompilation(String query);
    List<EplResultItem> executeQuery(String query);
    void sendEntityEvent(com.haulmont.cuba.core.entity.Entity entity, String eventName);
    void reloadStatements();
    void sendTestMessage();


}