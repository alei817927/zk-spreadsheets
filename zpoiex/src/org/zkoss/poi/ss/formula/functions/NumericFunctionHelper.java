/*    */ package org.zkoss.poi.ss.formula.functions;
/*    */ 
/*    */ import org.zkoss.poi.ss.formula.eval.EvaluationException;
/*    */ 
/*    */ public class NumericFunctionHelper
/*    */ {
/*    */   public static final double checkValue(double result)
/*    */     throws EvaluationException
/*    */   {
/* 24 */     NumericFunction.checkValue(result);
/* 25 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zpoiex-3.9.3.jar
 * Qualified Name:     org.zkoss.poi.ss.formula.functions.NumericFunctionHelper
 * JD-Core Version:    0.6.1
 */