package com.company.signalbox.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SigGenService {
    String NAME = "signalbox_SigGenService";
    com.espertech.esper.common.client.fireandforget.EPFireAndForgetQueryResult executeQuery(String query);
    void reloadStatements();
    void sendTestMessage();


}