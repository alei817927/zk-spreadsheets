package com.makenv.sheets.http;

import com.makenv.sheets.util.FileUtil;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by alei on 2015/1/29.
 */
public class MiServlet extends DHtmlLayoutServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        FileUtil.initWebRootPath(getServletContext().getRealPath("/"));
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        String date = request.getParameter("date");
        super.doGet(request,response);
    }
}
