package org.slf4j.internal;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

import org.eclipse.core.runtime.Platform;
import org.egs3d.log.internal.LogConsoleFactory;
import org.slf4j.Logger;
import org.slf4j.Marker;


public class EclipseConsoleLogger implements Logger {

    private static final long start = System.currentTimeMillis();

    private static final String DEBUG_LEVEL = "DEBUG";

    private static final String INFO_LEVEL = "INFO";

    private static final String WARN_LEVEL = "WARN";

    private static final String ERROR_LEVEL = "ERROR";

    private final String name;



    public EclipseConsoleLogger(final String name) {
        this.name = name;
    }


    private void log(String level, String msg, Object[] args, Throwable cause) {
        final long now = System.currentTimeMillis();
        final long elapsed = now - start;
        final String text = args == null || args.length == 0 ? msg : MessageFormat.format(msg, args);

        final StringBuffer buf = new StringBuffer().append(elapsed).append(" [").append(Thread.currentThread().getName()).append("] ").append(level)
                .append(' ').append(name).append(" - ").append(text);
        if (cause != null) {
            final StringWriter stackTraceBuf = new StringWriter();
            cause.printStackTrace(new PrintWriter(stackTraceBuf));
            buf.append('\n').append(stackTraceBuf.getBuffer());
        }

        LogConsoleFactory.getInstance().println(buf.toString());
    }


    public String getName() {
        return name;
    }


    public boolean isDebugEnabled() {
        return Platform.inDebugMode();
    }


    public void debug(String msg) {
        debug(msg, (Throwable) null);
    }


    public void debug(String format, Object arg) {
        debug(format, new Object[] { arg });
    }


    public void debug(String format, Object arg1, Object arg2) {
        debug(format, new Object[] { arg1, arg2 });
    }


    public void debug(String format, Object[] argArray) {
        if (isDebugEnabled()) {
            log(DEBUG_LEVEL, format, argArray, null);
        }
    }


    public void debug(String msg, Throwable t) {
        if (isDebugEnabled()) {
            log(DEBUG_LEVEL, msg, null, t);
        }
    }


    public boolean isDebugEnabled(Marker marker) {
        return isDebugEnabled();
    }


    public void debug(Marker marker, String msg) {
        debug(msg);
    }


    public void debug(Marker marker, String format, Object arg) {
        debug(format, arg);
    }


    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        debug(format, arg1, arg2);
    }


    public void debug(Marker marker, String format, Object[] argArray) {
        debug(format, argArray);
    }


    public void debug(Marker marker, String msg, Throwable t) {
        debug(msg, t);
    }


    public boolean isInfoEnabled() {
        return true;
    }


    public void info(String msg) {
        info(msg, (Throwable) null);
    }


    public void info(String format, Object arg) {
        info(format, new Object[] { arg });
    }


    public void info(String format, Object arg1, Object arg2) {
        info(format, new Object[] { arg1, arg2 });
    }


    public void info(String format, Object[] argArray) {
        log(INFO_LEVEL, format, argArray, null);
    }


    public void info(String msg, Throwable t) {
        log(INFO_LEVEL, msg, null, t);
    }


    public boolean isInfoEnabled(Marker marker) {
        return isInfoEnabled();
    }


    public void info(Marker marker, String msg) {
        info(msg);
    }


    public void info(Marker marker, String format, Object arg) {
        info(format, arg);
    }


    public void info(Marker marker, String format, Object arg1, Object arg2) {
        info(format, arg1, arg2);
    }


    public void info(Marker marker, String format, Object[] argArray) {
        info(format, argArray);
    }


    public void info(Marker marker, String msg, Throwable t) {
        info(msg, t);
    }


    public boolean isWarnEnabled() {
        return true;
    }


    public void warn(String msg) {
        warn(msg, (Throwable) null);
    }


    public void warn(String format, Object arg) {
        warn(format, new Object[] { arg });
    }


    public void warn(String format, Object[] argArray) {
        log(WARN_LEVEL, format, argArray, null);
    }


    public void warn(String format, Object arg1, Object arg2) {
        warn(format, new Object[] { arg1, arg2 });
    }


    public void warn(String msg, Throwable t) {
        log(WARN_LEVEL, msg, null, t);
    }


    public boolean isWarnEnabled(Marker marker) {
        return isWarnEnabled();
    }


    public void warn(Marker marker, String msg) {
        warn(msg);
    }


    public void warn(Marker marker, String format, Object arg) {
        warn(format, arg);
    }


    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        warn(format, arg1, arg2);
    }


    public void warn(Marker marker, String format, Object[] argArray) {
        warn(format, argArray);
    }


    public void warn(Marker marker, String msg, Throwable t) {
        warn(msg, t);
    }


    public boolean isErrorEnabled() {
        return true;
    }


    public void error(String msg) {
        error(msg, (Throwable) null);
    }


    public void error(String format, Object arg) {
        error(format, new Object[] { arg });
    }


    public void error(String format, Object arg1, Object arg2) {
        error(format, new Object[] { arg1, arg2 });
    }


    public void error(String format, Object[] argArray) {
        log(ERROR_LEVEL, format, argArray, null);
    }


    public void error(String msg, Throwable t) {
        log(ERROR_LEVEL, msg, null, t);
    }


    public boolean isErrorEnabled(Marker marker) {
        return isErrorEnabled();
    }


    public void error(Marker marker, String msg) {
        error(msg);
    }


    public void error(Marker marker, String format, Object arg) {
        error(format, arg);
    }


    public void error(Marker marker, String format, Object arg1, Object arg2) {
        error(format, arg1, arg2);
    }


    public void error(Marker marker, String format, Object[] argArray) {
        error(format, argArray);
    }


    public void error(Marker marker, String msg, Throwable t) {
        error(msg, t);
    }
}
