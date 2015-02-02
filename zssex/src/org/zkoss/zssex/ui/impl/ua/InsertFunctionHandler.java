//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.IllegalFormulaException;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
import org.zkoss.zssex.ui.dialog.InsertFormulaCtrl;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;
import org.zkoss.zul.Messagebox;

public class InsertFunctionHandler extends AbstractProtectedHandler {
    public InsertFunctionHandler() {
    }

    protected boolean processAction(UserActionContext ctx) {
        Sheet sheet = ctx.getSheet();
        AreaRef selection = ctx.getSelection();
        final Range selectedCell = Ranges.range(sheet, selection.getRow(), selection.getColumn());
        InsertFormulaCtrl.show(new EventListener<DialogCallbackEvent>() {
            public void onEvent(DialogCallbackEvent event) throws Exception {
                if("onOK".equals(event.getName())) {
                    String formula = (String)event.getData("formula");

                    try {
                        selectedCell.setCellEditText(formula);
                    } catch (IllegalFormulaException var4) {
                        InsertFunctionHandler.this.showFormulaError(var4);
                    }
                }

            }
        });
        return true;
    }

    private void showFormulaError(IllegalFormulaException ex) {
        String title = Labels.getLabel("zss.msg.warn_title");
        String msg = Labels.getLabel("zss.msg.formula_error", new Object[]{ex.getMessage()});
        Messagebox.show(msg, title, 1, "z-messagebox-icon z-messagebox-exclamation", (EventListener)null);
    }
}
