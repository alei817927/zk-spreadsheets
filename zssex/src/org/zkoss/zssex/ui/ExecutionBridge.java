//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zkoss.io.NullWriter;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.GenericRichlet;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.sys.DesktopCtrl;
import org.zkoss.zkplus.embed.Renders;

public abstract class ExecutionBridge {
    protected final ServletContext _servletContext;
    protected final HttpServletRequest _request;
    protected final HttpServletResponse _response;
    private Desktop _desktop;

    public ExecutionBridge(ServletContext servletContext, HttpServletRequest req, HttpServletResponse resp) {
        this._servletContext = servletContext;
        this._request = req;
        this._response = resp;
    }

    public void process() {
        if(this._desktop != null) {
            throw new IllegalStateException("can\'t process twice");
        } else {
            NullWriter dummy = new NullWriter();

            try {
                Renders.render(this._servletContext, this._request, this._response, new GenericRichlet() {
                    public void service(Page page) throws Exception {
                        ExecutionBridge.this._desktop = page.getDesktop();
                        ExecutionBridge.this.process(Executions.getCurrent());
                        ((DesktopCtrl)ExecutionBridge.this._desktop).removePage(page);
                    }
                }, (String)null, dummy);
            } catch (Exception var3) {
                throw new RuntimeException(var3.getMessage(), var3);
            }
        }
    }

    protected abstract void process(Execution var1) throws Exception;
}
