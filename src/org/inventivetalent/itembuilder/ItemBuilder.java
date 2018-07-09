package org.inventivetalent.itembuilder;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder
  extends Formattable
{
  ItemStack itemStack;
  
  public ItemBuilder() {}
  
  public ItemBuilder(ItemStack itemStack)
  {
    this.itemStack = itemStack;
  }
  
  public ItemBuilder(int typeId)
  {
    withTypeId(typeId);
  }
  
  public ItemBuilder(Material material)
  {
    withType(material);
  }
  
  public ItemBuilder(Material material, int amount)
  {
    this(material);
    withAmount(amount);
  }
  
  public ItemBuilder(Material material, int amount, int durability)
  {
    this(material, amount);
    withDurability(durability);
  }
  
  private void initItem(int id)
  {
    if (this.itemStack == null) {
      this.itemStack = new ItemStack(id);
    }
  }
  
  void validateInit()
  {
    if (this.itemStack == null) {
      throw new IllegalStateException("Item is not yet initiated (Missing material)");
    }
  }
  
  public ItemBuilder fromConfig(ConfigurationSection section)
  {
    if (section == null) {
      throw new IllegalArgumentException("section cannot be null");
    }
    if (section.contains("type"))
    {
      if (section.isInt("type")) {
        this.itemStack = new ItemStack(section.getInt("type"));
      }
      if (section.isString("type"))
      {
        Material material = null;
        try
        {
          material = Material.valueOf(section.getString("type").toUpperCase());
        }
        catch (Exception localException) {}
        if (material != null) {
          this.itemStack = new ItemStack(material);
        }
      }
    }
    if ((section.contains("amount")) && 
      (section.isInt("amount"))) {
      withAmount(section.getInt("amount"));
    }
    if ((section.contains("durability")) && 
      (section.isInt("durability"))) {
      withDurability(section.getInt("durability"));
    }
    if ((section.contains("meta")) && (section.isConfigurationSection("meta")))
    {
      ConfigurationSection meta = section.getConfigurationSection("meta");
      
      MetaBuilder metaBuilder = buildMeta();
      metaBuilder.withFormat(this.formatMap);
      metaBuilder.fromConfig(meta);
      withMeta(metaBuilder);
    }
    return this;
  }
  
  public ConfigurationSection toConfig(ConfigurationSection section)
  {
    if (section == null) {
      throw new IllegalArgumentException("section cannot be null");
    }
    section.set("type", this.itemStack.getType().name());
    section.set("amount", Integer.valueOf(this.itemStack.getAmount()));
    section.set("durability", Short.valueOf(this.itemStack.getDurability()));
    
    ConfigurationSection metaSection = section.getConfigurationSection("meta");
    if (metaSection == null) {
      metaSection = section.createSection("meta");
    }
    MetaBuilder metaBuilder = buildMeta();
    section.set("meta", metaBuilder.toConfig(metaSection));
    
    return section;
  }
  
  public ItemBuilder withFormat(String key, String value)
  {
    super.withFormat(key, value);
    return this;
  }
  
  public ItemBuilder withTypeId(int id)
  {
    initItem(id);
    return this;
  }
  
  public ItemBuilder withType(Material material)
  {
    initItem(material.getId());
    return this;
  }
  
  public ItemBuilder withAmount(int amount)
  {
    validateInit();
    this.itemStack.setAmount(amount);
    return this;
  }
  
  public ItemBuilder withDurability(int durability)
  {
    validateInit();
    this.itemStack.setDurability((short)durability);
    return this;
  }
  
  public ItemBuilder withData(MaterialData data)
  {
    validateInit();
    this.itemStack.setData(data);
    return this;
  }
  
  public MetaBuilder buildMeta()
  {
    return new MetaBuilder(this).convert(this.itemStack.getItemMeta().getClass());
  }
  
  public <T extends MetaBuilder> MetaBuilder buildMeta(Class<T> metaClazz)
  {
    return buildMeta().convertBuilder(metaClazz);
  }
  
  public ItemBuilder withMeta(ItemMeta meta)
  {
    validateInit();
    this.itemStack.setItemMeta(meta);
    return this;
  }
  
  public ItemBuilder withMeta(MetaBuilder meta)
  {
    validateInit();
    this.itemStack.setItemMeta(meta.build());
    return this;
  }
  
  public ItemStack build()
  {
    validateInit();
    return this.itemStack;
  }
}
