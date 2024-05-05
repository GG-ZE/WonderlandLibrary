package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum EnumChatFormatting
{
  private static final Map nameMapping;
  private static final Pattern formattingCodePattern;
  private final String field_175748_y;
  private final char formattingCode;
  private final boolean fancyStyling;
  private final String controlString;
  private final int field_175747_C;
  private static final EnumChatFormatting[] $VALUES;
  private static final String __OBFID = "CL_00000342";
  
  private static String func_175745_c(String p_175745_0_)
  {
    return p_175745_0_.toLowerCase().replaceAll("[^a-z]", "");
  }
  
  private EnumChatFormatting(String p_i46291_1_, int p_i46291_2_, String p_i46291_3_, char p_i46291_4_, int p_i46291_5_)
  {
    this(p_i46291_1_, p_i46291_2_, p_i46291_3_, p_i46291_4_, false, p_i46291_5_);
  }
  
  private EnumChatFormatting(String p_i46292_1_, int p_i46292_2_, String p_i46292_3_, char p_i46292_4_, boolean p_i46292_5_)
  {
    this(p_i46292_1_, p_i46292_2_, p_i46292_3_, p_i46292_4_, p_i46292_5_, -1);
  }
  
  private EnumChatFormatting(String p_i46293_1_, int p_i46293_2_, String p_i46293_3_, char p_i46293_4_, boolean p_i46293_5_, int p_i46293_6_)
  {
    this.field_175748_y = p_i46293_3_;
    this.formattingCode = p_i46293_4_;
    this.fancyStyling = p_i46293_5_;
    this.field_175747_C = p_i46293_6_;
    this.controlString = ("§" + p_i46293_4_);
  }
  
  public int func_175746_b()
  {
    return this.field_175747_C;
  }
  
  public boolean isFancyStyling()
  {
    return this.fancyStyling;
  }
  
  public boolean isColor()
  {
    return (!this.fancyStyling) && (this != RESET);
  }
  
  public String getFriendlyName()
  {
    return name().toLowerCase();
  }
  
  public String toString()
  {
    return this.controlString;
  }
  
  public static String getTextWithoutFormattingCodes(String p_110646_0_)
  {
    return p_110646_0_ == null ? null : formattingCodePattern.matcher(p_110646_0_).replaceAll("");
  }
  
  public static EnumChatFormatting getValueByName(String p_96300_0_)
  {
    return p_96300_0_ == null ? null : (EnumChatFormatting)nameMapping.get(func_175745_c(p_96300_0_));
  }
  
  public static EnumChatFormatting func_175744_a(int p_175744_0_)
  {
    if (p_175744_0_ < 0) {
      return RESET;
    }
    EnumChatFormatting[] var1 = values();
    int var2 = var1.length;
    for (int var3 = 0; var3 < var2; var3++)
    {
      EnumChatFormatting var4 = var1[var3];
      if (var4.func_175746_b() == p_175744_0_) {
        return var4;
      }
    }
    return null;
  }
  
  public static Collection getValidValues(boolean p_96296_0_, boolean p_96296_1_)
  {
    ArrayList var2 = Lists.newArrayList();
    EnumChatFormatting[] var3 = values();
    int var4 = var3.length;
    for (int var5 = 0; var5 < var4; var5++)
    {
      EnumChatFormatting var6 = var3[var5];
      if (((!var6.isColor()) || (p_96296_0_)) && ((!var6.isFancyStyling()) || (p_96296_1_))) {
        var2.add(var6.getFriendlyName());
      }
    }
    return var2;
  }
  
  static
  {
    nameMapping = Maps.newHashMap();
    
    formattingCodePattern = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
    
    $VALUES = new EnumChatFormatting[] { BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE, GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE, OBFUSCATED, BOLD, STRIKETHROUGH, UNDERLINE, ITALIC, RESET };
    
    EnumChatFormatting[] var0 = values();
    int var1 = var0.length;
    for (int var2 = 0; var2 < var1; var2++)
    {
      EnumChatFormatting var3 = var0[var2];
      nameMapping.put(func_175745_c(var3.field_175748_y), var3);
    }
  }
}
