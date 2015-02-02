/*    */ package org.zkoss.zssex.ui.impl.ua;
/*    */
/*    */ import org.zkoss.zss.api.Range;
/*    */ import org.zkoss.zss.api.Ranges;
/*    */ import org.zkoss.zss.api.SheetAnchor;
/*    */ import org.zkoss.zss.api.model.Chart;
/*    */ import org.zkoss.zss.ui.UserActionContext;
/*    */ import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
/*    */
/*    */ public class MoveChartHandler extends AbstractProtectedHandler
/*    */ {
/*    */   protected boolean processAction(UserActionContext ctx)
/*    */   {
/* 42 */     Chart chart = (Chart)ctx.getData("chart");
/* 43 */     SheetAnchor anchor = (SheetAnchor)ctx.getData("anchor");
/* 44 */     Range range = Ranges.range(ctx.getSheet());
/* 45 */     range.moveChart(anchor, chart);
/* 46 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.ui.impl.ua.MoveChartHandler
 * JD-Core Version:    0.6.1
 */