//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import java.util.List;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.Range.SortDataOption;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
import org.zkoss.zss.ui.impl.undo.SortCellAction;
import org.zkoss.zss.ui.sys.UndoableActionManager;
import org.zkoss.zssex.ui.dialog.CustomSortCtrl;
import org.zkoss.zssex.ui.dialog.CustomSortCtrl.SortRule;
import org.zkoss.zssex.ui.dialog.impl.DialogCallbackEvent;

public class CustomSortHandler extends AbstractProtectedHandler {
    public CustomSortHandler() {
    }

    protected boolean processAction(final UserActionContext ctx) {
        final Sheet sheet = ctx.getSheet();
        final AreaRef selection = ctx.getSelection();
        Ranges.range(sheet, selection);
        CustomSortCtrl.show(new EventListener<DialogCallbackEvent>() {
            public void onEvent(DialogCallbackEvent event) throws Exception {
                if("onOK".equals(event.getName())) {
                    boolean caseSensitive = ((Boolean)event.getData("caseSensitive")).booleanValue();
                    boolean hasHeader = ((Boolean)event.getData("hasHeader")).booleanValue();
                    boolean byRow = "row".equals((String)event.getData("orientation"));
                    List rules = (List)event.getData("rules");
                    if(rules.size() < 1) {
                        return;
                    }

                    Object[][] sortInfo = new Object[3][];
                    int row = selection.getRow();
                    int col = selection.getColumn();
                    int lastrow = selection.getLastRow();
                    int lastcol = selection.getLastColumn();

                    for(int uam = 0; uam < 3; ++uam) {
                        Object[] info = new Object[3];
                        if(uam < rules.size()) {
                            SortRule rule = (SortRule)rules.get(uam);
                            if(byRow) {
                                info[0] = Ranges.range(sheet, row + rule.sortIndex, col, row + rule.sortIndex, lastcol);
                            } else {
                                info[0] = Ranges.range(sheet, row, col + rule.sortIndex, lastrow, col + rule.sortIndex);
                            }

                            info[1] = Boolean.valueOf(!rule.ascending);
                        } else {
                            info[0] = null;
                            info[1] = Boolean.valueOf(false);
                        }

                        sortInfo[uam] = info;
                    }

                    UndoableActionManager var14 = ctx.getSpreadsheet().getUndoableActionManager();
                    var14.doAction(new SortCellAction(Labels.getLabel("zss.undo.sort"), sheet, selection.getRow(), selection.getColumn(), selection.getLastRow(), selection.getLastColumn(), (Range)sortInfo[0][0], ((Boolean)sortInfo[0][1]).booleanValue(), (SortDataOption)null, (Range)sortInfo[1][0], ((Boolean)sortInfo[1][1]).booleanValue(), (SortDataOption)null, (Range)sortInfo[2][0], ((Boolean)sortInfo[2][1]).booleanValue(), (SortDataOption)null, hasHeader, caseSensitive, byRow));
                }

            }
        }, sheet, selection);
        return true;
    }
}
