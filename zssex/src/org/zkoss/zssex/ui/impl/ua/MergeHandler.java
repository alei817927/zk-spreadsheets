//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.util.resource.Labels;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
import org.zkoss.zss.ui.impl.undo.MergeCellAction;
import org.zkoss.zss.ui.sys.UndoableActionManager;

public class MergeHandler extends AbstractProtectedHandler {
    boolean _across;

    public MergeHandler(boolean across) {
        this._across = across;
    }

    protected boolean processAction(UserActionContext ctx) {
        Sheet sheet = ctx.getSheet();
        AreaRef selection = ctx.getSelection();
        Ranges.range(sheet, selection);
        UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
        uam.doAction(new MergeCellAction(Labels.getLabel("zss.undo.merge"), sheet, selection.getRow(), selection.getColumn(), selection.getLastRow(), selection.getLastColumn(), this._across));
        ctx.clearClipboard();
        return true;
    }
}
