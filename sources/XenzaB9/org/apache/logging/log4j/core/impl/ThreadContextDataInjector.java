// 
// Decompiled by Procyon v0.6.0
// 

package org.apache.logging.log4j.core.impl;

import org.apache.logging.log4j.spi.ReadOnlyThreadContextMap;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import org.apache.logging.log4j.core.ContextDataInjector;
import java.util.concurrent.ConcurrentLinkedDeque;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.StringMap;
import org.apache.logging.log4j.core.config.Property;
import java.util.Collections;
import java.util.function.Consumer;
import org.apache.logging.log4j.util.ServiceLoaderUtil;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.core.util.ContextDataProvider;
import java.util.Collection;
import org.apache.logging.log4j.Logger;

public class ThreadContextDataInjector
{
    private static final Logger LOGGER;
    public static Collection<ContextDataProvider> contextDataProviders;
    private static final List<ContextDataProvider> SERVICE_PROVIDERS;
    
    @Deprecated
    public static void initServiceProviders() {
    }
    
    private static List<ContextDataProvider> getServiceProviders() {
        final List<ContextDataProvider> providers = new ArrayList<ContextDataProvider>();
        ServiceLoaderUtil.loadServices(ContextDataProvider.class, MethodHandles.lookup(), false).forEach(providers::add);
        return Collections.unmodifiableList((List<? extends ContextDataProvider>)providers);
    }
    
    public static void copyProperties(final List<Property> properties, final StringMap result) {
        if (properties != null) {
            for (int i = 0; i < properties.size(); ++i) {
                final Property prop = properties.get(i);
                result.putValue(prop.getName(), prop.getValue());
            }
        }
    }
    
    private static List<ContextDataProvider> getProviders() {
        final List<ContextDataProvider> providers = new ArrayList<ContextDataProvider>(ThreadContextDataInjector.contextDataProviders.size() + ThreadContextDataInjector.SERVICE_PROVIDERS.size());
        providers.addAll(ThreadContextDataInjector.contextDataProviders);
        providers.addAll(ThreadContextDataInjector.SERVICE_PROVIDERS);
        return providers;
    }
    
    static {
        LOGGER = StatusLogger.getLogger();
        ThreadContextDataInjector.contextDataProviders = new ConcurrentLinkedDeque<ContextDataProvider>();
        SERVICE_PROVIDERS = getServiceProviders();
    }
    
    public static class ForDefaultThreadContextMap implements ContextDataInjector
    {
        private final List<ContextDataProvider> providers;
        
        public ForDefaultThreadContextMap() {
            this.providers = getProviders();
        }
        
        @Override
        public StringMap injectContextData(final List<Property> props, final StringMap contextData) {
            Map<String, String> copy;
            if (this.providers.size() == 1) {
                copy = this.providers.get(0).supplyContextData();
            }
            else {
                copy = new HashMap<String, String>();
                for (final ContextDataProvider provider : this.providers) {
                    copy.putAll(provider.supplyContextData());
                }
            }
            if (props == null || props.isEmpty()) {
                return copy.isEmpty() ? ContextDataFactory.emptyFrozenContextData() : frozenStringMap(copy);
            }
            final StringMap result = new JdkMapAdapterStringMap(new HashMap<String, String>(copy));
            for (int i = 0; i < props.size(); ++i) {
                final Property prop = props.get(i);
                if (!copy.containsKey(prop.getName())) {
                    result.putValue(prop.getName(), prop.getValue());
                }
            }
            result.freeze();
            return result;
        }
        
        private static JdkMapAdapterStringMap frozenStringMap(final Map<String, String> copy) {
            final JdkMapAdapterStringMap result = new JdkMapAdapterStringMap(copy);
            result.freeze();
            return result;
        }
        
        @Override
        public ReadOnlyStringMap rawContextData() {
            final ReadOnlyThreadContextMap map = ThreadContext.getThreadContextMap();
            if (map instanceof ReadOnlyStringMap) {
                return (ReadOnlyStringMap)map;
            }
            final Map<String, String> copy = ThreadContext.getImmutableContext();
            return copy.isEmpty() ? ContextDataFactory.emptyFrozenContextData() : new JdkMapAdapterStringMap(copy);
        }
    }
    
    public static class ForGarbageFreeThreadContextMap implements ContextDataInjector
    {
        private final List<ContextDataProvider> providers;
        
        public ForGarbageFreeThreadContextMap() {
            this.providers = getProviders();
        }
        
        @Override
        public StringMap injectContextData(final List<Property> props, final StringMap reusable) {
            ThreadContextDataInjector.copyProperties(props, reusable);
            for (int i = 0; i < this.providers.size(); ++i) {
                reusable.putAll(this.providers.get(i).supplyStringMap());
            }
            return reusable;
        }
        
        @Override
        public ReadOnlyStringMap rawContextData() {
            return ThreadContext.getThreadContextMap().getReadOnlyContextData();
        }
    }
    
    public static class ForCopyOnWriteThreadContextMap implements ContextDataInjector
    {
        private final List<ContextDataProvider> providers;
        
        public ForCopyOnWriteThreadContextMap() {
            this.providers = getProviders();
        }
        
        @Override
        public StringMap injectContextData(final List<Property> props, final StringMap ignore) {
            if (this.providers.size() == 1 && (props == null || props.isEmpty())) {
                return this.providers.get(0).supplyStringMap();
            }
            int count = (props == null) ? 0 : props.size();
            final StringMap[] maps = new StringMap[this.providers.size()];
            for (int i = 0; i < this.providers.size(); ++i) {
                maps[i] = this.providers.get(i).supplyStringMap();
                count += maps[i].size();
            }
            final StringMap result = ContextDataFactory.createContextData(count);
            ThreadContextDataInjector.copyProperties(props, result);
            for (final StringMap map : maps) {
                result.putAll(map);
            }
            return result;
        }
        
        @Override
        public ReadOnlyStringMap rawContextData() {
            return ThreadContext.getThreadContextMap().getReadOnlyContextData();
        }
    }
}
