//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.init;

import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zssex.rt.Runtime;

public class WebAppInit implements org.zkoss.zk.ui.util.WebAppInit {
  public WebAppInit() {
  }

  public void init(WebApp var1) throws Exception {
    if(!Runtime.init(var1, true)) {
      String var2 = Runtime.WARNING_EVALUATION;
      Log var3 = Log.lookup("global");
      if(var3.errorable()) {
        var3.error(var2);
      } else {
        System.err.println(var2);
      }
    }

  }
}
