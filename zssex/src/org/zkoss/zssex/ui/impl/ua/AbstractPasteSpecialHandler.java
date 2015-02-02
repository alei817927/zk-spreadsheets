//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.util.resource.Labels;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.IllegalOpArgumentException;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.Range.PasteOperation;
import org.zkoss.zss.api.Range.PasteType;
import org.zkoss.zss.api.model.Book;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.UserActionContext.Clipboard;
import org.zkoss.zss.ui.impl.ua.AbstractHandler;
import org.zkoss.zss.ui.impl.undo.PasteSpecialCellAction;
import org.zkoss.zss.ui.sys.UndoableActionManager;

public abstract class AbstractPasteSpecialHandler extends AbstractHandler {
    public AbstractPasteSpecialHandler() {
    }

    protected boolean doPaste(UserActionContext ctx, PasteType pasteType, PasteOperation pasteOperation, boolean skipBlank, boolean transpose) {
        try {
            return this.doPaste0(ctx, pasteType, pasteOperation, skipBlank, transpose);
        } catch (IllegalOpArgumentException var7) {
            this.showInfoMessage(Labels.getLabel("zss.actionhandler.msg.illegal_range_operation") + " : " + var7.getMessage());
            return true;
        }
    }

    protected boolean doPaste0(UserActionContext ctx, PasteType pasteType, PasteOperation pasteOperation, boolean skipBlank, boolean transpose) {
        Clipboard cb = ctx.getClipboard();
        if(cb == null) {
            return false;
        } else if(cb.isCutMode()) {
            throw new IllegalOpArgumentException("Cannot support paste special for cut");
        } else {
            Book book = ctx.getBook();
            Sheet destSheet = ctx.getSheet();
            Sheet srcSheet = cb.getSheet();
            if(srcSheet == null) {
                this.showInfoMessage(Labels.getLabel("zss.actionhandler.msg.cant_find_sheet_to_paste"));
                ctx.clearClipboard();
                return true;
            } else {
                AreaRef src = cb.getSelection();
                AreaRef selection = ctx.getSelection();
                Range srcRange = Ranges.range(srcSheet, src.getRow(), src.getColumn(), src.getLastRow(), src.getLastColumn());
                Range destRange = Ranges.range(destSheet, selection.getRow(), selection.getColumn(), selection.getLastRow(), selection.getLastColumn());
                if(destRange.isProtected()) {
                    this.showProtectMessage();
                    return true;
                } else if(cb.isCutMode() && srcRange.isProtected()) {
                    this.showProtectMessage();
                    return true;
                } else {
                    UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
                    uam.doAction(new PasteSpecialCellAction(Labels.getLabel("zss.undo.paste"), srcSheet, src.getRow(), src.getColumn(), src.getLastRow(), src.getLastColumn(), destSheet, selection.getRow(), selection.getColumn(), selection.getLastRow(), selection.getLastColumn(), pasteType, pasteOperation, skipBlank, transpose));
                    return true;
                }
            }
        }
    }
}
