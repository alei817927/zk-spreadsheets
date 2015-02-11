package com.makenv.sheets.contr;

import com.makenv.sheets.repository.BookInfo;
import com.makenv.sheets.repository.BookRepositoryFactory;
import com.makenv.sheets.user.UserService;
import com.makenv.sheets.util.StringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import java.util.Collection;
import java.util.Map;

/**
 * Created by alei on 2015/2/10.
 */
public class SelectController extends SelectorComposer<Component> {
    @Wire
    Grid bookList;
    @Wire
    Grid yearList;
    @Wire
    Grid regionList;
    private int selectYear;
    private String selectBook;
    private String selectRegion;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
        Rows rows = bookList.getRows();
        Map<String, BookInfo> _tmpls = BookRepositoryFactory.getInstance().getRepository().getAllTemplates();
        Collection<BookInfo> bookInfos = _tmpls.values();
        for (BookInfo _bookInfo : bookInfos) {
            rows.appendChild(constructBookRow(_bookInfo.getName()));
        }
        Rows yearRows = yearList.getRows();
        for (int i = 2000; i < 2020; i++) {
            yearRows.appendChild(constructYearRow(i));
        }

        Rows regionRows = regionList.getRows();
        for (int i = 1; i < 10; i++) {
            regionRows.appendChild(constructRegionRow("region" + i));
        }
    }

    private Row constructRegionRow(final String region) {
        Row row = new Row();
        Label lab = new Label(region);
        row.appendChild(lab);
        row.setSclass("sidebar-fn");
        EventListener<Event> actionListener = new SerializableEventListener<Event>() {
            private static final long serialVersionUID = 1L;

            public void onEvent(Event event) throws Exception {
                selectRegion = region;
                trySelect();
            }
        };
        row.addEventListener(Events.ON_CLICK, actionListener);
        return row;
    }

    private Row constructYearRow(final int year) {
        Row row = new Row();
        Label lab = new Label(year + "");
        row.appendChild(lab);
        row.setSclass("sidebar-fn");
        EventListener<Event> actionListener = new SerializableEventListener<Event>() {
            private static final long serialVersionUID = 1L;

            public void onEvent(Event event) throws Exception {
                selectYear = year;
                trySelect();
            }
        };
        row.addEventListener(Events.ON_CLICK, actionListener);
        return row;
    }

    private void trySelect() {
        if (StringUtil.isEmpty(selectRegion)) {
            return;
        }
        if (StringUtil.isEmpty(selectBook)) {
            return;
        }
        if (selectYear <= 0) {
            return;
        }
        if (UserService.getInstance().select(selectRegion, selectYear, selectBook)) {
            Executions.getCurrent().sendRedirect("/");
        }
    }

    private Row constructBookRow(String bookName) {
        final String[] _bookNameArr = bookName.split("-");
        Row row = new Row();
        Label lab = new Label(_bookNameArr[1].split("\\.")[0]);
        row.appendChild(lab);
        row.setSclass("sidebar-fn");
        EventListener<Event> actionListener = new SerializableEventListener<Event>() {
            private static final long serialVersionUID = 1L;

            public void onEvent(Event event) throws Exception {
                selectBook = _bookNameArr[0];
                trySelect();
            }
        };
        row.addEventListener(Events.ON_CLICK, actionListener);
        return row;
    }
}
