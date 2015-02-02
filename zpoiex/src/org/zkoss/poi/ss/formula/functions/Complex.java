/*    */ package org.zkoss.poi.ss.formula.functions;
/*    */ 
/*    */ import org.zkoss.zpoiex.util.Objects;
/*    */ 
/*    */ public class Complex extends org.apache.commons.math.complex.Complex
/*    */ {
/*    */   protected String suffix;
/*    */ 
/*    */   public Complex(double real, double imaginary, String suffix)
/*    */   {
/* 10 */     super(real, imaginary);
/* 11 */     this.suffix = suffix;
/*    */   }
/*    */ 
/*    */   public Complex(double real, double imaginary) {
/* 15 */     this(real, imaginary, "i");
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 19 */     return (this.suffix == null ? 0 : this.suffix.hashCode()) ^ super.hashCode();
/*    */   }
/*    */   public boolean equals(Object arg0) {
/* 22 */     return (this == arg0) || (((arg0 instanceof Complex)) && (super.equals(arg0)) && (Objects.equals(this.suffix, ((Complex)arg0).getSuffix())));
/*    */   }
/*    */ 
/*    */   public String getSuffix()
/*    */   {
/* 29 */     return this.suffix;
/*    */   }
/*    */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zpoiex-3.9.3.jar
 * Qualified Name:     org.zkoss.poi.ss.formula.functions.Complex
 * JD-Core Version:    0.6.1
 */