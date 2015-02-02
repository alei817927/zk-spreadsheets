package org.zkoss.zssex.license;

import java.io.IOException;
import java.io.InputStream;

public abstract interface KeyStoreParam
{
  public abstract InputStream getStream()
    throws IOException;

  public abstract String getAlias();

  public abstract String getStorePwd();

  public abstract String getKeyPwd();
}

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.license.KeyStoreParam
 * JD-Core Version:    0.6.1
 */