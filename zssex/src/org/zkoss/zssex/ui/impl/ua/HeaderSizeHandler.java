//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
import org.zkoss.zss.ui.impl.undo.ResizeHeaderAction;
import org.zkoss.zss.ui.impl.undo.ResizeHeaderAction.Type;
import org.zkoss.zss.ui.sys.UndoableActionManager;
import org.zkoss.zssex.ui.dialog.HeaderSizeCtrl;
import org.zkoss.zssex.ui.dialog.HeaderSizeCtrl.HeaderType;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;

public class HeaderSizeHandler extends AbstractProtectedHandler {
    private final HeaderType _headerType;

    public HeaderSizeHandler(HeaderType headerType) {
        this._headerType = headerType;
    }

    protected boolean processAction(final UserActionContext ctx) {
        final Sheet sheet = ctx.getSheet();
        final AreaRef selection = ctx.getSelection();
        int size = this._headerType == HeaderType.COLUMN?sheet.getColumnWidth(selection.getColumn()):sheet.getRowHeight(selection.getRow());
        HeaderSizeCtrl.show(new EventListener<DialogCallbackEvent>() {
            public void onEvent(DialogCallbackEvent event) throws Exception {
                if("onOK".equals(event.getName())) {
                    int size = ((Integer)event.getData("headerSize")).intValue();
                    String label = HeaderSizeHandler.this._headerType == HeaderType.COLUMN?Labels.getLabel("zss.undo.columnSize"):Labels.getLabel("zss.undo.rowSize");
                    Type action = HeaderSizeHandler.this._headerType == HeaderType.COLUMN?Type.COLUMN:Type.ROW;
                    UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
                    uam.doAction(new ResizeHeaderAction(label, sheet, selection.getRow(), selection.getColumn(), selection.getLastRow(), selection.getLastColumn(), action, size, true));
                }

            }
        }, this._headerType, Integer.valueOf(size));
        return true;
    }
}
