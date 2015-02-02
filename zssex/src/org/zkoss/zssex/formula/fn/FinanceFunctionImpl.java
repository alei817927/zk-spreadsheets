//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula.fn;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.jfree.date.SerialDate;
import org.jfree.date.SerialDateUtilities;
import org.zkoss.poi.ss.formula.AreaEvalHelper;
import org.zkoss.poi.ss.formula.eval.ErrorEval;
import org.zkoss.poi.ss.formula.eval.EvaluationException;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.poi.ss.formula.functions.FinanceFunction;
import org.zkoss.poi.ss.formula.functions.Function;
import org.zkoss.poi.ss.formula.functions.NumericFunction;
import org.zkoss.poi.ss.formula.functions.UtilFns;
import org.zkoss.poi.ss.usermodel.DateUtil;

public class FinanceFunctionImpl {
    public static final Function ACCRINT = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dissue = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date issue = DateUtil.getJavaDate(dissue);
            double dFirI = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date firI = DateUtil.getJavaDate(dFirI);
            double dSettlement = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            Date settlement = DateUtil.getJavaDate(dSettlement);
            if(settlement.before(issue)) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                double rate = args[3] == null?0.0D:NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
                double par = args[4] == null?0.0D:NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex);
                int freq = (int)(args[5] == null?0.0D:NumericFunction.singleOperandEvaluate(args[5], srcRowIndex, srcColumnIndex));
                int basis = 0;
                if(args.length == 7) {
                    basis = FinanceFunctionImpl.basis((int)(args[6] == null?0.0D:NumericFunction.singleOperandEvaluate(args[6], srcRowIndex, srcColumnIndex)));
                }

                if(basis >= 0 && basis <= 4) {
                    boolean method = true;
                    if(args.length == 8) {
                        method = UtilFns.stringToBoolean(args[7]);
                    }

                    if(freq != 1 && freq != 2 && freq != 4) {
                        throw new EvaluationException(ErrorEval.NUM_ERROR);
                    } else if(rate > 0.0D && par > 0.0D) {
                        double result = 0.0D;
                        if(method) {
                            result = par * rate * FinanceFunctionImpl.getYearDiff(issue, settlement, basis);
                        } else {
                            result = par * rate * FinanceFunctionImpl.getYearDiff(firI, settlement, basis);
                        }

                        return result;
                    } else {
                        throw new EvaluationException(ErrorEval.NUM_ERROR);
                    }
                } else {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                }
            }
        }
    };
    public static final Function ACCRINTM = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double rate = args[2] == null?0.0D:NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            double par = args[3] == null?0.0D:NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
            if(rate > 0.0D && par > 0.0D) {
                double dissue = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
                Date issue = DateUtil.getJavaDate(dissue);
                double dSettlement = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
                Date settlement = DateUtil.getJavaDate(dSettlement);
                if(settlement.before(issue)) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                } else {
                    int basis = 0;
                    if(args.length == 5) {
                        basis = FinanceFunctionImpl.basis((int)(args[4] == null?0.0D:NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex)));
                    }

                    int A = UtilFns.dsm(issue, settlement, basis);
                    double D = UtilFns.basisToDouble(basis, issue, settlement, A);
                    double accrintm = par * rate * ((double)A / D);
                    return (new Double(accrintm)).doubleValue();
                }
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function AMORDEGRC = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double cost = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double dPurDate = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date purDate = DateUtil.getJavaDate(dPurDate);
            double dFirDate = args[2] == null?0.0D:NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            Date firDate = DateUtil.getJavaDate(dFirDate);
            double salvage = args[3] == null?0.0D:NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
            int period = (int)(args[4] == null?0.0D:NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex));
            double rate = args[5] == null?0.0D:NumericFunction.singleOperandEvaluate(args[5], srcRowIndex, srcColumnIndex);
            int basis = 0;
            if(args.length == 7) {
                basis = FinanceFunctionImpl.basis((int)(args[6] == null?0.0D:NumericFunction.singleOperandEvaluate(args[6], srcRowIndex, srcColumnIndex)));
            }

            double usePeriod = 1.0D / rate;
            double coeff;
            if(usePeriod < 3.0D) {
                coeff = 1.0D;
            } else if(usePeriod < 5.0D) {
                coeff = 1.5D;
            } else if(usePeriod <= 6.0D) {
                coeff = 2.0D;
            } else {
                coeff = 2.5D;
            }

            rate *= coeff;
            double nRate = (double)Math.round(FinanceFunctionImpl.yearFraction(purDate, firDate, basis) * rate * cost);
            cost -= nRate;
            double rest = cost - salvage;

            for(int n = 0; n < period; ++n) {
                nRate = (double)Math.round(rate * cost);
                rest -= nRate;
                if(rest < 0.0D) {
                    switch(period - n) {
                        case 0:
                        case 1:
                            return (double)Math.round(cost * 0.5D);
                        default:
                            return 0.0D;
                    }
                }

                cost -= nRate;
            }

            return nRate;
        }
    };
    public static final Function AMORLINC = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double cost = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double dPurDate = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date purDate = DateUtil.getJavaDate(dPurDate);
            double dFirDate = args[2] == null?0.0D:NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            Date firDate = DateUtil.getJavaDate(dFirDate);
            double salvage = args[3] == null?0.0D:NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
            int period = (int)(args[4] == null?0.0D:NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex));
            double rate = args[5] == null?0.0D:NumericFunction.singleOperandEvaluate(args[5], srcRowIndex, srcColumnIndex);
            int basis = 0;
            if(args.length == 7) {
                basis = FinanceFunctionImpl.basis((int)(args[6] == null?0.0D:NumericFunction.singleOperandEvaluate(args[6], srcRowIndex, srcColumnIndex)));
            }

            double T0 = cost * rate;
            double T1 = FinanceFunctionImpl.yearFraction(purDate, firDate, basis) * T0;
            int numPeriods = (int)((cost - salvage - T1) / T0);
            return period == 0?(new Double(T1)).doubleValue():(period <= numPeriods?(new Double(T0)).doubleValue():(period == numPeriods + 1?cost - salvage - T0 * (double)numPeriods - T1:0.0D));
        }
    };
    public static final Function COUPDAYBS = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            int frequency = FinanceFunctionImpl.frequency((int)(args[2] == null?0.0D:NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex)));
            int basis = 0;
            if(args.length == 4) {
                basis = FinanceFunctionImpl.basis((int)(args[3] == null?0.0D:NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex)));
            }

            return FinanceFunctionImpl.coupdaybs(settle, maturi, frequency, basis);
        }
    };
    public static final Function COUPDAYS = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            int frequency = FinanceFunctionImpl.frequency((int)(args[2] == null?0.0D:NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex)));
            int basis = 0;
            if(args.length == 4) {
                basis = FinanceFunctionImpl.basis((int)(args[3] == null?0.0D:NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex)));
            }

            return FinanceFunctionImpl.coupdays(settle, maturi, frequency, basis);
        }
    };
    public static final Function COUPDAYSNC = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            int frequency = FinanceFunctionImpl.frequency((int)(args[2] == null?0.0D:NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex)));
            int basis = 0;
            if(args.length == 4) {
                basis = FinanceFunctionImpl.basis((int)(args[3] == null?0.0D:NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex)));
            }

            return FinanceFunctionImpl.coupdaysnc(settle, maturi, frequency, basis);
        }
    };
    public static final Function COUPNCD = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            int frequency = FinanceFunctionImpl.frequency((int)(args[2] == null?0.0D:NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex)));
            int basis = 0;
            if(args.length == 4) {
                basis = FinanceFunctionImpl.basis((int)(args[3] == null?0.0D:NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex)));
            }

            Date couppcd = FinanceFunctionImpl.couppcd(settle, maturi, frequency, basis);
            Date coupncd = FinanceFunctionImpl.coupncd(couppcd, frequency);
            return DateUtil.getExcelDate(coupncd);
        }
    };
    public static final Function COUPNUM = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            int frequency = FinanceFunctionImpl.frequency((int)(args[2] == null?0.0D:NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex)));
            int basis = 0;
            if(args.length == 4) {
                basis = FinanceFunctionImpl.basis((int)(args[3] == null?0.0D:NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex)));
            }

            return FinanceFunctionImpl.coupnum(settle, maturi, frequency, basis);
        }
    };
    public static final Function COUPPCD = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            if(settle.after(maturi)) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                int frequency = FinanceFunctionImpl.frequency((int)(args[2] == null?0.0D:NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex)));
                int basis = 0;
                if(args.length == 4) {
                    basis = FinanceFunctionImpl.basis((int)(args[3] == null?0.0D:NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex)));
                }

                return DateUtil.getExcelDate(FinanceFunctionImpl.couppcd(settle, maturi, frequency, basis));
            }
        }
    };
    public static final Function CUMIPMT = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double rate = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            int nper = (int)Math.floor(NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex));
            double pv = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            int startPer = (int)Math.floor(NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex));
            int endPer = (int)Math.floor(NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex));
            if(rate != 0.0D && nper != 0 && pv != 0.0D && startPer >= 1 && endPer >= 1 && startPer <= endPer) {
                int type = 0;
                if(args.length > 5) {
                    type = (int)NumericFunction.singleOperandEvaluate(args[5], srcRowIndex, srcColumnIndex);
                }

                if(type != 0 && type != 1) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                } else {
                    double cumipmt = 0.0D;
                    double pmt = FinanceFunctionImpl.pmt(rate, (double) nper, pv, 0.0D, type);
                    if(startPer == 1) {
                        if(type <= 0) {
                            cumipmt = -pv;
                        }

                        ++startPer;
                    }

                    for(int i = startPer; i <= endPer; ++i) {
                        if(type > 0) {
                            cumipmt += FinanceFunctionImpl.pmt(rate, (double) (i - 2), pmt, pv, 1) - pv;
                        } else {
                            cumipmt += FinanceFunctionImpl.pmt(rate, (double) (i - 1), pmt, pv, 0);
                        }
                    }

                    cumipmt *= rate;
                    return cumipmt;
                }
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function CUMPRINC = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double rate = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double nper = Math.floor(NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex));
            double pv = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            int startPer = (int)Math.floor(NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex));
            int endPer = (int)Math.floor(NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex));
            if(rate != 0.0D && nper != 0.0D && pv != 0.0D && startPer >= 1 && endPer >= 1 && startPer <= endPer) {
                int type = 0;
                if(args.length > 5) {
                    type = (int)NumericFunction.singleOperandEvaluate(args[5], srcRowIndex, srcColumnIndex);
                }

                if(type != 0 && type != 1) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                } else {
                    double cumppmt = 0.0D;
                    double pmt = FinanceFunctionImpl.pmt(rate, nper, pv, 0.0D, type);
                    if(startPer == 1) {
                        if(type <= 0) {
                            cumppmt = pmt + pv * rate;
                        } else {
                            cumppmt = pmt;
                        }

                        ++startPer;
                    }

                    for(int i = startPer++; i <= endPer; ++i) {
                        if(type > 0) {
                            cumppmt += pmt - (FinanceFunctionImpl.pmt(rate, (double) (i - 2), pmt, pv, 1) - pmt) * rate;
                        } else {
                            cumppmt += pmt - FinanceFunctionImpl.pmt(rate, (double) (i - 1), pmt, pv, 0) * rate;
                        }
                    }

                    return cumppmt;
                }
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function DISC = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double pr = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            double redemption = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
            if(pr > 0.0D && redemption > 0.0D) {
                double dSettle = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
                Date settlement = DateUtil.getJavaDate(dSettle);
                double dMaturi = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
                Date maturity = DateUtil.getJavaDate(dMaturi);
                int basis = 0;
                if(args.length == 5) {
                    basis = FinanceFunctionImpl.basis((int)(args[4] == null?0.0D:NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex)));
                }

                int DSM = UtilFns.dsm(settlement, maturity, basis);
                double B = UtilFns.basisToDouble(basis, settlement, maturity, DSM);
                double disc = (redemption - pr) / redemption * (B / (double)DSM);
                return disc;
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function DOLLARDE = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dollar = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double fraction = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            if(fraction < 0.0D) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else if(fraction >= 0.0D && fraction < 1.0D) {
                throw new EvaluationException(ErrorEval.DIV_ZERO);
            } else {
                return Math.floor(dollar) + dollar % 1.0D * 100.0D / fraction;
            }
        }
    };
    public static final Function DOLLARFR = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dollar = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double fraction = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            if(fraction < 0.0D) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else if(fraction == 0.0D) {
                throw new EvaluationException(ErrorEval.DIV_ZERO);
            } else {
                return Math.floor(dollar) + dollar % 1.0D * fraction / 100.0D;
            }
        }
    };
    public static final Function DURATION = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            if(settle.after(maturi)) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                double coupon = args[2] == null?0.0D:NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
                double yld = args[3] == null?0.0D:NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
                if(yld <= 0.0D && coupon <= 0.0D) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                } else {
                    int freq = (int)(args[4] == null?1.0D:NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex));
                    if(freq != 1 && freq != 2 && freq != 4) {
                        throw new EvaluationException(ErrorEval.NUM_ERROR);
                    } else {
                        int basis = 0;
                        if(args.length == 6) {
                            basis = FinanceFunctionImpl.basis((int)(args[5] == null?0.0D:NumericFunction.singleOperandEvaluate(args[5], srcRowIndex, srcColumnIndex)));
                        }

                        double yearFrac = FinanceFunctionImpl.yearFraction(settle, maturi, basis);
                        double coupNs = FinanceFunctionImpl.coupnum(settle, maturi, 12 / freq, basis);
                        double dura = 0.0D;
                        coupon *= 100.0D / (double)freq;
                        yld /= (double)freq;
                        ++yld;
                        double nDiff = yearFrac * (double)freq - coupNs;

                        for(int p = 1; (double)p < coupNs; ++p) {
                            dura += ((double)p + nDiff) * coupon / Math.pow(yld, (double)p + nDiff);
                        }

                        dura += (coupNs + nDiff) * (coupon + 100.0D) / Math.pow(yld, coupNs + nDiff);
                        double var27 = 0.0D;

                        for(int t = 1; (double)t < coupNs; ++t) {
                            var27 += coupon / Math.pow(yld, (double)t + nDiff);
                        }

                        var27 += (coupon + 100.0D) / Math.pow(yld, coupNs + nDiff);
                        dura /= var27;
                        dura /= (double)freq;
                        return dura;
                    }
                }
            }
        }
    };
    public static final Function EFFECT = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double rate = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double npery = Math.floor(NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex));
            if(rate > 0.0D && npery >= 1.0D) {
                return Math.pow(1.0D + rate / npery, npery) - 1.0D;
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function FVSCHEDULE = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double principal = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            List l0 = AreaEvalHelper.toDoubleList(args[1], srcRowIndex, srcColumnIndex);
            double[] schedule = UtilFns.toDoubleArray(l0);

            for(int i = 0; i < schedule.length; ++i) {
                principal *= 1.0D + schedule[i];
            }

            return (new Double(principal)).doubleValue();
        }
    };
    public static final Function INTRATE = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double investment = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            double redemption = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
            if(investment > 0.0D && redemption > 0.0D) {
                double dSettle = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
                Date settle = DateUtil.getJavaDate(dSettle);
                double dMaturi = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
                Date maturi = DateUtil.getJavaDate(dMaturi);
                int basis = 0;
                if(args.length == 5) {
                    basis = FinanceFunctionImpl.basis((int)(args[4] == null?0.0D:NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex)));
                }

                int dsm = UtilFns.dsm(settle, maturi, basis);
                double B = UtilFns.basisToDouble(basis, settle, maturi, dsm);
                return (redemption - investment) / investment * (B / (double)dsm);
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function NOMINAL = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double rate = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double npery = Math.floor(NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex));
            if(rate > 0.0D && npery >= 1.0D) {
                return (Math.pow(rate + 1.0D, 1.0D / npery) - 1.0D) * npery;
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function NPV = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double d = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            List l0 = AreaEvalHelper.toDoubleList(args[1], srcRowIndex, srcColumnIndex);
            double[] values = UtilFns.toDoubleArray(l0);
            return FinanceFunctionImpl.npv(d, values);
        }
    };
    public static final Function PRICE = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            if(settle.after(maturi)) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                double rate = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
                double yld = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
                double redemption = NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex);
                if(yld <= 0.0D && rate <= 0.0D && redemption <= 0.0D) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                } else {
                    int freq = (int)(args[5] == null?1.0D:NumericFunction.singleOperandEvaluate(args[5], srcRowIndex, srcColumnIndex));
                    if(freq != 1 && freq != 2 && freq != 4) {
                        throw new EvaluationException(ErrorEval.NUM_ERROR);
                    } else {
                        int basis = 0;
                        if(args.length == 7) {
                            basis = FinanceFunctionImpl.basis((int)(args[6] == null?0.0D:NumericFunction.singleOperandEvaluate(args[6], srcRowIndex, srcColumnIndex)));
                        }

                        return FinanceFunctionImpl.price(settle, maturi, rate, yld, redemption, freq, basis);
                    }
                }
            }
        }
    };
    public static final Function PRICEDISC = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double discount = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            double redemption = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
            if(discount > 0.0D && redemption > 0.0D) {
                double dSettle = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
                Date settle = DateUtil.getJavaDate(dSettle);
                double dMaturi = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
                Date maturi = DateUtil.getJavaDate(dMaturi);
                int basis = 0;
                if(args.length == 5) {
                    basis = FinanceFunctionImpl.basis((int)(args[4] == null?0.0D:NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex)));
                }

                int dsm = UtilFns.dsm(settle, maturi, basis);
                double B = UtilFns.basisToDouble(basis, settle, maturi, dsm);
                return redemption - discount * redemption * ((double)dsm / B);
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function PRICEMAT = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            double dissue = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            Date issue = DateUtil.getJavaDate(dissue);
            double rate = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
            double yld = NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex);
            int basis = 0;
            if(args.length == 6) {
                basis = FinanceFunctionImpl.basis((int)(args[5] == null?0.0D:NumericFunction.singleOperandEvaluate(args[5], srcRowIndex, srcColumnIndex)));
            }

            if(rate >= 0.0D && yld >= 0.0D) {
                int dsm = UtilFns.dsm(settle, maturi, basis);
                int dim = UtilFns.dsm(issue, maturi, basis);
                int A = UtilFns.dsm(issue, settle, basis);
                double B = UtilFns.basisToDouble(basis, issue, settle, A);
                return (100.0D + (double)dim / B * rate * 100.0D) / (1.0D + (double)dsm / B * yld) - (double)A / B * rate * 100.0D;
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function RECEIVED = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double investment = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            double discount = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
            if(investment > 0.0D && discount > 0.0D) {
                double dSettle = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
                Date settle = DateUtil.getJavaDate(dSettle);
                double dMaturi = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
                Date maturi = DateUtil.getJavaDate(dMaturi);
                int basis = 0;
                if(args.length == 5) {
                    basis = FinanceFunctionImpl.basis((int)NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex));
                }

                int dim = UtilFns.dsm(settle, maturi, basis);
                double B = UtilFns.basisToDouble(basis, settle, maturi, dim);
                return investment / (1.0D - discount * ((double)dim / B));
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };
    public static final Function TBILLEQ = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            double discount = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            if(discount <= 0.0D) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                int DSM = UtilFns.dsm(settle, maturi, 2);
                return 365.0D * discount / (360.0D - discount * (double)DSM);
            }
        }
    };
    public static final Function TBILLPRICE = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            double discount = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            if(discount <= 0.0D) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                int DSM = UtilFns.dsm(settle, maturi, 2);
                return 100.0D * (1.0D - discount * (double)DSM / 360.0D);
            }
        }
    };
    public static final Function TBILLYIELD = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            double pr = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            if(pr <= 0.0D) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                int DSM = UtilFns.dsm(settle, maturi, 2);
                double p1 = (100.0D - pr) / pr;
                double p2 = 360.0D / (double)DSM;
                return p1 * p2;
            }
        }
    };
    public static final Function XNPV = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double rate = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            List l0 = AreaEvalHelper.toDoubleList(args[1], srcRowIndex, srcColumnIndex);
            double[] values = UtilFns.toDoubleArray(l0);
            List l1 = AreaEvalHelper.toDoubleList(args[2], srcRowIndex, srcColumnIndex);
            if(values.length != l1.size()) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                double[] dates = UtilFns.toDoubleArray(l1);
                double xnpv = 0.0D;

                for(int i = 0; i < dates.length; ++i) {
                    double di = dates[i];
                    double d1 = dates[0];
                    double firstArg = 1.0D + rate;
                    double secondArg = (di - d1) / 365.0D;
                    double pow = Math.pow(firstArg, secondArg);
                    xnpv += values[i] / pow;
                }

                return xnpv;
            }
        }
    };
    public static final Function YIELD = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            double rate = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            double pr = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
            double redemp = NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex);
            int freq = (int)NumericFunction.singleOperandEvaluate(args[5], srcRowIndex, srcColumnIndex);
            int basis = 0;
            if(args.length == 7) {
                basis = FinanceFunctionImpl.basis((int)NumericFunction.singleOperandEvaluate(args[6], srcRowIndex, srcColumnIndex));
            }

            double result;
            if(FinanceFunctionImpl.coupnum(settle, maturi, FinanceFunctionImpl.frequency(freq), basis) <= 1.0D) {
                double A = FinanceFunctionImpl.coupdaybs(settle, maturi, FinanceFunctionImpl.frequency(freq), basis);
                double DSR = FinanceFunctionImpl.coupdaysnc(settle, maturi, FinanceFunctionImpl.frequency(freq), basis);
                double E = FinanceFunctionImpl.coupdays(settle, maturi, FinanceFunctionImpl.frequency(freq), basis);
                double term = rate / (double)freq;
                double coeff = (double)freq * E / DSR;
                double num = redemp / 100.0D + term - (pr / 100.0D + A / E * term);
                double den = pr / 100.0D + A / E * term;
                result = num / den * coeff;
            } else {
                result = FinanceFunctionImpl.getYld(settle, maturi, rate, pr, redemp, freq, basis);
            }

            return result;
        }
    };
    public static final Function YIELDDISC = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            double price = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            double redemp = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
            int basis = 0;
            if(args.length == 5) {
                basis = FinanceFunctionImpl.basis((int)NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex));
            }

            double result = redemp / price - 1.0D;
            return result / FinanceFunctionImpl.yearFraction(settle, maturi, basis);
        }
    };
    public static final Function YIELDMAT = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double dSettle = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            Date settle = DateUtil.getJavaDate(dSettle);
            double dMaturi = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            Date maturi = DateUtil.getJavaDate(dMaturi);
            double dIssue = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            Date issue = DateUtil.getJavaDate(dIssue);
            double rate = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
            double price = NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex);
            int basis = 0;
            if(args.length == 6) {
                basis = FinanceFunctionImpl.basis((int)NumericFunction.singleOperandEvaluate(args[5], srcRowIndex, srcColumnIndex));
            }

            double fIssMat = FinanceFunctionImpl.yearFraction(issue, maturi, basis);
            double fIssSet = FinanceFunctionImpl.yearFraction(issue, settle, basis);
            double fSetMat = FinanceFunctionImpl.yearFraction(settle, maturi, basis);
            double y = 1.0D + fIssMat * rate;
            y /= price / 100.0D + fIssSet * rate;
            --y;
            return y / fSetMat;
        }
    };
    public static final Function Temp = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            return 0.0D;
        }
    };
    public static final Function DB = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double cost = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            if(cost < 0.0D) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                double salvage = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
                if(salvage < 0.0D) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                } else if(args[2] != null && args[3] != null) {
                    double life = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
                    double period = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
                    if(life >= 0.0D && period >= 0.0D) {
                        if(period - 1.0D > life) {
                            throw new EvaluationException(ErrorEval.NUM_ERROR);
                        } else {
                            double month = 12.0D;
                            if(args.length > 4) {
                                month = NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex);
                            }

                            double rate = (new BigDecimal(1.0D - Math.pow(salvage / cost, 1.0D / life))).setScale(3, 4).doubleValue();
                            return FinanceFunctionImpl.db(cost, salvage, life, period, month, rate);
                        }
                    } else {
                        throw new EvaluationException(ErrorEval.NUM_ERROR);
                    }
                } else {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                }
            }
        }
    };
    public static final Function DDB = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double cost = args[0] == null?0.0D:NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            if(cost < 0.0D) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                double salvage = args[1] == null?0.0D:NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
                if(salvage < 0.0D) {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                } else if(args[2] != null && args[3] != null) {
                    double life = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
                    double period = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
                    if(life >= 0.0D && period >= 0.0D) {
                        if(period > life) {
                            throw new EvaluationException(ErrorEval.NUM_ERROR);
                        } else {
                            double factor = 2.0D;
                            if(args.length > 4) {
                                factor = NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex);
                            }

                            return FinanceFunctionImpl.ddb(cost, salvage, life, period, factor);
                        }
                    } else {
                        throw new EvaluationException(ErrorEval.NUM_ERROR);
                    }
                } else {
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
                }
            }
        }
    };
    public static final Function IPMT = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double rate = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double per = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            double nper = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            if(per > nper) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                double pv = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
                double fv = args.length > 4 && args[4] != null?NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex):0.0D;
                int type = args.length > 5?(int)NumericFunction.singleOperandEvaluate(args[5], srcRowIndex, srcColumnIndex):0;
                return FinanceFunctionImpl.ipmt(rate, per, nper, pv, fv, type);
            }
        }
    };
    public static final Function PPMT = new NumericFunction() {
        public double eval(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            double rate = NumericFunction.singleOperandEvaluate(args[0], srcRowIndex, srcColumnIndex);
            double per = NumericFunction.singleOperandEvaluate(args[1], srcRowIndex, srcColumnIndex);
            double nper = NumericFunction.singleOperandEvaluate(args[2], srcRowIndex, srcColumnIndex);
            if(per > nper) {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            } else {
                double pv = NumericFunction.singleOperandEvaluate(args[3], srcRowIndex, srcColumnIndex);
                double fv = args.length > 4 && args[4] != null?NumericFunction.singleOperandEvaluate(args[4], srcRowIndex, srcColumnIndex):0.0D;
                int type = args.length > 5?(int)NumericFunction.singleOperandEvaluate(args[5], srcRowIndex, srcColumnIndex):0;
                return FinanceFunctionImpl.ppmt(rate, per, nper, pv, fv, type);
            }
        }
    };
    public static final Function SLN = new FinanceFunction() {
        protected double evaluate(double cost, double salvage, double life, double arg3, boolean type) throws EvaluationException {
            if(life == 0.0D) {
                throw new EvaluationException(ErrorEval.DIV_ZERO);
            } else {
                return (cost - salvage) / life;
            }
        }
    };
    public static final Function SYD = new FinanceFunction() {
        protected double evaluate(double cost, double salvage, double life, double per, boolean type) throws EvaluationException {
            if(life > 0.0D && per > 0.0D) {
                return (cost - salvage) * (life - per + 1.0D) * 2.0D / (life * (life + 1.0D));
            } else {
                throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };

    public FinanceFunctionImpl() {
    }

    private static double getYearDiff(Date firstDate, Date secondDate, int basis) {
        SerialDate startDate = SerialDate.createInstance(firstDate);
        SerialDate endDate = SerialDate.createInstance(secondDate);
        double result = 0.0D;
        boolean diff = false;
        int var8;
        switch(basis) {
            case 0:
                var8 = SerialDateUtilities.dayCount30(startDate, endDate);
                if(startDate.getYYYY() == endDate.getYYYY() && startDate.getMonth() == 2 && endDate.getMonth() != 2) {
                    if(SerialDate.isLeapYear(startDate.getYYYY())) {
                        --var8;
                    } else {
                        var8 -= 2;
                    }
                }

                result = (double)var8 / 360.0D;
                break;
            case 1:
                var8 = SerialDateUtilities.dayCountActual(startDate, endDate);
                if(SerialDate.isLeapYear(startDate.getYYYY())) {
                    result = (double)var8 / 366.0D;
                } else {
                    result = (double)var8 / 365.0D;
                }
                break;
            case 2:
                var8 = SerialDateUtilities.dayCountActual(startDate, endDate);
                result = (double)var8 / 360.0D;
                break;
            case 3:
                var8 = SerialDateUtilities.dayCountActual(startDate, endDate);
                result = (double)var8 / 365.0D;
                break;
            case 4:
                var8 = SerialDateUtilities.dayCount30E(startDate, endDate);
                result = (double)var8 / 360.0D;
        }

        return result;
    }

    private static double yearFraction(Date d0, Date d1, int basis) {
        SerialDate startDate = SerialDate.createInstance(d0);
        SerialDate endDate = SerialDate.createInstance(d1);
        double result = 0.0D;
        boolean diff = false;
        int diff1;
        switch(basis) {
            case 0:
                diff1 = SerialDateUtilities.dayCount30(startDate, endDate);
                result = (double)diff1 / 360.0D;
                break;
            case 1:
                SerialDate tempDate1 = SerialDate.addYears(1, startDate);
                int nYears;
                int nFeb29s;
                if(endDate.compare(tempDate1) > 0) {
                    nYears = endDate.getYYYY() - startDate.getYYYY() + 1;
                    tempDate1 = SerialDate.createInstance(1, 1, endDate.getYYYY() + 1);
                    SerialDate tempDate2 = SerialDate.createInstance(1, 1, startDate.getYYYY());
                    nFeb29s = tempDate1.compare(tempDate2) - 365 * nYears;
                } else {
                    nYears = 1;
                    if((!SerialDate.isLeapYear(startDate.getYYYY()) || startDate.getMonth() >= 3) && (!SerialDate.isLeapYear(endDate.getYYYY()) || endDate.compare(SerialDate.createInstance(29, 2, endDate.getYYYY())) < 0)) {
                        nFeb29s = 0;
                    } else {
                        nFeb29s = 1;
                    }
                }

                double peryear = 365.0D + (double)nFeb29s / (double)nYears;
                result = (double)endDate.compare(startDate) / peryear;
                break;
            case 2:
                diff1 = SerialDateUtilities.dayCountActual(startDate, endDate);
                result = (double)diff1 / 360.0D;
                break;
            case 3:
                diff1 = SerialDateUtilities.dayCountActual(startDate, endDate);
                result = (double)diff1 / 365.0D;
                break;
            case 4:
                diff1 = SerialDateUtilities.dayCount30E(startDate, endDate);
                result = (double)diff1 / 360.0D;
        }

        return result;
    }

    private static int basis(int basis) throws EvaluationException {
        if(basis <= 4 && basis >= 0) {
            return basis;
        } else {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        }
    }

    private static double coupdaybs(Date settle, Date maturi, int freq, int basis) throws EvaluationException {
        Date coupday = couppcd(settle, maturi, freq, basis);
        return (double)UtilFns.dsm(coupday, settle, basis);
    }

    private static Date couppcd(Date settle, Date maturi, int frequency, int basis) {
        GregorianCalendar couppcd = new GregorianCalendar();
        GregorianCalendar settleD = new GregorianCalendar();
        settleD.setTime(settle);
        couppcd.setTime(maturi);
        couppcd.set(1, settleD.get(1));
        if(couppcd.after(settleD)) {
            couppcd.add(1, -1);
        }

        while(!couppcd.after(settleD)) {
            couppcd.add(2, frequency);
        }

        couppcd.add(2, -1 * frequency);
        return couppcd.getTime();
    }

    private static int frequency(int frequency) throws EvaluationException {
        if(frequency != 1 && frequency != 2 && frequency != 4) {
            throw new EvaluationException(ErrorEval.NUM_ERROR);
        } else {
            return 12 / frequency;
        }
    }

    private static double coupdays(Date settle, Date maturi, int freq, int basis) throws EvaluationException {
        Date couppcd = couppcd(settle, maturi, freq, basis);
        Date coupncd = coupncd(couppcd, freq);
        return (double)UtilFns.dsm(couppcd, coupncd, basis);
    }

    private static Date coupncd(Date coupday, int frequency) {
        GregorianCalendar coupncd = new GregorianCalendar();
        coupncd.setTime(coupday);
        coupncd.add(2, frequency);
        return coupncd.getTime();
    }

    private static double coupdaysnc(Date settle, Date maturi, int freq, int basis) throws EvaluationException {
        Date couppcd = couppcd(settle, maturi, freq, basis);
        Date coupncd = coupncd(couppcd, freq);
        double coupdaysnc = (double)UtilFns.dsm(settle, coupncd, basis);
        return coupdaysnc;
    }

    private static double coupnum(Date settle, Date maturi, int freq, int basis) {
        Date couppcd = couppcd(settle, maturi, freq, basis);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(couppcd);
        int couppcdY = gc.get(1);
        int couppcdM = gc.get(2);
        gc.setTime(maturi);
        int maturiY = gc.get(1);
        int maturiM = gc.get(2);
        return Math.floor((double)(((maturiY - couppcdY) * 12 + maturiM - couppcdM) / freq));
    }

    private static double npv(double rate, double[] values) {
        double[] npv = new double[values.length];

        for(int i = 0; i < values.length; ++i) {
            npv[i] = values[i] / Math.pow(1.0D + rate, (double)(i + 1));
        }

        return UtilFns.getStats(npv).getSum();
    }

    private static double price(Date settle, Date maturi, double rate, double yld, double redemp, int freq, int basis) throws EvaluationException {
        Date couppcd = couppcd(settle, maturi, frequency(freq), basis);
        Date coupncd = coupncd(couppcd, frequency(freq));
        double E = (double)UtilFns.dsm(couppcd, coupncd, basis);
        double DSC = (double)UtilFns.dsm(settle, coupncd, basis);
        double N = coupnum(settle, maturi, frequency(freq), basis);
        double A = (double)UtilFns.dsm(couppcd, settle, basis);
        double price = redemp / Math.pow(1.0D + yld / (double)freq, N - 1.0D + DSC / E);
        double T1 = 100.0D * rate / (double)freq;
        double T2 = 1.0D + yld / (double)freq;
        double T3 = DSC / E;

        for(int i = 0; (double)i < N; ++i) {
            price += T1 / Math.pow(T2, (double)i + T3);
        }

        price -= T1 * A / E;
        return price;
    }

    private static double getYld(Date set, Date mat, double rate, double fPrice, double redemp, int freq, int basis) throws ArithmeticException, EvaluationException {
        double fPriceN = 0.0D;
        double fYield1 = 0.0D;
        double fYield2 = 1.0D;
        double fPrice1 = price(set, mat, rate, fYield1, redemp, freq, basis);
        double fPrice2 = price(set, mat, rate, fYield2, redemp, freq, basis);
        double fYieldN = (fYield2 - fYield1) * 0.5D;

        for(int nIter = 0; nIter < 100 && fPriceN != fPrice; ++nIter) {
            fPriceN = price(set, mat, rate, fYieldN, redemp, freq, basis);
            if(fPrice == fPrice1) {
                return fYield1;
            }

            if(fPrice == fPrice2) {
                return fYield2;
            }

            if(fPrice == fPriceN) {
                return fYieldN;
            }

            if(fPrice < fPrice2) {
                fYield2 *= 2.0D;
                fPrice2 = price(set, mat, rate, fYield2, redemp, freq, basis);
                fYieldN = (fYield2 - fYield1) * 0.5D;
            } else {
                if(fPrice < fPriceN) {
                    fYield1 = fYieldN;
                    fPrice1 = fPriceN;
                } else {
                    fYield2 = fYieldN;
                    fPrice2 = fPriceN;
                }

                fYieldN = fYield2 - (fYield2 - fYield1) * ((fPrice - fPrice2) / (fPrice1 - fPrice2));
            }
        }

        if(Math.abs(fPrice - fPriceN) > fPrice / 100.0D) {
            throw new ArithmeticException();
        } else {
            return fYieldN;
        }
    }

    private static double db(double cost, double salvage, double life, double period, double month, double rate) {
        double db = cost * rate * month / 12.0D;
        double d = cost * rate * month / 12.0D;
        if(period > 1.0D) {
            for(int i = 1; (double)i <= period - 1.0D; ++i) {
                if((double)i < life) {
                    db = (cost - d) * rate;
                    d += db;
                } else {
                    db = (cost - d) * rate * (12.0D - month) / 12.0D;
                }
            }
        }

        return db;
    }

    private static double ddb(double cost, double salvage, double life, double period, double factor) {
        double d1 = factor * cost * Math.pow((life - factor) / life, period - 1.0D) / life;
        double d2 = factor * cost * Math.pow((life - factor) / life, period - 1.0D) / life - ((1.0D - Math.pow((life - factor) / life, period)) * cost - (cost - salvage));
        return Math.min(d1, d2);
    }

    private static double ppmt(double rate, double per, double nper, double pv, double fv, int type) {
        return pmt(rate, nper, pv, fv, type) - ipmt(rate, per, nper, pv, fv, type);
    }

    private static double pmt(double rate, double nper, double pv, double fv, int type) {
        double pmt = 0.0D;
        if(rate == 0.0D) {
            pmt = (fv + pv) / nper;
        } else {
            double term = Math.pow(1.0D + rate, nper);
            if(type == 1) {
                pmt = (fv * rate / (term - 1.0D) + pv * rate / (1.0D - 1.0D / term)) / (1.0D + rate);
            } else {
                pmt = fv * rate / (term - 1.0D) + pv * rate / (1.0D - 1.0D / term);
            }
        }

        return -pmt;
    }

    private static double ipmt(double rate, double per, double nper, double pv, double fv, int type) {
        double ipmt = 0.0D;
        if(per == 1.0D) {
            ipmt = type == 0?-pv:0.0D;
        } else {
            double pmt = pmt(rate, nper, pv, fv, type);
            if(type == 0) {
                ipmt = GetZw(rate, per - 1.0D, pmt, pv, 0);
            } else if(type == 1) {
                ipmt = GetZw(rate, per - 2.0D, pmt, pv, 1) - pmt;
            }
        }

        return ipmt * rate;
    }

    private static double GetZw(double rate, double nper, double pmt, double pv, int type) {
        double Zw = 0.0D;
        if(rate == 0.0D) {
            Zw = pv + pmt * nper;
        } else {
            double term = Math.pow(1.0D + rate, nper);
            if((double)type > 0.0D) {
                Zw = pv * term + pmt * (1.0D + rate) * (term - 1.0D) / rate;
            } else {
                Zw = pv * term + pmt * (term - 1.0D) / rate;
            }
        }

        return -Zw;
    }
}
