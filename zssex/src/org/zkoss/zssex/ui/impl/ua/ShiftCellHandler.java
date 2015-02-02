//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.util.resource.Labels;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
import org.zkoss.zss.ui.impl.undo.ShiftCellAction;
import org.zkoss.zss.ui.sys.UndoableActionManager;

public class ShiftCellHandler extends AbstractProtectedHandler {
    public ShiftCellHandler() {
    }

    protected boolean processAction(UserActionContext ctx) {
        Sheet sheet = ctx.getSheet();
        AreaRef selection = ctx.getSelection();
        AreaRef src = (AreaRef)ctx.getData("source");
        int nRow = selection.getRow() - src.getRow();
        int nCol = selection.getColumn() - src.getColumn();
        UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
        uam.doAction(new ShiftCellAction(Labels.getLabel("zss.undo.moveCell"), sheet, src.getRow(), src.getColumn(), src.getLastRow(), src.getLastColumn(), nRow, nCol));
        return true;
    }
}
