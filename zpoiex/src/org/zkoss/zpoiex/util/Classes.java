//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zpoiex.util;

import java.lang.reflect.Field;

public class Classes {
  public Classes() {
  }

  public static final Field getAnyField(Class<?> cls, String name) throws NoSuchFieldException {
    while(true) {
      try {
        return cls.getDeclaredField(name);
      } catch (NoSuchFieldException var3) {
        cls = cls.getSuperclass();
        if(cls == null) {
          throw var3;
        }
      }
    }
  }
}
