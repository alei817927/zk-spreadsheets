/*    */ package org.zkoss.poi.xssf.usermodel;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTDataField;
/*    */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.STDataConsolidateFunction;
/*    */ import org.openxmlformats.schemas.spreadsheetml.x2006.main.STDataConsolidateFunction.Enum;
/*    */ import org.zkoss.poi.ss.usermodel.Calculation;
/*    */ import org.zkoss.poi.ss.usermodel.DataField;
/*    */ import org.zkoss.poi.ss.usermodel.PivotField;
/*    */ 
/*    */ public class XSSFDataField
/*    */   implements DataField
/*    */ {
/*    */   private final CTDataField _dataField;
/*    */   private final PivotField _pivotField;
/*    */   private static HashMap<STDataConsolidateFunction.Enum, Calculation> subtotalMap;
/*    */   private static HashMap<Calculation, STDataConsolidateFunction.Enum> subtotalMap2;
/*    */ 
/*    */   XSSFDataField(CTDataField dataField, PivotField pivotField)
/*    */   {
/* 41 */     this._dataField = dataField;
/* 42 */     this._pivotField = pivotField;
/*    */   }
/*    */ 
/*    */   public PivotField getPivotField() {
/* 46 */     return this._pivotField;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 50 */     return this._dataField.getName();
/*    */   }
/*    */ 
/*    */   public Calculation getSubtotal() {
/* 54 */     STDataConsolidateFunction.Enum type = this._dataField.getSubtotal();
/* 55 */     if (subtotalMap == null) {
/* 56 */       initSubtotalMap();
/*    */     }
/* 58 */     return (Calculation)subtotalMap.get(type);
/*    */   }
/*    */ 
/*    */   private static void initSubtotalMap()
/*    */   {
/* 63 */     subtotalMap = new HashMap();
/* 64 */     subtotalMap.put(STDataConsolidateFunction.AVERAGE, Calculation.AVERAGE);
/* 65 */     subtotalMap.put(STDataConsolidateFunction.COUNT, Calculation.COUNT);
/* 66 */     subtotalMap.put(STDataConsolidateFunction.COUNT_NUMS, Calculation.COUNT_NUMS);
/* 67 */     subtotalMap.put(STDataConsolidateFunction.MAX, Calculation.MAX);
/* 68 */     subtotalMap.put(STDataConsolidateFunction.MIN, Calculation.MIN);
/* 69 */     subtotalMap.put(STDataConsolidateFunction.PRODUCT, Calculation.PRODUCT);
/* 70 */     subtotalMap.put(STDataConsolidateFunction.STD_DEV, Calculation.STD_DEV);
/* 71 */     subtotalMap.put(STDataConsolidateFunction.STD_DEVP, Calculation.STD_DEV_P);
/* 72 */     subtotalMap.put(STDataConsolidateFunction.SUM, Calculation.SUM);
/* 73 */     subtotalMap.put(STDataConsolidateFunction.VAR, Calculation.VARIANCE);
/* 74 */     subtotalMap.put(STDataConsolidateFunction.VARP, Calculation.VARIANCE_P);
/*    */ 
/* 76 */     subtotalMap2 = new HashMap();
/* 77 */     subtotalMap2.put(Calculation.AVERAGE, STDataConsolidateFunction.AVERAGE);
/* 78 */     subtotalMap2.put(Calculation.COUNT, STDataConsolidateFunction.COUNT);
/* 79 */     subtotalMap2.put(Calculation.COUNT_NUMS, STDataConsolidateFunction.COUNT_NUMS);
/* 80 */     subtotalMap2.put(Calculation.MAX, STDataConsolidateFunction.MAX);
/* 81 */     subtotalMap2.put(Calculation.MIN, STDataConsolidateFunction.MIN);
/* 82 */     subtotalMap2.put(Calculation.PRODUCT, STDataConsolidateFunction.PRODUCT);
/* 83 */     subtotalMap2.put(Calculation.STD_DEV, STDataConsolidateFunction.STD_DEV);
/* 84 */     subtotalMap2.put(Calculation.STD_DEV_P, STDataConsolidateFunction.STD_DEVP);
/* 85 */     subtotalMap2.put(Calculation.SUM, STDataConsolidateFunction.SUM);
/* 86 */     subtotalMap2.put(Calculation.VARIANCE, STDataConsolidateFunction.VAR);
/* 87 */     subtotalMap2.put(Calculation.VARIANCE_P, STDataConsolidateFunction.VARP);
/*    */   }
/*    */ 
/*    */   static STDataConsolidateFunction.Enum getSubtotalType(Calculation subtotal) {
/* 91 */     if (subtotalMap2 == null) {
/* 92 */       initSubtotalMap();
/*    */     }
/* 94 */     return (STDataConsolidateFunction.Enum)subtotalMap2.get(subtotal);
/*    */   }
/*    */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zpoiex-3.9.3.jar
 * Qualified Name:     org.zkoss.poi.xssf.usermodel.XSSFDataField
 * JD-Core Version:    0.6.1
 */