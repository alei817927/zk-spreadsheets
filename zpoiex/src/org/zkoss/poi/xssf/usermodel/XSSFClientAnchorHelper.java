/*    */ package org.zkoss.poi.xssf.usermodel;
/*    */ 
/*    */ import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;
/*    */ 
/*    */ public class XSSFClientAnchorHelper
/*    */ {
/*    */   public static XSSFClientAnchor newXSSFClientAnchor(CTMarker from, CTMarker to)
/*    */   {
/* 24 */     return new XSSFClientAnchor(from, to);
/*    */   }
/*    */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zpoiex-3.9.3.jar
 * Qualified Name:     org.zkoss.poi.xssf.usermodel.XSSFClientAnchorHelper
 * JD-Core Version:    0.6.1
 */