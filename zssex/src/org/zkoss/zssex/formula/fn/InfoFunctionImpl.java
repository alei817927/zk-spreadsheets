//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula.fn;

import org.zkoss.poi.ss.formula.eval.AreaEval;
import org.zkoss.poi.ss.formula.eval.BoolEval;
import org.zkoss.poi.ss.formula.eval.ErrorEval;
import org.zkoss.poi.ss.formula.eval.EvaluationException;
import org.zkoss.poi.ss.formula.eval.NumberEval;
import org.zkoss.poi.ss.formula.eval.OperandResolver;
import org.zkoss.poi.ss.formula.eval.StringEval;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.poi.ss.formula.functions.Function;
import org.zkoss.poi.ss.formula.functions.NumericFunction;
import org.zkoss.poi.ss.formula.functions.TextFunction;

public class InfoFunctionImpl {
    public static final Function ISERR = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            try {
                ValueEval e = OperandResolver.getSingleValue(args[0], srcCellRow, srcCellCol);
                return BoolEval.valueOf(e instanceof ErrorEval && e != ErrorEval.NA);
            } catch (Exception var5) {
                return BoolEval.valueOf(true);
            }
        }
    };
    public static final Function N = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            ValueEval ve = OperandResolver.getSingleValue(args[0], srcCellRow, srcCellCol);
            return (ValueEval)(ve instanceof NumberEval?args[0]:(ve instanceof StringEval?new NumberEval(0.0D):(ve instanceof BoolEval?new NumberEval(((BoolEval)ve).getNumberValue()):new NumberEval(0.0D))));
        }
    };
    public static final Function TYPE = new NumericFunction() {
        protected double eval(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            ValueEval ve = args[0];
            if(!(ve instanceof ErrorEval)) {
                ve = OperandResolver.getSingleValue(args[0], srcCellRow, srcCellCol);
            }

            return ve instanceof NumberEval?1.0D:(ve instanceof StringEval?2.0D:(ve instanceof BoolEval?4.0D:(ve instanceof ErrorEval?16.0D:(ve instanceof AreaEval?64.0D:0.0D))));
        }
    };

    public InfoFunctionImpl() {
    }
}
