package com.makenv.sheets.user;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

/**
 * Created by alei on 2015/2/9.
 */
public class LoginController extends SelectorComposer<Component> {

    //wire components
    @Wire
    Textbox account;
    @Wire
    Textbox password;
    @Wire
    Label message;

    @Listen("onClick=#login; onOK=#loginWin")
    public void doLogin() {
        String nm = account.getValue();
        String pd = password.getValue();
        if (!UserService.getInstance().login(nm, pd)) {
            message.setValue("account or password are not correct.");
            return;
        }
        message.setValue("登录成功");
        message.setSclass("");
        Executions.sendRedirect("/select.zul");
    }
}
