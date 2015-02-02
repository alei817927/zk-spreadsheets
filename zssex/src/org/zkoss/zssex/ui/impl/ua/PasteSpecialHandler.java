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
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.UserActionContext.Clipboard;
import org.zkoss.zssex.ui.impl.ua.AbstractPasteSpecialHandler;

public class PasteSpecialHandler extends AbstractPasteSpecialHandler {
    private final PasteType _type;
    private final PasteOperation _operation;
    private final boolean _skipBlank;
    private final boolean _transpose;

    public PasteSpecialHandler(PasteType type, PasteOperation operation, boolean skipBlank, boolean transpose) {
        this._type = type;
        this._operation = operation;
        this._skipBlank = skipBlank;
        this._transpose = transpose;
    }

    protected boolean processAction(UserActionContext ctx) {
        Sheet sheet = ctx.getSheet();
        AreaRef selection = ctx.getSelection();
        Range range = Ranges.range(sheet, selection);
        if(range.isProtected()) {
            return true;
        } else {
            Clipboard cb = ctx.getClipboard();
            if(cb == null) {
                this.showInfoMessage(Labels.getLabel("zss.actionhandler.msg.cant_find_thing_to_paste"));
                return true;
            } else if(cb.isCutMode()) {
                throw new IllegalOpArgumentException("Cannot support paste special for cut");
            } else {
                this.doPaste(ctx, this._type, this._operation, this._skipBlank, this._transpose);
                return true;
            }
        }
    }
}
