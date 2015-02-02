//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula.fn;

import org.apache.commons.math.linear.InvalidMatrixException;
import org.apache.commons.math.linear.RealMatrix;
import org.apache.commons.math.linear.RealMatrixImpl;
import org.apache.commons.math.util.MathUtils;
import org.zkoss.poi.ss.formula.eval.ErrorEval;
import org.zkoss.poi.ss.formula.eval.EvaluationException;
import org.zkoss.poi.ss.formula.eval.StringEval;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.poi.ss.formula.functions.Function;
import org.zkoss.poi.ss.formula.functions.NumericFunction;
import org.zkoss.poi.ss.formula.functions.TextFunction;
import org.zkoss.poi.ss.formula.functions.TextFunctionHelper;
import org.zkoss.poi.ss.formula.functions.UtilFns;
import org.zkoss.xel.fn.CommonFns;

public class MathFunctionImpl {
    public static final Function FACTDOUBLE = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double n = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            if(n < 0.0D) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                return (double)MathFunctionImpl.doubleFactorial((int)n);
            }
        }
    };
    public static final Function GCD = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            return (double)MathFunctionImpl.gcd((int)NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex), (int)NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex));
        }
    };
    public static final Function LCM = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            int m = (int)NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            int n = (int)NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            return (double)(m * n / MathFunctionImpl.gcd(m, n));
        }
    };
    public static final Function MDETERM = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double[][] d = UtilFns.toDoubleMatrix(args[0], srcRowIndex, srcColumnIndex);
            if(d.length != d[0].length) {
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            } else {
                RealMatrixImpl m = new RealMatrixImpl(d);

                try {
                    double e = m.getDeterminant();
                    return UtilFns.toDouble15(e, 4);
                } catch (InvalidMatrixException var8) {
                    throw new EvaluationException(ErrorEval.VALUE_INVALID);
                }
            }
        }
    };
    public static final Function MINVERSE = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double[][] array = UtilFns.toDoubleMatrix(args[0], srcRowIndex, srcColumnIndex);
            if(array.length != array[0].length) {
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            } else {
                RealMatrixImpl m = new RealMatrixImpl(array);

                try {
                    RealMatrix e = m.inverse();
                    Double[][] result = new Double[e.getRowDimension()][e.getColumnDimension()];

                    for(int row = 0; row < e.getRowDimension(); ++row) {
                        for(int column = 0; column < e.getColumnDimension(); ++column) {
                            result[row][column] = Double.valueOf(UtilFns.toDouble15(e.getEntry(row, column), 4));
                        }
                    }

                    return result[0][0].doubleValue();
                } catch (InvalidMatrixException var10) {
                    throw new EvaluationException(ErrorEval.VALUE_INVALID);
                }
            }
        }
    };
    public static final Function MMULT = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double[][] array1 = UtilFns.toDoubleMatrix(args[0], srcRowIndex, srcColumnIndex);
            double[][] array2 = UtilFns.toDoubleMatrix(args[1], srcRowIndex, srcColumnIndex);
            if(array1[0].length != array2.length) {
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            } else {
                RealMatrixImpl m1 = new RealMatrixImpl(array1);
                RealMatrixImpl m2 = new RealMatrixImpl(array2);

                try {
                    RealMatrix e = m1.multiply(m2);
                    Double[][] result = new Double[e.getRowDimension()][e.getColumnDimension()];

                    for(int row = 0; row < e.getRowDimension(); ++row) {
                        for(int column = 0; column < e.getColumnDimension(); ++column) {
                            result[row][column] = Double.valueOf(UtilFns.toDouble15(e.getEntry(row, column), 4));
                        }
                    }

                    return result[0][0].doubleValue();
                } catch (InvalidMatrixException var12) {
                    throw new EvaluationException(ErrorEval.VALUE_INVALID);
                }
            }
        }
    };
    public static final Function MROUND = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double number = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double multiple = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            if((number <= 0.0D || multiple >= 0.0D) && (number >= 0.0D || multiple <= 0.0D)) {
                return multiple == 0.0D?0.0D:multiple * Math.floor(number / multiple + 0.5D);
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function MULTINOMIAL = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            int[] number = new int[args.length];

            int sum;
            for(sum = 0; sum < args.length; ++sum) {
                if(args[sum] instanceof ErrorEval) {
                    throw new EvaluationException((ErrorEval)args[sum]);
                }

                number[sum] = (int)NumericFunction.singleOperandEvaluate(args[sum], srcRowIndex, srcColumnIndex);
            }

            sum = 0;
            int product = 1;

            for(int i = 0; i < number.length; ++i) {
                if(number[i] < 0 || number[i] >= 255) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                }

                sum += number[i];
                product *= MathFunctionImpl.factorial(number[i]);
            }

            return (double)(MathFunctionImpl.factorial(sum) / product);
        }
    };
    public static final Function QUOTIENT = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double n2 = (double)((int)NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex));
            if(n2 == 0.0D) {
                throw new EvaluationException(ErrorEval.DIV_ZERO);
            } else {
                double n1 = (double)((int)NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex));
                return Math.rint(n1 / n2);
            }
        }
    };
    public static final Function RANDBETWEEN = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double bottom = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double top = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            if(bottom > top) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                return Math.floor(bottom + Math.random() * (top - bottom));
            }
        }
    };
    public static final Function ROMAN = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            int numberHA = TextFunctionHelper.evaluateIntArg(args[0], srcCellRow, srcCellCol);
            int form = 0;
            int paraNum = args.length;
            if(paraNum == 2) {
                form = TextFunctionHelper.evaluateIntArg(args[1], srcCellRow, srcCellCol);
            }

            if(numberHA >= 0 && numberHA <= 3999 && form >= 0 && form <= 4) {
                char[] unit = new char[]{'I', 'X', 'C', 'M'};
                char[] five = new char[]{'V', 'L', 'D'};
                char[] result = new char[16];
                short resultPosn = 15;

                for(short posn = 0; numberHA > 0; numberHA /= 10) {
                    short digit = (short)(numberHA % 10);
                    if(digit == 9) {
                        result[resultPosn--] = unit[posn + 1];
                        digit = 1;
                    } else if(digit == 4) {
                        result[resultPosn--] = five[posn];
                        digit = 1;
                    }

                    boolean five_sw = digit >= 5;

                    for(digit = (short)(digit % 5); digit-- > 0; result[resultPosn--] = unit[posn]) {
                        ;
                    }

                    if(five_sw) {
                        result[resultPosn--] = five[posn];
                    }

                    ++posn;
                }

                String numberRoman = (new String(result)).substring(resultPosn + 1);
                if(paraNum != 1 && form != 0) {
                    if(numberRoman.contains("XLV")) {
                        numberRoman = numberRoman.replaceAll("XLV", "VL");
                    }

                    if(numberRoman.contains("XCV")) {
                        numberRoman = numberRoman.replaceAll("XCV", "VC");
                    }

                    if(numberRoman.contains("CDL")) {
                        numberRoman = numberRoman.replaceAll("CDL", "LD");
                    }

                    if(numberRoman.contains("CML")) {
                        numberRoman = numberRoman.replaceAll("CML", "LM");
                    }

                    if(numberRoman.contains("CMVC")) {
                        numberRoman = numberRoman.replaceAll("CMVC", "LMVL");
                    }

                    if(form == 1) {
                        if(numberRoman.contains("CDXC")) {
                            numberRoman = numberRoman.replaceAll("CDXC", "LDXL");
                        }

                        if(numberRoman.contains("CDVC")) {
                            numberRoman = numberRoman.replaceAll("CDVC", "LDVL");
                        }

                        if(numberRoman.contains("CMXC")) {
                            numberRoman = numberRoman.replaceAll("CMXC", "LMXL");
                        }

                        if(numberRoman.contains("XCIX")) {
                            numberRoman = numberRoman.replaceAll("XCIX", "VCIV");
                        }

                        if(numberRoman.contains("XLIX")) {
                            numberRoman = numberRoman.replaceAll("XLIX", "VLIV");
                        }
                    }

                    if(form > 1) {
                        if(numberRoman.contains("XLIX")) {
                            numberRoman = numberRoman.replaceAll("XLIX", "IL");
                        }

                        if(numberRoman.contains("XCIX")) {
                            numberRoman = numberRoman.replaceAll("XCIX", "IC");
                        }

                        if(numberRoman.contains("CDXC")) {
                            numberRoman = numberRoman.replaceAll("CDXC", "XD");
                        }

                        if(numberRoman.contains("CDVC")) {
                            numberRoman = numberRoman.replaceAll("CDVC", "XDV");
                        }

                        if(numberRoman.contains("CDIC")) {
                            numberRoman = numberRoman.replaceAll("CDIC", "XDIX");
                        }

                        if(numberRoman.contains("LMVL")) {
                            numberRoman = numberRoman.replaceAll("LMVL", "XMV");
                        }

                        if(numberRoman.contains("CMIC")) {
                            numberRoman = numberRoman.replaceAll("CMIC", "XMIX");
                        }

                        if(numberRoman.contains("CMXC")) {
                            numberRoman = numberRoman.replaceAll("CMXC", "XM");
                        }
                    }

                    if(form > 2) {
                        if(numberRoman.contains("XDV")) {
                            numberRoman = numberRoman.replaceAll("XDV", "VD");
                        }

                        if(numberRoman.contains("XDIX")) {
                            numberRoman = numberRoman.replaceAll("XDIX", "VDIV");
                        }

                        if(numberRoman.contains("XMV")) {
                            numberRoman = numberRoman.replaceAll("XMV", "VM");
                        }

                        if(numberRoman.contains("XMIX")) {
                            numberRoman = numberRoman.replaceAll("XMIX", "VMIV");
                        }
                    }

                    if(form == 4 || !CommonFns.toBoolean(args[1])) {
                        if(numberRoman.contains("VDIV")) {
                            numberRoman = numberRoman.replaceAll("VDIV", "ID");
                        }

                        if(numberRoman.contains("VMIV")) {
                            numberRoman = numberRoman.replaceAll("VMIV", "IM");
                        }
                    }

                    return new StringEval(numberRoman);
                } else {
                    return new StringEval(numberRoman);
                }
            } else {
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            }
        }
    };
    public static final Function SQRTPI = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double number = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            if(number < 0.0D) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                return Math.sqrt(number * 3.141592653589793D);
            }
        }
    };

    public MathFunctionImpl() {
    }

    private static int doubleFactorial(int n) {
        return n <= 1?1:(n == 2?2:n * doubleFactorial(n - 2));
    }

    public static int gcd(int m, int n) throws EvaluationException {
        if(m >= 0 && n >= 0) {
            return MathUtils.gcd(m, n);
        } else {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        }
    }

    private static int factorial(int n) {
        return n <= 1?1:n * factorial(n - 1);
    }
}
