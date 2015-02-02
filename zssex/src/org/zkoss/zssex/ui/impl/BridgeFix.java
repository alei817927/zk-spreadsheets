//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.http.I18Ns;
import org.zkoss.zk.ui.http.WebManager;
import org.zkoss.zkplus.embed.Bridge;

public class BridgeFix {
    Object _oldI18NObj;
    Bridge _bridge;
    HttpServletRequest _request;

    public BridgeFix() {
    }

    public Bridge start(ServletContext svlctx, HttpServletRequest request, HttpServletResponse response, Desktop desktop) {
        this._request = request;
        Bridge bridge = Bridge.start(svlctx, request, response, desktop);
        if(Locales.getThreadLocal() == null) {
            Session sess = WebManager.getSession(svlctx, request);
            WebApp wapp = sess.getWebApp();
            this._oldI18NObj = I18Ns.setup(sess, request, response, wapp.getConfiguration().getResponseCharset());
        }

        return this._bridge = bridge;
    }

    public String getResult() {
        return this._bridge.getResult();
    }

    public void close() {
        if(this._oldI18NObj != null) {
            I18Ns.cleanup(this._request, this._oldI18NObj);
            this._oldI18NObj = null;
        }

        this._bridge.close();
        this._bridge = null;
        this._request = null;
    }
}
