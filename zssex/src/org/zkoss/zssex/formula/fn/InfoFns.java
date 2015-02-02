//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula.fn;

import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.zssex.formula.fn.InfoFunctionImpl;

public class InfoFns {
    public InfoFns() {
    }

    public static final ValueEval iserr(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return InfoFunctionImpl.ISERR.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval n(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return InfoFunctionImpl.N.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval type(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return InfoFunctionImpl.TYPE.evaluate(args, srcRowIndex, srcColumnIndex);
    }
}
