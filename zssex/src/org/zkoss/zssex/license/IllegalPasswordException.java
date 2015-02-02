package org.zkoss.zssex.license;

import org.zkoss.zssex.util.ObfuscatedString;

public class IllegalPasswordException extends IllegalArgumentException
{
  private static final long serialVersionUID = 1L;

  public String getLocalizedMessage()
  {
    return Resources.getString(new ObfuscatedString(new long[] { -6087108248892165543L, 4668112285741627657L, -1028382439244694792L, -1679939343705678708L }).toString());
  }
}

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.license.IllegalPasswordException
 * JD-Core Version:    0.6.1
 */