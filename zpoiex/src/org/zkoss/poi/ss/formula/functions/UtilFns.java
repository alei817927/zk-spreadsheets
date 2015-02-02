/*     */ package org.zkoss.poi.ss.formula.functions;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
/*     */ import org.apache.commons.math.stat.regression.SimpleRegression;
/*     */ import org.zkoss.lang.Classes;
/*     */ import org.zkoss.poi.ss.formula.AreaEvalHelper;
/*     */ import org.zkoss.poi.ss.formula.eval.AreaEval;
/*     */ import org.zkoss.poi.ss.formula.eval.BoolEval;
/*     */ import org.zkoss.poi.ss.formula.eval.ErrorEval;
/*     */ import org.zkoss.poi.ss.formula.eval.EvaluationException;
/*     */ import org.zkoss.poi.ss.formula.eval.NumberEval;
/*     */ import org.zkoss.poi.ss.formula.eval.StringEval;
/*     */ import org.zkoss.poi.ss.formula.eval.ValueEval;
/*     */ import org.zkoss.poi.ss.usermodel.DateUtil;
/*     */ import org.zkoss.util.Dates;
/*     */ import org.zkoss.zpoiex.util.Objects;
/*     */ 
/*     */ public class UtilFns
/*     */ {
/*  47 */   public static final TimeZone TZ_GMT = TimeZone.getTimeZone("GMT");
/*  48 */   public static final Calendar CAL_GMT = Calendar.getInstance(TZ_GMT);
/*     */   private static final Date DATE1899_12_30;
/*  58 */   private static final Date DATE1900_3_1 = CAL_GMT.getTime();
/*     */   private static final int PRECISION_SIZE = 15;
/*     */ 
/*     */   public static Date stringToDate(ValueEval arg)
/*     */     throws EvaluationException
/*     */   {
/*  68 */     if (!(arg instanceof StringEval)) {
/*  69 */       throw new EvaluationException(ErrorEval.VALUE_INVALID);
/*     */     }
/*  71 */     SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
/*  72 */     Date result = null;
/*     */     try {
/*  74 */       result = df.parse(((StringEval)arg).getStringValue());
/*     */     } catch (ParseException e) {
/*  76 */       throw new EvaluationException(ErrorEval.VALUE_INVALID);
/*     */     }
/*  78 */     return result;
/*     */   }
/*     */ 
/*     */   public static boolean stringToBoolean(ValueEval arg) throws EvaluationException {
/*  82 */     if (!(arg instanceof BoolEval)) {
/*  83 */       throw new EvaluationException(ErrorEval.VALUE_INVALID);
/*     */     }
/*  85 */     return ((BoolEval)arg).getBooleanValue();
/*     */   }
/*     */ 
/*     */   public static int dsm(Date settle, Date maturi, int basis) throws EvaluationException {
/*  89 */     if (settle.compareTo(maturi) > 0) {
/*  90 */       throw new EvaluationException(ErrorEval.NUM_ERROR);
/*     */     }
/*  92 */     int dsm = 0;
/*  93 */     if ((basis == 0) || (basis == 4)) {
/*  94 */       SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
/*  95 */       Calendar calendar = df.getCalendar();
/*  96 */       calendar.setTime(settle);
/*  97 */       int settleY = calendar.get(1);
/*  98 */       int settleM = calendar.get(2);
/*  99 */       int settleD = calendar.get(5) > 30 ? 30 : calendar.get(5);
/* 100 */       calendar.setTime(maturi);
/* 101 */       int maturiM = calendar.get(2);
/* 102 */       int maturiY = calendar.get(1);
/* 103 */       int maturiD = calendar.get(5) > 30 ? 30 : calendar.get(5);
/* 104 */       dsm = (maturiY - settleY) * 360 + (maturiM - settleM) * 30 + (maturiD - settleD);
/*     */     } else {
/* 106 */       dsm = (int)(DateUtil.getExcelDate(maturi) - DateUtil.getExcelDate(settle));
/*     */     }
/*     */ 
/* 109 */     return dsm;
/*     */   }
/*     */ 
/*     */   public static Long dateToDays(Date date)
/*     */   {
/* 121 */     long diff = Dates.subtract(date, TZ_GMT, 5, DATE1899_12_30);
/* 122 */     if (DATE1900_3_1.after(date)) diff -= 1L;
/* 123 */     return Long.valueOf(diff);
/*     */   }
/*     */ 
/*     */   public static double basisToDouble(int basis, Date settle, Date maturi, int dsm) {
/* 127 */     double result = 0.0D;
/* 128 */     if ((basis == 0) || (basis == 2) || (basis == 4)) {
/* 129 */       result = 360.0D;
/* 130 */     } else if (basis == 1) {
/* 131 */       SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
/* 132 */       Calendar calendar = df.getCalendar();
/* 133 */       calendar.setTime(maturi);
/* 134 */       int mYear = calendar.get(1);
/* 135 */       int mMonth = calendar.get(2) + 1;
/* 136 */       int mDate = calendar.get(5);
/* 137 */       calendar.setTime(settle);
/* 138 */       int sYear = calendar.get(1);
/* 139 */       int sMonth = calendar.get(2) + 1;
/* 140 */       if (dsm > 366) {
/* 141 */         result = 365.5D;
/*     */       }
/* 143 */       else if (((new GregorianCalendar().isLeapYear(mYear)) && ((mMonth > 2) || ((mMonth == 2) && (mDate == 29)))) || ((new GregorianCalendar().isLeapYear(sYear)) && (sMonth < 3)))
/*     */       {
/* 145 */         result = 366.0D;
/*     */       }
/* 147 */       else result = 365.0D;
/*     */ 
/*     */     }
/* 150 */     else if (basis == 3) {
/* 151 */       result = 365.0D;
/*     */     }
/* 153 */     return result;
/*     */   }
/*     */   public static String padZero(String num, int places) throws EvaluationException {
/* 156 */     if (places <= 0) {
/* 157 */       throw new EvaluationException(ErrorEval.NUM_ERROR);
/*     */     }
/* 159 */     StringBuffer result = new StringBuffer();
/* 160 */     for (int i = 0; i < places - num.length(); i++) {
/* 161 */       result.append("0");
/*     */     }
/* 163 */     result.append(num);
/* 164 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public static String replaceiToi1(String complex, String suffix)
/*     */   {
/* 173 */     int i = complex.indexOf(suffix);
/* 174 */     while (i != -1) {
/* 175 */       if (!Character.isDigit(complex.charAt(i - 1))) {
/* 176 */         StringBuffer sf = new StringBuffer(complex);
/* 177 */         complex = sf.replace(i, i + 1, "1" + suffix).toString();
/*     */       }
/* 179 */       i = complex.indexOf(suffix, i + 1);
/*     */     }
/* 181 */     return complex;
/*     */   }
/*     */ 
/*     */   public static Complex validateComplex(String complex)
/*     */     throws EvaluationException
/*     */   {
/* 191 */     Complex result = null;
/* 192 */     String s = "i";
/* 193 */     int i = complex.indexOf(s);
/* 194 */     if (i == -1) {
/* 195 */       s = "j";
/* 196 */       i = complex.indexOf(s);
/* 197 */       if (i == -1) {
/* 198 */         s = "k";
/*     */       }
/*     */     }
/* 201 */     ComplexFormat cf = new ComplexFormat(s);
/* 202 */     complex = replaceiToi1(complex, s);
/*     */     try {
/* 204 */       result = cf.parse(complex, s);
/*     */     } catch (ParseException e) {
/* 206 */       throw new EvaluationException(ErrorEval.NUM_ERROR);
/*     */     }
/* 208 */     return result;
/*     */   }
/*     */ 
/*     */   public static Complex cToComplex(org.apache.commons.math.complex.Complex c, String suffix)
/*     */   {
/* 217 */     Complex result = new Complex(c.getReal(), c.getImaginary(), suffix);
/* 218 */     return result;
/*     */   }
/*     */ 
/*     */   public static String format(Complex c)
/*     */   {
/* 227 */     NumberFormat nfr = NumberFormat.getInstance();
/* 228 */     nfr.setMaximumFractionDigits(14);
/* 229 */     NumberFormat nfi = NumberFormat.getInstance();
/* 230 */     nfi.setMaximumFractionDigits(14);
/* 231 */     ComplexFormat cf = new ComplexFormat(nfr, nfi);
/*     */ 
/* 233 */     return cf.format(c).replace(" ", "");
/*     */   }
/*     */ 
/*     */   public static int[] toIntArray(Object[] objs)
/*     */     throws EvaluationException
/*     */   {
/* 242 */     int[] in = new int[objs.length];
/* 243 */     for (int i = 0; i < objs.length; i++) {
/* 244 */       if ((objs[i] instanceof ErrorEval)) {
/* 245 */         throw new EvaluationException((ErrorEval)objs[i]);
/*     */       }
/* 247 */       in[i] = ((Number)Classes.coerce(Integer.TYPE, objs[i])).intValue();
/*     */     }
/*     */ 
/* 250 */     return in;
/*     */   }
/*     */   public static double[][] toDoubleMatrix(ValueEval arg, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
/* 253 */     if ((arg instanceof AreaEval)) {
/* 254 */       return AreaEvalHelper.toDoubleMatrix(arg, srcRowIndex, srcColumnIndex);
/*     */     }
/* 256 */     throw new EvaluationException(ErrorEval.VALUE_INVALID);
/*     */   }
/*     */ 
/*     */   public static double toDouble15(double dbl, int roundingMode)
/*     */   {
/* 267 */     boolean neg = dbl < 0.0D;
/* 268 */     String dbs = "" + dbl;
/* 269 */     BigDecimal bdx = new BigDecimal(dbs);
/* 270 */     int sz = dbs.length();
/* 271 */     int dotj = -1;
/* 272 */     int expj = -1;
/* 273 */     int scale = 0;
/* 274 */     if (sz > 15) {
/* 275 */       for (int j = 0; j < sz; j++) {
/* 276 */         char ch = dbs.charAt(j);
/* 277 */         if ('.' == ch) {
/* 278 */           dotj = j;
/* 279 */         } else if ('E' == ch) {
/* 280 */           expj = j;
/* 281 */           break;
/*     */         }
/*     */       }
/* 284 */       int useful = expj >= 0 ? expj : sz;
/* 285 */       int expsz = expj >= 0 ? Integer.parseInt(dbs.substring(expj + 1)) : 0;
/*     */ 
/* 287 */       if (dotj >= 0) {
/* 288 */         useful--;
/*     */       }
/* 290 */       if (neg) {
/* 291 */         useful--;
/* 292 */         dotj--;
/*     */       }
/* 294 */       if (useful > 15) {
/* 295 */         if (dotj < 0) dotj = useful;
/* 296 */         scale = 15 - dotj - expsz;
/* 297 */         bdx = bdx.setScale(scale, roundingMode);
/*     */       }
/*     */     }
/* 300 */     return bdx.doubleValue();
/*     */   }
/*     */ 
/*     */   public static List toList(ValueEval[] args, int srcRowIndex, int srcColumnIndex)
/*     */     throws EvaluationException
/*     */   {
/* 306 */     List result = new LinkedList();
/*     */ 
/* 308 */     for (int i = 0; i < args.length; i++) {
/* 309 */       toNumber(result, args[i], srcRowIndex, srcColumnIndex);
/*     */     }
/* 311 */     return result;
/*     */   }
/*     */ 
/*     */   private static void toNumber(List result, ValueEval arg, int srcRowIndex, int srcColumnIndex) throws EvaluationException
/*     */   {
/* 316 */     if ((arg instanceof StringEval)) {
/* 317 */       Double val = stringToDouble(TextFunctionHelper.evaluateStringArg(arg, srcRowIndex, srcColumnIndex), true);
/* 318 */       if (val == null)
/* 319 */         result.add(Objects.ZERO_DOUBLE);
/*     */       else
/* 321 */         result.add(val);
/*     */     }
/* 323 */     else if ((arg instanceof NumberEval)) {
/* 324 */       Double val = new Double(TextFunctionHelper.evaluateDoubleArg(arg, srcRowIndex, srcColumnIndex));
/* 325 */       result.add(val);
/* 326 */     } else if ((arg instanceof BoolEval)) {
/* 327 */       Double val = new Double(((BoolEval)arg).getNumberValue());
/* 328 */       result.add(val);
/* 329 */     } else if ((arg instanceof AreaEval)) {
/* 330 */       result.addAll(AreaEvalHelper.toDoubleList(arg, srcRowIndex, srcColumnIndex));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Double stringToDouble(String str, boolean nullable)
/*     */     throws EvaluationException
/*     */   {
/*     */     try
/*     */     {
/* 343 */       return new Double(Double.parseDouble(str));
/*     */     } catch (NumberFormatException ex) {
/* 345 */       if (nullable)
/* 346 */         return null;
/*     */     }
/* 348 */     throw new EvaluationException(ErrorEval.NUM_ERROR);
/*     */   }
/*     */ 
/*     */   public static double[] toDoubleArray(List ls)
/*     */     throws EvaluationException
/*     */   {
/* 359 */     LinkedList l = (LinkedList)ls;
/* 360 */     return toDoubleArray(l.toArray());
/*     */   }
/*     */ 
/*     */   public static double[] toDoubleArray(Object[] objs)
/*     */     throws EvaluationException
/*     */   {
/* 370 */     double[] da = new double[objs.length];
/*     */ 
/* 372 */     for (int i = 0; i < objs.length; i++) {
/* 373 */       if ((objs[i] instanceof ErrorEval)) {
/* 374 */         throw new EvaluationException((ErrorEval)objs[i]);
/*     */       }
/* 376 */       da[i] = ((Number)Classes.coerce(Double.TYPE, objs[i])).doubleValue();
/*     */     }
/*     */ 
/* 379 */     return da;
/*     */   }
/*     */ 
/*     */   public static DescriptiveStatistics getStats(double[] d)
/*     */   {
/* 388 */     DescriptiveStatistics stats = new DescriptiveStatistics();
/* 389 */     for (int i = 0; i < d.length; i++) {
/* 390 */       stats.addValue(d[i]);
/*     */     }
/* 392 */     return stats;
/*     */   }
/*     */ 
/*     */   public static SimpleRegression getRegre(double[] xs, double[] ys)
/*     */     throws EvaluationException
/*     */   {
/* 402 */     if (xs.length != ys.length) {
/* 403 */       throw new EvaluationException(ErrorEval.NA);
/*     */     }
/* 405 */     SimpleRegression sr = new SimpleRegression();
/* 406 */     for (int i = 0; i < xs.length; i++) {
/* 407 */       sr.addData(xs[i], ys[i]);
/*     */     }
/* 409 */     return sr;
/*     */   }
/*     */ 
/*     */   public static Date daysToDate(int arg)
/*     */   {
/* 421 */     if (arg < 61) arg++;
/* 422 */     return Dates.add(DATE1899_12_30, TZ_GMT, 5, arg);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  52 */     CAL_GMT.clear();
/*  53 */     CAL_GMT.set(1899, 11, 30);
/*  54 */     DATE1899_12_30 = CAL_GMT.getTime();
/*     */ 
/*  56 */     CAL_GMT.clear();
/*  57 */     CAL_GMT.set(1900, 2, 1);
/*     */   }
/*     */ }

/* Location:           C:\Users\alei\Desktop\src\3.5\zpoiex-3.9.3.jar
 * Qualified Name:     org.zkoss.poi.ss.formula.functions.UtilFns
 * JD-Core Version:    0.6.1
 */