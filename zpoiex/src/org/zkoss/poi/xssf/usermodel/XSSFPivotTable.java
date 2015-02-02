//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.poi.xssf.usermodel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTColFields;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTColItems;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDataField;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDataFields;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTField;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTI;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTLocation;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotField;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotFields;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotTableDefinition;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTRowFields;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTRowItems;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTX;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotTableDefinition.Factory;
import org.zkoss.poi.POIXMLDocumentPart;
import org.zkoss.poi.openxml4j.opc.PackagePart;
import org.zkoss.poi.openxml4j.opc.PackageRelationship;
import org.zkoss.poi.openxml4j.opc.TargetMode;
import org.zkoss.poi.ss.usermodel.Calculation;
import org.zkoss.poi.ss.usermodel.DataField;
import org.zkoss.poi.ss.usermodel.PivotCache;
import org.zkoss.poi.ss.usermodel.PivotField;
import org.zkoss.poi.ss.usermodel.PivotTable;
import org.zkoss.poi.ss.usermodel.Sheet;
import org.zkoss.poi.ss.usermodel.PivotCache.CacheField;
import org.zkoss.poi.ss.usermodel.PivotField.Item;
import org.zkoss.poi.ss.usermodel.PivotField.Type;
import org.zkoss.poi.ss.util.AreaReference;
import org.zkoss.poi.ss.util.CellReference;
import org.zkoss.poi.ss.util.ItemInfo;
import org.zkoss.poi.xssf.usermodel.XSSFDataField;
import org.zkoss.poi.xssf.usermodel.XSSFPivotCache;
import org.zkoss.poi.xssf.usermodel.XSSFPivotField;
import org.zkoss.poi.xssf.usermodel.XSSFRelation;
import org.zkoss.poi.xssf.usermodel.XSSFPivotField.XSSFItem;

public class XSSFPivotTable extends POIXMLDocumentPart implements PivotTable {
    private CTPivotTableDefinition _pivotTableDefinition;
    private XSSFPivotCache _pivotCache;
    private LinkedHashMap<String, PivotField> _pivotFields;

    XSSFPivotTable() {
        this.onDocumentCreate();
    }

    protected void onDocumentCreate() {
        this._pivotTableDefinition = Factory.newInstance();
        this._pivotTableDefinition.setApplyNumberFormats(false);
        this._pivotTableDefinition.setApplyBorderFormats(false);
        this._pivotTableDefinition.setApplyFontFormats(false);
        this._pivotTableDefinition.setApplyPatternFormats(false);
        this._pivotTableDefinition.setApplyAlignmentFormats(false);
        this._pivotTableDefinition.setApplyWidthHeightFormats(true);
        this._pivotTableDefinition.setDataCaption("Values");
        this._pivotTableDefinition.setShowCalcMbrs(false);
        this._pivotTableDefinition.setUseAutoFormatting(true);
        this._pivotTableDefinition.setItemPrintTitles(true);
        this._pivotTableDefinition.setIndent(0L);
        this._pivotTableDefinition.setOutlineData(true);
        this._pivotTableDefinition.setMultipleFieldFilters(false);
    }

    public XSSFPivotTable(PackagePart part, PackageRelationship rel, List<PivotCache> pivotCaches) throws IOException, XmlException {
        InputStream in = part.getInputStream();
        this._pivotTableDefinition = org.openxmlformats.schemas.spreadsheetml.x2006.main.PivotTableDefinitionDocument.Factory.parse(in).getPivotTableDefinition();
        long cacheId = this.getCacheId();
        Iterator i$ = pivotCaches.iterator();

        while(i$.hasNext()) {
            PivotCache e = (PivotCache)i$.next();
            if(e.getCacheId() == cacheId) {
                this._pivotCache = (XSSFPivotCache)e;
                break;
            }
        }

    }

    protected void commit() throws IOException {
        XmlOptions xmlOptions = new XmlOptions(DEFAULT_XML_OPTIONS);
        xmlOptions.setSaveSyntheticDocumentElement(new QName(CTPivotTableDefinition.type.getName().getNamespaceURI(), "pivotTableDefinition"));
        HashMap map = new HashMap();
        map.put(STRelationshipId.type.getName().getNamespaceURI(), "r");
        xmlOptions.setSaveSuggestedPrefixes(map);
        PackagePart part = this.getPackagePart();
        this.clearMemoryPackagePart(part);
        OutputStream out = part.getOutputStream();
        this._pivotTableDefinition.save(out, xmlOptions);
        out.close();
    }

    public CTPivotTableDefinition getPivotTableDefinition() {
        return this._pivotTableDefinition;
    }

    private String nextReleationId() {
        int idx = 1;

        StringBuilder var10000;
        String rId;
        for(rId = "rId" + idx; this.getRelationById(rId) != null; rId = var10000.append(idx).toString()) {
            var10000 = (new StringBuilder()).append("rId");
            ++idx;
        }

        return rId;
    }

    public void setPivotCache(PivotCache pivotCache) {
        this._pivotCache = (XSSFPivotCache)pivotCache;
        this.getPackagePart().addRelationship(this._pivotCache.getPackagePart().getPartName(), TargetMode.INTERNAL, XSSFRelation.PIVOT_CACHE_DEFINITION.getRelation());
        this.addRelation(this.nextReleationId(), this._pivotCache);
        this._pivotTableDefinition.setCacheId(pivotCache.getCacheId());
        this._pivotTableDefinition.setUpdatedVersion(pivotCache.getRefreshedVersion());
        this._pivotTableDefinition.setMinRefreshableVersion(pivotCache.getMinRefreshableVersion());
        this._pivotTableDefinition.setCreatedVersion(pivotCache.getCreatedVersion());
    }

    public long getCacheId() {
        return this._pivotTableDefinition.getCacheId();
    }

    public List<PivotField> getColumnFields() {
        CTColFields colFields = this._pivotTableDefinition.getColFields();
        if(colFields == null) {
            return Collections.emptyList();
        } else {
            ArrayList list = new ArrayList();
            List pivotFields = this.getPivotFields();
            Iterator i$ = colFields.getFieldList().iterator();

            while(i$.hasNext()) {
                CTField f = (CTField)i$.next();
                int x = f.getX();
                if(x >= 0) {
                    list.add(pivotFields.get(x));
                }
            }

            return list;
        }
    }

    private static CTColFields initColFields(CTPivotTableDefinition pivotTableDefinition) {
        CTColFields ctColFields = pivotTableDefinition.addNewColFields();
        CTRowItems rowItems = pivotTableDefinition.getRowItems();
        if(rowItems == null) {
            rowItems = pivotTableDefinition.addNewRowItems();
            rowItems.addNewI();
            rowItems.setCount(1L);
        }

        return ctColFields;
    }

    public void setColumnField(PivotField field) {
        int idx = this.getPivotFieldIndex(field, this.getPivotFields());
        if(idx < 0) {
            throw new IllegalArgumentException("Can\'t find PivotField");
        } else {
            boolean typeChanged = false;
            Type type = field.getType();
            if(type != null) {
                switch(type.ordinal()) {
                    case 1:
                        typeChanged = true;
                        this.removeRowField(idx, field);
                        break;
                    case 2:
                        typeChanged = true;
                        this.removeDataField(idx, field);
                }
            } else {
                typeChanged = true;
            }

            if(typeChanged) {
                field.setType(Type.COLUMN);
                CTColFields colFields = this._pivotTableDefinition.getColFields();
                if(colFields == null) {
                    colFields = initColFields(this._pivotTableDefinition);
                }

                int count = (int)colFields.getCount();
                List dataFields = this.getDataFields();
                boolean insrtDataCol = false;
                if(dataFields.size() > 1 && !this.getDataOnRows()) {
                    insrtDataCol = true;
                    int lastIdx = count - 1;
                    if(lastIdx >= 0) {
                        CTField f = (CTField)colFields.getFieldList().get(lastIdx);
                        if(f.getX() == -2) {
                            colFields.removeField(lastIdx);
                        }
                    }
                }

                colFields.addNewField().setX(idx);
                if(insrtDataCol) {
                    colFields.addNewField().setX(-2);
                }

                colFields.setCount((long)colFields.getFieldList().size());
            }

        }
    }

    public void setDataCaption(String caption) {
        this._pivotTableDefinition.setDataCaption(caption);
    }

    public String getDataCaption() {
        return this._pivotTableDefinition.getDataCaption();
    }

    public List<DataField> getDataFields() {
        CTDataFields dataFields = this._pivotTableDefinition.getDataFields();
        if(dataFields == null) {
            return Collections.emptyList();
        } else {
            ArrayList list = new ArrayList();
            List pivotFields = this.getPivotFields();
            List dataFieldList = dataFields.getDataFieldList();
            Iterator i$ = dataFieldList.iterator();

            while(i$.hasNext()) {
                CTDataField dataField = (CTDataField)i$.next();
                PivotField pivotField = (PivotField)pivotFields.get((int)dataField.getFld());
                list.add(new XSSFDataField(dataField, pivotField));
            }

            return list;
        }
    }

    public void setDataField(PivotField field, String name, Calculation subtotal) {
        CTDataFields dataFields = this._pivotTableDefinition.getDataFields();
        if(dataFields == null) {
            dataFields = this._pivotTableDefinition.addNewDataFields();
        }

        CTDataField dataField = dataFields.addNewDataField();
        dataField.setName(name);
        int idx = this.getPivotFieldIndex(field, this.getPivotFields());
        if(idx < 0) {
            throw new IllegalArgumentException("Can\'t find PivotField");
        } else {
            field.setType(Type.DATA);
            dataField.setFld((long)idx);
            dataField.setSubtotal(XSSFDataField.getSubtotalType(subtotal));
            dataField.setBaseField(0);
            dataField.setBaseItem(0L);
            dataFields.setCount((long)dataFields.getDataFieldList().size());
            CTRowItems rowItems = this._pivotTableDefinition.getRowItems();
            if(rowItems == null) {
                rowItems = this._pivotTableDefinition.addNewRowItems();
                rowItems.addNewI();
                rowItems.setCount((long)rowItems.getIList().size());
            }

            CTColItems colItems = this._pivotTableDefinition.getColItems();
            if(colItems == null) {
                colItems = this._pivotTableDefinition.addNewColItems();
                colItems.addNewI();
                colItems.setCount((long)colItems.getIList().size());
            }

        }
    }

    public void setFirstHeaderRow(int row) {
        this.getCTLocation().setFirstHeaderRow((long)row);
    }

    public CTLocation getCTLocation() {
        CTLocation location = this._pivotTableDefinition.getLocation();
        return location == null?this._pivotTableDefinition.addNewLocation():location;
    }

    public CTColFields getCTColFields() {
        CTColFields colFields = this._pivotTableDefinition.getColFields();
        return colFields == null?initColFields(this._pivotTableDefinition):colFields;
    }

    public CTRowFields getCTRowFields() {
        CTRowFields rowFields = this._pivotTableDefinition.getRowFields();
        return rowFields == null?initRowFields(this._pivotTableDefinition):rowFields;
    }

    public void setFirstData(int row, int col) {
        CTLocation ctLocation = this.getCTLocation();
        ctLocation.setFirstDataRow((long)row);
        ctLocation.setFirstDataCol((long)col);
    }

    public void setLocationRef(AreaReference ref) {
        this.getCTLocation().setRef(ref.formatAsString());
    }

    public AreaReference getLocationRef() {
        return new AreaReference(this._pivotTableDefinition.getLocation().getRef());
    }

    public CellReference getFirstDataRef() {
        CellReference firstCellRef = this.getLocationRef().getFirstCell();
        CTLocation l = this.getCTLocation();
        int col = firstCellRef.getCol() + (int)l.getFirstDataCol();
        int row = firstCellRef.getRow() + (int)l.getFirstDataRow();
        return new CellReference(row, col);
    }

    public void setGrandTotalCaption(String caption) {
        this._pivotTableDefinition.setGrandTotalCaption(caption);
    }

    public String getGrandTotalCaption() {
        return this._pivotTableDefinition.getGrandTotalCaption();
    }

    public void setName(String name) {
        this._pivotTableDefinition.setName(name);
    }

    public String getName() {
        return this._pivotTableDefinition.getName();
    }

    public PivotCache getPivotCache() {
        return this._pivotCache;
    }

    public PivotField getPivotField(String name) {
        List pivotFields = this.getPivotFields();
        Iterator i$ = pivotFields.iterator();

        PivotField f;
        String fName;
        do {
            if(!i$.hasNext()) {
                return null;
            }

            f = (PivotField)i$.next();
            fName = f.getName();
        } while(!fName.equalsIgnoreCase(name));

        return f;
    }

    public List<PivotField> getPivotFields() {
        if(this._pivotFields == null) {
            this.initPivotFields();
        }

        return new ArrayList(this._pivotFields.values());
    }

    private void initPivotFields() {
        this._pivotFields = new LinkedHashMap();
        CTPivotFields pivotFields = this._pivotTableDefinition.getPivotFields();
        if(pivotFields != null) {
            Iterator iterator = this._pivotCache.getFields().iterator();
            Iterator i$ = pivotFields.getPivotFieldList().iterator();

            while(i$.hasNext()) {
                CTPivotField f = (CTPivotField)i$.next();
                XSSFPivotField pf = new XSSFPivotField(f, (CacheField)iterator.next(), this);
                this._pivotFields.put(pf.getName(), pf);
            }

        }
    }

    private int getPivotFieldIndex(PivotField pivotField, List<PivotField> from) {
        int i = 0;

        for(Iterator i$ = from.iterator(); i$.hasNext(); ++i) {
            PivotField pf = (PivotField)i$.next();
            if(pf.equals(pivotField)) {
                return i;
            }
        }

        return -1;
    }

    public List<PivotField> getRowFields() {
        CTRowFields rowFields = this._pivotTableDefinition.getRowFields();
        if(rowFields == null) {
            return Collections.emptyList();
        } else {
            ArrayList list = new ArrayList();
            List pivotFields = this.getPivotFields();
            Iterator i$ = rowFields.getFieldList().iterator();

            while(i$.hasNext()) {
                CTField f = (CTField)i$.next();
                int idx = f.getX();
                if(idx >= 0) {
                    list.add(pivotFields.get(idx));
                }
            }

            return list;
        }
    }

    private static CTRowFields initRowFields(CTPivotTableDefinition pivotTableDefinition) {
        CTRowFields rowFields = pivotTableDefinition.addNewRowFields();
        CTColItems colItems = pivotTableDefinition.getColItems();
        if(colItems == null) {
            colItems = pivotTableDefinition.addNewColItems();
            colItems.addNewI();
            colItems.setCount(1L);
        }

        return rowFields;
    }

    public Sheet getSheet() {
        return (Sheet)this.getParent();
    }

    private ItemInfo getNonDataTypeItem(List<ItemInfo> items) {
        for(int i = 0; i < items.size(); ++i) {
            ItemInfo info = (ItemInfo)items.get(i);
            if(info != null && info.getType() != org.zkoss.poi.ss.usermodel.PivotField.Item.Type.DATA) {
                return info;
            }
        }

        return null;
    }

    public void setColumnItems(List<List<ItemInfo>> items) {
        CTColItems colItems = this._pivotTableDefinition.getColItems();
        if(colItems != null) {
            this._pivotTableDefinition.unsetColItems();
        }

        colItems = this._pivotTableDefinition.addNewColItems();
        IndexMapper indexMapper = new IndexMapper(this.getColumnFields());
        DataFieldIndexMapper dataIndexMapper = null;
        List dataFields = this.getDataFields();
        if(!this.getDataOnRows() && dataFields.size() > 1) {
            dataIndexMapper = new DataFieldIndexMapper(dataFields);
        }

        int itemSize = items.size();

        for(int c = 0; c < itemSize; ++c) {
            List col = (List)items.get(c);
            ItemInfo info = this.getNonDataTypeItem(col);
            int i;
            if(info != null) {
                CTI setRowIndex = colItems.addNewI();
                if(info.getDepth() > 0) {
                    setRowIndex.setR((long)info.getDepth());
                }

                org.zkoss.poi.ss.usermodel.PivotField.Item.Type firstRowIndex = info.getType();
                CTX ctI = setRowIndex.addNewX();
                setRowIndex.setT(XSSFItem.getCTItemType(firstRowIndex));
                if(firstRowIndex == org.zkoss.poi.ss.usermodel.PivotField.Item.Type.GRAND) {
                    i = info.getIndex();
                    if(i > 0) {
                        ctI.setV(i);
                    }
                } else {
                    i = indexMapper.getShareItemIndex(info.getDepth(), info.getValue());
                    if(i > 0) {
                        ctI.setV(i);
                    }
                }
            } else {
                boolean var18 = false;
                Integer var19 = null;
                CTI var20 = colItems.addNewI();

                for(i = 0; i < col.size(); ++i) {
                    ItemInfo item = (ItemInfo)col.get(i);
                    if(item != null) {
                        if(var19 == null) {
                            var19 = Integer.valueOf(i);
                        }

                        int depth = item.getDepth();
                        if(depth >= 0) {
                            CTX idx = var20.addNewX();
                            int ctX = indexMapper.getShareItemIndex(i, item.getValue());
                            if(ctX < 0) {
                                throw new IllegalArgumentException("can\'t find item: " + item.getValue());
                            }

                            if(ctX > 0) {
                                idx.setV(ctX);
                            }
                        } else {
                            int var21 = dataIndexMapper.getIndex((String)item.getValue());
                            if(var21 < 0) {
                                throw new IllegalArgumentException("can\'t find item: " + item.getValue());
                            }

                            CTX var22 = var20.addNewX();
                            if(var21 > 0) {
                                var20.setI((long)var21);
                                var22.setV(var21);
                            }
                        }
                    } else {
                        var18 = true;
                    }
                }

                if(var18 && var19 != null) {
                    var20.setR((long)var19.intValue());
                }
            }
        }

        colItems.setCount((long)colItems.getIList().size());
    }

    public void setRowItems(List<List<ItemInfo>> items) {
        CTRowItems rowItems = this._pivotTableDefinition.getRowItems();
        if(rowItems != null) {
            this._pivotTableDefinition.unsetRowItems();
        }

        rowItems = this._pivotTableDefinition.addNewRowItems();
        IndexMapper indexMapper = new IndexMapper(this.getRowFields());
        DataFieldIndexMapper dataIndexMapper = null;
        List dataFields = this.getDataFields();
        if(this.getDataOnRows() && dataFields.size() > 1) {
            dataIndexMapper = new DataFieldIndexMapper(dataFields);
        }

        int itemSize = items.size();

        for(int i = 0; i < itemSize; ++i) {
            List row = (List)items.get(i);
            ItemInfo info = this.getNonDataTypeItem(row);
            int c;
            if(info != null) {
                CTI setColumnIndex = rowItems.addNewI();
                if(info.getDepth() > 0) {
                    setColumnIndex.setR((long)info.getDepth());
                }

                org.zkoss.poi.ss.usermodel.PivotField.Item.Type firstColumnIndex = info.getType();
                CTX ctI = setColumnIndex.addNewX();
                setColumnIndex.setT(XSSFItem.getCTItemType(firstColumnIndex));
                if(firstColumnIndex == org.zkoss.poi.ss.usermodel.PivotField.Item.Type.GRAND) {
                    c = info.getIndex();
                    if(c > 0) {
                        ctI.setV(c);
                    }
                } else {
                    c = indexMapper.getShareItemIndex(info.getDepth(), info.getValue());
                    if(c > 0) {
                        ctI.setV(c);
                    }
                }
            } else {
                boolean var18 = false;
                Integer var19 = null;
                CTI var20 = rowItems.addNewI();

                for(c = 0; c < row.size(); ++c) {
                    ItemInfo item = (ItemInfo)row.get(c);
                    if(item != null) {
                        if(var19 == null) {
                            var19 = Integer.valueOf(c);
                        }

                        int depth = item.getDepth();
                        if(depth >= 0) {
                            CTX idx = var20.addNewX();
                            int ctX = indexMapper.getShareItemIndex(c, item.getValue());
                            if(ctX < 0) {
                                throw new IllegalArgumentException("can\'t find item: " + item.getValue());
                            }

                            if(ctX > 0) {
                                idx.setV(ctX);
                            }
                        } else {
                            int var21 = dataIndexMapper.getIndex((String)item.getValue());
                            if(var21 < 0) {
                                throw new IllegalArgumentException("can\'t find item: " + item.getValue());
                            }

                            CTX var22 = var20.addNewX();
                            if(var21 > 0) {
                                var20.setI((long)var21);
                                var22.setV(var21);
                            }
                        }
                    } else {
                        var18 = true;
                    }
                }

                if(var18 && var19 != null) {
                    var20.setR((long)var19.intValue());
                }
            }
        }

        rowItems.setCount((long)rowItems.getIList().size());
    }

    public void setRowField(PivotField field) {
        int idx = this.getPivotFieldIndex(field, this.getPivotFields());
        if(idx < 0) {
            throw new IllegalArgumentException("Can\'t find PivotField");
        } else {
            boolean typeChanged = false;
            Type type = field.getType();
            if(type != null) {
                switch(type.ordinal()) {
                    case 2:
                        typeChanged = true;
                        this.removeDataField(idx, field);
                        break;
                    case 3:
                        typeChanged = true;
                        this.removeColumnField(idx, field);
                }
            } else {
                typeChanged = true;
            }

            if(typeChanged) {
                field.setType(Type.ROW);
                CTRowFields rowFields = this._pivotTableDefinition.getRowFields();
                if(rowFields == null) {
                    rowFields = initRowFields(this._pivotTableDefinition);
                }

                int count = (int)rowFields.getCount();
                List dataFields = this.getDataFields();
                boolean insrtDataRow = false;
                if(dataFields.size() > 1 && this.getDataOnRows()) {
                    insrtDataRow = true;
                    int lastIdx = count - 1;
                    if(lastIdx >= 0) {
                        CTField f = (CTField)rowFields.getFieldList().get(lastIdx);
                        if(f.getX() == -2) {
                            rowFields.removeField(lastIdx);
                        }
                    }
                }

                rowFields.addNewField().setX(idx);
                if(insrtDataRow) {
                    rowFields.addNewField().setX(-2);
                }

                rowFields.setCount((long)rowFields.getFieldList().size());
            }

        }
    }

    private void removeDataField(int idx, PivotField field) {
        CTDataField dataField = null;
        CTDataFields dataFields = this._pivotTableDefinition.getDataFields();
        int i = 0;

        for(Iterator i$ = dataFields.getDataFieldList().iterator(); i$.hasNext(); ++i) {
            CTDataField d = (CTDataField)i$.next();
            if(d.getFld() == (long)idx) {
                dataField = d;
                break;
            }
        }

        if(dataField != null) {
            dataFields.removeDataField(i);
            dataFields.setCount((long)dataFields.getDataFieldList().size());
        }

    }

    private void removeRowField(int idx, PivotField field) {
        CTField ctField = null;
        CTRowFields rowFields = this._pivotTableDefinition.getRowFields();
        byte i = 0;
        Iterator i$ = rowFields.getFieldList().iterator();

        while(i$.hasNext()) {
            CTField f = (CTField)i$.next();
            if(f.getX() == idx) {
                ctField = f;
                break;
            }
        }

        if(ctField != null) {
            rowFields.removeField(i);
            rowFields.setCount((long)rowFields.getFieldList().size());
        }

    }

    private void removeColumnField(int idx, PivotField field) {
        CTField ctField = null;
        CTColFields colFields = this._pivotTableDefinition.getColFields();
        int i = 0;

        for(Iterator i$ = colFields.getFieldList().iterator(); i$.hasNext(); ++i) {
            CTField f = (CTField)i$.next();
            if(f.getX() == idx) {
                ctField = f;
                break;
            }
        }

        if(ctField != null) {
            colFields.removeField(i);
            colFields.setCount((long)colFields.getFieldList().size());
        }

    }

    public void setDataOnRows(boolean dataOnRows) {
        this._pivotTableDefinition.setDataOnRows(dataOnRows);
    }

    public boolean getDataOnRows() {
        return this._pivotTableDefinition.getDataOnRows();
    }

    public String getRowHeaderCaption() {
        return this._pivotTableDefinition.getRowHeaderCaption();
    }

    public void setRowHeaderCaption(String caption) {
        this._pivotTableDefinition.setRowHeaderCaption(caption);
    }

    public void setOutline(boolean outline) {
        this._pivotTableDefinition.setOutline(outline);
    }

    public boolean getOutline() {
        return this._pivotTableDefinition.getOutline();
    }

    public void setOutlineData(boolean outlineData) {
        this._pivotTableDefinition.setOutlineData(outlineData);
    }

    public boolean getOutlineData() {
        return this._pivotTableDefinition.getOutlineData();
    }

    private class DataFieldIndexMapper {
        HashMap<String, Integer> _mapper = new HashMap();

        DataFieldIndexMapper(List dataFields) {
            for(int i = 0; i < dataFields.size(); ++i) {
                DataField f = (DataField)dataFields.get(i);
                this._mapper.put(f.getName(), Integer.valueOf(i));
            }

        }

        int getIndex(String str) {
            Integer index = (Integer)this._mapper.get(str);
            return index != null?index.intValue():-1;
        }
    }

    private class IndexMapper {
        List<HashMap<Object, Integer>> _mapper = new ArrayList();

        IndexMapper(List fields) {
            Iterator i$ = fields.iterator();

            while(i$.hasNext()) {
                PivotField pivotField = (PivotField)i$.next();
                HashMap map = new HashMap();
                this._mapper.add(map);
                List items = pivotField.getItems();

                for(int j = 0; j < items.size(); ++j) {
                    Item item = (Item)items.get(j);
                    Object value = item.getValue();
                    if(value instanceof Calendar) {
                        map.put(((Calendar)value).getTime(), Integer.valueOf(j));
                    } else if(value instanceof Number) {
                        map.put(Double.valueOf(((Number)value).doubleValue()), Integer.valueOf(j));
                    } else {
                        map.put(value != null?value.toString():null, Integer.valueOf(j));
                    }
                }
            }

        }

        int getShareItemIndex(int fieldIndex, Object key) {
            HashMap map = (HashMap)this._mapper.get(fieldIndex);
            Integer index = (Integer)map.get(key);
            return index != null?index.intValue():-1;
        }
    }
}
