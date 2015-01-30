package com.makenv.sheets.ui.dlg;

import org.zkoss.zk.ui.event.Event;

import java.util.Map;

/**
 * Created by alei on 2015/1/27.
 */
public class DlgCallbackEvent extends Event {
    public DlgCallbackEvent(String name, Map<String, Object> data) {
        super(name, null, data);
    }

    public Map<String, Object> getData() {
        return (Map<String, Object>) super.getData();
    }

    public Object getData(String name) {
        Map<String, Object> data = getData();
        if (data != null) {
            return data.get(name);
        }
        return null;
    }
}
