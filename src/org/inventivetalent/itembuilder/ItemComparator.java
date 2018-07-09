package org.inventivetalent.itembuilder;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.Comparator;

public class ItemComparator
  implements Comparator<ItemStack>
{
  private CompareMode mode;
  private boolean ignoreAmount;
  private boolean ignoreDurability;
  private boolean ignoreMeta;
  
  public ItemComparator()
  {
    this.mode = CompareMode.TYPE;
    this.ignoreAmount = false;
    this.ignoreDurability = false;
    this.ignoreMeta = false;
  }
  
  public ItemComparator(CompareMode mode)
  {
    this();
    this.mode = mode;
  }
  
  public ItemComparator(CompareMode mode, boolean ignoreAmount)
  {
    this(mode);
    this.ignoreAmount = ignoreAmount;
  }
  
  public ItemComparator(CompareMode mode, boolean ignoreAmount, boolean ignoreDurability)
  {
    this(mode, ignoreAmount);
    this.ignoreDurability = ignoreDurability;
  }
  
  public ItemComparator(CompareMode mode, boolean ignoreAmount, boolean ignoreDurability, boolean ignoreMeta)
  {
    this(mode, ignoreAmount, ignoreDurability);
    this.ignoreMeta = ignoreMeta;
  }
  
  public int compare(ItemStack a, ItemStack b)
  {
    if (equals(a, b)) {
      return 0;
    }
    if (this.mode != null) {
      switch (this.mode)
      {
      case TYPE: 
        return b.getTypeId() > b.getTypeId() ? 1 : -1;
      case AMOUNT: 
        if (this.ignoreAmount) {
          return 0;
        }
        return b.getAmount() > a.getAmount() ? 1 : -1;
      case DURABILITY: 
        if (this.ignoreDurability) {
          return 0;
        }
        return b.getDurability() > a.getDurability() ? 1 : -1;
      case DISPLAY_NAME: 
        if (this.ignoreMeta) {
          return 0;
        }
        if ((a.hasItemMeta()) && (b.hasItemMeta()) && 
          (a.getItemMeta().getDisplayName() != null)) {
          return a.getItemMeta().getDisplayName().compareTo(b.getItemMeta().getDisplayName());
        }
        break;
      }
    }
    return 0;
  }
  
  public boolean equals(ItemStack a, ItemStack b)
  {
    if (a.getType() != b.getType()) {
      return false;
    }
    if ((!this.ignoreAmount) && 
      (a.getAmount() != b.getAmount())) {
      return false;
    }
    if ((!this.ignoreDurability) && 
      (a.getDurability() != b.getDurability())) {
      return false;
    }
    if ((!this.ignoreMeta) && 
      (!Bukkit.getItemFactory().equals(a.getItemMeta(), b.getItemMeta()))) {
      return false;
    }
    return true;
  }
  
  public static enum CompareMode
  {
    TYPE,  AMOUNT,  DURABILITY,  DISPLAY_NAME;
    
    private CompareMode() {}
  }
}
