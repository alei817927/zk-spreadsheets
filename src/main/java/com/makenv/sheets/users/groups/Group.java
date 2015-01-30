package com.makenv.sheets.users.groups;

import com.makenv.sheets.users.Privileges;

import java.util.List;

/**
 * Created by alei on 2015/1/29.
 */
public interface Group {
    List<Privileges> getPrivileges();

    String getName();
}
