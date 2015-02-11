package com.makenv.sheets.user;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;

/**
 * Created by alei on 2015/2/9.
 */
public class LogoutController extends SelectorComposer<Component> {
    private static final long serialVersionUID = 1L;

    @Listen("onClick=#logout")
    public void doLogout() {
        UserService.getInstance().logout();
        Executions.sendRedirect("/login.zul");
    }
}
