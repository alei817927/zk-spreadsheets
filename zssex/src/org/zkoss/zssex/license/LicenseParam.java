package org.zkoss.zssex.license;

import java.util.prefs.Preferences;

public abstract interface LicenseParam
{
  public abstract String getSubject();

  public abstract Preferences getPreferences();

  public abstract KeyStoreParam getKeyStoreParam();

  public abstract CipherParam getCipherParam();
}

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.license.LicenseParam
 * JD-Core Version:    0.6.1
 */