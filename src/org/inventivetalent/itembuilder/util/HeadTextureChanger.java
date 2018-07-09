package org.inventivetalent.itembuilder.util;

import org.bukkit.Location;
import org.bukkit.block.Skull;
import org.bukkit.inventory.meta.SkullMeta;
import org.inventivetalent.itembuilder.Reflection;

import java.util.Base64;
import java.util.UUID;

public class HeadTextureChanger
{
  public static String encodeBase64(byte[] bytes)
  {
    return Base64.getEncoder().encodeToString(bytes);
  }
  
  public static String buildResourceLocation(String url)
  {
    String format = "{textures:{SKIN:{url:\"%s\"}}}";
    return String.format(format, new Object[] { url });
  }
  
  public static Object createProfile(String data)
  {
    try
    {
      Object profile = NMUClass.com_mojang_authlib_GameProfile.getConstructor(new Class[] { UUID.class, String.class }).newInstance(new Object[] { UUID.randomUUID(), "CustomBlock" });
      Object propertyMap = AccessUtil.setAccessible(NMUClass.com_mojang_authlib_GameProfile.getDeclaredField("properties")).get(profile);
      Object property = NMUClass.com_mojang_authlib_properties_Property.getConstructor(new Class[] { String.class, String.class }).newInstance(new Object[] { "textures", data });
      NMUClass.com_google_common_collect_ForwardingMultimap.getDeclaredMethod("put", new Class[] { Object.class, Object.class }).invoke(propertyMap, new Object[] { "textures", property });
      
      return profile;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static Object createProfile(String value, String signature)
  {
    if (signature == null) {
      return createProfile(value);
    }
    try
    {
      Object profile = NMUClass.com_mojang_authlib_GameProfile.getConstructor(new Class[] { UUID.class, String.class }).newInstance(new Object[] { UUID.randomUUID(), "CustomBlock" });
      Object propertyMap = AccessUtil.setAccessible(NMUClass.com_mojang_authlib_GameProfile.getDeclaredField("properties")).get(profile);
      Object property = NMUClass.com_mojang_authlib_properties_Property.getConstructor(new Class[] { String.class, String.class, String.class }).newInstance(new Object[] { "textures", value, signature });
      NMUClass.com_google_common_collect_ForwardingMultimap.getDeclaredMethod("put", new Class[] { Object.class, Object.class }).invoke(propertyMap, new Object[] { "textures", property });
      
      return profile;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static void applyTextureToSkull(Skull skull, Object profile)
    throws Exception
  {
    Location loc = skull.getLocation();
    Object world = Reflection.getHandle(loc.getWorld());
    Object tileEntity = NMSClass.WorldServer.getDeclaredMethod("getTileEntity", new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE }).invoke(world, new Object[] { Integer.valueOf(loc.getBlockX()), Integer.valueOf(loc.getBlockY()), Integer.valueOf(loc.getBlockZ()) });
    AccessUtil.setAccessible(NMSClass.TileEntitySkull.getDeclaredField("j")).set(tileEntity, profile);
    NMSClass.World.getDeclaredMethod("notify", new Class[] { Integer.TYPE, Integer.TYPE, Integer.TYPE }).invoke(world, new Object[] { Integer.valueOf(loc.getBlockX()), Integer.valueOf(loc.getBlockY()), Integer.valueOf(loc.getBlockZ()) });
  }
  
  public static SkullMeta applyTextureToMeta(SkullMeta meta, Object profile)
    throws Exception
  {
    if (meta == null) {
      throw new IllegalArgumentException("meta cannot be null");
    }
    if (profile == null) {
      throw new IllegalArgumentException("profile cannot be null");
    }
    Object baseNBTTag = NMSClass.NBTTagCompound.newInstance();
    Object ownerNBTTag = NMSClass.NBTTagCompound.newInstance();
    
    NMSClass.GameProfileSerializer.getDeclaredMethod("serialize", new Class[] { NMSClass.NBTTagCompound, NMUClass.com_mojang_authlib_GameProfile }).invoke(null, new Object[] { ownerNBTTag, profile });
    
    NMSClass.NBTTagCompound.getDeclaredMethod("set", new Class[] { String.class, NMSClass.NBTBase }).invoke(baseNBTTag, new Object[] { "SkullOwner", ownerNBTTag });
    
    SkullMeta newMeta = (SkullMeta)AccessUtil.setAccessible(OBCClass.inventory_CraftMetaSkull.getDeclaredConstructor(new Class[] { NMSClass.NBTTagCompound })).newInstance(new Object[] { baseNBTTag });
    
    AccessUtil.setAccessible(OBCClass.inventory_CraftMetaSkull.getDeclaredField("profile")).set(meta, profile);
    AccessUtil.setAccessible(OBCClass.inventory_CraftMetaSkull.getDeclaredField("profile")).set(newMeta, profile);
    
    return newMeta;
  }
}
