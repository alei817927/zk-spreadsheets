package com.makenv.sheets.util;

import com.makenv.sheets.user.OnlineUser;
import org.zkoss.zk.ui.util.Clients;

import java.io.File;
import java.util.Calendar;

/**
 * Created by alei on 2015/2/6.
 */
public class MiUtil {
    public static String buildToken(String userName, String password) {
        return MD5Util.MD5(String.format("%s-%s-%s", userName, password, Calendar.getInstance().getTimeInMillis()));
    }

    public static String buildPassword(String password) {
        return MD5Util.MD5(password);
    }

    public static void gotoLogin() {
        Clients.evalJavaScript("setTimeout(function(){window.location.href='/login.html';},1000);");
    }

    public static String getBookUserFilePath(OnlineUser onlineUser) {
        return String.format("%s%s%s%s%s-%s-%s.xlsx",
                FileUtil.getBookUserPath(),
                File.separator,
                onlineUser.getUserName(),
                File.separator, onlineUser.getSelectBook(),
                onlineUser.getSelectYear(),
                onlineUser.getSelectRegion());
    }
}
