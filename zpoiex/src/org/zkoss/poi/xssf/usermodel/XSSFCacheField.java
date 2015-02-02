//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.poi.xssf.usermodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCacheField;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDateTime;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDiscretePr;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFieldGroup;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTGroupItems;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTIndex;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTNumber;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSharedItems;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTString;
import org.zkoss.poi.ss.usermodel.PivotCache.CacheField;

public class XSSFCacheField implements CacheField {
    private final CTCacheField _cacheField;

    XSSFCacheField(CTCacheField cacheField) {
        this._cacheField = cacheField;
        this._cacheField.getDatabaseField();
    }

    public void setName(String name) {
        this._cacheField.setName(name);
    }

    public String getName() {
        return this._cacheField.getName();
    }

    private static void addDates(List<CTDateTime> from, List<Object> to) {
        if(from != null) {
            Iterator i$ = from.iterator();

            while(i$.hasNext()) {
                CTDateTime e = (CTDateTime)i$.next();
                to.add(e.getV());
            }
        }

    }

    private static void addNumbers(List<CTNumber> from, List<Object> to) {
        if(from != null) {
            Iterator i$ = from.iterator();

            while(i$.hasNext()) {
                CTNumber e = (CTNumber)i$.next();
                to.add(Double.valueOf(e.getV()));
            }
        }

    }

    private static void addStrings(List<CTString> from, List<Object> to) {
        if(from != null) {
            Iterator i$ = from.iterator();

            while(i$.hasNext()) {
                CTString e = (CTString)i$.next();
                to.add(e.getV());
            }
        }

    }

    public List<Object> getSharedItems() {
        ArrayList list = new ArrayList();
        if(this._cacheField.getDatabaseField()) {
            CTSharedItems fieldGroup = this._cacheField.getSharedItems();
            if(fieldGroup != null) {
                if(fieldGroup.getContainsBlank()) {
                    list.add((Object)null);
                }

                addDates(fieldGroup.getDList(), list);
                addNumbers(fieldGroup.getNList(), list);
                addStrings(fieldGroup.getSList(), list);
                return list;
            }
        } else {
            CTFieldGroup fieldGroup1 = this._cacheField.getFieldGroup();
            if(fieldGroup1 != null) {
                CTGroupItems groupItems = fieldGroup1.getGroupItems();
                addDates(groupItems.getDList(), list);
                addNumbers(groupItems.getNList(), list);
                addStrings(groupItems.getSList(), list);
                return list;
            }
        }

        return list;
    }

    public void setDatabaseField(boolean databaseField) {
        this._cacheField.setDatabaseField(databaseField);
    }

    public boolean getDatabaseField() {
        return this._cacheField.getDatabaseField();
    }

    public List<Integer> getGroupDiscrete() {
        CTFieldGroup fieldGroup = this._cacheField.getFieldGroup();
        if(fieldGroup == null) {
            return null;
        } else {
            ArrayList list = new ArrayList();
            CTDiscretePr discretePr = fieldGroup.getDiscretePr();
            Iterator i$ = discretePr.getXList().iterator();

            while(i$.hasNext()) {
                CTIndex i = (CTIndex)i$.next();
                list.add(Integer.valueOf((int)i.getV()));
            }

            return list;
        }
    }

    public int getFieldGroup() {
        CTFieldGroup fieldGroup = this._cacheField.getFieldGroup();
        return fieldGroup == null?-1:(int)fieldGroup.getPar();
    }

    public int getGroupBase() {
        CTFieldGroup fieldGroup = this._cacheField.getFieldGroup();
        return fieldGroup == null?-1:(int)fieldGroup.getBase();
    }

    public long getNumberFormatId() {
        return this._cacheField.getNumFmtId();
    }
}
