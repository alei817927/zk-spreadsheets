//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl;

import org.zkoss.zss.model.SSheet;
import org.zkoss.zss.ui.impl.CellFormatHelper;
import org.zkoss.zss.ui.sys.CellDisplayLoader;

public class CellDisplayLoaderImpl implements CellDisplayLoader {
    public CellDisplayLoaderImpl() {
    }

    public String getCellHtmlText(SSheet sheet, int row, int column) {
        return CellFormatHelper.getRichCellHtmlText(sheet, row, column);
    }
}
