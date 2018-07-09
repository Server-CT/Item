package org.inventivetalent.itembuilder;

import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkMetaBuilder
  extends MetaBuilder
{
  public FireworkMetaBuilder() {}
  
  public FireworkMetaBuilder(ItemStack itemStack)
  {
    super(itemStack);
  }
  
  public FireworkMetaBuilder(ItemBuilder itemBuilder)
  {
    super(itemBuilder);
  }
  
  protected FireworkMetaBuilder(MetaBuilder builder)
  {
    this.meta = builder.meta;
    this.itemBuilder = builder.itemBuilder;
  }
  
  private FireworkMeta getMeta()
  {
    return (FireworkMeta)this.meta;
  }
  
  public FireworkMetaBuilder withEffect(FireworkEffect... effects)
  {
    validateInit();
    getMeta().addEffects(effects);
    return this;
  }
  
  public FireworkMetaBuilder withEffects(Iterable<FireworkEffect> effects)
  {
    validateInit();
    getMeta().addEffects(effects);
    return this;
  }
  
  public FireworkMetaBuilder withPower(int power)
  {
    validateInit();
    getMeta().setPower(power);
    return this;
  }
  
  public FireworkMeta build()
  {
    return (FireworkMeta)super.build();
  }
}
