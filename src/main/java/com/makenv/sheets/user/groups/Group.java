package com.makenv.sheets.user.groups;

import com.makenv.sheets.user.Privileges;

import java.util.List;

/**
 * Created by alei on 2015/1/29.
 */
public interface Group {
    Privileges[] getPrivileges();

    String getName();
}
