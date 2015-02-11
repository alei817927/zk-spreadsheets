package com.makenv.sheets.processor.impl;

import com.makenv.sheets.http.respo.ErrorResponse;
import com.makenv.sheets.http.respo.SelectResponse;
import com.makenv.sheets.user.UserService;
import com.makenv.sheets.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alei on 2015/2/4.
 */
public class SelectProcessor extends AbstractProcessor {
    @Override
    public void doProc(HttpServletRequest request, HttpServletResponse response) {

        try {
            String region = request.getParameter("region");
            String year = request.getParameter("year");
            String book = request.getParameter("book");
            if (StringUtil.isEmpty(year)) {
                response.getWriter().println(this.toJSON(new ErrorResponse(1101, "请选择时间")));
                return;
            }
            if (StringUtil.isEmpty(region)) {
                response.getWriter().println(this.toJSON(new ErrorResponse(1102, "请选择区域")));
                return;
            }
            if (StringUtil.isEmpty(book)) {
                response.getWriter().println(this.toJSON(new ErrorResponse(1103, "请选择模板")));
                return;
            }
            int _year = Integer.parseInt(year);
            if (_year <= 0) {
                response.getWriter().println(this.toJSON(new ErrorResponse(1104, "请选择时间")));
                return;
            }
            if (!UserService.getInstance().isLogin()) {
                response.getWriter().println(this.toJSON(new ErrorResponse(1105, "请登录先")));
                return;
            }
            if (!UserService.getInstance().select(region, _year, book)) {
                response.getWriter().println(this.toJSON(new ErrorResponse(1106, "选择失败")));
                return;
            }
            response.getWriter().println(this.toJSON(new SelectResponse()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
