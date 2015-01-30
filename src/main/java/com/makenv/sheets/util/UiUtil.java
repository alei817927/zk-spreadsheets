package com.makenv.sheets.util;

import org.zkoss.lang.Library;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ext.Selectable;

/**
 * Created by alei on 2015/1/27.
 */
public class UiUtil {

    public static Object getSingleSelection(Selectable selection) {
        if (selection != null && selection.getSelection().size() > 0) {
            return selection.getSelection().iterator().next();
        }
        return null;
    }

    public static void showInfoMessage(String message, long time) {
        Clients.showNotification(message, "info", null, null, 2000, true);
    }

    public static void showInfoMessage(String message) {
        showInfoMessage(message, 2000);
    }

    public static void showWarnMessage(String message, long time) {
        Clients.showNotification(message, "warn", null, null, 2000, true);
    }

    public static void showWarnMessage(String message) {
        showWarnMessage(message, 2000);
    }

    public static boolean isRepositoryReadonly() {
        String readonly = Library.getProperty("zssapp.bookrepostory.readonly", "false").toLowerCase();
        return "true".equals(readonly);
    }
}
