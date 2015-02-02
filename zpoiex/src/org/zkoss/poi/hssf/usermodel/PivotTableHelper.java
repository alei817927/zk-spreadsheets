/*    */ package org.zkoss.poi.hssf.usermodel;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import org.zkoss.poi.xssf.usermodel.XSSFPivotCache;
/*    */ import org.zkoss.poi.xssf.usermodel.XSSFPivotCacheRecords;
/*    */ import org.zkoss.poi.xssf.usermodel.XSSFPivotTable;
/*    */ import org.zkoss.poi.xssf.usermodel.XSSFRelation;
/*    */ import org.zkoss.zpoiex.util.Classes;
/*    */ 
/*    */ public class PivotTableHelper
/*    */ {
/*    */   static
/*    */   {
/* 29 */     Field fd = null;
/*    */     try {
/* 31 */       fd = Classes.getAnyField(XSSFRelation.class, "_cls");
/*    */     } catch (NoSuchFieldException e) {
/* 33 */       throw new RuntimeException(e);
/*    */     }
/* 35 */     boolean old = fd.isAccessible();
/*    */     try {
/* 37 */       fd.setAccessible(true);
/* 38 */       fd.set(XSSFRelation.PIVOT_TABLE, XSSFPivotTable.class);
/* 39 */       fd.set(XSSFRelation.PIVOT_CACHE_DEFINITION, XSSFPivotCache.class);
/* 40 */       fd.set(XSSFRelation.PIVOT_CACHE_RECORDS, XSSFPivotCacheRecords.class);
/*    */     } catch (IllegalAccessException e) {
/* 42 */       throw new RuntimeException(e);
/*    */     } finally {
/* 44 */       fd.setAccessible(old);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zpoiex-3.9.3.jar
 * Qualified Name:     org.zkoss.poi.hssf.usermodel.PivotTableHelper
 * JD-Core Version:    0.6.1
 */