package org.inventivetalent.itembuilder.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class EnchantmentGlow
  extends EnchantmentWrapper
{
  public static int ID = 99;
  private static boolean INJECTED = false;
  private static EnchantmentGlow STATIC;
  
  EnchantmentGlow()
  {
    super(ID);
  }
  
  public int getMaxLevel()
  {
    return 1;
  }
  
  public int getStartLevel()
  {
    return 1;
  }
  
  public EnchantmentTarget getItemTarget()
  {
    return null;
  }
  
  public boolean canEnchantItem(ItemStack item)
  {
    return true;
  }
  
  public String getName()
  {
    return "";
  }
  
  public boolean conflictsWith(Enchantment other)
  {
    return false;
  }
  
  public static EnchantmentGlow inject()
  {
    if ((INJECTED) && (STATIC != null)) {
      return STATIC;
    }
    try
    {
      Field f = Enchantment.class.getDeclaredField("acceptingNew");
      f.setAccessible(true);
      f.set(null, Boolean.valueOf(true));
    }
    catch (Exception e)
    {
      System.err.println("Can't inject EnchantmentGlow, failed to set Enchantment field accessible");
      e.printStackTrace();
      return STATIC;
    }
    EnchantmentGlow enchantmentGlow = new EnchantmentGlow();
    registerEnchantment(enchantmentGlow);
    
    INJECTED = true;
    STATIC = enchantmentGlow;return enchantmentGlow;
  }
}
