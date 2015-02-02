package org.zkoss.zssex.license;

import java.rmi.Remote;

public abstract interface LicenseVerifier extends Remote
{
  public abstract LicenseContent verify(byte[] paramArrayOfByte)
    throws Exception;
}

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.license.LicenseVerifier
 * JD-Core Version:    0.6.1
 */