package org.inventivetalent.itembuilder;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.inventivetalent.itembuilder.util.EnchantmentGlow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetaBuilder
  extends Formattable
{
  protected ItemBuilder itemBuilder;
  protected ItemMeta meta;
  
  public MetaBuilder() {}
  
  public MetaBuilder(ItemStack itemStack)
  {
    forItem(itemStack);
  }
  
  public MetaBuilder(ItemBuilder itemBuilder)
  {
    forItem(itemBuilder);
  }
  
  protected void validateInit()
  {
    if (this.meta == null) {
      throw new IllegalStateException("Meta not yet initiated");
    }
  }
  
  public MetaBuilder fromConfig(ConfigurationSection section)
  {
    return fromConfig(section, true);
  }
  
  public MetaBuilder fromConfig(ConfigurationSection section, boolean translateColors)
  {
    if (section == null) {
      throw new IllegalArgumentException("section cannot be null");
    }
    if (section.contains("display")) {
      withDisplayName(format(translateColors(section.getString("display"), translateColors)));
    }
    if (section.contains("unbreak")) {
      this.meta.spigot().setUnbreakable(section.getBoolean("unbreak", false));
    }
    if (section.contains("lore")) {
      if ((translateColors) || (!this.formatMap.isEmpty()))
      {
        List<String> translated = new ArrayList();
        for (String s : section.getStringList("lore")) {
          translated.add(format(translateColors(s, translateColors)));
        }
        withLore(translated);
      }
      else
      {
        withLore(section.getStringList("lore"));
      }
    }
    if (section.contains("flags"))
    {
      List<String> flagStrings = section.getStringList("flags");
      for (String s : flagStrings)
      {
        ItemFlag flag = null;
        try
        {
          flag = ItemFlag.valueOf(s.toUpperCase());
        }
        catch (Exception localException) {}
        if (flag != null) {
          withItemFlags(new ItemFlag[] { flag });
        }
      }
    }
    if (section.contains("enchants"))
    {
      List<String> enchantStrings = section.getStringList("enchants");
      for (String s : enchantStrings)
      {
        String enchantName = "";
        String enchantOptions = "";
        
        int level = 1;
        boolean force = false;
        if (s.contains("x"))
        {
          String[] split = s.split("x");
          enchantName = split[0];
          enchantOptions = split[1];
        }
        if ((force = enchantName.contains("-f"))) {
          enchantName = enchantName.split("-f")[0];
        }
        if (enchantOptions.contains("-f"))
        {
          force = true;
          try
          {
            level = Integer.parseInt(enchantOptions.split("-f")[0]);
          }
          catch (Exception localException1) {}
        }
        Enchantment enchantment = null;
        try
        {
          enchantment = Enchantment.getByName(enchantName.toUpperCase());
        }
        catch (Exception localException2) {}
        if (enchantment != null) {
          withEnchant(enchantment, level, force);
        }
      }
    }
    return this;
  }
  
  public ConfigurationSection toConfig(ConfigurationSection section)
  {
    if (section == null) {
      throw new IllegalArgumentException("section cannot be null");
    }
    section.set("display", this.meta.getDisplayName());
    section.set("lore", this.meta.getLore() != null ? new ArrayList(this.meta.getLore()) : new ArrayList());
    section.set("flags", new ArrayList() {});
    section.set("enchants", new ArrayList() {});
    return section;
  }
  
  public MetaBuilder withFormat(String key, String value)
  {
    super.withFormat(key, value);
    return this;
  }
  
  public MetaBuilder forItem(ItemStack itemStack)
  {
    this.meta = itemStack.getItemMeta();
    return this;
  }
  
  public MetaBuilder forItem(ItemBuilder itemBuilder)
  {
    itemBuilder.validateInit();
    this.meta = itemBuilder.itemStack.getItemMeta();
    this.itemBuilder = itemBuilder;
    return this;
  }
  
  public MetaBuilder withDisplayName(String displayName)
  {
    validateInit();
    this.meta.setDisplayName(displayName);
    return this;
  }
  
  public MetaBuilder withLore(List<String> lore)
  {
    validateInit();
    this.meta.setLore(lore);
    return this;
  }
  
  public MetaBuilder withLore(String... lore)
  {
    validateInit();
    List<String> currentLore = this.meta.getLore();
    if (currentLore == null) {
      currentLore = new ArrayList();
    }
    currentLore.addAll(Arrays.asList(lore));
    return withLore(currentLore);
  }
  
  public MetaBuilder withItemFlags(ItemFlag... flags)
  {
    validateInit();
    this.meta.addItemFlags(flags);
    return this;
  }
  
  public MetaBuilder withEnchant(Enchantment enchant, int level, boolean force)
  {
    validateInit();
    this.meta.addEnchant(enchant, level, force);
    return this;
  }
  
  public MetaBuilder unbreakable(boolean unbreakable)
  {
    validateInit();
    this.meta.spigot().setUnbreakable(unbreakable);
    return this;
  }
  
  public MetaBuilder unbreakable()
  {
    return unbreakable(true);
  }
  
  public MetaBuilder glow()
  {
    withEnchant(EnchantmentGlow.inject(), 1, true);
    return this;
  }
  
  public <T extends MetaBuilder> MetaBuilder convert(Class<? extends ItemMeta> metaClazz)
    throws IllegalArgumentException
  {
    if ((ItemMeta.class.equals(metaClazz)) || (this.CraftMetaItem.equals(metaClazz))) {
      return this;
    }
    if (SkullMeta.class.isAssignableFrom(metaClazz)) {
      return new SkullMetaBuilder(this);
    }
    if (BannerMeta.class.isAssignableFrom(metaClazz)) {
      return new BannerMetaBuilder(this);
    }
    if (PotionMeta.class.isAssignableFrom(metaClazz)) {
      return new PotionMetaBuilder(this);
    }
    if (MapMeta.class.isAssignableFrom(metaClazz)) {
      return new MapMetaBuilder(this);
    }
    if (BookMeta.class.isAssignableFrom(metaClazz)) {
      return new BookMetaBuilder(this);
    }
    if (LeatherArmorMeta.class.isAssignableFrom(metaClazz)) {
      return new LeatherArmorMetaBuilder(this);
    }
    if (FireworkMeta.class.isAssignableFrom(metaClazz)) {
      return new FireworkMetaBuilder(this);
    }
    if (FireworkEffectMeta.class.isAssignableFrom(metaClazz)) {
      return new FireworkEffectMetaBuilder(this);
    }
    throw new IllegalArgumentException("No meta builder found for class " + metaClazz.getName());
  }
  
  public <T extends MetaBuilder> MetaBuilder convertBuilder(Class<T> metaClazz)
    throws IllegalArgumentException
  {
    if (MetaBuilder.class.equals(metaClazz)) {
      return this;
    }
    try
    {
      return (MetaBuilder)metaClazz.getDeclaredConstructor(new Class[] { MetaBuilder.class }).newInstance(new Object[] { this });
    }
    catch (Exception e)
    {
      e.printStackTrace();
      
      throw new IllegalArgumentException("No meta builder found for class " + metaClazz.getName());
    }
  }
  
  public ItemBuilder item()
  {
    if (this.itemBuilder != null) {
      this.itemBuilder.withMeta(this);
    }
    return this.itemBuilder;
  }
  
  public ItemMeta build()
  {
    validateInit();
    return this.meta;
  }
  
  protected String translateColors(String string, boolean translate)
  {
    return translate ? ChatColor.translateAlternateColorCodes('&', string) : string;
  }
  
  private final Class<?> CraftMetaItem = Reflection.getOBCClass("inventory.CraftMetaItem");
}
