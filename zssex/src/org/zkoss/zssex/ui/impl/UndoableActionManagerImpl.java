//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl;

import java.util.LinkedList;
import org.zkoss.lang.Objects;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.CellRef;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.Spreadsheet;
import org.zkoss.zss.ui.event.UndoableActionManagerEvent;
import org.zkoss.zss.ui.event.UndoableActionManagerEvent.Type;
import org.zkoss.zss.ui.sys.UndoableAction;
import org.zkoss.zss.ui.sys.UndoableActionManager;

public class UndoableActionManagerImpl implements UndoableActionManager {
    int _maxHistory = 10;
    int _index = -1;
    LinkedList<UndoableAction> _actionHisotry = new LinkedList();
    Spreadsheet _spreadsheet;

    public UndoableActionManagerImpl() {
    }

    public void doAction(UndoableAction action) {
        action.doAction();

        while(this._actionHisotry.size() > this._index + 1) {
            this._actionHisotry.removeLast();
        }

        while(this._actionHisotry.size() >= this._maxHistory) {
            this._actionHisotry.remove(0);
        }

        this._actionHisotry.add(action);
        this._index = this._actionHisotry.size() - 1;
        Events.postEvent(new UndoableActionManagerEvent("onAfterUndoableManagerAction", this._spreadsheet, Type.DO, action));
    }

    private UndoableAction current() {
        return this._index >= 0 && this._index < this._actionHisotry.size()?(UndoableAction)this._actionHisotry.get(this._index):null;
    }

    private UndoableAction next() {
        int i = this._index + 1;
        return i >= 0 && i < this._actionHisotry.size()?(UndoableAction)this._actionHisotry.get(i):null;
    }

    public boolean isUndoable() {
        UndoableAction action = this.current();
        return action != null && action.isUndoable();
    }

    public String getUndoLabel() {
        UndoableAction action = this.current();
        return action != null?action.getLabel():null;
    }

    public void undoAction() {
        UndoableAction action = this.current();
        if(action != null) {
            if(action.isUndoable()) {
                Sheet sheet = action.getUndoSheet();
                if(this._spreadsheet != null && sheet != null && !this.sheetEquals(this._spreadsheet.getSelectedSheet(), sheet)) {
                    this._spreadsheet.setSelectedSheet(sheet.getSheetName());
                    this._spreadsheet.focus();
                    return;
                }

                action.undoAction();
                --this._index;
                AreaRef selection = this.getVisibleArea(action.getUndoSelection());
                if(this._spreadsheet != null && selection != null) {
                    this._spreadsheet.setSelection(selection);
                    this._spreadsheet.setCellFocus(new CellRef(selection.getRow(), selection.getColumn()));
                }
            } else {
                this.clear();
            }

            Events.postEvent(new UndoableActionManagerEvent("onAfterUndoableManagerAction", this._spreadsheet, Type.UNDO, action));
        }

    }

    public boolean isRedoable() {
        UndoableAction action = this.next();
        return action != null && action.isRedoable();
    }

    public String getRedoLabel() {
        UndoableAction action = this.next();
        return action != null?action.getLabel():null;
    }

    public void redoAction() {
        UndoableAction action = this.next();
        if(action != null) {
            if(action.isRedoable()) {
                Sheet sheet = action.getRedoSheet();
                if(this._spreadsheet != null && sheet != null && !this.sheetEquals(this._spreadsheet.getSelectedSheet(), sheet)) {
                    this._spreadsheet.setSelectedSheet(sheet.getSheetName());
                    this._spreadsheet.focus();
                    return;
                }

                action.doAction();
                ++this._index;
                AreaRef selection = this.getVisibleArea(action.getRedoSelection());
                if(this._spreadsheet != null && selection != null) {
                    this._spreadsheet.setSelection(selection);
                    this._spreadsheet.setCellFocus(new CellRef(selection.getRow(), selection.getColumn()));
                }
            } else {
                this.clear();
            }

            Events.postEvent(new UndoableActionManagerEvent("onAfterUndoableManagerAction", this._spreadsheet, Type.REDO, action));
        }

    }

    private boolean sheetEquals(Sheet sheet1, Sheet sheet2) {
        try {
            return Objects.equals(sheet1, sheet2);
        } catch (Exception var4) {
            return false;
        }
    }

    private AreaRef getVisibleArea(AreaRef rect) {
        return rect == null?null:new AreaRef(rect.getRow(), rect.getColumn(), Math.min(rect.getLastRow(), this._spreadsheet.getMaxVisibleRows()), Math.min(rect.getLastColumn(), this._spreadsheet.getMaxVisibleColumns()));
    }

    public void clear() {
        this._index = -1;
        this._actionHisotry.clear();
        Events.postEvent(new UndoableActionManagerEvent("onAfterUndoableManagerAction", this._spreadsheet, Type.CLEAR, (UndoableAction)null));
    }

    public void setMaxHsitorySize(int size) {
        if(size < 1) {
            throw new IllegalArgumentException("must large than 1");
        } else {
            this.clear();
            this._maxHistory = size;
        }
    }

    public void bind(Spreadsheet spreadsheet) {
        this._spreadsheet = spreadsheet;
    }
}
