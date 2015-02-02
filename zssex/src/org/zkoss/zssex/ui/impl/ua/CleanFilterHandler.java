//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.util.resource.Labels;
import org.zkoss.zss.api.AreaRef;
import org.zkoss.zss.api.Range;
import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.SheetOperationUtil;
import org.zkoss.zss.api.model.Book;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.api.model.Book.BookType;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;

public class CleanFilterHandler extends AbstractProtectedHandler {
    public CleanFilterHandler() {
    }

    public boolean isEnabled(Book book, Sheet sheet) {
        return book != null && sheet != null && !sheet.isProtected() && book.getType() != BookType.XLS;
    }

    protected boolean processAction(UserActionContext ctx) {
        Sheet sheet = ctx.getSheet();
        AreaRef selection = ctx.getSelection();
        Range range = Ranges.range(sheet, selection);
        Range filterRange = range.findAutoFilterRange();
        if(!range.isAutoFilterEnabled() && filterRange == null) {
            this.showInfoMessage(Labels.getLabel("zssex.actionhandler.msg.cant_find_filter_range"));
            return true;
        } else {
            SheetOperationUtil.resetAutoFilter(range);
            ctx.clearClipboard();
            return true;
        }
    }
}
