//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.widget;

import java.io.IOException;
import org.zkoss.zk.au.AuResponse;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.sys.ContentRenderer;
import org.zkoss.zss.ui.Spreadsheet;
import org.zkoss.zssex.ui.widget.BaseWidget;
import org.zkoss.zssex.ui.widget.Ghost;

public class WidgetCtrl extends AbstractComponent {
    private static final long serialVersionUID = 1L;
    private BaseWidget _widget;

    public WidgetCtrl(BaseWidget widget) {
        this._widget = widget;
    }

    protected void renderProperties(ContentRenderer renderer) throws IOException {
        super.renderProperties(renderer);
        renderer.render("id", this._widget.getId());
        renderer.render("type", this._widget.getWidgetType());
        renderer.render("panel", this._widget.getPanel());
        renderer.render("row", this._widget.getRow());
        renderer.render("col", this._widget.getColumn());
        renderer.render("left", this._widget.getLeft());
        renderer.render("top", this._widget.getTop());
        renderer.render("zIndex", this._widget.getZindex());
        renderer.render("style", this.getStyle());
        renderer.render("sizable", this._widget.isSizable());
        renderer.render("movable", this._widget.isMovable());
        renderer.render("ctrlKeys", this._widget.getCtrlKeys());
    }

    protected String getStyle() {
        StringBuffer sb = new StringBuffer(64);
        appendStyle(sb, "z-index", Integer.toString(this._widget.getZindex()));
        return sb.toString();
    }

    public Spreadsheet getSpreadsheet() {
        return (Spreadsheet)this.getParent();
    }

    public void setSpreadsheet(Spreadsheet ss) {
        this.setParent(ss);
    }

    public void setParent(Component parent) {
        if(parent != null && !(parent instanceof Ghost)) {
            throw new UiException("parent must be a " + Ghost.class);
        } else {
            super.setParent(parent);
        }
    }

    public BaseWidget getWidget() {
        return this._widget;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append("[").append(this._widget.toString()).append("]");
        return sb.toString();
    }

    public void smartUpdate(String attr, Object value) {
        super.smartUpdate(attr, value);
    }

    public void response(AuResponse response) {
        super.response(response);
    }

    private static final void appendStyle(StringBuffer sb, String name, String val) {
        if(val != null && val.length() != 0) {
            sb.append(name).append(':').append(val).append(';');
        }

    }
}
