//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.http.WebManager;
import org.zkoss.zk.ui.sys.WebAppCtrl;
import org.zkoss.zssex.ui.impl.BridgeFix;

public abstract class UpdateBridge {
    protected final ServletContext _servletContext;
    protected final HttpServletRequest _request;
    protected final HttpServletResponse _response;
    protected final String _desktopId;

    public UpdateBridge(ServletContext servletContext, HttpServletRequest req, HttpServletResponse resp, String desktopId) {
        this._servletContext = servletContext;
        this._request = req;
        this._response = resp;
        this._desktopId = desktopId;
    }

    public boolean hasDesktop() {
        return findDesktopIfAny(this._servletContext, this._request, this._desktopId) != null;
    }

    public String process() {
        Desktop desktop = findDesktopIfAny(this._servletContext, this._request, this._desktopId);
        if(desktop == null) {
            throw new IllegalStateException("can\'t find zk desktop by id " + this._desktopId);
        } else {
            BridgeFix zbridge = new BridgeFix();
            zbridge.start(this._servletContext, this._request, this._response, desktop);

            String var4;
            try {
                this.process(desktop);
                String e = zbridge.getResult();
                var4 = e;
            } catch (Exception var8) {
                throw new RuntimeException(var8.getMessage(), var8);
            } finally {
                zbridge.close();
            }

            return var4;
        }
    }

    public static Desktop findDesktopIfAny(ServletContext ctx, HttpServletRequest request, String desktopId) {
        Session sess = WebManager.getSession(ctx, request, false);
        if(sess != null) {
            Desktop desktop = ((WebAppCtrl)sess.getWebApp()).getDesktopCache(sess).getDesktopIfAny(desktopId);
            return desktop;
        } else {
            return null;
        }
    }

    public static Desktop findDesktop(ServletContext ctx, HttpServletRequest request, String desktopId) {
        Session sess = WebManager.getSession(ctx, request, false);
        if(sess != null) {
            Desktop desktop = ((WebAppCtrl)sess.getWebApp()).getDesktopCache(sess).getDesktop(desktopId);
            return desktop;
        } else {
            return null;
        }
    }

    protected abstract void process(Desktop var1) throws Exception;
}
