//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.dialog;

import java.util.Map;
import org.zkoss.lang.Strings;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;
import org.zkoss.zssex.ui.dialog.impl.DialogCtrlBase;
import org.zkoss.zssex.ui.dialog.impl.DialogCtrlBase.Entry;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class FormatNumberCtrl extends DialogCtrlBase {
    private static final long serialVersionUID = 1L;
    private static final String URI = "~./zssex/dlg/formatNumber.zul";
    public static final String ARG_FORMAT_CODE = "formatCode";
    private String formatCode;
    @Wire
    private Listbox mfn_category;
    @Wire
    private Listbox mfn_general;
    @Wire
    private Listbox selectedCategory;
    private String[] categories = new String[]{"mfn_number", "mfn_currency", "mfn_accounting", "mfn_date", "mfn_time", "mfn_percentage", "mfn_fraction", "mfn_scientific", "mfn_text", "mfn_special"};

    public FormatNumberCtrl() {
    }

    public static void show(EventListener<DialogCallbackEvent> callback, String formatCode) {
        Map arg = newArg(callback);
        arg.put("formatCode", formatCode);
        Window comp = (Window)Executions.createComponents("~./zssex/dlg/formatNumber.zul", (Component)null, arg);
        comp.doModal();
    }

    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        Map arg = Executions.getCurrent().getArg();
        this.formatCode = (String)arg.get("formatCode");
        if(Strings.isBlank(this.formatCode)) {
            ;
        }

        this.openFormatList(this.categories[0]);
        this.mfn_category.setSelectedIndex(0);
    }

    @Listen("onSelect=#mfn_category")
    public void onSelect$mfn_category(SelectEvent event) {
        this.openFormatList((String)this.mfn_category.getSelectedItem().getValue());
    }

    private void openFormatList(String listId) {
        for(int i = 0; i < this.categories.length; ++i) {
            Listbox lb = (Listbox)((Window)this.getSelf()).getFellow(this.categories[i]);
            if(lb != null) {
                if(listId.equals(this.categories[i])) {
                    lb.setVisible(true);
                    lb.setSelectedIndex(0);
                    this.selectedCategory = lb;
                } else {
                    lb.setVisible(false);
                }
            }
        }

    }

    @Listen("onOK=window; onClick=#okBtn")
    public void onClick$okBtn() {
        Listitem seldItem = this.mfn_category.getSelectedItem();
        if(seldItem == null) {
            this.showSelectFormatDialog();
        } else if(this.selectedCategory != null && this.selectedCategory != this.mfn_general) {
            Listitem selectedItem = this.selectedCategory.getSelectedItem();
            if(selectedItem != null) {
                String formatCodes = selectedItem.getValue().toString();
                this.detach();
                this.postCallback("onOK", newMap(new Entry[]{newEntry("formatCode", formatCodes)}));
            } else {
                this.showSelectFormatDialog();
            }
        } else {
            this.showSelectFormatDialog();
        }
    }

    private void showSelectFormatDialog() {
        Messagebox.show(Labels.getLabel("zssex.dlg.format.select_format_code"));
    }

    @Listen("onCancel=window; onClick=#cancelBtn")
    public void onCancel() {
        this.detach();
        this.postCallback("onCancel", (Map)null);
    }
}
