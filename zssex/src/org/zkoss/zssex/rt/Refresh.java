//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.rt;

import java.util.List;
import java.util.Map;

public interface Refresh {
  Object refresh(List var1);

  boolean checkVersion(Map var1);

  boolean checkPackage(Map var1);

  boolean isTargetSubject(Map var1);
}
