package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import net.minecraft.util.IRegistry;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrySimple implements IRegistry {
   private static final Logger logger = LogManager.getLogger();
   protected final Map registryObjects = this.createUnderlyingMap();

   public Object getObject(Object name) {
      return this.registryObjects.get(name);
   }

   public void putObject(Object p_82595_1_, Object p_82595_2_) {
      Validate.notNull(p_82595_1_);
      Validate.notNull(p_82595_2_);
      if(this.registryObjects.containsKey(p_82595_1_)) {
         logger.debug("Adding duplicate key \'" + p_82595_1_ + "\' to registry");
      }

      this.registryObjects.put(p_82595_1_, p_82595_2_);
   }

   public Iterator iterator() {
      return this.registryObjects.values().iterator();
   }

   public boolean containsKey(Object p_148741_1_) {
      return this.registryObjects.containsKey(p_148741_1_);
   }

   public Set getKeys() {
      return Collections.unmodifiableSet(this.registryObjects.keySet());
   }

   protected Map createUnderlyingMap() {
      return Maps.newHashMap();
   }
}
