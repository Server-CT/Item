package org.inventivetalent.itembuilder.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class AccessUtil
{
  public static Field setAccessible(Field f)
    throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
  {
    f.setAccessible(true);
    Field modifiersField = Field.class.getDeclaredField("modifiers");
    modifiersField.setAccessible(true);
    modifiersField.setInt(f, f.getModifiers() & 0xFFFFFFEF);
    return f;
  }
  
  public static Method setAccessible(Method m)
    throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
  {
    m.setAccessible(true);
    return m;
  }
  
  public static Constructor setAccessible(Constructor c)
    throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
  {
    c.setAccessible(true);
    return c;
  }
}
