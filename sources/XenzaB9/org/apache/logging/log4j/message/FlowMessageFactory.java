// 
// Decompiled by Procyon v0.6.0
// 

package org.apache.logging.log4j.message;

public interface FlowMessageFactory
{
    EntryMessage newEntryMessage(final Message message);
    
    ExitMessage newExitMessage(final Object result, final Message message);
    
    ExitMessage newExitMessage(final EntryMessage message);
    
    ExitMessage newExitMessage(final Object result, final EntryMessage message);
}
