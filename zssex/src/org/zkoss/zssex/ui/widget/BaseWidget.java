//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.widget;

import org.zkoss.lang.Objects;
import org.zkoss.zk.au.out.AuInvoke;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zss.ui.Widget;
import org.zkoss.zss.ui.sys.WidgetHandler;
import org.zkoss.zssex.ui.widget.WidgetCtrl;

public abstract class BaseWidget implements Widget {
    private String _id;
    private int _row;
    private int _column;
    private int _row2;
    private int _column2;
    private int _left;
    private int _top;
    private int _zindex = 0;
    private boolean _visible = true;
    private boolean _movable = false;
    private boolean _sizable = false;
    private String _ctrlKeys;
    private WidgetCtrl _ctrl;
    private WidgetHandler _handler;
    private boolean _inClient = false;
    private String _panel;

    protected BaseWidget(String panel) {
        this._panel = panel;
    }

    protected WidgetCtrl newCtrl() {
        WidgetCtrl ctrl = new WidgetCtrl(this);
        return ctrl;
    }

    boolean isInClient() {
        return this._inClient;
    }

    void setInClient(boolean inClient) {
        this._inClient = inClient;
    }

    public Component getComponent() {
        return this.getCtrl();
    }

    protected Component getInnerComponent() {
        return null;
    }

    protected synchronized WidgetCtrl getCtrl() {
        if(this._ctrl == null) {
            this._ctrl = this.newCtrl();
        }

        return this._ctrl;
    }

    public WidgetHandler getHandler() {
        return this._handler;
    }

    public void setHandler(WidgetHandler handler) {
        if(!Objects.equals(this._handler, handler)) {
            if(this._handler != null) {
                this._handler.removeWidget(this);
            }

            this._handler = handler;
            if(this._handler != null) {
                this._handler.addWidget(this);
            }
        }

    }

    void setHandler0(WidgetHandler handler) {
        if(!Objects.equals(this._handler, handler)) {
            this._handler = handler;
        }

    }

    public int getRow() {
        return this._row;
    }

    public void setRow(int row) {
        if(this._row != row) {
            this._row = row;
            this.getCtrl().smartUpdate("row", Integer.valueOf(this._row));
        }

    }

    public int getColumn() {
        return this._column;
    }

    public void setColumn(int column) {
        if(this._column != column) {
            this._column = column;
            this.getCtrl().smartUpdate("col", Integer.valueOf(this._column));
        }

    }

    public int getRow2() {
        return this._row2;
    }

    public void setRow2(int row) {
        if(this._row2 != row) {
            this._row2 = row;
            this.getCtrl().smartUpdate("row2", Integer.valueOf(this._row2));
        }

    }

    public int getColumn2() {
        return this._column2;
    }

    public void setColumn2(int column) {
        if(this._column2 != column) {
            this._column2 = column;
            this.getCtrl().smartUpdate("col2", Integer.valueOf(this._column2));
        }

    }

    public int getLeft() {
        return this._left;
    }

    public void setLeft(int left) {
        if(this._left != left) {
            this._left = left;
            this.getCtrl().smartUpdate("left", Integer.valueOf(this._left));
        }

    }

    public int getTop() {
        return this._top;
    }

    public void setTop(int top) {
        if(this._top != top) {
            this._top = top;
            this.getCtrl().smartUpdate("top", Integer.valueOf(this._top));
        }

    }

    public void setZindex(int zindex) {
        if(this._zindex != zindex) {
            this._zindex = zindex;
            this.getCtrl().smartUpdate("zindex", Integer.valueOf(this._zindex));
        }

    }

    public int getZindex() {
        return this._zindex;
    }

    public boolean isVisible() {
        return this._visible;
    }

    public void setVisible(boolean visible) {
        if(this._visible != visible) {
            this._visible = visible;
            this.getCtrl().smartUpdate("visible", Boolean.valueOf(this._visible));
        }

    }

    public boolean isMovable() {
        return this._movable;
    }

    public void setMovable(boolean movable) {
        if(this._movable != movable) {
            this._movable = movable;
            this.getCtrl().smartUpdate("movable", Boolean.valueOf(movable));
        }

    }

    public boolean isSizable() {
        return this._sizable;
    }

    public void setSizable(boolean sizable) {
        if(this._sizable != sizable) {
            this._sizable = sizable;
            this.getCtrl().smartUpdate("sizable", Boolean.valueOf(sizable));
        }

    }

    public void setCtrlKeys(String ctrlKeys) {
        if(ctrlKeys != null && ctrlKeys.length() == 0) {
            ctrlKeys = null;
        }

        if(this._ctrlKeys != ctrlKeys) {
            this._ctrlKeys = ctrlKeys;
            this.getCtrl().smartUpdate("ctrlKeys", ctrlKeys);
        }

    }

    public String getCtrlKeys() {
        return this._ctrlKeys;
    }

    public void setId(String id) {
        if(this._id != id) {
            this._id = id;
            this.getCtrl().smartUpdate("id", id);
        }

    }

    public String getId() {
        return this._id;
    }

    public void addEventListener(String evtnm, EventListener listener) {
        Component comp = this.getInnerComponent();
        if(comp != null) {
            comp.addEventListener(evtnm, listener);
        }

    }

    public boolean removeEventListener(String evtnm, EventListener listener) {
        Component comp = this.getInnerComponent();
        return comp != null?comp.removeEventListener(evtnm, listener):false;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(this.getClass().getSimpleName());
        sb.append(",row:").append(this._row).append(",col:").append(this._column).append(",panel:").append(this._panel);
        sb.append(",client:").append(this._inClient);
        sb.append(",zindex:").append(this._zindex);
        return sb.toString();
    }

    public String getPanel() {
        return this._panel;
    }

    public void adjustLocation() {
        this.getCtrl().response(new AuInvoke(this.getCtrl(), "adjustLocation"));
    }

    public abstract void invalidate();

    public abstract String getWidgetType();
}
