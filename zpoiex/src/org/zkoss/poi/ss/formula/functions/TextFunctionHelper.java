/*    */ package org.zkoss.poi.ss.formula.functions;
/*    */ 
/*    */ import org.zkoss.poi.ss.formula.eval.EvaluationException;
/*    */ import org.zkoss.poi.ss.formula.eval.ValueEval;
/*    */ 
/*    */ public class TextFunctionHelper
/*    */ {
/*    */   public static final int evaluateIntArg(ValueEval eval, int srcCellRow, int srcCellCol)
/*    */     throws EvaluationException
/*    */   {
/* 22 */     return TextFunction.evaluateIntArg(eval, srcCellRow, srcCellCol);
/*    */   }
/*    */   public static final String evaluateStringArg(ValueEval eval, int srcCellRow, int srcCellCol) throws EvaluationException {
/* 25 */     return TextFunction.evaluateStringArg(eval, srcCellRow, srcCellCol);
/*    */   }
/*    */   public static final double evaluateDoubleArg(ValueEval eval, int srcCellRow, int srcCellCol) throws EvaluationException {
/* 28 */     return TextFunction.evaluateDoubleArg(eval, srcCellRow, srcCellCol);
/*    */   }
/*    */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zpoiex-3.9.3.jar
 * Qualified Name:     org.zkoss.poi.ss.formula.functions.TextFunctionHelper
 * JD-Core Version:    0.6.1
 */