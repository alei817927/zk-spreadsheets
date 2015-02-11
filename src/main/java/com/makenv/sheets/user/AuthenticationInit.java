package com.makenv.sheets.user;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;

import java.util.Map;

/**
 * Created by alei on 2015/2/9.
 */
public class AuthenticationInit implements Initiator {
    //services
    @Override
    public void doInit(Page page, Map<String, Object> map) throws Exception {
        if(!UserService.getInstance().isLogin()){
            Executions.sendRedirect("/login.zul");
        }
    }
}
