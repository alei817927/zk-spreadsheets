//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.license;

import org.zkoss.zssex.license.AbstractKeyStoreParam;

public class DefaultKeyStoreParam extends AbstractKeyStoreParam {
  private final String alias;
  private final String storePwd;
  private final String keyPwd;

  public DefaultKeyStoreParam(Class var1, String var2, String var3, String var4, String var5) {
    super(var1, var2);
    this.alias = var3;
    this.storePwd = var4;
    this.keyPwd = var5;
  }

  public String getAlias() {
    return this.alias;
  }

  public String getStorePwd() {
    return this.storePwd;
  }

  public String getKeyPwd() {
    return this.keyPwd;
  }
}
