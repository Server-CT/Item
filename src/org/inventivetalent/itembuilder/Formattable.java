package org.inventivetalent.itembuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Formattable
{
  protected Map<String, String> formatMap = new HashMap();
  
  protected Formattable withFormat(Map<String, String> map)
  {
    this.formatMap.putAll(map);
    return this;
  }
  
  public Formattable withFormat(String key, String value)
  {
    this.formatMap.put(key, value);
    return this;
  }
  
  protected String format(String string)
  {
    String formatted = string;
    for (Map.Entry<String, String> entry : this.formatMap.entrySet()) {
      formatted = formatted.replace((CharSequence)entry.getKey(), (CharSequence)entry.getValue());
    }
    return formatted;
  }
}
