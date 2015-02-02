//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.poi.xssf.usermodel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.XmlCalendar;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCacheField;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCacheFields;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTCacheSource;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDateTime;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTNumber;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotCacheDefinition;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTRecord;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTSharedItems;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTString;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorksheetSource;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STSourceType;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotCacheDefinition.Factory;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STSourceType.Enum;
import org.zkoss.poi.POIXMLDocument;
import org.zkoss.poi.POIXMLDocumentPart;
import org.zkoss.poi.POIXMLException;
import org.zkoss.poi.openxml4j.exceptions.InvalidFormatException;
import org.zkoss.poi.openxml4j.opc.PackagePart;
import org.zkoss.poi.openxml4j.opc.PackageRelationship;
import org.zkoss.poi.ss.usermodel.Cell;
import org.zkoss.poi.ss.usermodel.CellStyle;
import org.zkoss.poi.ss.usermodel.DateUtil;
import org.zkoss.poi.ss.usermodel.PivotCache;
import org.zkoss.poi.ss.usermodel.Row;
import org.zkoss.poi.ss.usermodel.Sheet;
import org.zkoss.poi.ss.usermodel.Workbook;
import org.zkoss.poi.ss.usermodel.PivotCache.CacheField;
import org.zkoss.poi.ss.usermodel.PivotCache.CacheRecord;
import org.zkoss.poi.ss.usermodel.PivotCache.SheetSource;
import org.zkoss.poi.ss.util.AreaReference;
import org.zkoss.poi.xssf.usermodel.XSSFCacheField;
import org.zkoss.poi.xssf.usermodel.XSSFCacheRecord;
import org.zkoss.poi.xssf.usermodel.XSSFFactory;
import org.zkoss.poi.xssf.usermodel.XSSFPivotCacheRecords;
import org.zkoss.poi.xssf.usermodel.XSSFRelation;

public class XSSFPivotCache extends POIXMLDocumentPart implements PivotCache {
    private long _cacheId;
    private CTPivotCacheDefinition _pivotCacheDefinition;
    private XSSFPivotCacheRecords _pivotCacheRecords;

    public XSSFPivotCache() {
        this.onDocumentCreate();
    }

    protected void onDocumentCreate() {
        this._pivotCacheDefinition = Factory.newInstance();
        this._pivotCacheDefinition.addNewCacheSource();
        byte ver = 3;
        this._pivotCacheDefinition.setCreatedVersion(ver);
        this._pivotCacheDefinition.setRefreshedVersion(ver);
        this._pivotCacheDefinition.setMinRefreshableVersion(ver);
    }

    public XSSFPivotCache(PackagePart part, PackageRelationship rel) {
        super(part, rel);
    }

    public XSSFPivotCache(long cacheId, POIXMLDocumentPart parent, PackagePart part, PackageRelationship rel) throws IOException {
        super(parent, part, rel);
        this._cacheId = cacheId;
        this.onDocumentRead();
    }

    protected void onDocumentRead() throws IOException {
        try {
            PackagePart e = this.getPackagePart();
            PackageRelationship rel = this.getPackageRelationship();
            this._pivotCacheDefinition = org.openxmlformats.schemas.spreadsheetml.x2006.main.PivotCacheDefinitionDocument.Factory.parse(e.getInputStream()).getPivotCacheDefinition();
            PackageRelationship relationship = e.getRelationshipsByType(XSSFRelation.PIVOT_CACHE_RECORDS.getRelation()).getRelationshipByID(rel.getId());
            this._pivotCacheRecords = new XSSFPivotCacheRecords(e.getRelatedPart(relationship), relationship);
        } catch (IOException var4) {
            throw new POIXMLException(var4);
        } catch (XmlException var5) {
            throw new POIXMLException(var5);
        } catch (InvalidFormatException var6) {
            throw new POIXMLException(var6);
        }
    }

    protected void commit() throws IOException {
        XmlOptions xmlOptions = new XmlOptions(DEFAULT_XML_OPTIONS);
        xmlOptions.setSaveSyntheticDocumentElement(new QName(CTPivotCacheDefinition.type.getName().getNamespaceURI(), "pivotCacheDefinition"));
        HashMap map = new HashMap();
        map.put(STRelationshipId.type.getName().getNamespaceURI(), "r");
        xmlOptions.setSaveSuggestedPrefixes(map);
        PackagePart part = this.getPackagePart();
        this.clearMemoryPackagePart(part);
        this._pivotCacheDefinition.setId(this._pivotCacheRecords.getPackageRelationship().getId());
        OutputStream out = part.getOutputStream();
        this._pivotCacheDefinition.save(out, xmlOptions);
        out.close();
    }

    public Workbook getWorkbook() {
        return (Workbook)this.getParent();
    }

    public void setCacheId(long cacheId) {
        this._cacheId = cacheId;
    }

    public long getCacheId() {
        return this._cacheId;
    }

    public List<CacheField> getFields() {
        CTCacheFields cacheFields = this._pivotCacheDefinition.getCacheFields();
        if(cacheFields == null) {
            return Collections.emptyList();
        } else {
            ArrayList list = new ArrayList();
            Iterator i$ = cacheFields.getCacheFieldList().iterator();

            while(i$.hasNext()) {
                CTCacheField e = (CTCacheField)i$.next();
                list.add(new XSSFCacheField(e));
            }

            return list;
        }
    }

    public List<CacheRecord> getRecords() {
        List records = this._pivotCacheRecords.getRows();
        if(records == null) {
            return Collections.emptyList();
        } else {
            CTCacheFields cacheFields = this._pivotCacheDefinition.getCacheFields();
            ArrayList list = new ArrayList();
            Iterator i$ = records.iterator();

            while(i$.hasNext()) {
                CTRecord record = (CTRecord)i$.next();
                list.add(new XSSFCacheRecord(record, cacheFields.getCacheFieldList()));
            }

            return list;
        }
    }

    private static String setSheetSource(AreaReference areaRef, CTCacheSource cacheSource) {
        CTWorksheetSource sheetSource = null;
        Enum type = cacheSource.getType();
        if(type != STSourceType.WORKSHEET) {
            cacheSource.setType(STSourceType.WORKSHEET);
            sheetSource = cacheSource.addNewWorksheetSource();
        } else {
            sheetSource = cacheSource.getWorksheetSource();
        }

        String sheetName = areaRef.getLastCell().getSheetName();
        String ref = areaRef.formatAsString();
        ref = ref.substring(sheetName.length() + 1);
        sheetSource.setSheet(sheetName);
        sheetSource.setRef(ref);
        return sheetName;
    }

    private static Cell getCell(int row, int col, Sheet sheet) {
        Row r = sheet.getRow(row);
        return r != null?r.getCell(col):null;
    }

    private static String getCellText(Cell cell) {
        if(cell == null) {
            return null;
        } else {
            int type = cell.getCellType();
            return type == 1?cell.getStringCellValue():(type == 2 && cell.getCachedFormulaResultType() == 1?cell.getRichStringCellValue().getString():null);
        }
    }

    private static ArrayList<FieldInfo> prepareFields(int row, int lCol, int rCol, Sheet sheet) {
        ArrayList fields = new ArrayList();

        for(int i = lCol; i <= rCol; ++i) {
            String field = getCellText(getCell(row, i, sheet));
            if(field == null || field.length() == 0) {
                throw new IllegalArgumentException("PivotTable field name should not be empty");
            }

            fields.add(new FieldInfo(field));
        }

        return fields;
    }

    public void setSheetSource(AreaReference sourceRef) {
        if(this._pivotCacheRecords == null) {
            byte cacheFields = 1;
            this._pivotCacheRecords = (XSSFPivotCacheRecords)this.createRelationship(XSSFRelation.PIVOT_CACHE_RECORDS, XSSFFactory.getInstance(), cacheFields);
        }

        CTCacheFields var16 = this._pivotCacheDefinition.getCacheFields();
        if(var16 == null) {
            var16 = this._pivotCacheDefinition.addNewCacheFields();
        }

        String refreshedBy = ((POIXMLDocument)this.getWorkbook()).getProperties().getCoreProperties().getCreator();
        if(refreshedBy != null && !"".equals(refreshedBy)) {
            this._pivotCacheDefinition.setRefreshedBy(refreshedBy);
        }

        this._pivotCacheDefinition.setRefreshedDate(DateUtil.getExcelDate(Calendar.getInstance().getTime()));
        String sheetName = setSheetSource(sourceRef, this._pivotCacheDefinition.getCacheSource());
        if(sheetName != null && sheetName.length() != 0) {
            Sheet sheet = this.getWorkbook().getSheet(sheetName);
            int tRow = sourceRef.getFirstCell().getRow();
            short lCol = sourceRef.getFirstCell().getCol();
            int bRow = sourceRef.getLastCell().getRow();
            short rCol = sourceRef.getLastCell().getCol();
            if(bRow - tRow + 1 < 2) {
                throw new IllegalArgumentException("PivotTable requires at least two rows of source data.");
            } else {
                ArrayList fields = prepareFields(tRow, lCol, rCol, sheet);

                for(int r = tRow + 1; r <= bRow; ++r) {
                    Iterator iter = fields.iterator();

                    for(int col = lCol; col <= rCol; ++col) {
                        Cell cell = getCell(r, col, sheet);
                        FieldInfo field = (FieldInfo)iter.next();
                        field.add(cell);
                    }
                }

                createCacheRecords(fields, createSharedItems((List)fields, (CTCacheFields)var16), this._pivotCacheRecords);
                var16.setCount((long)fields.size());
                this._pivotCacheDefinition.setRecordCount((long)((FieldInfo)fields.get(0)).size());
            }
        } else {
            throw new IllegalArgumentException("AreaReference shall has source sheet");
        }
    }

    private static void newCell(Object src, CTRecord record) {
        if(src instanceof Calendar) {
            record.addNewD().setV((Calendar)src);
        } else if(src instanceof Number) {
            record.addNewN().setV(((Number)src).doubleValue());
        } else if(src instanceof String) {
            record.addNewS().setV((String)src);
        } else if(src == null) {
            record.addNewM();
        }

    }

    private static void createCacheRecords(List<FieldInfo> fields, HashMap<Integer, CTSharedItems> shareItems, XSSFPivotCacheRecords cacheRecords) {
        IndexMapper indexMapper = new IndexMapper(shareItems);
        int rowSize = ((FieldInfo)fields.get(0)).size();
        cacheRecords.setCount(rowSize);

        for(int i = 0; i < rowSize; ++i) {
            CTRecord r = cacheRecords.addNewRow();

            for(int j = 0; j < fields.size(); ++j) {
                FieldInfo field = (FieldInfo)fields.get(j);
                Object obj = field.getValue(i);
                int idx = indexMapper.getShareItemIndex(j, obj);
                if(idx >= 0) {
                    r.addNewX().setV((long)idx);
                } else {
                    newCell(obj, r);
                }
            }
        }

    }

    private static void setupSharedItemsProperties(FieldInfo field, CTSharedItems sharedItems) {
        if(field.containsBlank) {
            sharedItems.setContainsBlank(true);
        }

        if(field.containsDate) {
            if(!field.isContainsSemiMixedTypes()) {
                sharedItems.setContainsSemiMixedTypes(false);
            }

            if(!field.containsNonDate) {
                sharedItems.setContainsNonDate(false);
            }

            sharedItems.setContainsDate(true);
            if(!field.containsString) {
                sharedItems.setContainsString(false);
            }

            if(field.isContainsMixedTypes()) {
                sharedItems.setContainsMixedTypes(true);
            }

            sharedItems.setMinDate(field.minDate);
            sharedItems.setMaxDate(field.maxDate);
        } else if(field.containsNumber) {
            sharedItems.setContainsString(field.containsString);
            sharedItems.setContainsNumber(true);
            if(field.isContainsMixedTypes()) {
                sharedItems.setContainsMixedTypes(true);
            } else if(!field.isContainsSemiMixedTypes()) {
                sharedItems.setContainsSemiMixedTypes(false);
            }

            sharedItems.setMinValue(field.minNumber.doubleValue());
            sharedItems.setMaxValue(field.maxNumber.doubleValue());
            if(field.containsInteger) {
                sharedItems.setContainsInteger(true);
            }
        } else if(field.isMissing()) {
            sharedItems.setContainsNonDate(false);
            sharedItems.setContainsString(false);
            sharedItems.setContainsBlank(true);
        }

    }

    private static void createSharedItems(FieldInfo field, CTSharedItems sharedItems) {
        int size = 0;
        if(field.containsBlank) {
            sharedItems.addNewM();
            ++size;
        }

        Iterator i$;
        if(field.containsDate) {
            i$ = field.getSharedDates().iterator();

            while(i$.hasNext()) {
                Calendar s = (Calendar)i$.next();
                sharedItems.addNewD().setV(s);
            }

            size += field.getSharedDates().size();
        }

        if(field.containsNumber) {
            i$ = field.getSharedNumbers().iterator();

            while(i$.hasNext()) {
                Double var5 = (Double)i$.next();
                sharedItems.addNewN().setV(var5.doubleValue());
            }

            size += field.getSharedNumbers().size();
        }

        if(field.containsString) {
            i$ = field.getSharedStrings().iterator();

            while(i$.hasNext()) {
                String var6 = (String)i$.next();
                sharedItems.addNewS().setV(var6);
            }

            size += field.getSharedStrings().size();
        }

        sharedItems.setCount((long)size);
    }

    private static HashMap<Integer, CTSharedItems> createSharedItems(List<FieldInfo> fields, CTCacheFields cacheFields) {
        HashMap shareItems = new HashMap();
        int itemIndex = 0;
        Iterator i$ = fields.iterator();

        while(i$.hasNext()) {
            FieldInfo fieldInfo = (FieldInfo)i$.next();
            CTCacheField ctCacheField = cacheFields.addNewCacheField();
            ctCacheField.setName(fieldInfo.fieldName);
            ctCacheField.setNumFmtId((long)fieldInfo.formatId);
            CTSharedItems ctSharedItems = ctCacheField.addNewSharedItems();
            setupSharedItemsProperties(fieldInfo, ctSharedItems);
            createSharedItems((FieldInfo)fieldInfo, (CTSharedItems)ctSharedItems);
            shareItems.put(Integer.valueOf(itemIndex++), ctSharedItems);
        }

        return shareItems;
    }

    public SheetSource getSheetSource() {
        CTCacheSource source = this._pivotCacheDefinition.getCacheSource();
        Enum type = source.getType();
        return type == STSourceType.WORKSHEET?new SheetSourceImpl(source.getWorksheetSource()):null;
    }

    public short getRefreshedVersion() {
        return this._pivotCacheDefinition.getRefreshedVersion();
    }

    public short getMinRefreshableVersion() {
        return this._pivotCacheDefinition.getMinRefreshableVersion();
    }

    public short getCreatedVersion() {
        return this._pivotCacheDefinition.getCreatedVersion();
    }

    private static class IndexMapper {
        List<HashMap<Object, Integer>> _mapper = new ArrayList();

        IndexMapper(HashMap<Integer, CTSharedItems> shareItems) {
            int itemSize = shareItems.size();

            for(int i = 0; i < itemSize; ++i) {
                HashMap map = new HashMap();
                this._mapper.add(map);
                CTSharedItems ctSharedItems = (CTSharedItems)shareItems.get(Integer.valueOf(i));
                int idx = 0;
                if(ctSharedItems.getContainsBlank()) {
                    map.put((Object)null, Integer.valueOf(idx++));
                }

                List sList;
                Iterator i$;
                if(ctSharedItems.getContainsDate()) {
                    sList = ctSharedItems.getDList();
                    i$ = sList.iterator();

                    while(i$.hasNext()) {
                        CTDateTime s = (CTDateTime)i$.next();
                        map.put(s.getV(), Integer.valueOf(idx++));
                    }
                }

                if(ctSharedItems.getContainsNumber()) {
                    sList = ctSharedItems.getNList();
                    i$ = sList.iterator();

                    while(i$.hasNext()) {
                        CTNumber var10 = (CTNumber)i$.next();
                        map.put(Double.valueOf(var10.getV()), Integer.valueOf(idx++));
                    }
                }

                if(ctSharedItems.getContainsString()) {
                    sList = ctSharedItems.getSList();
                    i$ = sList.iterator();

                    while(i$.hasNext()) {
                        CTString var11 = (CTString)i$.next();
                        map.put(var11.getV(), Integer.valueOf(idx++));
                    }
                }
            }

        }

        int getShareItemIndex(int colIndex, Object key) {
            HashMap map = (HashMap)this._mapper.get(colIndex);
            Integer index = (Integer)map.get(key);
            return index != null?index.intValue():-1;
        }
    }

    private static class FieldInfo {
        String fieldName;
        boolean containsNonDate;
        boolean containsDate;
        boolean containsString;
        boolean containsNumber;
        boolean containsBlank;
        boolean containsInteger = true;
        Calendar minDate;
        Calendar maxDate;
        Double minNumber;
        Double maxNumber;
        int formatId;
        LinkedHashSet<Double> sharedNumbers;
        LinkedHashSet<String> sharedStrings;
        LinkedHashSet<Calendar> sharedDates;
        ArrayList<Cell> cells = new ArrayList();
        ArrayList<Object> values = new ArrayList();

        FieldInfo(String name) {
            this.fieldName = name;
        }

        boolean isInteger(Double src) {
            return Math.floor(src.doubleValue()) == src.doubleValue();
        }

        boolean isDateFormat(Cell cell) {
            if(cell == null) {
                return false;
            } else {
                CellStyle style = cell.getCellStyle();
                return style == null?false:DateUtil.isADateFormat(style.getDataFormat(), style.getDataFormatString());
            }
        }

        boolean isValidDate(Cell cell) {
            return DateUtil.isCellDateFormatted(cell);
        }

        boolean isNumber(Cell cell) {
            int cellType = cell.getCellType();
            return cellType == 0 || cellType == 2 && cell.getCachedFormulaResultType() == 0;
        }

        boolean isString(Cell cell) {
            int cellType = cell.getCellType();
            return cellType == 1 || cellType == 2 && cell.getCachedFormulaResultType() == 1;
        }

        void add(Cell cell) {
            this.cells.add(cell);
            if(cell != null) {
                if(this.isNumber(cell)) {
                    this.formatId = cell.getCellStyle().getDataFormat();
                    if(this.isDateFormat(cell)) {
                        this.containsDate = true;
                        if(!this.isValidDate(cell)) {
                            this.containsNonDate = true;
                            this.values.add(Double.valueOf(cell.getNumericCellValue()));
                        } else {
                            XmlCalendar str = new XmlCalendar(DateUtil.getJavaDate(cell.getNumericCellValue()));
                            str.clear(15);
                            this.getSharedDates().add(str);
                            this.values.add(str);
                            if(this.minDate == null) {
                                this.minDate = str;
                            } else {
                                this.minDate = this.getMinDate(str, this.minDate);
                            }

                            if(this.maxDate == null) {
                                this.maxDate = str;
                            } else {
                                this.maxDate = this.getMaxDate(str, this.maxDate);
                            }
                        }
                    } else {
                        this.containsNumber = true;
                        Double str1 = Double.valueOf(cell.getNumericCellValue());
                        if(!this.isInteger(str1)) {
                            this.containsInteger = false;
                        }

                        this.getSharedNumbers().add(str1);
                        this.values.add(str1);
                        if(this.minNumber == null) {
                            this.minNumber = str1;
                        } else {
                            this.minNumber = Double.valueOf(Math.min(str1.doubleValue(), this.minNumber.doubleValue()));
                        }

                        if(this.maxNumber == null) {
                            this.maxNumber = str1;
                        } else {
                            this.maxNumber = Double.valueOf(Math.max(str1.doubleValue(), this.maxNumber.doubleValue()));
                        }
                    }
                } else if(this.isString(cell)) {
                    this.containsString = true;
                    String str2 = XSSFPivotCache.getCellText(cell);
                    this.getSharedStrings().add(str2);
                    this.values.add(str2);
                } else if(cell.getCellType() == 3) {
                    this.values.add((Object)null);
                }
            } else {
                this.containsBlank = true;
                this.values.add((Object)null);
            }

        }

        private Calendar getMaxDate(Calendar c1, Calendar c2) {
            int cmp = c1.compareTo(c2);
            return cmp >= 0?c1:c2;
        }

        private Calendar getMinDate(Calendar c1, Calendar c2) {
            int cmp = c1.compareTo(c2);
            return cmp <= 0?c1:c2;
        }

        LinkedHashSet<Double> getSharedNumbers() {
            if(this.sharedNumbers == null) {
                this.sharedNumbers = new LinkedHashSet();
            }

            return this.sharedNumbers;
        }

        LinkedHashSet<Calendar> getSharedDates() {
            if(this.sharedDates == null) {
                this.sharedDates = new LinkedHashSet();
            }

            return this.sharedDates;
        }

        LinkedHashSet<String> getSharedStrings() {
            if(this.sharedStrings == null) {
                this.sharedStrings = new LinkedHashSet();
            }

            return this.sharedStrings;
        }

        Object getValue(int index) {
            return this.values.get(index);
        }

        int size() {
            return this.cells.size();
        }

        boolean isMissing() {
            Iterator i$ = this.values.iterator();

            Object o;
            do {
                if(!i$.hasNext()) {
                    return true;
                }

                o = i$.next();
            } while(o == null);

            return false;
        }

        boolean isContainsSemiMixedTypes() {
            int bTrue = 0;
            if(this.containsBlank) {
                ++bTrue;
            }

            if(this.containsDate) {
                ++bTrue;
            }

            if(this.containsString) {
                ++bTrue;
            }

            if(this.containsNumber) {
                ++bTrue;
            }

            return bTrue > 1;
        }

        boolean isContainsMixedTypes() {
            int bTrue = 0;
            if(this.containsDate) {
                ++bTrue;
                if(this.containsNonDate) {
                    ++bTrue;
                }
            }

            if(this.containsString) {
                ++bTrue;
            }

            if(this.containsNumber) {
                ++bTrue;
            }

            return bTrue > 1;
        }
    }

    private class SheetSourceImpl implements SheetSource {
        private final CTWorksheetSource _source;

        SheetSourceImpl(CTWorksheetSource source) {
            this._source = source;
        }

        public String getName() {
            return this._source.getSheet();
        }

        public String getRef() {
            return this._source.getRef();
        }
    }
}
