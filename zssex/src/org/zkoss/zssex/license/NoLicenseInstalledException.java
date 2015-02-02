package org.zkoss.zssex.license;

import org.zkoss.zssex.util.ObfuscatedString;

public class NoLicenseInstalledException extends Exception
{
  private static final long serialVersionUID = 1L;
  private static final String EXC_NO_LICENSE_INSTALLED = new ObfuscatedString(new long[] { 5636850220590995934L, -798521115123526970L, 3054112192777193179L, 881750348384376277L }).toString();

  public NoLicenseInstalledException(String paramString)
  {
    super(paramString);
  }

  public String getLocalizedMessage()
  {
    return Resources.getString(EXC_NO_LICENSE_INSTALLED, super.getMessage());
  }
}

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.license.NoLicenseInstalledException
 * JD-Core Version:    0.6.1
 */