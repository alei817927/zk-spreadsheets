package com.makenv.sheets.user;

import com.makenv.sheets.user.groups.Group;
import com.makenv.sheets.util.StringUtil;

/**
 * Created by alei on 2015/2/3.
 */
public class OnlineUser {
    private UserInfo userInfo;
    private String selectRegion;
    private String selectBook;
    private int selectYear;


    public String getUserName() {
        return userInfo.getUserName();
    }

    public Group getGroup() {
        return userInfo.getGroup();
    }


    public OnlineUser(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public String getSelectRegion() {
        return selectRegion;
    }

    public void setSelectRegion(String selectRegion) {
        this.selectRegion = selectRegion;
    }

    public boolean isSelValid() {
        return !StringUtil.isEmpty(selectRegion) && !StringUtil.isEmpty(selectBook) && selectYear > 0;
    }

    public String getSelectBook() {
        return selectBook;
    }

    public void setSelectBook(String selectBook) {
        this.selectBook = selectBook;
    }

    public int getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(int selectYear) {
        this.selectYear = selectYear;
    }
}
