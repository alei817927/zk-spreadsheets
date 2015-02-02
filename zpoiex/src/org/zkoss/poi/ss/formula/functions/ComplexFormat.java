/*    */ package org.zkoss.poi.ss.formula.functions;
/*    */ 
/*    */ import java.text.NumberFormat;
/*    */ import java.text.ParseException;
/*    */ 
/*    */ public class ComplexFormat extends org.apache.commons.math.complex.ComplexFormat
/*    */ {
/*    */   public ComplexFormat()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ComplexFormat(NumberFormat realFormat, NumberFormat imaginaryFormat)
/*    */   {
/* 17 */     super(realFormat, imaginaryFormat);
/*    */   }
/*    */ 
/*    */   public ComplexFormat(NumberFormat format)
/*    */   {
/* 22 */     super(format);
/*    */   }
/*    */ 
/*    */   public ComplexFormat(String imaginaryCharacter, NumberFormat realFormat, NumberFormat imaginaryFormat)
/*    */   {
/* 28 */     super(imaginaryCharacter, realFormat, imaginaryFormat);
/*    */   }
/*    */ 
/*    */   public ComplexFormat(String imaginaryCharacter, NumberFormat format)
/*    */   {
/* 33 */     super(imaginaryCharacter, format);
/*    */   }
/*    */ 
/*    */   public ComplexFormat(String imaginaryCharacter)
/*    */   {
/* 38 */     super(imaginaryCharacter);
/*    */   }
/*    */ 
/*    */   public Complex parse(String source, String suffix) throws ParseException
/*    */   {
/* 43 */     org.apache.commons.math.complex.Complex c = super.parse(source);
/* 44 */     return new Complex(c.getReal(), c.getImaginary(), suffix);
/*    */   }
/*    */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zpoiex-3.9.3.jar
 * Qualified Name:     org.zkoss.poi.ss.formula.functions.ComplexFormat
 * JD-Core Version:    0.6.1
 */