//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.zkoss.lang.Strings;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.Range.PasteOperation;
import org.zkoss.zss.api.Range.PasteType;
import org.zkoss.zss.api.model.Book;
import org.zkoss.zss.api.model.Chart;
import org.zkoss.zss.api.model.Picture;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.api.model.Chart.Grouping;
import org.zkoss.zss.api.model.Chart.LegendPosition;
import org.zkoss.zss.api.model.Chart.Type;
import org.zkoss.zss.model.SBook;
import org.zkoss.zss.ui.AuxAction;
import org.zkoss.zss.ui.CellSelectionType;
import org.zkoss.zss.ui.Spreadsheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.UserActionHandler;
import org.zkoss.zss.ui.event.CellSelectionAction;
import org.zkoss.zss.ui.event.CellSelectionUpdateEvent;
import org.zkoss.zss.ui.event.WidgetKeyEvent;
import org.zkoss.zss.ui.event.WidgetUpdateEvent;
import org.zkoss.zss.ui.impl.DefaultUserActionManagerCtrl;
import org.zkoss.zss.ui.impl.DefaultUserActionManagerCtrl.Category;
import org.zkoss.zss.ui.impl.DefaultUserActionManagerCtrl.UserActionContextImpl;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
import org.zkoss.zssex.ui.dialog.HeaderSizeCtrl.HeaderType;
import org.zkoss.zssex.ui.impl.ua.ApplyFilterHandler;
import org.zkoss.zssex.ui.impl.ua.AutoFillHandler;
import org.zkoss.zssex.ui.impl.ua.CleanFilterHandler;
import org.zkoss.zssex.ui.impl.ua.CustomSortHandler;
import org.zkoss.zssex.ui.impl.ua.DeleteChartHandler;
import org.zkoss.zssex.ui.impl.ua.DeletePictureHandler;
import org.zkoss.zssex.ui.impl.ua.FormatCellHandler;
import org.zkoss.zssex.ui.impl.ua.HeaderSizeHandler;
import org.zkoss.zssex.ui.impl.ua.HyperlinkHandler;
import org.zkoss.zssex.ui.impl.ua.InsertChartHandler;
import org.zkoss.zssex.ui.impl.ua.InsertFunctionHandler;
import org.zkoss.zssex.ui.impl.ua.InsertPictureHandler;
import org.zkoss.zssex.ui.impl.ua.MergeCenterHandler;
import org.zkoss.zssex.ui.impl.ua.MergeHandler;
import org.zkoss.zssex.ui.impl.ua.MoveChartHandler;
import org.zkoss.zssex.ui.impl.ua.MovePictureHandler;
import org.zkoss.zssex.ui.impl.ua.PasteSpecialDialogHandler;
import org.zkoss.zssex.ui.impl.ua.PasteSpecialHandler;
import org.zkoss.zssex.ui.impl.ua.ProtectSheetAction;
import org.zkoss.zssex.ui.impl.ua.ReapplyFilterHandler;
import org.zkoss.zssex.ui.impl.ua.ShiftCellHandler;
import org.zkoss.zssex.ui.impl.ua.UnmergeHandler;

public class UserActionManagerCtrlImpl extends DefaultUserActionManagerCtrl {
    private static final long serialVersionUID = 1L;
    private final Set<String> _interestedEvents = new LinkedHashSet();

    public UserActionManagerCtrlImpl() {
        this._interestedEvents.add("onWidgetCtrlKey");
        this._interestedEvents.add("onWidgetUpdate");
        this._interestedEvents.add("onCellSelectionUpdate");
        this.initDefaultAuxHandlers();
    }

    private void initDefaultAuxHandlers() {
        String category = Category.AUXACTION.getName();
        this.registerHandler(category, AuxAction.PASTE_FORMULA.getAction(), new PasteSpecialHandler(PasteType.FORMULAS, PasteOperation.NONE, false, false));
        this.registerHandler(category, AuxAction.PASTE_VALUE.getAction(), new PasteSpecialHandler(PasteType.VALUES, PasteOperation.NONE, false, false));
        this.registerHandler(category, AuxAction.PASTE_ALL_EXPECT_BORDERS.getAction(), new PasteSpecialHandler(PasteType.ALL_EXCEPT_BORDERS, PasteOperation.NONE, false, false));
        this.registerHandler(category, AuxAction.PASTE_TRANSPOSE.getAction(), new PasteSpecialHandler(PasteType.ALL, PasteOperation.NONE, false, true));
        this.registerHandler(category, AuxAction.MERGE_AND_CENTER.getAction(), new MergeCenterHandler());
        this.registerHandler(category, AuxAction.MERGE_ACROSS.getAction(), new MergeHandler(true));
        this.registerHandler(category, AuxAction.MERGE_CELL.getAction(), new MergeHandler(false));
        this.registerHandler(category, AuxAction.UNMERGE_CELL.getAction(), new UnmergeHandler());
        this.registerHandler(category, AuxAction.FILTER.getAction(), new ApplyFilterHandler());
        this.registerHandler(category, AuxAction.CLEAR_FILTER.getAction(), new CleanFilterHandler());
        this.registerHandler(category, AuxAction.REAPPLY_FILTER.getAction(), new ReapplyFilterHandler());
        this.registerHandler(category, AuxAction.PROTECT_SHEET.getAction(), new ProtectSheetAction());
        this.registerHandler(category, AuxAction.COLUMN_CHART.getAction(), new InsertChartHandler(Type.COLUMN, Grouping.STANDARD, LegendPosition.RIGHT));
        this.registerHandler(category, AuxAction.COLUMN_CHART_3D.getAction(), new InsertChartHandler(Type.COLUMN_3D, Grouping.STANDARD, LegendPosition.RIGHT));
        this.registerHandler(category, AuxAction.LINE_CHART.getAction(), new InsertChartHandler(Type.LINE, Grouping.STANDARD, LegendPosition.RIGHT));
        this.registerHandler(category, AuxAction.LINE_CHART_3D.getAction(), new InsertChartHandler(Type.LINE_3D, Grouping.STANDARD, LegendPosition.RIGHT));
        this.registerHandler(category, AuxAction.PIE_CHART.getAction(), new InsertChartHandler(Type.PIE, Grouping.STANDARD, LegendPosition.RIGHT));
        this.registerHandler(category, AuxAction.PIE_CHART_3D.getAction(), new InsertChartHandler(Type.PIE_3D, Grouping.STANDARD, LegendPosition.RIGHT));
        this.registerHandler(category, AuxAction.BAR_CHART.getAction(), new InsertChartHandler(Type.BAR, Grouping.STANDARD, LegendPosition.RIGHT));
        this.registerHandler(category, AuxAction.BAR_CHART_3D.getAction(), new InsertChartHandler(Type.BAR_3D, Grouping.STANDARD, LegendPosition.RIGHT));
        this.registerHandler(category, AuxAction.AREA_CHART.getAction(), new InsertChartHandler(Type.AREA, Grouping.STANDARD, LegendPosition.RIGHT));
        this.registerHandler(category, AuxAction.SCATTER_CHART.getAction(), new InsertChartHandler(Type.SCATTER, Grouping.STANDARD, LegendPosition.RIGHT));
        this.registerHandler(category, AuxAction.DOUGHNUT_CHART.getAction(), new InsertChartHandler(Type.DOUGHNUT, Grouping.STANDARD, LegendPosition.RIGHT));
        this.registerHandler(category, AuxAction.INSERT_PICTURE.getAction(), new InsertPictureHandler());
        this.registerHandler(category, AuxAction.ROW_HEIGHT.getAction(), new HeaderSizeHandler(HeaderType.ROW));
        this.registerHandler(category, AuxAction.COLUMN_WIDTH.getAction(), new HeaderSizeHandler(HeaderType.COLUMN));
        this.registerHandler(category, AuxAction.PASTE_SPECIAL.getAction(), new PasteSpecialDialogHandler());
        this.registerHandler(category, AuxAction.CUSTOM_SORT.getAction(), new CustomSortHandler());
        this.registerHandler(category, AuxAction.HYPERLINK.getAction(), new HyperlinkHandler());
        this.registerHandler(category, AuxAction.FORMAT_CELL.getAction(), new FormatCellHandler());
        this.registerHandler(category, AuxAction.INSERT_FUNCTION.getAction(), new InsertFunctionHandler());
        AbstractProtectedHandler folderhandler = new AbstractProtectedHandler() {
            protected boolean processAction(UserActionContext ctx) {
                return false;
            }
        };
        this.registerHandler(category, AuxAction.OTHER_CHART.getAction(), folderhandler);
        category = Category.KEYSTROKE.getName();
        this.registerHandler(category, "#del/picture", new DeletePictureHandler());
        this.registerHandler(category, "#del/chart", new DeleteChartHandler());
        category = Category.EVENT.getName();
        this.registerHandler(category, "onCellSelectionUpdate/move", new ShiftCellHandler());
        this.registerHandler(category, "onCellSelectionUpdate/resize", new AutoFillHandler());
        this.registerHandler(category, "onWidgetUpdate/move/picture", new MovePictureHandler());
        this.registerHandler(category, "onWidgetUpdate/resize/picture", new MovePictureHandler());
        this.registerHandler(category, "onWidgetUpdate/move/chart", new MoveChartHandler());
        this.registerHandler(category, "onWidgetUpdate/resize/chart", new MoveChartHandler());
    }

    public Set<String> getInterestedEvents() {
        LinkedHashSet evts = new LinkedHashSet();
        evts.addAll(super.getInterestedEvents());
        evts.addAll(this._interestedEvents);
        return evts;
    }

    protected boolean dispatchCellSelectionUpdateAction(UserActionContext ctx) {
        CellSelectionUpdateEvent evt = (CellSelectionUpdateEvent)ctx.getEvent();
        String action;
        if(evt.getAction() == CellSelectionAction.MOVE) {
            action = "onCellSelectionUpdate/move";
        } else {
            if(evt.getAction() != CellSelectionAction.RESIZE) {
                return false;
            }

            action = "onCellSelectionUpdate/resize";
        }

        ((UserActionContextImpl)ctx).setAction(action);
        ((UserActionContextImpl)ctx).setData("source", new AreaRef(evt.getOrigRow(), evt.getOrigColumn(), evt.getOrigLastRow(), evt.getOrigLastColumn()));
        boolean r = false;
        Iterator i$ = this.getHandlerList(ctx.getCategory(), ctx.getAction()).iterator();

        while(i$.hasNext()) {
            UserActionHandler uac = (UserActionHandler)i$.next();
            if(uac != null && uac.isEnabled(ctx.getBook(), ctx.getSheet())) {
                r |= uac.process(ctx);
            }
        }

        return r;
    }

    protected boolean dispatchWidgetKeyAction(UserActionContext ctx) {
        WidgetKeyEvent event = (WidgetKeyEvent)ctx.getEvent();
        String action = ctx.getAction();
        if(event != null) {
            action = this.getAction(event);
            if(Strings.isBlank(action)) {
                return false;
            }

            Object r = event.getData();
            if(r instanceof Picture) {
                action = action + '/' + "picture";
                ((UserActionContextImpl)ctx).setData("picture", r);
            } else if(r instanceof Chart) {
                action = action + '/' + "chart";
                ((UserActionContextImpl)ctx).setData("chart", r);
            }
        }

        ((UserActionContextImpl)ctx).setAction(action);
        boolean r1 = false;
        Iterator i$ = this.getHandlerList(ctx.getCategory(), ctx.getAction()).iterator();

        while(i$.hasNext()) {
            UserActionHandler uac = (UserActionHandler)i$.next();
            if(uac != null && uac.isEnabled(ctx.getBook(), ctx.getSheet())) {
                r1 |= uac.process(ctx);
            }
        }

        return r1;
    }

    protected boolean dispatchWidgetUpdateAction(UserActionContext ctx) {
        WidgetUpdateEvent event = (WidgetUpdateEvent)ctx.getEvent();
        Object widgetData = event.getData();
        String action = "";
        ((UserActionContextImpl)ctx).setData("anchor", event.getSheetAnchor());
        if(widgetData instanceof Picture) {
            switch(event.getAction().ordinal()) {
                case 1:
                    action = "onWidgetUpdate/resize/picture";
                    break;
                case 2:
                    action = "onWidgetUpdate/move/picture";
            }

            ((UserActionContextImpl)ctx).setData("picture", widgetData);
        } else if(widgetData instanceof Chart) {
            switch(event.getAction().ordinal()) {
                case 1:
                    action = "onWidgetUpdate/resize/chart";
                    break;
                case 2:
                    action = "onWidgetUpdate/move/chart";
            }

            ((UserActionContextImpl)ctx).setData("chart", widgetData);
        }

        ((UserActionContextImpl)ctx).setAction(action);
        boolean r = false;
        Iterator i$ = this.getHandlerList(ctx.getCategory(), ctx.getAction()).iterator();

        while(i$.hasNext()) {
            UserActionHandler uac = (UserActionHandler)i$.next();
            if(uac != null && uac.isEnabled(ctx.getBook(), ctx.getSheet())) {
                r |= uac.process(ctx);
            }
        }

        return r;
    }

    public void onEvent(Event event) throws Exception {
        super.onEvent(event);
        String nm = event.getName();
        if(this._interestedEvents.contains(nm)) {
            Spreadsheet spreadsheet = (Spreadsheet)event.getTarget();
            Book book = spreadsheet.getBook();
            Sheet sheet = spreadsheet.getSelectedSheet();
            String action = "";
            AreaRef selection = spreadsheet.getSelection();
            HashMap extraData = new HashMap();
            AreaRef visibleSelection = new AreaRef(selection.getRow(), selection.getColumn(), Math.min(spreadsheet.getMaxVisibleRows(), selection.getLastRow()), Math.min(spreadsheet.getMaxVisibleColumns(), selection.getLastColumn()));
            CellSelectionType _selectionType = CellSelectionType.CELL;
            SBook sbook = book == null?null:book.getInternalBook();
            if(sbook != null) {
                boolean ctx = selection.getColumn() == 0 && selection.getLastColumn() >= sbook.getMaxColumnIndex();
                boolean wholeColumn = selection.getRow() == 0 && selection.getLastRow() >= sbook.getMaxRowIndex();
                boolean wholeSheet = ctx && wholeColumn;
                _selectionType = wholeSheet?CellSelectionType.ALL:(ctx?CellSelectionType.ROW:(wholeColumn?CellSelectionType.COLUMN:CellSelectionType.CELL));
            }

            UserActionContextImpl ctx1;
            if("onWidgetCtrlKey".equals(nm)) {
                ctx1 = new UserActionContextImpl(spreadsheet, event, book, sheet, visibleSelection, _selectionType, extraData, Category.KEYSTROKE.getName(), action);
                this.dispatchWidgetKeyAction(ctx1);
            } else if("onWidgetUpdate".equals(nm)) {
                ctx1 = new UserActionContextImpl(spreadsheet, event, book, sheet, visibleSelection, _selectionType, extraData, Category.EVENT.getName(), action);
                this.dispatchWidgetUpdateAction(ctx1);
            } else if("onCellSelectionUpdate".equals(nm)) {
                ctx1 = new UserActionContextImpl(spreadsheet, event, book, sheet, visibleSelection, _selectionType, extraData, Category.EVENT.getName(), action);
                this.dispatchCellSelectionUpdateAction(ctx1);
            }

        }
    }
}
