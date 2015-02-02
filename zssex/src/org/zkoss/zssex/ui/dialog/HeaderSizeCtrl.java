//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.dialog;

import java.util.Map;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;
import org.zkoss.zssex.ui.dialog.impl.DialogCtrlBase;
import org.zkoss.zssex.ui.dialog.impl.DialogCtrlBase.Entry;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Window;

public class HeaderSizeCtrl extends DialogCtrlBase {
    private static final String URI = "~./zssex/dlg/headerSize.zul";
    public static final String ARG_HEADER_TYPE = "headerType";
    private HeaderType headerType;
    public static final String ARG_HEADER_SIZE = "headerSize";
    private Integer size;
    @Wire
    private Label title;
    @Wire
    private Intbox headerSize;

    public HeaderSizeCtrl() {
    }

    public static void show(EventListener<DialogCallbackEvent> callback, HeaderType headrType, Integer headerSize) {
        Map arg = newArg(callback);
        arg.put("headerType", headrType);
        arg.put("headerSize", headerSize);
        Window comp = (Window)Executions.createComponents("~./zssex/dlg/headerSize.zul", (Component)null, arg);
        comp.doModal();
    }

    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        Map arg = Executions.getCurrent().getArg();
        this.headerType = (HeaderType)arg.get("headerType");
        this.initTitle(this.headerType);
        this.size = (Integer)arg.get("headerSize");
        this.headerSize.setValue(this.size);
    }

    private void initTitle(HeaderType target) {
        String val = null;
        if(target == HeaderType.ROW) {
            val = Labels.getLabel("zssex.dlg.header.rowHeight");
        } else {
            val = Labels.getLabel("zssex.dlg.header.columnWidth");
        }

        ((Window)this.getSelf()).setTitle(val);
        this.title.setValue(val + ": ");
    }

    @Listen("onOK=window; onClick=#okBtn")
    public void onOK() {
        int size = this.headerSize.getValue().intValue();
        this.detach();
        this.postCallback("onOK", newMap(new Entry[]{newEntry("headerType", this.headerType), newEntry("headerSize", Integer.valueOf(size))}));
    }

    @Listen("onCancel=window; onClick=#cancelBtn")
    public void onCancel() {
        this.detach();
        this.postCallback("onCancel", (Map)null);
    }

    public static enum HeaderType {
        ROW,
        COLUMN;

        private HeaderType() {
        }
    }
}
