package org.slf4j.internal;


import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;


public class EclipseConsoleLoggerFactory implements ILoggerFactory {

    public Logger getLogger(String name) {
        return new EclipseConsoleLogger(name);
    }
}
