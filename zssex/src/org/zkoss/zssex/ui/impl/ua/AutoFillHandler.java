//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.util.resource.Labels;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.Range.AutoFillType;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
import org.zkoss.zss.ui.impl.undo.AutoFillCellAction;
import org.zkoss.zss.ui.sys.UndoableActionManager;

public class AutoFillHandler extends AbstractProtectedHandler {
    public AutoFillHandler() {
    }

    protected boolean processAction(UserActionContext ctx) {
        Sheet sheet = ctx.getSheet();
        AreaRef selection = ctx.getSelection();
        AreaRef src = (AreaRef)ctx.getData("source");
        UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
        uam.doAction(new AutoFillCellAction(Labels.getLabel("zss.undo.fillCell"), sheet, src.getRow(), src.getColumn(), src.getLastRow(), src.getLastColumn(), sheet, selection.getRow(), selection.getColumn(), selection.getLastRow(), selection.getLastColumn(), AutoFillType.DEFAULT));
        return true;
    }
}
