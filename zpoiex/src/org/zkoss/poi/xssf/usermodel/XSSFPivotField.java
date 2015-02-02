//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.poi.xssf.usermodel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTItem;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTItems;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotField;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STAxis;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STFieldSortType;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STItemType;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STItemType.Enum;
import org.zkoss.poi.ss.usermodel.Calculation;
import org.zkoss.poi.ss.usermodel.PivotField;
import org.zkoss.poi.ss.usermodel.PivotTable;
import org.zkoss.poi.ss.usermodel.PivotCache.CacheField;
import org.zkoss.poi.ss.usermodel.PivotField.FieldGroup;
import org.zkoss.poi.ss.usermodel.PivotField.Item;
import org.zkoss.poi.ss.usermodel.PivotField.SortType;
import org.zkoss.poi.ss.usermodel.PivotField.Item.Type;

public class XSSFPivotField implements PivotField {
    private CTPivotField _pivotField;
    private CacheField _cacheField;
    private PivotTable _pivotTable;

    XSSFPivotField(CTPivotField pivotField, CacheField cacheField, PivotTable pivotTable) {
        this._pivotField = pivotField;
        this._cacheField = cacheField;
        this._pivotTable = pivotTable;
    }

    private void checkLastItemIsNotData(CTItems items) {
        List itemList = items.getItemList();
        if(((CTItem)itemList.get(itemList.size() - 1)).getT() == STItemType.DATA) {
            throw new IllegalArgumentException("The last item shall not be data type");
        }
    }

    public void setItems(List<Object> items) {
        CTItems ctItems = this._pivotField.getItems();
        if(ctItems != null) {
            this._pivotField.unsetItems();
        }

        ctItems = this._pivotField.addNewItems();
        IndexMapper indexMapper = new IndexMapper(this._cacheField.getSharedItems());
        Iterator i$ = items.iterator();

        while(i$.hasNext()) {
            Object i = i$.next();
            if(i instanceof Item.Type) {
                Item.Type idx = (Item.Type)i;
                ctItems.addNewItem().setT(XSSFItem.getCTItemType(idx));
            } else {
                int idx1 = indexMapper.getIndex(i);
                if(idx1 < 0) {
                    throw new IllegalArgumentException("Expecting " + i + ", but not found in SharedItem");
                }

                ctItems.addNewItem().setX((long)idx1);
            }
        }

        if(this._pivotField.getDefaultSubtotal()) {
            this.checkLastItemIsNotData(ctItems);
        }

        ctItems.setCount((long)ctItems.getItemList().size());
    }

    public List<Item> getItems() {
        CTItems items = this._pivotField.getItems();
        if(items == null) {
            return Collections.emptyList();
        } else {
            List values = this._cacheField.getSharedItems();
            ArrayList list = new ArrayList();
            Iterator i$ = items.getItemList().iterator();

            while(i$.hasNext()) {
                CTItem item = (CTItem)i$.next();
                Enum type = item.getT();
                if(type == STItemType.DATA) {
                    list.add(new XSSFItem(item, values.get((int)item.getX())));
                } else if(type == STItemType.DEFAULT) {
                    list.add(new XSSFItem(item, this._pivotTable.getGrandTotalCaption()));
                } else {
                    list.add(new XSSFItem(item, (Object)null));
                }
            }

            return list;
        }
    }

    public FieldGroup getFieldGroup() {
        int index = this._cacheField.getFieldGroup();
        return index >= 0?new XSSFFieldGroup(this):null;
    }

    public void setName(String name) {
        this._cacheField.setName(name);
    }

    public String getName() {
        return this._cacheField.getName();
    }

    public void setType(org.zkoss.poi.ss.usermodel.PivotField.Type type) {
        switch(type.ordinal()) {
            case 1:
                if(this._pivotField.getDataField()) {
                    this._pivotField.unsetDataField();
                }

                this._pivotField.setAxis(STAxis.AXIS_ROW);
                break;
            case 2:
                if(this._pivotField.getDataField()) {
                    this._pivotField.unsetDataField();
                }

                this._pivotField.setAxis(STAxis.AXIS_COL);
                break;
            case 3:
                if(this._pivotField.getAxis() != null) {
                    this._pivotField.unsetAxis();
                }

                this._pivotField.setDataField(true);
        }

    }

    CTPivotField getCTPivotField() {
        return this._pivotField;
    }

    public org.zkoss.poi.ss.usermodel.PivotField.Type getType() {
        if(this._pivotField.getDataField()) {
            return org.zkoss.poi.ss.usermodel.PivotField.Type.DATA;
        } else {
            org.openxmlformats.schemas.spreadsheetml.x2006.main.STAxis.Enum type = this._pivotField.getAxis();
            return type == null?null:(type == STAxis.AXIS_COL?org.zkoss.poi.ss.usermodel.PivotField.Type.COLUMN:(type == STAxis.AXIS_ROW?org.zkoss.poi.ss.usermodel.PivotField.Type.ROW:null));
        }
    }

    public void setDefaultSubtotal(boolean defaultSubtotal) {
        this._pivotField.setDefaultSubtotal(defaultSubtotal);
    }

    public boolean getDefaultSubtotal() {
        return this._pivotField.getDefaultSubtotal();
    }

    public void setSubtotals(Set<Calculation> subtotals) {
        if(subtotals.contains(Calculation.SUM)) {
            this._pivotField.setSumSubtotal(true);
        } else if(this._pivotField.getSumSubtotal()) {
            this._pivotField.unsetSumSubtotal();
        }

        if(subtotals.contains(Calculation.COUNT)) {
            this._pivotField.setCountASubtotal(true);
        } else if(this._pivotField.getCountASubtotal()) {
            this._pivotField.unsetCountASubtotal();
        }

        if(subtotals.contains(Calculation.AVERAGE)) {
            this._pivotField.setAvgSubtotal(true);
        } else if(this._pivotField.getAvgSubtotal()) {
            this._pivotField.unsetAvgSubtotal();
        }

        if(subtotals.contains(Calculation.MAX)) {
            this._pivotField.setMaxSubtotal(true);
        } else if(this._pivotField.getMaxSubtotal()) {
            this._pivotField.unsetMaxSubtotal();
        }

        if(subtotals.contains(Calculation.MIN)) {
            this._pivotField.setMinSubtotal(true);
        } else if(this._pivotField.getMinSubtotal()) {
            this._pivotField.unsetMinSubtotal();
        }

        if(subtotals.contains(Calculation.PRODUCT)) {
            this._pivotField.setProductSubtotal(true);
        } else if(this._pivotField.getProductSubtotal()) {
            this._pivotField.setProductSubtotal(true);
        }

        if(subtotals.contains(Calculation.COUNT_NUMS)) {
            this._pivotField.setCountSubtotal(true);
        } else if(this._pivotField.getCountSubtotal()) {
            this._pivotField.unsetCountSubtotal();
        }

        if(subtotals.contains(Calculation.STD_DEV)) {
            this._pivotField.setStdDevSubtotal(true);
        } else if(this._pivotField.getStdDevSubtotal()) {
            this._pivotField.unsetStdDevSubtotal();
        }

        if(subtotals.contains(Calculation.STD_DEV_P)) {
            this._pivotField.setStdDevPSubtotal(true);
        } else if(this._pivotField.getStdDevPSubtotal()) {
            this._pivotField.unsetStdDevPSubtotal();
        }

    }

    public Set<Calculation> getSubtotals() {
        LinkedHashSet set = new LinkedHashSet();
        if(this._pivotField.getSumSubtotal()) {
            set.add(Calculation.SUM);
        }

        if(this._pivotField.getCountASubtotal()) {
            set.add(Calculation.COUNT);
        }

        if(this._pivotField.getAvgSubtotal()) {
            set.add(Calculation.AVERAGE);
        }

        if(this._pivotField.getMaxSubtotal()) {
            set.add(Calculation.MAX);
        }

        if(this._pivotField.getMinSubtotal()) {
            set.add(Calculation.MIN);
        }

        if(this._pivotField.getProductSubtotal()) {
            set.add(Calculation.PRODUCT);
        }

        if(this._pivotField.getCountSubtotal()) {
            set.add(Calculation.COUNT_NUMS);
        }

        if(this._pivotField.getStdDevSubtotal()) {
            set.add(Calculation.STD_DEV);
        }

        if(this._pivotField.getStdDevPSubtotal()) {
            set.add(Calculation.STD_DEV_P);
        }

        if(this._pivotField.getVarSubtotal()) {
            set.add(Calculation.VARIANCE);
        }

        if(this._pivotField.getVarPSubtotal()) {
            set.add(Calculation.VARIANCE_P);
        }

        return set;
    }

    public void setSortType(SortType type) {
        switch(type.ordinal()) {
            case 1:
                this._pivotField.setSortType(STFieldSortType.ASCENDING);
                break;
            case 2:
                this._pivotField.setSortType(STFieldSortType.DESCENDING);
                break;
            case 3:
                this._pivotField.setSortType(STFieldSortType.MANUAL);
        }

    }

    public SortType getSortType() {
        org.openxmlformats.schemas.spreadsheetml.x2006.main.STFieldSortType.Enum type = this._pivotField.getSortType();
        return type == STFieldSortType.MANUAL?SortType.MANUAL:(type == STFieldSortType.ASCENDING?SortType.ASCENDING:(type == STFieldSortType.DESCENDING?SortType.DESCENDING:null));
    }

    public boolean getDatabaseField() {
        return this._cacheField.getDatabaseField();
    }

    public void setOutline(boolean outline) {
        this._pivotField.setOutline(outline);
    }

    public boolean getOutline() {
        return this._pivotField.getOutline();
    }

    private class IndexMapper {
        HashMap<Object, Integer> _map = new HashMap();

        IndexMapper(List src) {
            for(int i = 0; i < src.size(); ++i) {
                Object obj = src.get(i);
                if(obj instanceof Calendar) {
                    this._map.put(((Calendar)obj).getTime(), Integer.valueOf(i));
                } else {
                    this._map.put(obj, Integer.valueOf(i));
                }
            }

        }

        int getIndex(Object obj) {
            Integer idx = (Integer)this._map.get(obj);
            return idx != null?idx.intValue():-1;
        }
    }

    private class XSSFFieldGroup implements FieldGroup {
        private final XSSFPivotField _fieldGroup;
        private final CacheField _cacheField;
        private final PivotTable _pivotTable;

        XSSFFieldGroup(XSSFPivotField fieldGroup) {
            this._fieldGroup = fieldGroup;
            this._cacheField = fieldGroup._cacheField;
            this._pivotTable = fieldGroup._pivotTable;
        }

        private int getGroupIndex() {
            List items = this.getItems();
            return items != null?items.size() - 1:-1;
        }

        public Item getItem() {
            int groupIndex = this.getGroupIndex();
            if(groupIndex >= 0) {
                CTItems items = XSSFPivotField.this._pivotField.getItems();
                List values = this._cacheField.getSharedItems();
                Iterator i$ = items.getItemList().iterator();

                while(i$.hasNext()) {
                    CTItem item = (CTItem)i$.next();
                    int index = (int)item.getX();
                    if(index == groupIndex) {
                        return new XSSFItem(item, values.get(index));
                    }
                }
            }

            return null;
        }

        public List<Object> getItems() {
            return this._cacheField.getSharedItems();
        }

        public PivotField getBase() {
            return (PivotField)this._pivotTable.getPivotFields().get(this._cacheField.getGroupBase());
        }

        public Set<Object> getGroup() {
            XSSFPivotField base = (XSSFPivotField)this.getBase();
            Iterator iterator = base._cacheField.getSharedItems().iterator();
            int groupIndex = this.getGroupIndex();
            LinkedHashSet set = new LinkedHashSet();
            List discrete = this._cacheField.getGroupDiscrete();

            for(int i = 0; i < discrete.size(); ++i) {
                Object str = iterator.next();
                if(((Integer)discrete.get(i)).intValue() == groupIndex) {
                    set.add(str);
                }
            }

            return set;
        }

        public FieldGroup getParent() {
            int index = this._cacheField.getFieldGroup();
            return index > 0?XSSFPivotField.this.new XSSFFieldGroup((XSSFPivotField)this._pivotTable.getPivotFields().get(index)):null;
        }
    }

    public static class XSSFItem implements Item {
        private final CTItem _item;
        private final Object _value;
        private static Map<Enum, Type> map;
        private static Map<Type, Enum> map2;

        XSSFItem(CTItem item, Object value) {
            this._item = item;
            this._value = value;
        }

        public void setHide(boolean hide) {
            this._item.setH(hide);
        }

        public boolean getHide() {
            return this._item.getH();
        }

        public Object getValue() {
            return this._value;
        }

        public void setShowDetail(boolean showDetail) {
            this._item.setSd(showDetail);
        }

        public boolean getShowDetail() {
            return this._item.getSd();
        }

        public void setType(Type type) {
            if(map == null) {
                initItemTypeMap();
            }

            this._item.setT(map2.get(type));
        }

        public Type getType() {
            if(map == null) {
                initItemTypeMap();
            }

            return (Type)map.get(this._item.getT());
        }

        public int hashCode() {
            return this._value.hashCode();
        }

        public boolean equals(Object obj) {
            if(obj instanceof XSSFItem) {
                XSSFItem that = (XSSFItem)obj;
                return this._value.equals(that._value);
            } else {
                return false;
            }
        }

        static Enum getCTItemType(Type type) {
            if(map2 == null) {
                initItemTypeMap();
            }

            return map2.get(type);
        }

        private static void initItemTypeMap() {
            map = new HashMap();
            map.put(STItemType.AVG, Type.AVERAGE);
            map.put(STItemType.BLANK, Type.BLANK);
            map.put(STItemType.COUNT, Type.COUNT_NUMS);
            map.put(STItemType.COUNT_A, Type.COUNT);
            map.put(STItemType.DATA, Type.DATA);
            map.put(STItemType.DEFAULT, Type.DEFAULT);
            map.put(STItemType.GRAND, Type.GRAND);
            map.put(STItemType.MAX, Type.MAX);
            map.put(STItemType.MIN, Type.MIN);
            map.put(STItemType.PRODUCT, Type.PRODUCT);
            map.put(STItemType.STD_DEV, Type.STD_DEV);
            map.put(STItemType.STD_DEV_P, Type.STD_DEV_P);
            map.put(STItemType.SUM, Type.SUM);
            map.put(STItemType.VAR, Type.VARIANCE);
            map.put(STItemType.VAR_P, Type.VARIANCE_P);
            map2 = new HashMap();
            map2.put(Type.AVERAGE, STItemType.AVG);
            map2.put(Type.BLANK, STItemType.BLANK);
            map2.put(Type.COUNT_NUMS, STItemType.COUNT);
            map2.put(Type.COUNT, STItemType.COUNT_A);
            map2.put(Type.DATA, STItemType.DATA);
            map2.put(Type.DEFAULT, STItemType.DEFAULT);
            map2.put(Type.GRAND, STItemType.GRAND);
            map2.put(Type.MAX, STItemType.MAX);
            map2.put(Type.MIN, STItemType.MIN);
            map2.put(Type.PRODUCT, STItemType.PRODUCT);
            map2.put(Type.STD_DEV, STItemType.STD_DEV);
            map2.put(Type.STD_DEV_P, STItemType.STD_DEV_P);
            map2.put(Type.SUM, STItemType.SUM);
            map2.put(Type.VARIANCE, STItemType.VAR);
            map2.put(Type.VARIANCE_P, STItemType.VAR_P);
        }
    }
}
