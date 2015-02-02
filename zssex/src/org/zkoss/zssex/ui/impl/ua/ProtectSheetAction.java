/*    */ package org.zkoss.zssex.ui.impl.ua;
/*    */
/*    */ import org.zkoss.zss.api.Range;
/*    */ import org.zkoss.zss.api.Ranges;
/*    */ import org.zkoss.zss.api.SheetOperationUtil;
/*    */ import org.zkoss.zss.api.model.Sheet;
/*    */ import org.zkoss.zss.ui.UserActionContext;
/*    */ import org.zkoss.zss.ui.impl.ua.AbstractSheetHandler;
/*    */
/*    */ public class ProtectSheetAction extends AbstractSheetHandler
/*    */ {
/*    */   protected boolean processAction(UserActionContext ctx)
/*    */   {
/* 37 */     Sheet sheet = ctx.getSheet();
/*    */
/* 39 */     Range range = Ranges.range(sheet);
/*    */
/* 41 */     String newpassword = getPassword();
/* 42 */     if (range.isProtected())
/* 43 */       SheetOperationUtil.protectSheet(range, null, null);
/*    */     else {
/* 45 */       SheetOperationUtil.protectSheet(range, null, newpassword);
/*    */     }
/* 47 */     return true;
/*    */   }
/*    */
/*    */   protected String getPassword() {
/* 51 */     return "1234";
/*    */   }
/*    */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.ui.impl.ua.ProtectSheetAction
 * JD-Core Version:    0.6.1
 */