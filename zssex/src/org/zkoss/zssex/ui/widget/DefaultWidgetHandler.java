//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.widget;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zss.ui.Spreadsheet;
import org.zkoss.zss.ui.Widget;
import org.zkoss.zss.ui.sys.SpreadsheetCtrl;
import org.zkoss.zss.ui.sys.WidgetHandler;
import org.zkoss.zssex.ui.au.out.AuRedrawWidget;
import org.zkoss.zssex.ui.widget.BaseWidget;
import org.zkoss.zssex.ui.widget.Ghost;
import org.zkoss.zssex.ui.widget.WidgetCtrl;

public class DefaultWidgetHandler implements WidgetHandler, Serializable {
    private static final long serialVersionUID = 1L;
    private Spreadsheet _spreadsheet;
    private List<WidgetCtrl> _clientWidgets = new ArrayList();
    private List<WidgetCtrl> _nonClientWidgets = new ArrayList();
    private Ghost _ghost;
    private static String WIDGET_RES_PREFIX = "zsw_";

    public DefaultWidgetHandler() {
    }

    public boolean addWidget(Widget widget) {
        if(!(widget instanceof BaseWidget)) {
            return false;
        } else {
            if(this._ghost == null) {
                this._ghost = this.newGhost();
                this._spreadsheet.appendChild(this._ghost);
            }

            WidgetCtrl ctrl = ((BaseWidget)widget).getCtrl();
            boolean r = this._ghost.appendChild(ctrl);
            if(r) {
                ((BaseWidget)widget).setHandler0(this);
                this._nonClientWidgets.add(ctrl);
                if(!this._spreadsheet.isInvalidated()) {
                    SSheet sheet = this._spreadsheet.getSelectedSSheet();
                    AreaRef rect = ((SpreadsheetCtrl)this._spreadsheet.getExtraCtrl()).getVisibleArea();
                    this.onLoadWidgetOnDemand((BaseWidget)widget, sheet, rect.getColumn(), rect.getRow(), rect.getLastColumn(), rect.getLastRow());
                }
            }

            return r;
        }
    }

    /** @deprecated */
    @Deprecated
    public void updateWidgets(SSheet sheet, int left, int top, int right, int bottom) {
    }

    public void updateWidget(SSheet sheet, String widgetId) {
        Iterator i$ = this._clientWidgets.iterator();

        while(i$.hasNext()) {
            WidgetCtrl ctrl = (WidgetCtrl)i$.next();
            BaseWidget widget = ctrl.getWidget();
            if(widget.getId().equals(widgetId) && widget instanceof BaseWidget) {
                ((BaseWidget)widget).invalidate();
            }
        }

    }

    public Spreadsheet getSpreadsheet() {
        return this._spreadsheet;
    }

    public boolean removeWidget(Widget widget) {
        if(!(widget instanceof BaseWidget)) {
            return false;
        } else if(((BaseWidget)widget).getHandler() != this) {
            return false;
        } else {
            WidgetCtrl ctrl = ((BaseWidget)widget).getCtrl();
            if(this._ghost == null) {
                this._ghost = this.newGhost();
                this._spreadsheet.appendChild(this._ghost);
            }

            boolean r = this._ghost.removeChild(ctrl);
            if(r) {
                ((BaseWidget)widget).setHandler0((WidgetHandler)null);
                ((BaseWidget)widget).setInClient(false);
                this._clientWidgets.remove(ctrl);
                this._nonClientWidgets.remove(ctrl);
            }

            return r;
        }
    }

    public void init(Spreadsheet spreadsheet) {
        this._spreadsheet = spreadsheet;
    }

    public void onLoadOnDemand(SSheet sheet, int left, int top, int right, int bottom) {
        if(this._ghost != null) {
            int size = this._nonClientWidgets.size();

            for(int i = size - 1; i >= 0; --i) {
                WidgetCtrl ctrl = (WidgetCtrl)this._nonClientWidgets.get(i);
                BaseWidget widget = ctrl.getWidget();
                this.onLoadWidgetOnDemand(widget, sheet, left, top, right, bottom);
            }

        }
    }

    private boolean onLoadWidgetOnDemand(BaseWidget widget, SSheet sheet, int left, int top, int right, int bottom) {
        int r = widget.getRow();
        int c = widget.getColumn();
        int r2 = widget.getRow2();
        int c2 = widget.getColumn2();
        WidgetCtrl ctrl = widget.getCtrl();
        if(r2 >= top && r <= bottom && c2 >= left && c <= right) {
            this.redrawWidget(sheet.getId(), widget);
            if(!widget.isInClient()) {
                widget.setInClient(true);
                this._clientWidgets.add(ctrl);
                this._nonClientWidgets.remove(ctrl);
            }

            return true;
        } else {
            return false;
        }
    }

    private void redrawWidget(String sheetid, BaseWidget widget) {
        WidgetCtrl ctrl = widget.getCtrl();
        String uuid = ctrl.getUuid();
        this._spreadsheet.response(WIDGET_RES_PREFIX + uuid, new AuRedrawWidget(this._spreadsheet, sheetid, uuid));
    }

    public void invaliate() {
        Iterator iter = this._clientWidgets.iterator();

        while(iter.hasNext()) {
            WidgetCtrl ctrl = (WidgetCtrl)iter.next();
            ctrl.getWidget().setInClient(false);
            this._nonClientWidgets.add(ctrl);
        }

        this._clientWidgets.clear();
    }

    private Ghost newGhost() {
        Ghost ghost = new Ghost();
        ghost.setAttribute("zsschildren", "");
        return ghost;
    }
}
