package org.inventivetalent.itembuilder;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.inventivetalent.itembuilder.util.AccessUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class BookMetaBuilder
  extends MetaBuilder
{
  public BookMetaBuilder() {}
  
  public BookMetaBuilder(ItemStack itemStack)
  {
    super(itemStack);
  }
  
  public BookMetaBuilder(ItemBuilder itemBuilder)
  {
    super(itemBuilder);
  }
  
  protected BookMetaBuilder(MetaBuilder builder)
  {
    this.meta = builder.meta;
    this.itemBuilder = builder.itemBuilder;
  }
  
  private BookMeta getMeta()
  {
    return (BookMeta)this.meta;
  }
  
  public BookMetaBuilder fromConfig(ConfigurationSection section, boolean translateColors)
  {
    super.fromConfig(section, translateColors);
    if (section.contains("author")) {
      withAuthor(format(translateColors(section.getString("author"), translateColors)));
    }
    if (section.contains("title")) {
      withTitle(format(translateColors(section.getString("title"), translateColors)));
    }
    if (section.contains("pages"))
    {
      List<String> pageStrings = section.getStringList("pages");
      if ((translateColors) || (!this.formatMap.isEmpty()))
      {
        List<String> translated = new ArrayList();
        for (String s : pageStrings) {
          translated.add(format(translateColors(s, translateColors)));
        }
        withPages(translated);
      }
      else
      {
        withPages(pageStrings);
      }
    }
    return this;
  }
  
  public ConfigurationSection toConfig(ConfigurationSection section)
  {
    section = super.toConfig(section);
    
    section.set("author", getMeta().getAuthor());
    section.set("title", getMeta().getTitle());
    section.set("pages", new ArrayList() {});
    return section;
  }
  
  public BookMetaBuilder withTitle(String title)
  {
    validateInit();
    getMeta().setTitle(title);
    return this;
  }
  
  public BookMetaBuilder withAuthor(String author)
  {
    validateInit();
    getMeta().setAuthor(author);
    return this;
  }
  
  public BookMetaBuilder withPage(int page, String content)
  {
    validateInit();
    getMeta().setPage(page, content);
    return this;
  }
  
  public BookMetaBuilder withPage(int page, BaseComponent component)
  {
    validateInit();
    try
    {
      List<Object> pages = (List)this.CraftMetaBook_pages.get(getMeta());
      pages.set(page - 1, deseriliazeMessage(ComponentSerializer.toString(component)));
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
    return this;
  }
  
  public BookMetaBuilder withPages(List<String> pages)
  {
    validateInit();
    getMeta().setPages(pages);
    return this;
  }
  
  public BookMetaBuilder withPage(String... pages)
  {
    validateInit();
    getMeta().addPage(pages);
    return this;
  }
  
  public BookMetaBuilder withPage(BaseComponent... components)
  {
    validateInit();
    try
    {
      List<Object> pages = (List)this.CraftMetaBook_pages.get(getMeta());
      for (BaseComponent component : components) {
        pages.add(deseriliazeMessage(ComponentSerializer.toString(component)));
      }
    }
    catch (Exception e)
    {
      throw new RuntimeException(e);
    }
    return this;
  }
  
  public BookMetaBuilder withPages(String... pages)
  {
    validateInit();
    getMeta().setPages(pages);
    return this;
  }
  
  public BookMeta build()
  {
    return (BookMeta)super.build();
  }
  
  String serializeMessage(Object component)
  {
    String serialized = "{}";
    try
    {
      serialized = (String)AccessUtil.setAccessible(Reflection.getMethod(this.ChatSerializer, "a", new Class[] { this.IChatBaseComponent })).invoke(null, new Object[] { component });
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return serialized;
  }
  
  Object deseriliazeMessage(String serialized)
  {
    try
    {
      return AccessUtil.setAccessible(Reflection.getMethod(this.ChatSerializer, "a", new Class[] { String.class })).invoke(null, new Object[] { serialized });
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  Class<?> CraftMetaBook = Reflection.getOBCClass("inventory.CraftMetaBook");
  Class<?> IChatBaseComponent = Reflection.getNMSClass("IChatBaseComponent");
  Class<?> ChatSerializer = Reflection.getNMSClass("IChatBaseComponent$ChatSerializer");
  Field CraftMetaBook_pages = Reflection.getField(this.CraftMetaBook, "pages");
}
