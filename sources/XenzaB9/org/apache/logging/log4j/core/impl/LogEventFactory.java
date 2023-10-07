// 
// Decompiled by Procyon v0.6.0
// 

package org.apache.logging.log4j.core.impl;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Property;
import java.util.List;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;

public interface LogEventFactory extends LocationAwareLogEventFactory
{
    LogEvent createEvent(final String loggerName, final Marker marker, final String fqcn, final Level level, final Message data, final List<Property> properties, final Throwable t);
    
    default LogEvent createEvent(final String loggerName, final Marker marker, final String fqcn, final StackTraceElement location, final Level level, final Message data, final List<Property> properties, final Throwable t) {
        return this.createEvent(loggerName, marker, fqcn, level, data, properties, t);
    }
}
