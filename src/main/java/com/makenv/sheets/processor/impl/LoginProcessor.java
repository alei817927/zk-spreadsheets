package com.makenv.sheets.processor.impl;

import com.makenv.sheets.http.respo.ErrorResponse;
import com.makenv.sheets.http.respo.LoginResponse;
import com.makenv.sheets.user.UserService;
import com.makenv.sheets.util.StringUtil;
import org.zkoss.zk.ui.Sessions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alei on 2015/2/4.
 */
public class LoginProcessor extends AbstractProcessor {
    @Override
    public void doProc(HttpServletRequest request, HttpServletResponse response) {
        try {
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            if (StringUtil.isEmpty(userName)) {
                response.getWriter().println(this.toJSON(new ErrorResponse(1001, "用户名不能为空")));
                return;
            }
            if (StringUtil.isEmpty(password)) {
                response.getWriter().println(this.toJSON(new ErrorResponse(1002, "密码不能为空")));
                return;
            }
            if (!UserService.getInstance().login(userName, password)) {
                response.getWriter().println(this.toJSON(new ErrorResponse(1003, "账号或密码错误")));
                return;
            }
            response.getWriter().println(this.toJSON(new LoginResponse()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
