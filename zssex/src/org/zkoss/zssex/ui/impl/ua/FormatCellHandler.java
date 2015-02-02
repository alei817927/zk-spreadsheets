//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.CellOperationUtil;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
import org.zkoss.zss.ui.impl.undo.CellStyleAction;
import org.zkoss.zss.ui.sys.UndoableActionManager;
import org.zkoss.zssex.ui.dialog.FormatNumberCtrl;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;

public class FormatCellHandler extends AbstractProtectedHandler {
    public FormatCellHandler() {
    }

    protected boolean processAction(final UserActionContext ctx) {
        final Sheet sheet = ctx.getSheet();
        final AreaRef selection = ctx.getSelection();
        Range range = Ranges.range(sheet, selection.getRow(), selection.getColumn());
        String format = range.getCellFormatText();
        FormatNumberCtrl.show(new EventListener<DialogCallbackEvent>() {
            public void onEvent(DialogCallbackEvent event) throws Exception {
                if("onOK".equals(event.getName())) {
                    String format = (String)event.getData("formatCode");
                    UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
                    uam.doAction(new CellStyleAction(Labels.getLabel("zss.undo.cellFormat"), sheet, selection.getRow(), selection.getColumn(), selection.getLastRow(), selection.getLastColumn(), CellOperationUtil.getDataFormatApplier(format)));
                }

            }
        }, format);
        return true;
    }
}
