//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.IllegalOpArgumentException;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.Range.PasteOperation;
import org.zkoss.zss.api.Range.PasteType;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.UserActionContext.Clipboard;
import org.zkoss.zssex.ui.dialog.PasteSpecialCtrl;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;
import org.zkoss.zssex.ui.impl.ua.AbstractPasteSpecialHandler;

public class PasteSpecialDialogHandler extends AbstractPasteSpecialHandler {
    public PasteSpecialDialogHandler() {
    }

    protected boolean processAction(final UserActionContext ctx) {
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
                PasteSpecialCtrl.show(new EventListener<DialogCallbackEvent>() {
                    public void onEvent(DialogCallbackEvent event) throws Exception {
                        if("onOK".equals(event.getName())) {
                            PasteType type = (PasteType)event.getData("pasteType");
                            PasteOperation operation = (PasteOperation)event.getData("pasteOp");
                            boolean skipBlank = ((Boolean)event.getData("skipBlank")).booleanValue();
                            boolean transpose = ((Boolean)event.getData("transport")).booleanValue();
                            PasteSpecialDialogHandler.this.doPaste(ctx, type, operation, skipBlank, transpose);
                        }

                    }
                }, sheet.getBook().getType());
                return true;
            }
        }
    }
}
