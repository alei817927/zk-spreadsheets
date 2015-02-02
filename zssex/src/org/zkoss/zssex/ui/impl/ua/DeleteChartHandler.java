/*    */ package org.zkoss.zssex.ui.impl.ua;
/*    */
/*    */ import org.zkoss.zss.api.Range;
/*    */ import org.zkoss.zss.api.Ranges;
/*    */ import org.zkoss.zss.api.model.Chart;
/*    */ import org.zkoss.zss.ui.UserActionContext;
/*    */ import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
/*    */
/*    */ public class DeleteChartHandler extends AbstractProtectedHandler
/*    */ {
/*    */   protected boolean processAction(UserActionContext ctx)
/*    */   {
/* 41 */     Chart chart = (Chart)ctx.getData("chart");
/* 42 */     Range range = Ranges.range(ctx.getSheet());
/* 43 */     range.deleteChart(chart);
/* 44 */     return true;
/*    */   }
/*    */ }
