package com.makenv.sheets.http;

import com.makenv.sheets.processor.Processor;
import com.makenv.sheets.processor.ProcessorType;
import com.makenv.sheets.user.UserService;
import com.makenv.sheets.util.FileUtil;
import org.zkoss.poi.util.SystemOutLogger;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;
import org.zkoss.zk.ui.http.WebManager;
import org.zkoss.zk.ui.sys.SessionsCtrl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alei on 2015/1/29.
 */
public class MiServlet extends DHtmlLayoutServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        FileUtil.initWebRootPath(getServletContext().getRealPath("/"));
        UserService.getInstance().init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        Session _session = Sessions.getCurrent();
        System.out.print(Sessions.getCount());
        String method = request.getParameter("method");
        Processor processor = ProcessorType.getProcessor(method);
        if (processor != null) {
            processor.doProcess(request, response);
        }
    }
}
