//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.license;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.zkoss.zssex.license.KeyStoreParam;

public abstract class AbstractKeyStoreParam implements KeyStoreParam {
  private final Class clazz;
  private final String resource;

  protected AbstractKeyStoreParam(Class var1, String var2) {
    this.clazz = var1;
    this.resource = var2;
  }

  public InputStream getStream() throws IOException {
    InputStream var1 = this.clazz.getResourceAsStream(this.resource);
    if(var1 == null) {
      throw new FileNotFoundException(this.resource);
    } else {
      return var1;
    }
  }

  public int hashCode() {
    byte var1 = 5;
    int var2 = 97 * var1 + (this.clazz != null?this.clazz.hashCode():0);
    var2 = 97 * var2 + (this.resource != null?this.resource.hashCode():0);
    return var2;
  }

  public boolean equals(Object var1) {
    if(!(var1 instanceof AbstractKeyStoreParam)) {
      return false;
    } else {
      AbstractKeyStoreParam var2 = (AbstractKeyStoreParam)var1;
      return this.clazz.getResource(this.resource).equals(var2.clazz.getResource(var2.resource)) && this.getAlias().equals(var2.getAlias());
    }
  }
}
