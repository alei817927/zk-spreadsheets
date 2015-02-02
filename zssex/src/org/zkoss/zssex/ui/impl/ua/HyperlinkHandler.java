//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.CellOperationUtil;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.model.Hyperlink;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.api.model.Hyperlink.HyperlinkType;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
import org.zkoss.zssex.ui.dialog.InsertHyperlinkCtrl;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;

public class HyperlinkHandler extends AbstractProtectedHandler {
    public HyperlinkHandler() {
    }

    protected boolean processAction(UserActionContext ctx) {
        final Sheet sheet = ctx.getSheet();
        final AreaRef selection = ctx.getSelection();
        Range range = Ranges.range(sheet, selection.getRow(), selection.getColumn());
        Hyperlink link = range.getCellHyperlink();
        String display = link == null?range.getCellFormatText():link.getLabel();
        String address = link == null?null:link.getAddress();
        HyperlinkType type = link == null?null:link.getType();
        InsertHyperlinkCtrl.show(new EventListener<DialogCallbackEvent>() {
            public void onEvent(DialogCallbackEvent event) throws Exception {
                if("onOK".equals(event.getName())) {
                    String label = (String)event.getData("headerSize");
                    String address = (String)event.getData("address");
                    HyperlinkType type = (HyperlinkType)event.getData("linktype");
                    CellOperationUtil.applyHyperlink(Ranges.range(sheet, selection), type, address, label);
                }

            }
        }, type, address, display);
        return true;
    }
}
