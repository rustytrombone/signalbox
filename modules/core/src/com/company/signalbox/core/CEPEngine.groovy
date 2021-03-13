package com.company.signalbox.core

import jdk.jfr.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(CEPEngine.NAME)
public class CEPEngine {
    private Logger log = LoggerFactory.getLogger(getClass())

    public static final String NAME = "signalbox_CEPEngine";

    public void sentTestMessage() {
        log.info("Test message");
    }
}