// 
// Decompiled by Procyon v0.6.0
// 

package org.apache.logging.log4j.spi;

public interface LoggerContextShutdownAware
{
    void contextShutdown(final LoggerContext loggerContext);
}
