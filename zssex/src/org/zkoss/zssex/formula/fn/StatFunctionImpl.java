//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula.fn;

import java.util.List;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.BinomialDistributionImpl;
import org.apache.commons.math.distribution.ChiSquaredDistributionImpl;
import org.apache.commons.math.distribution.ExponentialDistributionImpl;
import org.apache.commons.math.distribution.FDistributionImpl;
import org.apache.commons.math.distribution.GammaDistributionImpl;
import org.apache.commons.math.distribution.HypergeometricDistributionImpl;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.apache.commons.math.distribution.PoissonDistributionImpl;
import org.apache.commons.math.distribution.TDistributionImpl;
import org.apache.commons.math.distribution.WeibullDistributionImpl;
import org.apache.commons.math.special.Gamma;
import org.zkoss.poi.ss.formula.AreaEvalHelper;
import org.zkoss.poi.ss.formula.eval.ErrorEval;
import org.zkoss.poi.ss.formula.eval.EvaluationException;
import org.zkoss.poi.ss.formula.eval.OperandResolver;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.poi.ss.formula.functions.Function;
import org.zkoss.poi.ss.formula.functions.NumericFunction;
import org.zkoss.poi.ss.formula.functions.NumericFunctionHelper;
import org.zkoss.poi.ss.formula.functions.UtilFns;

public class StatFunctionImpl {
    public static final Function AVERAGEA = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            List ls = UtilFns.toList(args, srcRowIndex, srcColumnIndex);
            if(ls.isEmpty()) {
                throw new EvaluationException(ErrorEval.DIV_ZERO);
            } else {
                return NumericFunctionHelper.checkValue(UtilFns.getStats(UtilFns.toDoubleArray(ls)).getMean());
            }
        }
    };
    public static final Function BINOMDIST = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double number = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            int trails = (int)NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            double p_s = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            ValueEval ve = OperandResolver.getSingleValue(args[3], srcRowIndex, srcColumnIndex);
            Boolean isCumulative = OperandResolver.coerceValueToBoolean(ve, false);
            if(number <= (double)trails && number >= 0.0D) {
                BinomialDistributionImpl bd = new BinomialDistributionImpl(trails, p_s);
                if(isCumulative.booleanValue()) {
                    try {
                        return NumericFunctionHelper.checkValue(bd.cumulativeProbability((int)number));
                    } catch (MathException var13) {
                        throw new EvaluationException(ErrorEval.NUM_ERROR);
                    }
                } else {
                    return NumericFunctionHelper.checkValue(bd.probability(number));
                }
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function CHIDIST = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double df = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            if(x < 0.0D) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                ChiSquaredDistributionImpl chi = new ChiSquaredDistributionImpl(df);

                try {
                    return NumericFunctionHelper.checkValue(1.0D - chi.cumulativeProbability(x));
                } catch (MathException var10) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                }
            }
        }
    };
    public static final Function CHIINV = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double p = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double df = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            if(p >= 0.0D && p <= 1.0D) {
                ChiSquaredDistributionImpl chi = new ChiSquaredDistributionImpl(df);

                try {
                    return NumericFunctionHelper.checkValue(chi.inverseCumulativeProbability(1.0D - p));
                } catch (MathException var10) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                }
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function EXPONDIST = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double lambda = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            double mean = 1.0D / lambda;
            ValueEval ve = OperandResolver.getSingleValue(args[2], srcRowIndex, srcColumnIndex);
            Boolean isCumulative = OperandResolver.coerceValueToBoolean(ve, false);
            ExponentialDistributionImpl expDist = new ExponentialDistributionImpl(mean);

            try {
                return isCumulative.booleanValue()?NumericFunctionHelper.checkValue(expDist.cumulativeProbability(x)):NumericFunctionHelper.checkValue(expDist.cumulativeProbability(x) / mean);
            } catch (MathException var14) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function FDIST = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double df = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            double ddf = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            FDistributionImpl fDist = new FDistributionImpl(df, ddf);

            double result;
            try {
                result = 1.0D - fDist.cumulativeProbability(x);
            } catch (MathException var14) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }

            return NumericFunctionHelper.checkValue(result);
        }
    };
    public static final Function FINV = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double p = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double df = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            double ddf = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            FDistributionImpl fDist = new FDistributionImpl(df, ddf);

            double result;
            try {
                result = fDist.inverseCumulativeProbability(1.0D - p);
            } catch (MathException var14) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }

            return NumericFunctionHelper.checkValue(result);
        }
    };
    public static final Function GAMMADIST = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double alpha = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            double beta = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            ValueEval ve = OperandResolver.getSingleValue(args[3], srcRowIndex, srcColumnIndex);
            Boolean isCumulative = OperandResolver.coerceValueToBoolean(ve, false);
            GammaDistributionImpl gammaDist = new GammaDistributionImpl(alpha, beta);

            try {
                return isCumulative.booleanValue()?NumericFunctionHelper.checkValue(gammaDist.cumulativeProbability(x)):NumericFunctionHelper.checkValue(gammaDist.density(Double.valueOf(x)));
            } catch (MathException var14) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function GAMMAINV = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double p = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double alpha = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            double beta = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            GammaDistributionImpl gammaDist = new GammaDistributionImpl(alpha, beta);

            double result;
            try {
                result = gammaDist.inverseCumulativeProbability(p);
            } catch (MathException var14) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }

            return NumericFunctionHelper.checkValue(result);
        }
    };
    public static final Function GAMMALN = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            return NumericFunctionHelper.checkValue(Gamma.logGamma(x));
        }
    };
    public static final Function GEOMEAN = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            return NumericFunctionHelper.checkValue(UtilFns.getStats(UtilFns.toDoubleArray(UtilFns.toList(args, srcRowIndex, srcColumnIndex))).getGeometricMean());
        }
    };
    public static final Function HARMEAN = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double result = 0.0D;
            double[] values = UtilFns.toDoubleArray(UtilFns.toList(args, srcRowIndex, srcColumnIndex));
            double[] arr$ = values;
            int len$ = values.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                double v = arr$[i$];
                result += 1.0D / v;
            }

            if(result != 0.0D) {
                result = (double)values.length / result;
            }

            return NumericFunctionHelper.checkValue(result);
        }
    };
    public static final Function HYPGEOMDIST = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            int s = (int)NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            int ns = (int)NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            int p = (int)NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            int np = (int)NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
            HypergeometricDistributionImpl hDist = new HypergeometricDistributionImpl(np, p, ns);
            return NumericFunctionHelper.checkValue(hDist.probability(s));
        }
    };
    public static final Function INTERCEPT = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        }
    };
    public static final Function KURT = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            return NumericFunctionHelper.checkValue(UtilFns.getStats(UtilFns.toDoubleArray(UtilFns.toList(args, srcRowIndex, srcColumnIndex))).getKurtosis());
        }
    };
    public static final Function NORMDIST = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double mean = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            double sDev = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            NormalDistributionImpl normDist = new NormalDistributionImpl(mean, sDev);

            double result;
            try {
                result = normDist.cumulativeProbability(x);
            } catch (MathException var14) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }

            return NumericFunctionHelper.checkValue(result);
        }
    };
    public static final Function POISSON = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double mean = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            ValueEval ve = OperandResolver.getSingleValue(args[2], srcRowIndex, srcColumnIndex);
            Boolean isCumulative = OperandResolver.coerceValueToBoolean(ve, false);
            PoissonDistributionImpl poiDist = new PoissonDistributionImpl(mean);

            try {
                return isCumulative.booleanValue()?NumericFunctionHelper.checkValue(poiDist.cumulativeProbability((int)x)):NumericFunctionHelper.checkValue(poiDist.probability((int)x));
            } catch (MathException var12) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function SKEW = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            return NumericFunctionHelper.checkValue(UtilFns.getStats(UtilFns.toDoubleArray(UtilFns.toList(args, srcRowIndex, srcColumnIndex))).getSkewness());
        }
    };
    public static final Function SLOPE = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            List l0 = AreaEvalHelper.toDoubleList(args[0], srcRowIndex, srcColumnIndex);
            List l1 = AreaEvalHelper.toDoubleList(args[1], srcRowIndex, srcColumnIndex);
            return NumericFunctionHelper.checkValue(UtilFns.getRegre(UtilFns.toDoubleArray(l1), UtilFns.toDoubleArray(l0)).getSlope());
        }
    };
    public static final Function STDEV = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            return NumericFunctionHelper.checkValue(UtilFns.getStats(UtilFns.toDoubleArray(UtilFns.toList(args, srcRowIndex, srcColumnIndex))).getStandardDeviation());
        }
    };
    public static final Function STDEVP = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        }
    };
    public static final Function TDIST = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double degree_freedom = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            int tails = (int)NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            TDistributionImpl td = new TDistributionImpl(degree_freedom);

            double result;
            try {
                if(tails == 1) {
                    result = 1.0D - td.cumulativeProbability(x);
                } else {
                    if(tails != 2) {
                        throw new EvaluationException(ErrorEval.NUM_ERROR);
                    }

                    result = (1.0D - td.cumulativeProbability(x)) * 2.0D;
                }
            } catch (MathException var13) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }

            return NumericFunctionHelper.checkValue(result);
        }
    };
    public static final Function TINV = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double p = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double degree_freedom = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            TDistributionImpl td = new TDistributionImpl(degree_freedom);

            double result;
            try {
                result = td.inverseCumulativeProbability(1.0D - p / 2.0D);
            } catch (MathException var12) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }

            return NumericFunctionHelper.checkValue(result);
        }
    };
    public static final Function VAR = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            return NumericFunctionHelper.checkValue(UtilFns.getStats(UtilFns.toDoubleArray(UtilFns.toList(args, srcRowIndex, srcColumnIndex))).getVariance());
        }
    };
    public static final Function VARP = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        }
    };
    public static final Function WEIBULL = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double x = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double alpha = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            double beta = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            WeibullDistributionImpl wb = new WeibullDistributionImpl(alpha, beta);

            double result;
            try {
                result = wb.cumulativeProbability(x);
            } catch (Exception var14) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }

            return NumericFunctionHelper.checkValue(result);
        }
    };

    public StatFunctionImpl() {
    }
}
