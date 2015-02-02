package org.zkoss.zssex.license;

import java.security.GeneralSecurityException;

public class LicenseNotaryException extends GeneralSecurityException
{
  private static final long serialVersionUID = 1L;
  private String alias;

  public LicenseNotaryException(String paramString1, String paramString2)
  {
    super(paramString1);
    this.alias = paramString2;
  }

  public String getLocalizedMessage()
  {
    return Resources.getString(super.getMessage(), this.alias);
  }
}

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.license.LicenseNotaryException
 * JD-Core Version:    0.6.1
 */