//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.api.model.CellData.CellType;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;
import org.zkoss.zssex.ui.dialog.impl.DialogCtrlBase;
import org.zkoss.zssex.ui.dialog.impl.DialogCtrlBase.Entry;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class CustomSortCtrl extends DialogCtrlBase {
    private static final long serialVersionUID = 1L;
    private static final String URI = "~./zssex/dlg/customSort.zul";
    public static final String ARG_SELECTION = "selection";
    public static final String ARG_SHEET = "sheet";
    public static final String ARG_CASE_SENSITIVE = "caseSensitive";
    public static final String ARG_HAS_HEADER = "hasHeader";
    public static final String ARG_ORIENTATION = "orientation";
    public static final String ARG_RULES = "rules";
    private ListModelList<SortRule> sortRuleModel;
    private List<String> availableSortIndex = new ArrayList();
    @Wire
    private Listbox sortRuleBox;
    @Wire
    private Checkbox caseSensitiveBox;
    @Wire
    private Checkbox hasHeaderBox;
    @Wire
    private Combobox sortOrientationBox;
    @Wire
    private Button addBtn;
    @Wire
    private Button delBtn;
    @Wire
    private Button upBtn;
    @Wire
    private Button downBtn;
    AreaRef selection;
    Sheet sheet;
    private ListitemRenderer sortRuleRenderer = new ListitemRenderer() {
        public void render(Listitem item, Object data, int index) throws Exception {
            Listcell cell = new Listcell();
            SortRule sort = (SortRule)data;
            cell.setLabel(index == 0?Labels.getLabel("zssex.dlg.sort.sortBy"):Labels.getLabel("zssex.dlg.sort.thenBy"));
            item.appendChild(cell);
            cell = new Listcell();
            item.appendChild(cell);
            SortIndexCombobox indexComp = CustomSortCtrl.this.new SortIndexCombobox(item, sort);
            cell.appendChild(indexComp);
            cell = new Listcell();
            item.appendChild(cell);
            SortAlgorithmCombobox algorComp = CustomSortCtrl.this.new SortAlgorithmCombobox(item, sort);
            cell.appendChild(algorComp);
        }
    };

    public CustomSortCtrl() {
    }

    public static void show(EventListener<DialogCallbackEvent> callback, Sheet sheet, AreaRef selection) {
        Map arg = newArg(callback);
        arg.put("sheet", sheet);
        arg.put("selection", selection);
        Window comp = (Window)Executions.createComponents("~./zssex/dlg/customSort.zul", (Component)null, arg);
        comp.doModal();
    }

    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        Map arg = Executions.getCurrent().getArg();
        this.selection = (AreaRef)arg.get("selection");
        this.sheet = (Sheet)arg.get("sheet");
        this.sortOrientationBox.setSelectedIndex(0);
        this.sortRuleModel = new ListModelList();
        this.sortRuleModel.add(new SortRule());
        this.sortRuleBox.setItemRenderer(this.sortRuleRenderer);
        this.refreshSortRule();
    }

    private void refreshSortRule() {
        this.updateAvailableList();
        this.sortRuleBox.setModel((ListModel)null);
        this.sortRuleBox.setModel(this.sortRuleModel);
        this.updateButtons();
    }

    private void updateAvailableList() {
        this.availableSortIndex.clear();
        boolean header = this.hasHeaderBox.isChecked();
        int col;
        int i;
        Range r;
        if(this.sortOrientationBox.getSelectedIndex() == 0) {
            col = this.selection.getRow() + (header?1:0);
            if(col <= this.selection.getLastRow()) {
                for(i = this.selection.getColumn(); i <= this.selection.getLastColumn(); ++i) {
                    r = Ranges.range(this.sheet, col, i);
                    this.availableSortIndex.add(new String("Column " + Ranges.getColumnRefString(i) + " (" + r.getCellFormatText() + ", ...)"));
                }
            }
        } else {
            col = this.selection.getColumn() + (header?1:0);

            for(i = this.selection.getRow(); i <= this.selection.getLastRow(); ++i) {
                if(col <= this.selection.getLastColumn()) {
                    r = Ranges.range(this.sheet, i, col);
                    this.availableSortIndex.add(new String("Row " + Ranges.getRowRefString(i) + " (" + r.getCellFormatText() + ", ...)"));
                }
            }
        }

    }

    @Listen("onSelect=#sortRuleBox")
    public void onSelect$sortRuleBox() {
        this.updateButtons();
    }

    private void updateButtons() {
        int idx = getSingleSelectionIndex(this.sortRuleModel);
        this.upBtn.setDisabled(idx <= 0);
        this.downBtn.setDisabled(idx < 0 || idx == this.sortRuleModel.size() - 1);
        this.addBtn.setDisabled(this.sortRuleModel.size() >= 3);
        this.delBtn.setDisabled(idx < 0);
    }

    @Listen("onClick=#addBtn")
    public void onClick$addBtn() {
        if(this.sortRuleModel.size() < 3) {
            this.sortRuleModel.add(new SortRule());
            this.updateButtons();
        }
    }

    @Listen("onClick=#delBtn")
    public void onClick$delBtn() {
        int idx = getSingleSelectionIndex(this.sortRuleModel);
        if(idx >= 0) {
            this.sortRuleModel.remove(idx);
            this.updateButtons();
        }
    }

    @Listen("onClick=#upBtn")
    public void onClick$upBtn() {
        int idx = getSingleSelectionIndex(this.sortRuleModel);
        if(idx > 0) {
            SortRule o1 = (SortRule)this.sortRuleModel.get(idx);
            SortRule o2 = (SortRule)this.sortRuleModel.get(idx - 1);
            this.sortRuleModel.set(idx - 1, o1);
            this.sortRuleModel.set(idx, o2);
            this.sortRuleModel.addToSelection(o1);
            this.updateButtons();
        }
    }

    @Listen("onClick=#downBtn")
    public void onClick$downBtn() {
        int idx = getSingleSelectionIndex(this.sortRuleModel);
        if(idx >= 0 && idx != this.sortRuleModel.size() - 1) {
            SortRule o1 = (SortRule)this.sortRuleModel.get(idx);
            SortRule o2 = (SortRule)this.sortRuleModel.get(idx + 1);
            this.sortRuleModel.set(idx + 1, o1);
            this.sortRuleModel.set(idx, o2);
            this.sortRuleModel.addToSelection(o1);
            this.updateButtons();
        }
    }

    @Listen("onOK=#window;onClick=#okBtn")
    public void onClick$okBtn() {
        if(!this.checkEmptySortRule(this.sortRuleModel.getInnerList()) && !this.checkDuplicateSortIndex(this.sortRuleModel.getInnerList())) {
            this.postCallback("onOK", newMap(new Entry[]{newEntry("caseSensitive", Boolean.valueOf(this.caseSensitiveBox.isChecked())), newEntry("hasHeader", Boolean.valueOf(this.hasHeaderBox.isChecked())), newEntry("orientation", this.sortOrientationBox.getSelectedIndex() == 1?"row":"column"), newEntry("rules", this.sortRuleModel.getInnerList())}));
            this.detach();
        }
    }

    private boolean checkEmptySortRule(List<SortRule> list) {
        boolean r = false;
        boolean column = this.sortOrientationBox.getSelectedIndex() == 0;
        int w = this.selection.getLastColumn() - this.selection.getColumn();
        int h = this.selection.getLastRow() - this.selection.getRow();
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            SortRule s = (SortRule)i$.next();
            if(s.sortIndex == -1) {
                r = true;
                break;
            }

            if(column && s.sortIndex > w) {
                r = true;
                break;
            }

            if(!column && s.sortIndex > h) {
                r = true;
                break;
            }
        }

        if(list.size() == 0 || r) {
            Messagebox.show(Labels.getLabel("zssex.dlg.sort.err.hasEmptySortRule"));
            r = true;
        }

        return r;
    }

    private boolean checkDuplicateSortIndex(List<SortRule> list) {
        HashMap map = new HashMap();
        Iterator i$ = list.iterator();

        while(i$.hasNext()) {
            SortRule s = (SortRule)i$.next();
            if(map.containsKey(Integer.valueOf(s.sortIndex))) {
                String title = null;
                if(this.sortOrientationBox.getSelectedIndex() == 0) {
                    title = Labels.getLabel("zssex.dlg.column") + " " + Ranges.getColumnRefString(this.selection.getColumn() + s.sortIndex);
                } else {
                    title = Labels.getLabel("zssex.dlg.row") + " " + Ranges.getRowRefString(this.selection.getRow() + s.sortIndex);
                }

                Messagebox.show(Labels.getLabel("zssex.dlg.sort.err.duplicateSortRule", new Object[]{title}));
                return true;
            }

            map.put(Integer.valueOf(s.sortIndex), Boolean.valueOf(true));
        }

        return false;
    }

    @Listen("onCheck=#hasHeaderBox")
    public void onCheck$hasHeaderBox() {
        this.refreshSortRule();
    }

    @Listen("onSelect=#sortOrientationBox")
    public void onSelect$sortOrientationBox() {
        this.refreshSortRule();
    }

    @Listen("onCancel=window; onClick=#cancelBtn")
    public void onCancel() {
        this.detach();
        this.postCallback("onCancel", (Map)null);
    }

    public static class SortRule {
        public int sortIndex = 0;
        public boolean ascending = true;

        public SortRule() {
        }

        public String toString() {
            return "{index:" + this.sortIndex + ",ascending:" + this.ascending + "}";
        }
    }

    public class SortAlgorithmCombobox extends Combobox {
        private SortRule sort;
        Listitem listitem;
        private static final String STR_ASCENDING_KEY = "zssex.dlg.sort.str.ascending";
        private static final String STR_DESCENDING_KEY = "zssex.dlg.sort.str.descending";
        private static final String NUM_ASCENDING_KEY = "zssex.dlg.sort.num.ascending";
        private static final String NUM_DESCENDING_KEY = "zssex.dlg.sort.num.descending";

        public SortAlgorithmCombobox(Listitem listitem, SortRule sort) {
            this.sort = sort;
            this.setWidth("100%");
            this.setReadonly(true);
            this.setMold("rounded");
            this.refreshModel();
            listitem.addEventListener("onSortRuleChanged", new EventListener() {
                public void onEvent(Event event) throws Exception {
                    if(event.getData() == SortAlgorithmCombobox.this.sort) {
                        SortAlgorithmCombobox.this.refreshModel();
                    }

                }
            });
            this.listitem = listitem;
        }

        void refreshModel() {
            ListModelList model = new ListModelList(this.createLabelList());
            model.addToSelection(model.get(this.sort.ascending?0:1));
            this.setModel(model);
        }

        private List<String> createLabelList() {
            ArrayList list = new ArrayList(2);
            if(this.isAllCellNumberType(this.sort.sortIndex)) {
                list.add(Labels.getLabel("zssex.dlg.sort.num.ascending"));
                list.add(Labels.getLabel("zssex.dlg.sort.num.descending"));
            } else {
                list.add(Labels.getLabel("zssex.dlg.sort.str.ascending"));
                list.add(Labels.getLabel("zssex.dlg.sort.str.descending"));
            }

            return list;
        }

        private boolean isAllCellNumberType(int idx) {
            boolean column = CustomSortCtrl.this.sortOrientationBox.getSelectedIndex() == 0;
            boolean header = CustomSortCtrl.this.hasHeaderBox.isChecked();
            int row;
            int col;
            int lcol;
            Range r;
            if(column) {
                row = CustomSortCtrl.this.selection.getColumn() + idx;
                col = CustomSortCtrl.this.selection.getRow() + (header?1:0);

                for(lcol = CustomSortCtrl.this.selection.getLastRow(); col <= lcol; ++col) {
                    r = Ranges.range(CustomSortCtrl.this.sheet, col, row);
                    if(r.getCellData().getResultType() != CellType.NUMERIC) {
                        return false;
                    }
                }
            } else {
                row = CustomSortCtrl.this.selection.getRow() + idx;
                col = CustomSortCtrl.this.selection.getColumn() + (header?1:0);

                for(lcol = CustomSortCtrl.this.selection.getLastColumn(); col <= lcol; ++col) {
                    r = Ranges.range(CustomSortCtrl.this.sheet, row, col);
                    if(r.getCellData().getResultType() != CellType.NUMERIC) {
                        return false;
                    }
                }
            }

            return true;
        }

        public void onSelect(SelectEvent evt) {
            this.sort.ascending = CustomSortCtrl.getSingleSelectionIndex(this.getModel()) != 1;
        }
    }

    public class SortIndexCombobox extends Combobox {
        SortRule sort;
        Listitem listitem;

        public SortIndexCombobox(Listitem listitem, SortRule sort) {
            this.setWidth("100%");
            this.setReadonly(true);
            this.setMold("rounded");
            this.sort = sort;
            ListModelList model = new ListModelList(CustomSortCtrl.this.availableSortIndex);
            if(sort.sortIndex >= 0 && sort.sortIndex < model.size()) {
                model.addToSelection(model.get(sort.sortIndex));
            } else if(model.size() == 0) {
                sort.sortIndex = -1;
            }

            this.setModel(model);
            this.listitem = listitem;
        }

        public void onSelect() {
            this.sort.sortIndex = CustomSortCtrl.getSingleSelectionIndex(this.getModel());
            Events.postEvent(new Event("onSortRuleChanged", this.listitem, this.sort));
        }
    }
}
