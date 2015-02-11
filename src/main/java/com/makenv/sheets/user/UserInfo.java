package com.makenv.sheets.user;

import com.makenv.sheets.user.groups.Group;
import com.makenv.sheets.util.MiUtil;

/**
 * Created by alei on 2015/1/29.
 */
public class UserInfo {
    private String userName;
    private String password;
    private Group group;

    public UserInfo(String userName, String password, Group group) {
        this.userName = userName;
        this.password = password;
        this.group = group;
    }

    public boolean checkPassword(String password) {
        return MiUtil.buildPassword(password).equals(this.password);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
