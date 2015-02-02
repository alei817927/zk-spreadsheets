//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.SheetAnchor;
import org.zkoss.zss.api.SheetOperationUtil;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.api.model.Chart.Grouping;
import org.zkoss.zss.api.model.Chart.LegendPosition;
import org.zkoss.zss.api.model.Chart.Type;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;

public class InsertChartHandler extends AbstractProtectedHandler {
    private final Type _type;
    private final Grouping _grouping;
    private final LegendPosition _pos;

    public InsertChartHandler(Type type, Grouping grouping, LegendPosition pos) {
        this._type = type;
        this._grouping = grouping;
        this._pos = pos;
    }

    protected boolean processAction(UserActionContext ctx) {
        Sheet sheet = ctx.getSheet();
        AreaRef selection = ctx.getSelection();
        Range range = Ranges.range(sheet, selection.getRow(), selection.getColumn(), selection.getLastRow(), selection.getLastColumn());
        if(range.isProtected()) {
            this.showProtectMessage();
            return true;
        } else {
            SheetAnchor anchor = SheetOperationUtil.toChartAnchor(range);
            SheetOperationUtil.addChart(range, anchor, this._type, this._grouping, this._pos);
            ctx.clearClipboard();
            return true;
        }
    }
}
