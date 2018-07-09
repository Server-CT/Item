package org.inventivetalent.itembuilder;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionMetaBuilder
  extends MetaBuilder
{
  public PotionMetaBuilder() {}
  
  public PotionMetaBuilder(ItemStack itemStack)
  {
    super(itemStack);
  }
  
  public PotionMetaBuilder(ItemBuilder itemBuilder)
  {
    super(itemBuilder);
  }
  
  protected PotionMetaBuilder(MetaBuilder builder)
  {
    this.meta = builder.meta;
    this.itemBuilder = builder.itemBuilder;
  }
  
  private PotionMeta getMeta()
  {
    return (PotionMeta)this.meta;
  }
  
  public PotionMetaBuilder withMainEffect(PotionEffectType mainEffect)
  {
    validateInit();
    getMeta().setMainEffect(mainEffect);
    return this;
  }
  
  public PotionMetaBuilder withCustomEffect(PotionEffect effect, boolean overwrite)
  {
    validateInit();
    getMeta().addCustomEffect(effect, overwrite);
    return this;
  }
  
  public PotionMeta build()
  {
    return (PotionMeta)super.build();
  }
}
