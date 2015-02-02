//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.dialog.impl;

import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;

public class DialogCallbackEvent extends Event {
    public DialogCallbackEvent(String name, Map<String, Object> data) {
        super(name, (Component)null, data);
    }

    public Map<String, Object> getData() {
        return (Map)super.getData();
    }

    public Object getData(String name) {
        Map data = this.getData();
        return data != null?data.get(name):null;
    }
}
