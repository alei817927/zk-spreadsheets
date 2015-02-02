//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula;

import java.util.HashMap;
import java.util.Map;
import org.zkoss.poi.ss.formula.OperationEvaluationContext;
import org.zkoss.poi.ss.formula.eval.ErrorEval;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.poi.ss.formula.functions.FreeRefFunction;
import org.zkoss.poi.ss.formula.udf.UDFFinder;
import org.zkoss.zssex.formula.ELEval;
import org.zkoss.zssex.formula.ELEvalFunction;

public class ZKUDFFinder implements UDFFinder {
    private static Map<String, FreeRefFunction> FNS = new HashMap(3);
    private static final FreeRefFunction UNSUPPORTED_FUNCTION = new FreeRefFunction() {
        public ValueEval evaluate(ValueEval[] args, OperationEvaluationContext ec) {
            return ErrorEval.NAME_INVALID;
        }
    };
    public static final UDFFinder instance;

    private ZKUDFFinder() {
    }

    public FreeRefFunction findFunction(String name) {
        if(name != null && name.startsWith("_xlfn.")) {
            name = name.substring(6);
        }

        Object fn = (FreeRefFunction)FNS.get(name);
        if(fn == null) {
            fn = new ELEvalFunction(name);
            if(!((ELEvalFunction)fn).hasFunction()) {
                return null;
            }
        }

        return (FreeRefFunction)fn;
    }

    static {
        FNS.put("ELEVAL", ELEval.instance);
        FNS.put("EDATE", UNSUPPORTED_FUNCTION);
        FNS.put("EOMONTH", UNSUPPORTED_FUNCTION);
        FNS.put("WEEKNUM", UNSUPPORTED_FUNCTION);
        FNS.put("MDURATION", UNSUPPORTED_FUNCTION);
        FNS.put("ODDFPRICE", UNSUPPORTED_FUNCTION);
        FNS.put("ODDFYIELD", UNSUPPORTED_FUNCTION);
        FNS.put("ODDLPRICE", UNSUPPORTED_FUNCTION);
        FNS.put("ODDLYIELD", UNSUPPORTED_FUNCTION);
        FNS.put("XIRR", UNSUPPORTED_FUNCTION);
        FNS.put("CONVERT", UNSUPPORTED_FUNCTION);
        FNS.put("SERIESSUM", UNSUPPORTED_FUNCTION);
        FNS.put("AVERAGEIF", UNSUPPORTED_FUNCTION);
        FNS.put("AVERAGEIFS", UNSUPPORTED_FUNCTION);
        FNS.put("COUNTIFS", UNSUPPORTED_FUNCTION);
        FNS.put("BAHTTEXT", UNSUPPORTED_FUNCTION);
        FNS.put("PHONETIC", UNSUPPORTED_FUNCTION);
        FNS.put("JIS", UNSUPPORTED_FUNCTION);
        instance = new ZKUDFFinder();
    }
}
