package com.makenv.sheets.user.groups;

import com.makenv.sheets.user.Privileges;

/**
 * Created by alei on 2015/2/3.
 */
public class AGroup implements Group {
    @Override
    public Privileges[] getPrivileges() {
        return new Privileges[]{
                Privileges.ALL
        };
    }

    @Override
    public String getName() {
        return "AGroup";
    }
}
