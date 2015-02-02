//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.dialog;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zssex.ui.dialog.FormulaMetaInfo;
import org.zkoss.zssex.ui.dialog.Formulas;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;
import org.zkoss.zssex.ui.dialog.impl.DialogCtrlBase;
import org.zkoss.zssex.ui.dialog.impl.DialogCtrlBase.Entry;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class InsertFormulaCtrl extends DialogCtrlBase {
    private static final long serialVersionUID = 1L;
    public static final String FORMULA = "formula";
    private static final String URI = "~./zssex/dlg/insertFormulaDialog.zul";
    private static final String ALL = "All";
    @Wire
    private Div findArea;
    @Wire
    private Textbox searchTextbox;
    @Wire
    private Combobox categoryCombobox;
    @Wire
    private Listbox functionListbox;
    @Wire
    private Label expression;
    @Wire
    private Label description;
    @Wire
    private Button selectButton;
    @Wire
    private Window _composeFormulaDialog;
    @Wire("#_composeFormulaDialog #formulaStart")
    private Label formulaStart;
    @Wire("#_composeFormulaDialog #formulaEnd")
    private Label formulaEnd;
    @Wire("#_composeFormulaDialog #composeFormulaTextbox")
    private Textbox composeFormulaTextbox;
    private LinkedHashMap<String, List<FormulaMetaInfo>> formulaInfos;

    public InsertFormulaCtrl() {
    }

    public static void show(EventListener<DialogCallbackEvent> dialogEventListener) {
        Map arg = newArg(dialogEventListener);
        Window window = (Window)Executions.createComponents("~./zssex/dlg/insertFormulaDialog.zul", (Component)null, arg);
        window.doModal();
    }

    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        this.formulaInfos = Formulas.getFormulaInfos();
        LinkedList categoryArys = new LinkedList();
        categoryArys.add("All");
        categoryArys.addAll(this.formulaInfos.keySet());
        this.categoryCombobox.setModel(new ListModelList(categoryArys, false));
        this.categoryCombobox.addEventListener("onAfterRender", new EventListener() {
            public void onEvent(Event event) throws Exception {
                InsertFormulaCtrl.this.categoryCombobox.setSelectedIndex(0);
                InsertFormulaCtrl.this.initFunctionListboxByCategory();
            }
        });
        this.searchTextbox.setText((String)null);
        this.searchTextbox.focus();
        this.functionListbox.setItemRenderer(new ListitemRenderer() {
            public void render(Listitem item, Object data) throws Exception {
                FormulaMetaInfo info = (FormulaMetaInfo)data;
                item.setLabel(info.getFunction());
                item.setValue(info);
            }

            public void render(Listitem item, Object data, int index) throws Exception {
                this.render(item, data);
            }
        });
        this.functionListbox.addEventListener("onDoubleClick", new EventListener() {
            public void onEvent(Event event) throws Exception {
                InsertFormulaCtrl.this.openComposeFormulaDialog();
            }
        });
    }

    @Listen("onSelect = #categoryCombobox")
    public void onSelect$categoryCombobox() {
        this.initFunctionListboxByCategory();
    }

    private void initFunctionListboxByCategory() {
        String category = (String)this.categoryCombobox.getSelectedItem().getValue();
        if(category != null) {
            Object categoriedFunctions = (List)this.formulaInfos.get(category);
            if(categoriedFunctions == null) {
                categoriedFunctions = new LinkedList();
                Iterator i$ = this.formulaInfos.values().iterator();

                while(i$.hasNext()) {
                    List infoAry = (List)i$.next();
                    ((List)categoriedFunctions).addAll(infoAry);
                }
            }

            this.functionListbox.setModel(new SimpleListModel((List)categoriedFunctions));
            this.selectButton.setDisabled(true);
        }
    }

    @Listen("onClick = #functionListbox")
    public void onClick$functionListbox() {
        FormulaMetaInfo info = (FormulaMetaInfo)this.functionListbox.getSelectedItem().getValue();
        this.expression.setValue(info.getExpression());
        this.description.setValue(info.getDescription());
    }

    @Listen("onClick = #selectButton")
    public void confirmSelection() {
        this.openComposeFormulaDialog();
    }

    private void openComposeFormulaDialog() {
        Listitem item = this.functionListbox.getSelectedItem();
        if(item == null) {
            Messagebox.show(Labels.getLabel("zssex.dlg.insert_function.err.no_function_selected"));
        } else {
            FormulaMetaInfo info = (FormulaMetaInfo)item.getValue();
            if(info.getRequiredParameter() == 0) {
                this.insertFunction();
            } else {
                this.openComposeFormulaDialog((FormulaMetaInfo)item.getValue());
            }

        }
    }

    private List<FormulaMetaInfo> search(String keyword) {
        keyword = keyword.toLowerCase();
        LinkedList searchResult = new LinkedList();
        Iterator i$ = this.formulaInfos.values().iterator();

        while(i$.hasNext()) {
            List infoAry = (List)i$.next();
            Iterator i$1 = infoAry.iterator();

            while(i$1.hasNext()) {
                FormulaMetaInfo info = (FormulaMetaInfo)i$1.next();
                if(info.getFunction().toLowerCase().contains(keyword) || info.getExpression().toLowerCase().contains(keyword) || info.getDescription().toLowerCase().contains(keyword)) {
                    searchResult.add(info);
                }
            }
        }

        Collections.sort(searchResult, new Comparator<FormulaMetaInfo>() {
            public int compare(FormulaMetaInfo o1, FormulaMetaInfo o2) {
                return o1.getFunction().compareTo(o2.getFunction());
            }
        });
        return searchResult;
    }

    @Listen("onChanging = #searchTextbox")
    public void startSearch(InputEvent event) {
        String keyword = event.getValue();
        if(keyword != null && !"".equals(keyword)) {
            this.functionListbox.setModel(new SimpleListModel(this.search(keyword)));
            this.selectButton.setDisabled(true);
        } else {
            this.initFunctionListboxByCategory();
        }

    }

    @Listen("onClick = #_composeFormulaDialog #insertBtn; onOK = #_composeFormulaDialog #composeFormulaTextbox")
    public void insertFunction() {
        this.postCallback("onOK", newMap(new Entry[]{newEntry("formula", this.formulaStart.getValue() + this.composeFormulaTextbox.getText() + this.formulaEnd.getValue())}));
        this.detach();
    }

    private void openComposeFormulaDialog(FormulaMetaInfo metainfo) {
        Events.sendEvent("onOpen", this._composeFormulaDialog, metainfo);
        this.findArea.setVisible(false);
        this._composeFormulaDialog.setVisible(true);
    }

    @Listen("onSelect = #functionListbox")
    public void enableSelectButton() {
        this.selectButton.setDisabled(false);
    }
}
