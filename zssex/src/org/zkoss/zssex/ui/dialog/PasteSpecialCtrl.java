//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.dialog;

import java.util.Map;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zss.api.Range.PasteOperation;
import org.zkoss.zss.api.Range.PasteType;
import org.zkoss.zss.api.model.Book.BookType;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;
import org.zkoss.zssex.ui.dialog.impl.DialogCtrlBase;
import org.zkoss.zssex.ui.dialog.impl.DialogCtrlBase.Entry;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

public class PasteSpecialCtrl extends DialogCtrlBase {
    private static final String URI = "~./zssex/dlg/pasteSpecial.zul";
    public static final String ARG_PASTE_OP = "pasteOp";
    public static final String ARG_SKIP_BLANK = "skipBlank";
    public static final String ARG_PASTE_TYPE = "pasteType";
    public static final String ARG_TRANSPOSE = "transport";
    public static final String ARG_BOOK_TYPE = "bookType";
    @Wire
    private Radiogroup pasteTypeRadiogroup;
    @Wire
    private Radiogroup operationRadiogroup;
    @Wire
    private Checkbox skipBlankBox;
    @Wire
    private Checkbox transposeBox;
    @Wire
    private Radio comment;
    @Wire
    private Radio validation;

    public static void show(EventListener<DialogCallbackEvent> callback, BookType type) {
        Map arg = newArg(callback);
        arg.put("bookType", type);
        Window comp = (Window)Executions.createComponents("~./zssex/dlg/pasteSpecial.zul", (Component)null, arg);
        comp.doModal();
    }

    public PasteSpecialCtrl() {
    }

    public void doAfterCompose(Window w) throws Exception {
        super.doAfterCompose(w);
        BookType type = (BookType)Executions.getCurrent().getArg().get("bookType");
        if(type == BookType.XLS) {
            this.comment.setDisabled(true);
            this.validation.setDisabled(true);
        }

    }

    @Listen("onOK=window; onClick=#okBtn")
    public void onClick$okBtn() {
        this.detach();
        this.postCallback("onOK", newMap(new Entry[]{newEntry("pasteType", this.getPasteType()), newEntry("pasteOp", PasteOperation.NONE), newEntry("skipBlank", Boolean.valueOf(this.skipBlankBox.isChecked())), newEntry("transport", Boolean.valueOf(this.transposeBox.isChecked()))}));
    }

    private PasteOperation getPasteOperation() {
        Radio sel = this.operationRadiogroup.getSelectedItem();
        String operation = sel == null?null:(String)sel.getValue();
        if(operation != null && !"none".equals(operation)) {
            if("add".equals(operation)) {
                return PasteOperation.ADD;
            } else if("sub".equals(operation)) {
                return PasteOperation.SUB;
            } else if("mul".equals(operation)) {
                return PasteOperation.MUL;
            } else if("divide".equals(operation)) {
                return PasteOperation.DIV;
            } else {
                (new Exception("unknow paste operaton " + operation)).printStackTrace();
                return PasteOperation.NONE;
            }
        } else {
            return PasteOperation.NONE;
        }
    }

    private PasteType getPasteType() {
        Radio sel = this.pasteTypeRadiogroup.getSelectedItem();
        String type = sel == null?null:(String)sel.getValue();
        if(type != null && !"paste".equals(type) && !"all".equals(type)) {
            if("allExcpetBorder".equals(type)) {
                return PasteType.ALL_EXCEPT_BORDERS;
            } else if("columnWidth".equals(type)) {
                return PasteType.COLUMN_WIDTHS;
            } else if("comment".equals(type)) {
                return PasteType.COMMENTS;
            } else if("formula".equals(type)) {
                return PasteType.FORMULAS;
            } else if("formulaWithNumFmt".equals(type)) {
                return PasteType.FORMULAS_AND_NUMBER_FORMATS;
            } else if("value".equals(type)) {
                return PasteType.VALUES;
            } else if("valueWithNumFmt".equals(type)) {
                return PasteType.VALUES_AND_NUMBER_FORMATS;
            } else if("format".equals(type)) {
                return PasteType.FORMATS;
            } else if("validation".equals(type)) {
                return PasteType.VALIDATAION;
            } else {
                (new Exception("unknow paste operaton " + type)).printStackTrace();
                return PasteType.ALL;
            }
        } else {
            return PasteType.ALL;
        }
    }

    @Listen("onCancel=window; onClick=#cancelBtn")
    public void onCancel() {
        this.detach();
        this.postCallback("onCancel", (Map)null);
    }
}
