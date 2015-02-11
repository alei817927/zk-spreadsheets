package com.makenv.sheets.user;

import com.makenv.sheets.user.groups.AGroup;
import com.makenv.sheets.user.groups.Group;
import com.makenv.sheets.util.FileUtil;
import com.makenv.sheets.util.MiUtil;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alei on 2015/2/3.
 */
public class UserService {
    private static UserService userService;
    private static Map<String, UserInfo> allUsers;

    public UserService() {
        allUsers = new HashMap<>();
    }

    public void init() {
        Group _group = new AGroup();
        UserInfo userInfo = new UserInfo("test", "098F6BCD4621D373CADE4E832627B4F6", _group);
        allUsers.put(userInfo.getUserName(), userInfo);
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public boolean login(String userName, String password) {
        if (!allUsers.containsKey(userName)) {
            return false;
        }
        UserInfo userInfo = allUsers.get(userName);
        if (!userInfo.checkPassword(password)) {
            return false;
        }
        Sessions.getCurrent().setAttribute("token", new OnlineUser(userInfo));
        return true;
    }

    public boolean select(String region, int year, String book) {
        OnlineUser onlineUser = getOnlineUser();
        if (onlineUser == null) {
            return false;
        }
        // Executions.getCurrent().getSession();
        onlineUser.setSelectRegion(region);
        onlineUser.setSelectYear(year);
        onlineUser.setSelectBook(book);
        return true;
    }

    public OnlineUser getOnlineUser() {
        OnlineUser onlineUser = (OnlineUser) Sessions.getCurrent().getAttribute("token");
        return onlineUser;
    }

    public boolean isLogin() {
        return getOnlineUser() != null;
    }

    public String getBookUserFilePath(Session session) {
        OnlineUser onlineUser = getOnlineUser();
        if (onlineUser == null) {
            return null;
        }
        FileUtil.checkAndCreateDir(onlineUser.getUserName());
        return MiUtil.getBookUserFilePath(onlineUser);
    }

    public void logout() {

    }
}
