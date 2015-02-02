//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.ui.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.zkoss.json.JSONArray;
import org.zkoss.poi.ss.usermodel.ZssContext;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zss.api.model.Sheet;
import org.zkoss.zss.api.model.impl.SheetImpl;
import org.zkoss.zss.model.CellRegion;
import org.zkoss.zss.model.SCell;
import org.zkoss.zss.model.SDataValidation;
import org.zkoss.zss.model.SSheet;
import org.zkoss.zss.model.sys.EngineFactory;
import org.zkoss.zss.model.sys.format.FormatContext;
import org.zkoss.zss.model.sys.format.FormatEngine;
import org.zkoss.zss.model.sys.format.FormatResult;
import org.zkoss.zss.ui.sys.DataValidationHandler;
import org.zkoss.zssex.ui.ValidationHelper;

public class DataValidationHandlerImpl implements DataValidationHandler {
    public DataValidationHandlerImpl() {
    }

    public List<Map<String, Object>> loadDataValidtionJASON(Sheet sheet) {
        SSheet xsheet = ((SheetImpl)sheet).getNative();
        return this.convertDataValidationToJSON(xsheet, (List)xsheet.getDataValidations());
    }

    private List<Map<String, Object>> convertDataValidationToJSON(SSheet sheet, List<SDataValidation> dvs) {
        if(dvs != null && !dvs.isEmpty()) {
            ArrayList results = new ArrayList(dvs.size());
            Iterator i$ = dvs.iterator();

            while(i$.hasNext()) {
                SDataValidation dv = (SDataValidation)i$.next();
                results.add(this.convertDataValidationToJSON(sheet, (SDataValidation)dv));
            }

            return results;
        } else {
            return null;
        }
    }

    private Map<String, Object> convertDataValidationToJSON(SSheet sheet, SDataValidation dv) {
        ArrayList addrmapary = new ArrayList(dv.getRegions().size() * 4 / 3 + 1);
        Iterator validMap = dv.getRegions().iterator();

        int len$;
        int i$;
        while(validMap.hasNext()) {
            CellRegion validationList = (CellRegion)validMap.next();
            int jsonAry = validationList.getColumn();
            int arr$ = validationList.getLastColumn();
            len$ = validationList.getRow();
            i$ = validationList.getLastRow();
            HashMap v = new HashMap();
            v.put("left", Integer.valueOf(jsonAry));
            v.put("top", Integer.valueOf(len$));
            v.put("right", Integer.valueOf(arr$));
            v.put("bottom", Integer.valueOf(i$));
            addrmapary.add(v);
        }

        HashMap var11 = new HashMap();
        var11.put("id", dv.getId());
        var11.put("rangeList", addrmapary);
        if(dv.isShowInput()) {
            var11.put("showPrompt", Boolean.valueOf(true));
            var11.put("promptTitle", dv.getInputTitle());
            var11.put("promptText", dv.getInputMessage());
        }

        String[] var12 = this.getValidationList(dv);
        if(var12 != null && dv.isInCellDropdown()) {
            var11.put("showButton", Boolean.valueOf(true));
            JSONArray var13 = new JSONArray();
            String[] var14 = var12;
            len$ = var12.length;

            for(i$ = 0; i$ < len$; ++i$) {
                String var15 = var14[i$];
                var13.add(var15);
            }

            var11.put("validationList", var13);
        }

        return var11;
    }

    private String[] getValidationList(SDataValidation dv) {
        LinkedList list = new LinkedList();
        if(dv.hasReferToCellList()) {
            FormatEngine s = EngineFactory.getInstance().createFormatEngine();
            Iterator i = dv.getReferToCellList().iterator();

            while(i.hasNext()) {
                SCell val = (SCell)i.next();
                FormatResult r = s.format(val, new FormatContext(ZssContext.getCurrent().getLocale()));
                list.add(r.getText());
            }
        } else {
            int var7 = dv.getNumOfValue1();
            if(dv.hasReferToCellList()) {
                return this.getValidationList(dv);
            }

            for(int var8 = 0; var8 < var7; ++var8) {
                Object var9 = dv.getValue1(var8);
                list.add(var9 == null?"":var9.toString());
            }
        }

        return (String[])list.toArray(new String[list.size()]);
    }

    public boolean validate(Sheet sheet, int row, int col, String editText, EventListener callback) {
        return (new ValidationHelper()).validate(sheet, row, col, editText, callback);
    }
}
