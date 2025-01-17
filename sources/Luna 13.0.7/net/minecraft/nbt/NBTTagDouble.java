package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.MathHelper;

public class NBTTagDouble
  extends NBTBase.NBTPrimitive
{
  private double data;
  private static final String __OBFID = "CL_00001218";
  
  NBTTagDouble() {}
  
  public NBTTagDouble(double data)
  {
    this.data = data;
  }
  
  void write(DataOutput output)
    throws IOException
  {
    output.writeDouble(this.data);
  }
  
  void read(DataInput input, int depth, NBTSizeTracker sizeTracker)
    throws IOException
  {
    sizeTracker.read(64L);
    this.data = input.readDouble();
  }
  
  public byte getId()
  {
    return 6;
  }
  
  public String toString()
  {
    return "" + this.data + "d";
  }
  
  public NBTBase copy()
  {
    return new NBTTagDouble(this.data);
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (super.equals(p_equals_1_))
    {
      NBTTagDouble var2 = (NBTTagDouble)p_equals_1_;
      return this.data == var2.data;
    }
    return false;
  }
  
  public int hashCode()
  {
    long var1 = Double.doubleToLongBits(this.data);
    return super.hashCode() ^ (int)(var1 ^ var1 >>> 32);
  }
  
  public long getLong()
  {
    return Math.floor(this.data);
  }
  
  public int getInt()
  {
    return MathHelper.floor_double(this.data);
  }
  
  public short getShort()
  {
    return (short)(MathHelper.floor_double(this.data) & 0xFFFF);
  }
  
  public byte getByte()
  {
    return (byte)(MathHelper.floor_double(this.data) & 0xFF);
  }
  
  public double getDouble()
  {
    return this.data;
  }
  
  public float getFloat()
  {
    return (float)this.data;
  }
}
