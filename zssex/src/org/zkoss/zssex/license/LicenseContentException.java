package org.zkoss.zssex.license;

public class LicenseContentException extends Exception
{
  private static final long serialVersionUID = 1L;

  public LicenseContentException(String paramString)
  {
    super(paramString);
  }

  public String getLocalizedMessage()
  {
    return Resources.getString(super.getMessage());
  }
}

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.license.LicenseContentException
 * JD-Core Version:    0.6.1
 */