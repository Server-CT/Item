package org.inventivetalent.itembuilder;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.ArrayList;
import java.util.List;

public class BannerMetaBuilder
  extends MetaBuilder
{
  public BannerMetaBuilder() {}
  
  public BannerMetaBuilder(ItemStack itemStack)
  {
    super(itemStack);
  }
  
  public BannerMetaBuilder(ItemBuilder itemBuilder)
  {
    super(itemBuilder);
  }
  
  protected BannerMetaBuilder(MetaBuilder builder)
  {
    this.meta = builder.meta;
    this.itemBuilder = builder.itemBuilder;
  }
  
  private BannerMeta getMeta()
  {
    return (BannerMeta)this.meta;
  }
  
  public BannerMetaBuilder fromConfig(ConfigurationSection section, boolean translateColors)
  {
    super.fromConfig(section, translateColors);
    if (section.contains("patterns"))
    {
      List<String> patternStrings = section.getStringList("patterns");
      for (String s : patternStrings)
      {
        String[] split = s.split("-");
        if (split.length == 2)
        {
          String patternName = split[0];
          String colorName = split[1];
          
          PatternType patternType = null;
          try
          {
            patternType = PatternType.valueOf(patternName.toUpperCase());
          }
          catch (Exception localException) {}
          DyeColor color = null;
          try
          {
            color = DyeColor.valueOf(colorName);
          }
          catch (Exception localException1) {}
          if ((patternType != null) && (color != null))
          {
            Pattern pattern = new Pattern(color, patternType);
            
            withPattern(new Pattern[] { pattern });
          }
        }
      }
    }
    return this;
  }
  
  public ConfigurationSection toConfig(ConfigurationSection section)
  {
    section = super.toConfig(section);
    
    section.set("patterns", new ArrayList() {});
    return section;
  }
  
  public BannerMetaBuilder withBaseColor(DyeColor baseColor)
  {
    validateInit();
    getMeta().setBaseColor(baseColor);
    return this;
  }
  
  public BannerMetaBuilder withPattern(Pattern... patterns)
  {
    validateInit();
    for (Pattern pattern : patterns) {
      getMeta().addPattern(pattern);
    }
    return this;
  }
  
  public BannerMeta build()
  {
    return (BannerMeta)super.build();
  }
}
