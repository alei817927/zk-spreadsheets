package com.makenv.sheets.ui;

import com.makenv.sheets.repository.BookInfo;
import com.makenv.sheets.repository.BookRepository;
import com.makenv.sheets.repository.BookRepositoryFactory;
import com.makenv.sheets.user.UserService;
import com.makenv.sheets.util.MiUtil;
import com.makenv.sheets.util.UiUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zss.api.model.Book;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.ui.*;
import org.zkoss.zss.ui.event.Events;
import org.zkoss.zss.ui.impl.DefaultUserActionManagerCtrl;

import java.io.IOException;

/**
 * Created by alei on 2015/1/27.
 * http://books.zkoss.org/wiki/ZK%20Developer%27s%20Reference
 */
public class AppCtrl extends CtrlBase<Component> {
    private static Logger log = LoggerFactory.getLogger(AppCtrl.class);
    private static final long serialVersionUID = 1L;
    @Wire
    Spreadsheet ss;
    private Book loadedBook;

    public AppCtrl() {
        super(true);
    }

    public void doBeforeComposeChildren(Component comp) throws Exception {
        super.doBeforeComposeChildren(comp);
        comp.setAttribute(APPCOMP, comp);
    }

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        boolean readonly = UiUtil.isRepositoryReadonly();
        UserActionManager uam = ss.getUserActionManager();
        if (!readonly) {
            uam.setHandler(DefaultUserActionManagerCtrl.Category.AUXACTION.getName(), AuxAction.SAVE_BOOK.getAction(), new UserActionHandler() {

                @Override
                public boolean process(UserActionContext ctx) {
                    return doSaveBook();
                }

                @Override
                public boolean isEnabled(Book book, Sheet sheet) {
                    return book != null;
                }
            });
        }

        ss.addEventListener(Events.ON_SHEET_SELECT, new EventListener<Event>() {
            @Override
            public void onEvent(Event event) throws Exception {
                onSheetSelect();
            }
        });

        ss.addEventListener(Events.ON_AFTER_UNDOABLE_MANAGER_ACTION, new EventListener<Event>() {
            @Override
            public void onEvent(Event event) throws Exception {
                onAfterUndoableManagerAction();
            }
        });

        doOpenBook();
    }

    /**
     * 从默认模板打开文件
     */
    void doOpenBook() {
        if (!UserService.getInstance().isLogin()) {
            Clients.showNotification("请先登录", true);
            MiUtil.gotoLogin();
            return;
        }
        BookRepository rep = getRepository();
        BookInfo bookInfo = rep.getBookInfo();
        if (bookInfo == null) {
            bookInfo = rep.getTemplate(UserService.getInstance().getOnlineUser().getSelectBook());
        }
        try {
            loadedBook = rep.load(bookInfo);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            UiUtil.showWarnMessage("Can't load a new book");
            return;
        }
        ss.setBook(loadedBook);
        pushAppEvent(AppEvts.ON_LOADED_BOOK, loadedBook);
        pushAppEvent(AppEvts.ON_CHANGED_SPREADSHEET, ss);
        updatePageInfo();
    }

    private void onAfterUndoableManagerAction() {
        pushAppEvent(AppEvts.ON_UPDATE_UNDO_REDO, ss);
    }

    private boolean doSaveBook() {
        if (!UserService.getInstance().isLogin()) {
            Clients.showNotification("请先登录", true);
            return false;
        }
        if (UiUtil.isRepositoryReadonly()) {
            Clients.showNotification("文档只读", true);
            return false;
        }
        if (loadedBook == null) {
            UiUtil.showWarnMessage("Please load a book first before save it");
            return false;
        }
        BookRepository rep = getRepository();
        try {
            if (rep.save(loadedBook)) {
                UiUtil.showInfoMessage("保存成功");
                pushAppEvent(AppEvts.ON_SAVED_BOOK, loadedBook);
                updatePageInfo();
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            UiUtil.showWarnMessage("保存失败");
            return false;
        }
        return true;
    }

    private void onSheetSelect() {
        pushAppEvent(AppEvts.ON_CHANGED_SPREADSHEET, ss);
    }

    private void updatePageInfo() {

        String name = loadedBook.getBookName();
        getPage().setTitle(name != null ? "Book : " + name : "");

    }

    private BookRepository getRepository() {
        return BookRepositoryFactory.getInstance().getRepository();
    }
}
