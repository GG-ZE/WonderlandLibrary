// 
// Decompiled by Procyon v0.6.0
// 

package org.apache.logging.log4j.core.util;

import java.net.URLConnection;

public interface AuthorizationProvider
{
    void addAuthorization(final URLConnection urlConnection);
}
