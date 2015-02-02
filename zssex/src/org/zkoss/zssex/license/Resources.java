package org.zkoss.zssex.license;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import org.zkoss.zssex.util.ObfuscatedString;

class Resources
{
  private static final String CLASS_NAME = new ObfuscatedString(new long[] { -1963633268832885471L, 676417691649839214L, -5177292160997995086L, 3369595814102260271L, -6474921594339001576L, -3632589461156125613L }).toString();
  private static final ResourceBundle resources = ResourceBundle.getBundle(CLASS_NAME);

  public static String getString(String paramString)
  {
    return resources.getString(paramString);
  }

  public static String getString(String paramString, Object[] paramArrayOfObject)
  {
    return MessageFormat.format(getString(paramString), paramArrayOfObject);
  }

  public static String getString(String paramString, Object paramObject)
  {
    return MessageFormat.format(getString(paramString), new Object[] { paramObject });
  }
}

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.license.Resources
 * JD-Core Version:    0.6.1
 */