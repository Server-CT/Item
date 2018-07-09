/*    */ package org.inventivetalent.itembuilder.util;
/*    */ 
/*    */

import org.inventivetalent.itembuilder.Reflection;

import java.lang.reflect.Field;

/*    */
/*    */ 
/*    */ 
/*    */ public abstract class NMUClass
/*    */ {
/*    */   private static boolean initialized;
/*    */   public static Class<?> com_mojang_authlib_GameProfile;
/*    */   public static Class<?> com_mojang_authlib_properties_PropertyMap;
/*    */   public static Class<?> com_mojang_authlib_properties_Property;
/*    */   public static Class<?> com_google_common_collect_ForwardingMultimap;
/*    */   
/*    */   static
/*    */   {
/* 17 */     if (!initialized) {
/* 18 */       for (Field f : NMUClass.class.getDeclaredFields()) {
/* 19 */         if (f.getType().equals(Class.class)) {
/*    */           try {
/* 21 */             String name = f.getName().replace("_", ".");
/* 22 */             if (Reflection.getVersion().contains("1_8")) {
/* 23 */               f.set(null, Class.forName(name));
/*    */             } else {
/* 25 */               f.set(null, Class.forName("net.minecraft.util." + name));
/*    */             }
/*    */           } catch (Exception e) {
/* 28 */             e.printStackTrace();
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }