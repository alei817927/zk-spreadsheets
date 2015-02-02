package org.zkoss.zssex.license;

import java.rmi.Remote;

public abstract interface LicenseCreator extends Remote
{
  public abstract byte[] create(LicenseContent paramLicenseContent)
    throws Exception;
}

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.license.LicenseCreator
 * JD-Core Version:    0.6.1
 */