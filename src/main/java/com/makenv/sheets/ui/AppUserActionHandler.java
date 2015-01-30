package com.makenv.sheets.ui;

import org.zkoss.zss.api.model.Book;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.UserActionContext;
import org.zkoss.zss.ui.UserActionHandler;

/**
 * Created by alei on 2015/1/30.
 */
public class AppUserActionHandler implements UserActionHandler {
    @Override
    public boolean isEnabled(Book book, Sheet sheet) {
        return false;
    }

    @Override
    public boolean process(UserActionContext ctx) {
        return false;
    }
}
