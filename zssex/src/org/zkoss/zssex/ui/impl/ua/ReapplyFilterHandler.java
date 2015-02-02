//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl.ua;

import org.zkoss.zss.api.Ranges;
import org.zkoss.zss.api.SheetOperationUtil;
import org.zkoss.zss.api.model.Book;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.api.model.Book.BookType;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;

public class ReapplyFilterHandler extends AbstractProtectedHandler {
    public ReapplyFilterHandler() {
    }

    public boolean isEnabled(Book book, Sheet sheet) {
        return book != null && sheet != null && !sheet.isProtected() && book.getType() != BookType.XLS;
    }

    protected boolean processAction(UserActionContext ctx) {
        SheetOperationUtil.applyAutoFilter(Ranges.range(ctx.getSheet()));
        ctx.clearClipboard();
        return true;
    }
}
