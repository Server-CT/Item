package org.inventivetalent.itembuilder;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

public class MapMetaBuilder
  extends MetaBuilder
{
  public MapMetaBuilder() {}
  
  public MapMetaBuilder(ItemStack itemStack)
  {
    super(itemStack);
  }
  
  public MapMetaBuilder(ItemBuilder itemBuilder)
  {
    super(itemBuilder);
  }
  
  protected MapMetaBuilder(MetaBuilder builder)
  {
    this.meta = builder.meta;
    this.itemBuilder = builder.itemBuilder;
  }
  
  private MapMeta getMeta()
  {
    return (MapMeta)this.meta;
  }
  
  public MapMetaBuilder withScaling(boolean scaling)
  {
    validateInit();
    getMeta().setScaling(scaling);
    return this;
  }
  
  public MapMetaBuilder withScaling()
  {
    return withScaling(true);
  }
  
  public MapMeta build()
  {
    return (MapMeta)super.build();
  }
}
