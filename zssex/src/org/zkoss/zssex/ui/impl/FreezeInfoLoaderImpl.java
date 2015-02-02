//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl;

import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zss.ui.sys.FreezeInfoLoader;

public class FreezeInfoLoaderImpl implements FreezeInfoLoader {
    public FreezeInfoLoaderImpl() {
    }

    public int getRowFreeze(Object sheet) {
        return sheet instanceof Sheet?((Sheet)sheet).getRowFreeze() - 1:(sheet instanceof SSheet?((SSheet)sheet).getViewInfo().getNumOfRowFreeze() - 1:-1);
    }

    public int getColumnFreeze(Object sheet) {
        return sheet instanceof Sheet?((Sheet)sheet).getColumnFreeze() - 1:(sheet instanceof SSheet?((SSheet)sheet).getViewInfo().getNumOfColumnFreeze() - 1:-1);
    }
}
