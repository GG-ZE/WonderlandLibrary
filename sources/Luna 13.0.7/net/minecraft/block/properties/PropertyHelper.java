package net.minecraft.block.properties;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;

public abstract class PropertyHelper
  implements IProperty
{
  private final Class valueClass;
  private final String name;
  private static final String __OBFID = "CL_00002018";
  
  protected PropertyHelper(String name, Class valueClass)
  {
    this.valueClass = valueClass;
    this.name = name;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public Class getValueClass()
  {
    return this.valueClass;
  }
  
  public String toString()
  {
    return Objects.toStringHelper(this).add("name", this.name).add("clazz", this.valueClass).add("values", getAllowedValues()).toString();
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (this == p_equals_1_) {
      return true;
    }
    if ((p_equals_1_ != null) && (getClass() == p_equals_1_.getClass()))
    {
      PropertyHelper var2 = (PropertyHelper)p_equals_1_;
      return (this.valueClass.equals(var2.valueClass)) && (this.name.equals(var2.name));
    }
    return false;
  }
  
  public int hashCode()
  {
    return 31 * this.valueClass.hashCode() + this.name.hashCode();
  }
}
