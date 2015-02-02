/*     */ package org.zkoss.zpoiex.ss.usermodel.helpers;
/*     */
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.apache.xmlbeans.XmlException;
/*     */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotCache;
/*     */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotCaches;
/*     */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotField;
/*     */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotFields;
/*     */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotTableDefinition;
/*     */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotTableStyle;
/*     */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTWorkbook;
/*     */ import org.zkoss.poi.POIXMLDocumentPart;
/*     */ import org.zkoss.poi.POIXMLException;
/*     */ import org.zkoss.poi.openxml4j.exceptions.InvalidFormatException;
/*     */ import org.zkoss.poi.openxml4j.exceptions.PartAlreadyExistsException;
/*     */ import org.zkoss.poi.openxml4j.opc.PackagePart;
/*     */ import org.zkoss.poi.openxml4j.opc.PackageRelationship;
/*     */ import org.zkoss.poi.openxml4j.opc.PackageRelationshipCollection;
/*     */ import org.zkoss.poi.ss.usermodel.PivotCache;
/*     */ import org.zkoss.poi.ss.usermodel.PivotCache.CacheField;
/*     */ import org.zkoss.poi.ss.usermodel.PivotTable;
/*     */ import org.zkoss.poi.ss.usermodel.Sheet;
/*     */ import org.zkoss.poi.ss.usermodel.Workbook;
/*     */ import org.zkoss.poi.ss.util.AreaReference;
/*     */ import org.zkoss.poi.ss.util.CellReference;
/*     */ import org.zkoss.poi.xssf.usermodel.XSSFFactory;
/*     */ import org.zkoss.poi.xssf.usermodel.XSSFPivotCache;
/*     */ import org.zkoss.poi.xssf.usermodel.XSSFPivotCacheRecords;
/*     */ import org.zkoss.poi.xssf.usermodel.XSSFPivotTable;
/*     */ import org.zkoss.poi.xssf.usermodel.XSSFRelation;
/*     */ import org.zkoss.poi.xssf.usermodel.XSSFSheet;
/*     */ import org.zkoss.poi.xssf.usermodel.XSSFWorkbook;
/*     */ import org.zkoss.zpoiex.util.Classes;
/*     */
/*     */ public class PivotTableHelper
/*     */   implements org.zkoss.poi.ss.usermodel.PivotTableHelper
/*     */ {
/*     */   public PivotCache createPivotCache(AreaReference sourceRef, Workbook book)
/*     */   {
/*  77 */     if ((book instanceof XSSFWorkbook)) {
/*  78 */       return createXSSFPivotCache(sourceRef, (XSSFWorkbook)book);
/*     */     }
/*  80 */     return null;
/*     */   }
/*     */
/*     */   private PivotCache createXSSFPivotCache(AreaReference sourceRef, XSSFWorkbook book) {
/*  84 */     List pivotCacheList = book.getPivotCaches();
/*  85 */     CTWorkbook ctWorkbook = book.getCTWorkbook();
/*  86 */     CTPivotCaches pivotCaches = ctWorkbook.getPivotCaches();
/*     */
/*  88 */     if (pivotCaches == null) {
/*  89 */       pivotCaches = ctWorkbook.addNewPivotCaches();
/*     */     }
/*     */
/*  92 */     CTPivotCache ctPivotCache = pivotCaches.addNewPivotCache();
/*     */
/*  94 */     int cacheId = 1;
/*  95 */     for (CTPivotCache c : pivotCaches.getPivotCacheList()) {
/*  96 */       cacheId = (int)Math.max(c.getCacheId() + 1L, cacheId);
/*     */     }
/*     */
/*  99 */     XSSFPivotCache pivotCache = (XSSFPivotCache)book.createRelationship(XSSFRelation.PIVOT_CACHE_DEFINITION, XSSFFactory.getInstance(), cacheId);
/* 100 */     ctPivotCache.setId(pivotCache.getPackageRelationship().getId());
/* 101 */     ctPivotCache.setCacheId(cacheId);
/*     */
/* 103 */     pivotCache.setCacheId(cacheId);
/* 104 */     pivotCache.setSheetSource(sourceRef);
/*     */
/* 107 */     pivotCacheList.add(pivotCache);
/* 108 */     return pivotCache;
/*     */   }
/*     */
/*     */   public List<PivotTable> initPivotTables(Sheet sheet) {
/* 112 */     if ((sheet instanceof XSSFSheet)) {
/* 113 */       return initXSSFPivotTables((XSSFSheet)sheet);
/*     */     }
/* 115 */     return Collections.emptyList();
/*     */   }
/*     */   private List<PivotTable> initXSSFPivotTables(XSSFSheet sheet) {
/* 118 */     XSSFWorkbook book = sheet.getWorkbook();
/* 119 */     List pivotCaches = book.getPivotCaches();
/*     */
/* 121 */     List pivotTableList = new ArrayList();
/* 122 */     PackagePart part = sheet.getPackagePart();
/*     */     try {
/* 124 */       PackageRelationshipCollection rels = part.getRelationshipsByType(XSSFRelation.PIVOT_TABLE.getRelation());
/* 125 */       for (PackageRelationship rel : rels) {
/* 126 */         PackagePart p = part.getRelatedPart(rel);
/* 127 */         XSSFPivotTable pivotTable = new XSSFPivotTable(p, rel, pivotCaches);
/* 128 */         pivotTableList.add(pivotTable);
/*     */       }
/* 130 */       return pivotTableList;
/*     */     } catch (InvalidFormatException e) {
/* 132 */       throw new POIXMLException(e);
/*     */     } catch (XmlException e) {
/* 134 */       throw new POIXMLException(e);
/*     */     } catch (IOException e) {
/* 136 */       throw new POIXMLException(e);
/*     */     }
/*     */   }
/*     */
/*     */   public List<PivotCache> initPivotCaches(Workbook book) {
/* 141 */     if ((book instanceof XSSFWorkbook)) {
/* 142 */       return initXSSFPivotCaches((XSSFWorkbook)book);
/*     */     }
/* 144 */     return Collections.emptyList();
/*     */   }
/*     */
/*     */   private List<PivotCache> initXSSFPivotCaches(XSSFWorkbook book) {
/*     */     try {
/* 149 */       List pivotCacheList = new ArrayList();
/* 150 */       CTPivotCaches pivotCaches = book.getCTPivotCaches();
/* 151 */       if (pivotCaches == null) {
/* 152 */         return pivotCacheList;
/*     */       }
/*     */
/* 155 */       for (CTPivotCache ctPivotCache : pivotCaches.getPivotCacheList()) {
/* 156 */         POIXMLDocumentPart p = book.getRelationById(ctPivotCache.getId());
/* 157 */         pivotCacheList.add(new XSSFPivotCache(ctPivotCache.getCacheId(), book, p.getPackagePart(), p.getPackageRelationship()));
/*     */       }
/* 159 */       return pivotCacheList;
/*     */     } catch (IOException e) {
/* 161 */       throw new POIXMLException(e);
/*     */     }
/*     */   }
/*     */
/*     */   public PivotTable createPivotTable(CellReference destination, String name, PivotCache pivotCache, Sheet sheet) {
/* 166 */     List pivotTables = sheet.getPivotTables();
/* 167 */     if ((sheet instanceof XSSFSheet)) {
/* 168 */       if (containsPivotTable(name, pivotTables)) {
/* 169 */         throw new IllegalArgumentException("Already contains a pivot tabel of this name");
/*     */       }
/*     */
/* 172 */       XSSFSheet sheet0 = (XSSFSheet)sheet;
/* 173 */       XSSFPivotCache cache = (XSSFPivotCache)pivotCache;
/*     */
/* 175 */       int pvIdx = pivotTables.size() + 1;
/*     */
/* 177 */       XSSFPivotTable pt = null;
/* 178 */       while (pt == null)
/*     */         try {
/* 180 */           pt = (XSSFPivotTable)sheet0.createRelationship(XSSFRelation.PIVOT_TABLE, XSSFFactory.getInstance(), pvIdx++);
/*     */         }
/*     */         catch (PartAlreadyExistsException e)
/*     */         {
/*     */         }
/* 185 */       pt.setPivotCache(cache);
/* 186 */       pt.setName(name);
/*     */
/* 188 */       pivotTables.add(pt);
/*     */
/* 191 */       CTPivotTableDefinition pivotTableDefinition = pt.getPivotTableDefinition();
/* 192 */       AreaReference ref = new AreaReference(destination, new CellReference(destination.getRow() + 17, destination.getCol() + 2));
/* 193 */       pt.setFirstHeaderRow(1);
/* 194 */       pt.setFirstData(1, 0);
/* 195 */       pt.setLocationRef(ref);
/*     */
/* 198 */       CTPivotFields pivotFields = pivotTableDefinition.addNewPivotFields();
/* 199 */       for (PivotCache.CacheField cf : pivotCache.getFields()) {
/* 200 */         CTPivotField pivotField = pivotFields.addNewPivotField();
/* 201 */         pivotField.setShowAll(false);
/* 202 */         long formatId = cf.getNumberFormatId();
/* 203 */         if (formatId > 0L) {
/* 204 */           pivotField.setNumFmtId(formatId);
/*     */         }
/*     */       }
/* 207 */       pivotFields.setCount(pivotFields.getPivotFieldList().size());
/*     */
/* 210 */       CTPivotTableStyle pivotTableStyleInfo = pivotTableDefinition.addNewPivotTableStyleInfo();
/* 211 */       pivotTableStyleInfo.setName("PivotStyleLight16");
/* 212 */       pivotTableStyleInfo.setShowRowHeaders(true);
/* 213 */       pivotTableStyleInfo.setShowColHeaders(true);
/* 214 */       pivotTableStyleInfo.setShowRowStripes(false);
/* 215 */       pivotTableStyleInfo.setShowColStripes(false);
/* 216 */       pivotTableStyleInfo.setShowLastColumn(true);
/* 217 */       return pt;
/*     */     }
/* 219 */     return null;
/*     */   }
/*     */   private boolean containsPivotTable(String name, List<PivotTable> pivotTables) {
/* 222 */     for (PivotTable pt : pivotTables) {
/* 223 */       if (pt.getName().equalsIgnoreCase(name)) {
/* 224 */         return true;
/*     */       }
/*     */     }
/* 227 */     return false;
/*     */   }
/*     */
/*     */   static
/*     */   {
/*  57 */     Field fd = null;
/*     */     try {
/*  59 */       fd = Classes.getAnyField(XSSFRelation.class, "_cls");
/*     */     } catch (NoSuchFieldException e) {
/*  61 */       throw new RuntimeException(e);
/*     */     }
/*  63 */     boolean old = fd.isAccessible();
/*     */     try {
/*  65 */       fd.setAccessible(true);
/*  66 */       fd.set(XSSFRelation.PIVOT_TABLE, XSSFPivotTable.class);
/*  67 */       fd.set(XSSFRelation.PIVOT_CACHE_DEFINITION, XSSFPivotCache.class);
/*  68 */       fd.set(XSSFRelation.PIVOT_CACHE_RECORDS, XSSFPivotCacheRecords.class);
/*     */     } catch (IllegalAccessException e) {
/*  70 */       throw new RuntimeException(e);
/*     */     } finally {
/*  72 */       fd.setAccessible(old);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zpoiex-3.9.3.jar
 * Qualified Name:     org.zkoss.zpoiex.ss.usermodel.helpers.PivotTableHelper
 * JD-Core Version:    0.6.1
 */