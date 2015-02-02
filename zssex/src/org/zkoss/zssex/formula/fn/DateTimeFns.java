//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula.fn;

import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.zssex.formula.fn.DateTimeFunctionImpl;

public class DateTimeFns {
    public DateTimeFns() {
    }

    public static final ValueEval dateValue(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return DateTimeFunctionImpl.DATEVALUE.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval hour(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return DateTimeFunctionImpl.HOUR.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval minute(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return DateTimeFunctionImpl.MINUTE.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval second(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return DateTimeFunctionImpl.SECOND.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval weekDay(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return DateTimeFunctionImpl.WEEKDAY.evaluate(args, srcRowIndex, srcColumnIndex);
    }
}
