/*    */ package org.zkoss.zssex.ui.impl.ua;
/*    */
/*    */ import org.zkoss.zss.api.Range;
/*    */ import org.zkoss.zss.api.Ranges;
/*    */ import org.zkoss.zss.api.model.Picture;
/*    */ import org.zkoss.zss.ui.UserActionContext;
/*    */ import org.zkoss.zss.ui.impl.ua.AbstractProtectedHandler;
/*    */
/*    */ public class DeletePictureHandler extends AbstractProtectedHandler
/*    */ {
/*    */   protected boolean processAction(UserActionContext ctx)
/*    */   {
/* 43 */     Picture picture = (Picture)ctx.getData("picture");
/* 44 */     Range range = Ranges.range(ctx.getSheet());
/* 45 */     range.deletePicture(picture);
/* 46 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.ui.impl.ua.DeletePictureHandler
 * JD-Core Version:    0.6.1
 */