//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.poi.xssf.usermodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTBoolean;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCacheField;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDateTime;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTIndex;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTNumber;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTRecord;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSharedItems;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTString;
import org.w3c.dom.Node;
import org.zkoss.poi.ss.usermodel.PivotCache.CacheRecord;

public class XSSFCacheRecord implements CacheRecord {
    private CTRecord _record;
    private List<CTCacheField> _fields;
    private List<Object> _data;

    XSSFCacheRecord(CTRecord record, List<CTCacheField> fields) {
        this._record = record;
        this._fields = fields;
    }

    public List<Object> getData() {
        if(this._data == null) {
            this.initData();
        }

        return this._data;
    }

    private void initData() {
        this._data = new ArrayList();
        HashMap dataMap = new HashMap();
        IndexMapper indexMapper = new IndexMapper(this._record.getDomNode());
        List xList = this._record.getXList();
        if(xList != null) {
            Iterator nList = xList.iterator();

            while(nList.hasNext()) {
                CTIndex sList = (CTIndex)nList.next();
                int dList = ((Integer)indexMapper.get(sList.getDomNode())).intValue();
                CTSharedItems bList = ((CTCacheField)this._fields.get(dList)).getSharedItems();
                List i = bList.getSList();
                String b = ((CTString)i.get((int)sList.getV())).getV();
                dataMap.put(Integer.valueOf(dList), b);
            }
        }

        List var11 = this._record.getNList();
        if(var11 != null) {
            Iterator var12 = var11.iterator();

            while(var12.hasNext()) {
                CTNumber var14 = (CTNumber)var12.next();
                int var17 = ((Integer)indexMapper.get(var14.getDomNode())).intValue();
                dataMap.put(Integer.valueOf(var17), Double.valueOf(var14.getV()));
            }
        }

        List var13 = this._record.getSList();
        int var21;
        if(var13 != null) {
            Iterator var15 = var13.iterator();

            while(var15.hasNext()) {
                CTString var18 = (CTString)var15.next();
                var21 = ((Integer)indexMapper.get(var18.getDomNode())).intValue();
                dataMap.put(Integer.valueOf(var21), var18.getV());
            }
        }

        List var16 = this._record.getDList();
        if(var16 != null) {
            Iterator var19 = var16.iterator();

            while(var19.hasNext()) {
                CTDateTime var22 = (CTDateTime)var19.next();
                int var24 = ((Integer)indexMapper.get(var22.getDomNode())).intValue();
                dataMap.put(Integer.valueOf(var24), var22.getV());
            }
        }

        List var20 = this._record.getBList();
        if(var20 != null) {
            Iterator var23 = var20.iterator();

            while(var23.hasNext()) {
                CTBoolean var25 = (CTBoolean)var23.next();
                int index = ((Integer)indexMapper.get(var25.getDomNode())).intValue();
                dataMap.put(Integer.valueOf(index), Boolean.valueOf(var25.getV()));
            }
        }

        for(var21 = 0; var21 < dataMap.size(); ++var21) {
            this._data.add(dataMap.get(Integer.valueOf(var21)));
        }

    }

    private class IndexMapper extends HashMap<Node, Integer> {
        IndexMapper(Node parent) {
            int i = 0;

            for(Node chd = parent.getFirstChild(); chd != null; chd = chd.getNextSibling()) {
                this.put(chd, Integer.valueOf(i++));
            }

        }
    }
}
