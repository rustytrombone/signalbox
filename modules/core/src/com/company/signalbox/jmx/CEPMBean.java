package com.company.signalbox.jmx;

import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "JMX bean for some settings")
public interface CEPMBean {
    void sentTestMessage();
}