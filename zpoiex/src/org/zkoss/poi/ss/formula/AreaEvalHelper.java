/*    */ package org.zkoss.poi.ss.formula;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.zkoss.poi.ss.formula.eval.AreaEval;
/*    */ import org.zkoss.poi.ss.formula.eval.EvaluationException;
/*    */ import org.zkoss.poi.ss.formula.eval.NumberEval;
/*    */ import org.zkoss.poi.ss.formula.eval.StringEval;
/*    */ import org.zkoss.poi.ss.formula.eval.ValueEval;
/*    */ import org.zkoss.poi.ss.formula.functions.TextFunctionHelper;
/*    */ import org.zkoss.poi.ss.formula.functions.UtilFns;
/*    */ import org.zkoss.zpoiex.util.Objects;
/*    */ 
/*    */ public class AreaEvalHelper
/*    */ {
/*    */   public static ValueEval getRelativeValue(ValueEval valueEval, int relativeRowIndex, int relativeColumnIndex)
/*    */   {
/* 35 */     return ((AreaEval)valueEval).getRelativeValue(relativeRowIndex, relativeColumnIndex);
/*    */   }
/*    */ 
/*    */   public static double[][] toDoubleMatrix(ValueEval valueEval, int relativeRowIndex, int relativeColumnIndex) throws EvaluationException {
/* 39 */     double[][] d = (double[][])null;
/* 40 */     if ((valueEval instanceof AreaEval)) {
/* 41 */       AreaEval areaEval = (AreaEval)valueEval;
/* 42 */       int col = areaEval.getLastColumn() - areaEval.getFirstColumn();
/* 43 */       int row = areaEval.getLastRow() - areaEval.getFirstRow();
/* 44 */       d = new double[col + 1][row + 1];
/* 45 */       for (int i = 0; i <= col; i++) {
/* 46 */         for (int j = 0; j <= row; j++) {
/* 47 */           d[i][j] = TextFunctionHelper.evaluateDoubleArg(areaEval.getRelativeValue(j, i), relativeRowIndex, relativeColumnIndex);
/*    */         }
/*    */       }
/*    */     }
/* 51 */     return d;
/*    */   }
/*    */ 
/*    */   public static List<Double> toDoubleList(ValueEval valueEval, int srcRowIndex, int srcColumnIndex) throws EvaluationException
/*    */   {
/* 56 */     List d = new LinkedList();
/* 57 */     if ((valueEval instanceof AreaEval)) {
/* 58 */       AreaEval areaEval = (AreaEval)valueEval;
/* 59 */       int col = areaEval.getLastColumn() - areaEval.getFirstColumn();
/* 60 */       int row = areaEval.getLastRow() - areaEval.getFirstRow();
/* 61 */       for (int i = 0; i <= col; i++) {
/* 62 */         for (int j = 0; j <= row; j++) {
/* 63 */           ValueEval val = areaEval.getRelativeValue(j, i);
/* 64 */           if ((val instanceof StringEval)) {
/* 65 */             Double a = null;
/* 66 */             Double dval = (a = UtilFns.stringToDouble(((StringEval)val).getStringValue(), true)) == null ? Objects.ZERO_DOUBLE : a;
/* 67 */             d.add(dval);
/* 68 */           } else if ((val instanceof NumberEval)) {
/* 69 */             d.add(new Double(TextFunctionHelper.evaluateDoubleArg(val, srcRowIndex, srcColumnIndex)));
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/* 74 */     return d;
/*    */   }
/*    */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zpoiex-3.9.3.jar
 * Qualified Name:     org.zkoss.poi.ss.formula.AreaEvalHelper
 * JD-Core Version:    0.6.1
 */