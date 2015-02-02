//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula.fn;

import org.apache.commons.math.MathException;
import org.apache.commons.math.special.Erf;
import org.zkoss.poi.ss.formula.eval.ErrorEval;
import org.zkoss.poi.ss.formula.eval.EvaluationException;
import org.zkoss.poi.ss.formula.eval.NumberEval;
import org.zkoss.poi.ss.formula.eval.StringEval;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.poi.ss.formula.functions.Complex;
import org.zkoss.poi.ss.formula.functions.Function;
import org.zkoss.poi.ss.formula.functions.NumericFunction;
import org.zkoss.poi.ss.formula.functions.NumericFunctionHelper;
import org.zkoss.poi.ss.formula.functions.TextFunction;
import org.zkoss.poi.ss.formula.functions.TextFunctionHelper;
import org.zkoss.poi.ss.formula.functions.UtilFns;

public class EngineeringFunctionImpl {
    private static final double THRESHOLD = 30.0D;
    private static final double minE = 1.0E-10D;
    private static final long iterateMax = 100L;
    public static final Function BESSELI = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            int n = (int)NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            return NumericFunctionHelper.checkValue(EngineeringFunctionImpl.engineeringBesseli(x, n));
        }
    };
    public static final Function BESSELJ = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            int n = (int)NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            return NumericFunctionHelper.checkValue(engineeringBesselj(x, n));
        }
    };
    public static final Function BESSELK = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            int n = (int)NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            if(n < 0) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                double result;
                if(x == 0.0D) {
                    result = EngineeringFunctionImpl.BesselK0(x);
                } else if(x == 1.0D) {
                    result = EngineeringFunctionImpl.BesselK1(x);
                } else {
                    double term = EngineeringFunctionImpl.BesselK0(x);
                    result = EngineeringFunctionImpl.BesselK1(x);

                    for(int i = 1; i < n; ++i) {
                        double temp = term + (double)(i * 2) / x * result;
                        term = result;
                        result = temp;
                    }
                }

                return NumericFunctionHelper.checkValue(result);
            }
        }
    };
    public static final Function BESSELY = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            int n = (int)NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            if(n < 0) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                double result;
                if(x == 0.0D) {
                    result = EngineeringFunctionImpl.BesselY0(x);
                } else if(x == 1.0D) {
                    result = EngineeringFunctionImpl.BesselY1(x);
                } else {
                    double term = EngineeringFunctionImpl.BesselY0(x);
                    result = EngineeringFunctionImpl.BesselY1(x);

                    for(int i = 1; i < n; ++i) {
                        double temp = (double)(i * 2) / x * result - term;
                        term = result;
                        result = temp;
                    }
                }

                return NumericFunctionHelper.checkValue(result);
            }
        }
    };
    public static final Function BIN2DEC = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            int num = (int)NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            return (double)EngineeringFunctionImpl.binToDec(Integer.toString(num));
        }
    };
    public static final Function BIN2HEX = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String bin = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            int places = 0;
            if(args.length == 2) {
                places = TextFunctionHelper.evaluateIntArg(args[1], srcCellRow, srcCellCol);
            }

            return new StringEval(EngineeringFunctionImpl.decToHex(EngineeringFunctionImpl.binToDec(bin), places));
        }
    };
    public static final Function BIN2OCT = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String bin = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            int places = 0;
            if(args.length == 2) {
                places = TextFunctionHelper.evaluateIntArg(args[1], srcCellRow, srcCellCol);
            }

            return new StringEval(EngineeringFunctionImpl.decToOct(EngineeringFunctionImpl.binToDec(bin), places));
        }
    };
    public static final Function COMPLEX = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            double real = TextFunctionHelper.evaluateDoubleArg(args[0], srcCellRow, srcCellCol);
            double imaginary = TextFunctionHelper.evaluateDoubleArg(args[1], srcCellRow, srcCellCol);
            String suffix = "i";
            if(args.length == 3) {
                suffix = TextFunctionHelper.evaluateStringArg(args[2], srcCellRow, srcCellCol);
            }

            if(!Double.isNaN(real) && !Double.isNaN(imaginary) && ("i".equals(suffix) || "j".equals(suffix))) {
                return new StringEval(UtilFns.format(new Complex(real, imaginary, suffix)));
            } else {
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            }
        }
    };
    public static final Function DEC2BIN = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            int dec = TextFunctionHelper.evaluateIntArg(args[0], srcCellRow, srcCellCol);
            int places = 0;
            if(args.length == 2) {
                places = TextFunctionHelper.evaluateIntArg(args[1], srcCellRow, srcCellCol);
            }

            return new StringEval(EngineeringFunctionImpl.decToBin(dec, places));
        }
    };
    public static final Function DEC2HEX = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            int dec = TextFunctionHelper.evaluateIntArg(args[0], srcCellRow, srcCellCol);
            int places = 0;
            if(args.length == 2) {
                places = TextFunctionHelper.evaluateIntArg(args[1], srcCellRow, srcCellCol);
            }

            return new StringEval(EngineeringFunctionImpl.decToHex(dec, places));
        }
    };
    public static final Function DEC2OCT = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            int dec = TextFunctionHelper.evaluateIntArg(args[0], srcCellRow, srcCellCol);
            int places = 0;
            if(args.length == 2) {
                places = TextFunctionHelper.evaluateIntArg(args[1], srcCellRow, srcCellCol);
            }

            return new StringEval(EngineeringFunctionImpl.decToOct(dec, places));
        }
    };
    public static final Function HEX2DEC = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String hex = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            return new NumberEval((double)EngineeringFunctionImpl.hexToDec(hex));
        }
    };
    public static final Function HEX2BIN = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String hex = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            int places = 0;
            if(args.length == 2) {
                places = TextFunctionHelper.evaluateIntArg(args[1], srcCellRow, srcCellCol);
            }

            return new StringEval(EngineeringFunctionImpl.decToBin((int)EngineeringFunctionImpl.hexToDec(hex), places));
        }
    };
    public static final Function HEX2OCT = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String hex = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            int places = 0;
            if(args.length == 2) {
                places = TextFunctionHelper.evaluateIntArg(args[1], srcCellRow, srcCellCol);
            }

            return new StringEval(EngineeringFunctionImpl.decToOct((int)EngineeringFunctionImpl.hexToDec(hex), places));
        }
    };
    public static final Function DELTA = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double n1 = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double n2 = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            byte result = 0;
            if(n1 == n2) {
                result = 1;
            }

            return (double)result;
        }
    };
    public static final Function ERF = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double lowerLimit = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double upperLimit = 0.0D;
            if(lowerLimit >= 0.0D && upperLimit >= 0.0D) {
                try {
                    double e = Erf.erf(lowerLimit);
                    if(args.length == 2) {
                        upperLimit = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
                        e = Erf.erf(upperLimit) - e;
                    }

                    return e;
                } catch (MathException var10) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                }
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function ERFC = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            if(x < 0.0D) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                try {
                    double e = 1.0D - Erf.erf(x);
                    return e;
                } catch (MathException var8) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                }
            }
        }
    };
    public static final Function GESTEP = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double num = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double step = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            return num >= step?1.0D:0.0D;
        }
    };
    public static final Function IMABS = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            String inum = TextFunctionHelper.evaluateStringArg(args[0], srcRowIndex, srcColumnIndex);
            return UtilFns.validateComplex(inum).abs();
        }
    };
    public static final Function IMAGINARY = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            String inum = TextFunctionHelper.evaluateStringArg(args[0], srcRowIndex, srcColumnIndex);
            return UtilFns.validateComplex(inum).getImaginary();
        }
    };
    public static final Function IMARGUMENT = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            String inum = TextFunctionHelper.evaluateStringArg(args[0], srcRowIndex, srcColumnIndex);
            Complex c = UtilFns.validateComplex(inum);
            double real = c.getReal();
            double img = c.getImaginary();
            if(img == 0.0D && real == 0.0D) {
                throw new EvaluationException(ErrorEval.DIV_ZERO);
            } else {
                return Math.atan2(img, real);
            }
        }
    };
    public static final Function IMCONJUGATE = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String inum = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            Complex c = UtilFns.validateComplex(inum);
            return new StringEval(UtilFns.format(UtilFns.cToComplex(c.conjugate(), c.getSuffix())));
        }
    };
    public static final Function IMCOS = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String inum = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            Complex c = UtilFns.validateComplex(inum);
            return new StringEval(UtilFns.format(UtilFns.cToComplex(c.cos(), c.getSuffix())));
        }
    };
    public static final Function IMDIV = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String inum1 = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            String inum2 = TextFunctionHelper.evaluateStringArg(args[1], srcCellRow, srcCellCol);
            Complex c1 = UtilFns.validateComplex(inum1);
            Complex c2 = UtilFns.validateComplex(inum2);
            return new StringEval(UtilFns.format(UtilFns.cToComplex(c1.divide(c2), c1.getSuffix())));
        }
    };
    public static final Function IMEXP = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String inum = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            Complex c = UtilFns.validateComplex(inum);
            String suffix = c.getSuffix();
            return new StringEval(UtilFns.format(UtilFns.cToComplex((new Complex(2.718281828459045D, 0.0D, suffix)).pow(c), suffix)));
        }
    };
    public static final Function IMLN = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String inum = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            Complex c = UtilFns.validateComplex(inum);
            return new StringEval(UtilFns.format(UtilFns.cToComplex(c.log(), c.getSuffix())));
        }
    };
    public static final Function IMLOG10 = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String inum = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            Complex c = UtilFns.validateComplex(inum);
            String suffix = c.getSuffix();
            return new StringEval(UtilFns.format(UtilFns.cToComplex(c.log().divide((new Complex(10.0D, 0.0D, suffix)).log()), suffix)));
        }
    };
    public static final Function IMLOG2 = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String inum = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            Complex c = UtilFns.validateComplex(inum);
            String suffix = c.getSuffix();
            return new StringEval(UtilFns.format(UtilFns.cToComplex(c.log().divide((new Complex(2.0D, 0.0D, suffix)).log()), suffix)));
        }
    };
    public static final Function IMPOWER = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String inum1 = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            String inum2 = TextFunctionHelper.evaluateStringArg(args[1], srcCellRow, srcCellCol);
            Complex c1 = UtilFns.validateComplex(inum1);
            Complex c2 = UtilFns.validateComplex(inum2);
            return new StringEval(UtilFns.format(UtilFns.cToComplex(c1.pow(c2), c1.getSuffix())));
        }
    };
    public static final Function IMPRODUCT = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            Complex[] c = EngineeringFunctionImpl.toComplexArray(args, srcCellRow, srcCellCol);
            org.apache.commons.math.complex.Complex product = null;

            for(int i = 0; i < c.length - 1; ++i) {
                product = c[i].multiply(c[i + 1]);
            }

            return new StringEval(UtilFns.format(UtilFns.cToComplex(product, UtilFns.validateComplex(TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol)).getSuffix())));
        }
    };
    public static final Function IMREAL = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            String inum = TextFunctionHelper.evaluateStringArg(args[0], srcRowIndex, srcColumnIndex);
            return UtilFns.validateComplex(inum).getReal();
        }
    };
    public static final Function IMSIN = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String inum = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            Complex c = UtilFns.validateComplex(inum);
            return new StringEval(UtilFns.format(UtilFns.cToComplex(c.sin(), c.getSuffix())));
        }
    };
    public static final Function IMSQRT = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String inum = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            Complex c = UtilFns.validateComplex(inum);
            return new StringEval(UtilFns.format(UtilFns.cToComplex(c.sqrt(), c.getSuffix())));
        }
    };
    public static final Function IMSUB = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            Complex[] complex = EngineeringFunctionImpl.toComplexArray(args, srcCellRow, srcCellCol);
            return new StringEval(UtilFns.format(UtilFns.cToComplex(complex[0].subtract(complex[1]), complex[0].getSuffix())));
        }
    };
    public static final Function IMSUM = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            Complex[] c = EngineeringFunctionImpl.toComplexArray(args, srcCellRow, srcCellCol);
            org.apache.commons.math.complex.Complex sum = null;

            for(int i = 0; i < c.length - 1; ++i) {
                sum = c[i].add(c[i + 1]);
            }

            return new StringEval(UtilFns.format(UtilFns.cToComplex(sum, UtilFns.validateComplex(TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol)).getSuffix())));
        }
    };
    public static final Function OCT2BIN = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String oct = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            int places = 0;
            if(args.length == 2) {
                places = TextFunctionHelper.evaluateIntArg(args[1], srcCellRow, srcCellCol);
            }

            return new StringEval(EngineeringFunctionImpl.decToBin(EngineeringFunctionImpl.octToDec(oct), places));
        }
    };
    public static final Function OCT2DEC = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String oct = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            return new NumberEval((double)EngineeringFunctionImpl.octToDec(oct));
        }
    };
    public static final Function OCT2HEX = new TextFunction() {
        public ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String oct = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            int places = 0;
            if(args.length == 2) {
                places = TextFunctionHelper.evaluateIntArg(args[1], srcCellRow, srcCellCol);
            }

            return new StringEval(EngineeringFunctionImpl.decToHex(EngineeringFunctionImpl.octToDec(oct), places));
        }
    };

    public EngineeringFunctionImpl() {
    }

    private static long fac(long n) {
        long result = 1L;
        if(n <= 1L) {
            return 1L;
        } else {
            while(n >= 2L) {
                result *= n;
                --n;
            }

            return result;
        }
    }

    private static double engineeringBesselj(double x, int n) throws EvaluationException {
        double result = 0.0D;
        int k = 1;
        double absX = Math.abs(x);
        if(n < 0) {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        } else {
            if(absX < 30.0D) {
                double Term = Math.pow(x / 2.0D, (double)n) / (double)fac((long)n);
                result = Term;
                double upTerm = -(Math.pow(x, 2.0D) / 4.0D);

                do {
                    Term = upTerm / (double)(k * (n + k)) * Term;
                    result += Term;
                    if(Math.abs(Term) <= 1.0E-10D) {
                        break;
                    }

                    ++k;
                } while((long)k < 100L);
            } else {
                result = Math.sqrt(2.0D / (3.141592653589793D * absX)) * Math.cos(absX - (double)n * 3.141592653589793D / 2.0D - 0.7853981633974483D);
                if(x < 0.0D && n % 2 == 1) {
                    result *= -1.0D;
                }
            }

            return result;
        }
    }

    private static double BesselK0(double x) throws EvaluationException {
        double result;
        double y;
        if(x <= 2.0D) {
            y = x * 0.5D;
            double y1 = y * y;
            Double[] para = new Double[]{new Double(x), new Double(0.0D)};
            result = -1.0D * Math.log(y) * engineeringBesseli(x, 0) + -0.57721566D + y1 * (0.4227842D + y1 * (0.23069756D + y1 * (0.0348859D + y1 * (0.00262698D + y1 * (1.075E-4D + y1 * 7.4E-6D)))));
        } else {
            y = 2.0D / x;
            result = Math.exp(-1.0D * x) / Math.sqrt(x) * (1.25331414D + y * (-0.07832358D + y * (0.02189568D + y * (-0.01062446D + y * (0.00587872D + y * (-0.0025154D + y * 5.3208E-4D))))));
        }

        return result;
    }

    private static double BesselK1(double x) throws EvaluationException {
        double result;
        double y;
        if(x <= 2.0D) {
            y = x * 0.5D;
            double y1 = y * y;
            Double[] para_for_i = new Double[]{new Double(x), new Double(1.0D)};
            result = Math.log(y) * engineeringBesseli(x, 1) + (1.0D + y1 * (0.15443144D + y1 * (-0.67278579D + y1 * (-0.18156897D + y1 * (-0.01919402D + y1 * (-0.00110404D + y1 * -4.686E-5D)))))) / x;
        } else {
            y = 2.0D / x;
            result = Math.exp(-1.0D * x) / Math.sqrt(x) * (1.25331414D + y * (0.23498619D + y * (-0.0365562D + y * (0.01504268D + y * (-0.00780353D + y * (0.00325614D + y * -6.8245E-4D))))));
        }

        return result;
    }

    public static double engineeringBesseli(double x, int n) throws EvaluationException {
        double result = 0.0D;
        if(n < 0) {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        } else {
            double absX = Math.abs(x);
            if(absX < 30.0D) {
                double Term = Math.pow(x / 2.0D, (double)n) / (double)fac((long)n);
                result = Term;
                double upTerm = Math.pow(x, 2.0D) / 4.0D;
                int k = 1;

                do {
                    Term = upTerm / (double)(k * (n + k)) * Term;
                    result += Term;
                    if(Math.abs(Term) <= 1.0E-10D) {
                        break;
                    }

                    ++k;
                } while((long)k < 100L);
            } else {
                result = Math.exp(x) / Math.sqrt(6.283185307179586D * x);
                if(x < 0.0D && n % 2 == 1) {
                    result *= -1.0D;
                }
            }

            return result;
        }
    }

    private static double BesselY0(double x) throws EvaluationException {
        double result;
        double z;
        double y;
        double x2;
        if(x < 8.0D) {
            z = x * x;
            y = -2.957821389E9D + z * (7.062834065E9D + z * (-5.123598036E8D + z * (1.087988129E7D + z * (-86327.92757D + z * 228.4622733D))));
            x2 = 4.0076544269E10D + z * (7.452499648E8D + z * (7189466.438D + z * (47447.2647D + z * (226.1030244D + z))));
            Double[] f1 = new Double[]{new Double(x), new Double(0.0D)};
            result = y / x2 + 0.636619772D * engineeringBesselj(x, 0) * Math.log(x);
        } else {
            z = 8.0D / x;
            y = z * z;
            x2 = x - 0.785398164D;
            double f11 = 1.0D + y * (-0.001098628627D + y * (2.734510407E-5D + y * (-2.073370639E-6D + y * 2.093887211E-7D)));
            double f2 = -0.01562499995D + y * (1.430488765E-4D + y * (-6.911147651E-6D + y * (7.621095161E-7D + y * -9.34945152E-8D)));
            result = Math.sqrt(0.636619772D / x) * (Math.sin(x2) * f11 + z * Math.cos(x2) * f2);
        }

        return result;
    }

    private static double BesselY1(double x) throws EvaluationException {
        double result;
        if(x < 8.0D) {
            double y = x * x;
            double f1 = x * (-4.900604943E12D + y * (1.27527439E12D + y * (-5.153438139E10D + y * (7.349264551E8D + y * (-4237922.726D + y * 8511.937935D)))));
            double f2 = 2.49958057E13D + y * (4.244419664E11D + y * (3.733650367E9D + y * (2.245904002E7D + y * (102042.605D + y * (354.9632885D + y)))));
            Double[] para = new Double[]{new Double(x), new Double(1.0D)};
            result = f1 / f2 + 0.636619772D * (engineeringBesselj(x, 1) * Math.log(x) - 1.0D / x);
        } else {
            result = Math.sqrt(0.636619772D / x) * Math.sin(x - 2.356194491D);
        }

        return result;
    }

    private static int binToDec(String bin) throws EvaluationException {
        int places = bin.length();
        if(places > 10) {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        } else {
            for(int result = 0; result < places; ++result) {
                if(bin.charAt(result) != 48 && bin.charAt(result) != 49) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                }
            }

            Integer var3 = Integer.valueOf(bin, 2);
            if(places == 10) {
                var3 = Integer.valueOf(var3.intValue() - 1024);
            }

            return var3.intValue();
        }
    }

    private static String decToBin(int dec, int places) throws EvaluationException {
        if(dec >= -512 && dec <= 511) {
            String result = Integer.toBinaryString(dec);
            if(dec < 0) {
                result = result.substring(result.length() - 10, result.length());
            }

            if(places != 0 && result.length() < 10) {
                result = UtilFns.padZero(result, places);
            }

            return result;
        } else {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        }
    }

    private static String decToHex(int dec, int places) throws EvaluationException {
        String result = Integer.toHexString(dec).toUpperCase();
        if(dec < 0) {
            StringBuffer sf = new StringBuffer("FFFFFFFFFF");
            result = sf.replace(10 - result.length(), 10, result).toString();
        }

        if(places != 0 && result.length() < 10) {
            result = UtilFns.padZero(result, places);
        }

        return result;
    }

    private static String decToOct(int dec, int places) throws EvaluationException {
        if(dec >= -536870912 && dec <= 536870911) {
            String result = Integer.toOctalString(dec);
            if(dec < 0) {
                result = result.substring(result.length() - 10, result.length());
            }

            if(places != 0 && result.length() < 10) {
                result = UtilFns.padZero(result, places);
            }

            return result;
        } else {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        }
    }

    private static boolean isHex(String hex) {
        boolean result = true;

        for(int i = 0; i < hex.length(); ++i) {
            if(!Character.isDigit(hex.charAt(i)) && !hex.substring(i, i + 1).matches("[a-fA-F]")) {
                result = false;
            }
        }

        return result;
    }

    private static long hexToDec(String hex) throws EvaluationException {
        int places = hex.length();
        if(places > 10) {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        } else if(!isHex(hex)) {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        } else {
            Long result = Long.valueOf(hex, 16);
            if(result.longValue() > Long.valueOf("7fffffffff", 16).longValue()) {
                result = Long.valueOf(result.longValue() - Long.valueOf("10000000000", 16).longValue());
            }

            return result.longValue();
        }
    }

    public static Complex[] toComplexArray(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
        Complex[] complex = new Complex[args.length];

        for(int i = 0; i < args.length; ++i) {
            if(args[i] instanceof ErrorEval) {
                throw new EvaluationException((ErrorEval)args[i]);
            }

            complex[i] = UtilFns.validateComplex(TextFunctionHelper.evaluateStringArg(args[i], srcCellRow, srcCellCol));
            if(!complex[i].getSuffix().equals("k")) {
                for(int j = 0; j < i; ++j) {
                    if(!complex[j].getSuffix().equals("k") && !complex[i].getSuffix().equals(complex[j].getSuffix())) {
                        throw new EvaluationException(ErrorEval.NUM_ERROR);
                    }
                }
            }
        }

        return complex;
    }

    private static boolean isOct(String oct) {
        boolean result = true;

        for(int i = 0; i < oct.length(); ++i) {
            if(!oct.substring(i, i + 1).matches("[0-7]")) {
                result = false;
            }
        }

        return result;
    }

    private static int octToDec(String oct) throws EvaluationException {
        int places = oct.length();
        if(places > 10) {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        } else if(!isOct(oct)) {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        } else {
            Integer result = Integer.valueOf(oct, 8);
            if(result.intValue() > 536870911) {
                result = Integer.valueOf(result.intValue() - 1073741824);
            }

            return result.intValue();
        }
    }
}
