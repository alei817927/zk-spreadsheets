//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula.fn;

import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.zssex.formula.fn.LogicalFunctionImpl;

public class LogicalFns {
    public LogicalFns() {
    }

    public static final ValueEval iferror(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return LogicalFunctionImpl.IFERROR.evaluate(args, srcRowIndex, srcColumnIndex);
    }
}
