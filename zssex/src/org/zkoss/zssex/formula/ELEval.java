//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula;

import java.util.Date;
import org.zkoss.poi.ss.formula.OperationEvaluationContext;
import org.zkoss.poi.ss.formula.eval.BoolEval;
import org.zkoss.poi.ss.formula.eval.ErrorEval;
import org.zkoss.poi.ss.formula.eval.EvaluationException;
import org.zkoss.poi.ss.formula.eval.NumberEval;
import org.zkoss.poi.ss.formula.eval.OperandResolver;
import org.zkoss.poi.ss.formula.eval.StringEval;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.poi.ss.formula.functions.FreeRefFunction;
import org.zkoss.xel.Expressions;
import org.zkoss.xel.XelContext;
import org.zkoss.zssex.formula.XelContextHolder;

public class ELEval implements FreeRefFunction {
    public static final FreeRefFunction instance = new ELEval();
    public static final String NAME = "ELEVAL";

    private ELEval() {
    }

    public ValueEval evaluate(ValueEval[] args, OperationEvaluationContext ec) {
        if(args.length != 1) {
            return ErrorEval.NAME_INVALID;
        } else {
            try {
                ValueEval e = OperandResolver.getSingleValue(args[0], ec.getRowIndex(), ec.getColumnIndex());
                String expression = OperandResolver.coerceValueToString(e);
                XelContext ctx = XelContextHolder.getXelContext();
                Object obj = Expressions.evaluate(ctx, expression, (Class)ctx.getAttribute("zkoss.zss.CellType"));
                if(obj == null) {
                    return ErrorEval.NAME_INVALID;
                }

                if(obj instanceof String) {
                    return new StringEval((String)obj);
                }

                if(obj instanceof Number) {
                    return new NumberEval(((Number)obj).doubleValue());
                }

                if(obj instanceof Boolean) {
                    return BoolEval.valueOf(((Boolean)obj).booleanValue());
                }

                if(obj instanceof Date) {
                    return new NumberEval(this.javaMillSecondToExcelDate(((Date)obj).getTime()));
                }
            } catch (EvaluationException var7) {
                return var7.getErrorEval();
            }

            return ErrorEval.VALUE_INVALID;
        }
    }

    private double javaMillSecondToExcelDate(long date) {
        return (double)(date / 86400000L);
    }
}
