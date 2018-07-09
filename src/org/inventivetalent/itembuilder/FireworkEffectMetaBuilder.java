package org.inventivetalent.itembuilder;

import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;

public class FireworkEffectMetaBuilder
  extends MetaBuilder
{
  public FireworkEffectMetaBuilder() {}
  
  public FireworkEffectMetaBuilder(ItemStack itemStack)
  {
    super(itemStack);
  }
  
  public FireworkEffectMetaBuilder(ItemBuilder itemBuilder)
  {
    super(itemBuilder);
  }
  
  protected FireworkEffectMetaBuilder(MetaBuilder builder)
  {
    this.meta = builder.meta;
    this.itemBuilder = builder.itemBuilder;
  }
  
  private FireworkEffectMeta getMeta()
  {
    return (FireworkEffectMeta)this.meta;
  }
  
  public FireworkEffectMetaBuilder withEffect(FireworkEffect effect)
  {
    validateInit();
    getMeta().setEffect(effect);
    return this;
  }
  
  public FireworkEffectMeta build()
  {
    return (FireworkEffectMeta)super.build();
  }
}
