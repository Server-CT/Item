package org.inventivetalent.itembuilder;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.inventivetalent.itembuilder.util.HeadTextureChanger;

public class SkullMetaBuilder
  extends MetaBuilder
{
  public SkullMetaBuilder() {}
  
  public SkullMetaBuilder(ItemStack itemStack)
  {
    super(itemStack);
  }
  
  public SkullMetaBuilder(ItemBuilder itemBuilder)
  {
    super(itemBuilder);
  }
  
  protected SkullMetaBuilder(MetaBuilder builder)
  {
    this.meta = builder.meta;
    this.itemBuilder = builder.itemBuilder;
  }
  
  private SkullMeta getMeta()
  {
    return (SkullMeta)this.meta;
  }
  
  public SkullMetaBuilder fromConfig(ConfigurationSection section, boolean translateColors)
  {
    super.fromConfig(section, translateColors);
    if (section.contains("owner")) {
      withOwner(format(section.getString("owner")));
    }
    if (section.contains("texture")) {
      withTexture(section.getString("texture"));
    }
    return this;
  }
  
  public ConfigurationSection toConfig(ConfigurationSection section)
  {
    section = super.toConfig(section);
    
    section.set("owner", getMeta().getOwner());
    
    return section;
  }
  
  public SkullMetaBuilder withOwner(String owner)
  {
    validateInit();
    getMeta().setOwner(owner);
    return this;
  }
  
  public SkullMetaBuilder withTexture(String texture)
  {
    validateInit();
    getMeta().setOwner("MHF_ItemBuilder");
    try
    {
      HeadTextureChanger.applyTextureToMeta(getMeta(), HeadTextureChanger.createProfile(texture));
    }
    catch (Throwable e)
    {
      throw new RuntimeException("Failed to apply custom texture", e);
    }
    return this;
  }
  
  public SkullMeta build()
  {
    return (SkullMeta)super.build();
  }
}
