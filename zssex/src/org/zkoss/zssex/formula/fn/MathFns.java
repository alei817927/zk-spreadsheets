//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula.fn;

import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.zssex.formula.fn.MathFunctionImpl;

public class MathFns {
    public MathFns() {
    }

    public static final ValueEval factdouble(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return MathFunctionImpl.FACTDOUBLE.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval gcd(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return MathFunctionImpl.GCD.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval lcm(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return MathFunctionImpl.LCM.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval mdeterm(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return MathFunctionImpl.MDETERM.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval minverse(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return MathFunctionImpl.MINVERSE.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval mmult(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return MathFunctionImpl.MMULT.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval mround(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return MathFunctionImpl.MROUND.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval multinomial(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return MathFunctionImpl.MULTINOMIAL.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval quotient(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return MathFunctionImpl.QUOTIENT.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval randbetween(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return MathFunctionImpl.RANDBETWEEN.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval roman(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return MathFunctionImpl.ROMAN.evaluate(args, srcRowIndex, srcColumnIndex);
    }

    public static final ValueEval sqrtpi(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        return MathFunctionImpl.SQRTPI.evaluate(args, srcRowIndex, srcColumnIndex);
    }
}
