package org.inventivetalent.itembuilder.util;

import org.inventivetalent.itembuilder.Reflection;

import java.lang.reflect.Field;

public abstract class OBCClass
{
  private static boolean initialized;
  public static Class<?> CraftWorld;
  public static Class<?> inventory_CraftMetaSkull;
  
  static
  {
    if (!initialized) {
      for (Field f : OBCClass.class.getDeclaredFields()) {
        if (f.getType().equals(Class.class))
        {
          String name = f.getName().replace("_", ".");
          try
          {
            f.set(null, Reflection.getOBCClassWithException(name));
          }
          catch (Exception localException) {}
        }
      }
    }
  }
}
