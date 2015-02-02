//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula;

import org.zkoss.xel.XelContext;

public class XelContextHolder {
    private static ThreadLocal<XelContext> _ctx = new ThreadLocal();

    public XelContextHolder() {
    }

    public static void setXelContext(XelContext value) {
        _ctx.set(value);
    }

    public static XelContext getXelContext() {
        return (XelContext)_ctx.get();
    }
}
