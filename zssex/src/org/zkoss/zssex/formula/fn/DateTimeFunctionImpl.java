//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula.fn;

import java.util.Calendar;
import java.util.Date;
import org.zkoss.poi.ss.formula.eval.ErrorEval;
import org.zkoss.poi.ss.formula.eval.EvaluationException;
import org.zkoss.poi.ss.formula.eval.NumberEval;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.poi.ss.formula.functions.Function;
import org.zkoss.poi.ss.formula.functions.NumericFunction;
import org.zkoss.poi.ss.formula.functions.TextFunction;
import org.zkoss.poi.ss.formula.functions.TextFunctionHelper;
import org.zkoss.poi.ss.formula.functions.UtilFns;
import org.zkoss.poi.ss.usermodel.DateUtil;
import org.zkoss.xel.fn.CommonFns;
import org.zkoss.zssex.formula.fn.FunctionHelper;

public class DateTimeFunctionImpl {
    public static final Function DATEVALUE = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String txtDate = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            Object[] objs = FunctionHelper.parseToDate(txtDate);
            return new NumberEval(DateUtil.getExcelDate((Date)objs[0], false));
        }
    };
    public static final Function HOUR = new NumericFunction() {
        protected double eval(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String txt = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            Object[] result = FunctionHelper.editTextToValue(txt, (String)null);
            double time;
            if(result[1] instanceof Date) {
                time = DateUtil.getExcelDate((Date)result[1]);
            } else {
                time = ((Double)result[1]).doubleValue();
            }

            return (double)DateTimeFunctionImpl.argToCalendar(DateUtil.getJavaDate(time, false)).get(11);
        }
    };
    public static final Function MINUTE = new NumericFunction() {
        protected double eval(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String txt = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            Object[] result = FunctionHelper.editTextToValue(txt, (String)null);
            double time;
            if(result[1] instanceof Date) {
                time = DateUtil.getExcelDate((Date)result[1]);
            } else {
                time = ((Double)result[1]).doubleValue();
            }

            return (double)DateTimeFunctionImpl.argToCalendar(DateUtil.getJavaDate(time, false)).get(12);
        }
    };
    public static final Function SECOND = new NumericFunction() {
        protected double eval(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String txt = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            Object[] result = FunctionHelper.editTextToValue(txt, (String)null);
            double time;
            if(result[1] instanceof Date) {
                time = DateUtil.getExcelDate((Date)result[1]);
            } else {
                time = ((Double)result[1]).doubleValue();
            }

            return (double)DateTimeFunctionImpl.argToCalendar(DateUtil.getJavaDate(time, false)).get(13);
        }
    };
    public static final Function WEEKDAY = new NumericFunction() {
        protected double eval(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            double time = NumericFunction.singleOperandEvaluate(args[0], srcCellRow, srcCellCol);
            Calendar cal = DateTimeFunctionImpl.argToCalendar(DateUtil.getJavaDate(time, false));
            int type = 1;
            if(args.length == 2) {
                type = (int)NumericFunction.singleOperandEvaluate(args[1], srcCellRow, srcCellCol);
            }

            switch(type) {
                case 1:
                    return (double)(new Integer(cal.get(7))).intValue();
                case 2:
                    return (double)(new Integer(DateTimeFunctionImpl.weekdayFromMonday(cal.get(7)))).intValue();
                case 3:
                    return (double)(new Integer(DateTimeFunctionImpl.weekdayFromMonday(cal.get(7)) - 1)).intValue();
                default:
                    throw new EvaluationException(ErrorEval.NUM_ERROR);
            }
        }
    };

    public DateTimeFunctionImpl() {
    }

    private static int weekdayFromMonday(int day) {
        return day == 1?7:day - 1;
    }

    public static Calendar argToCalendar(Object arg) {
        Calendar calendar = Calendar.getInstance();
        if(arg instanceof Date) {
            calendar.setTime((Date)arg);
            return calendar;
        } else {
            calendar.setTime(UtilFns.daysToDate(CommonFns.toNumber(arg).intValue()));
            return calendar;
        }
    }
}
