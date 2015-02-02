//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula;

import org.zkoss.poi.ss.formula.DependencyTracker;
import org.zkoss.poi.ss.formula.OperationEvaluationContext;
import org.zkoss.poi.ss.formula.UserDefinedFunction;
import org.zkoss.poi.ss.formula.eval.NameEval;
import org.zkoss.poi.ss.formula.eval.NotImplementedException;
import org.zkoss.poi.ss.formula.eval.StringEval;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.poi.ss.formula.ptg.Ptg;
import org.zkoss.util.logging.Log;
import org.zkoss.zssex.formula.FunctionResolverEx;

public class DependencyTrackerEx implements DependencyTracker {
    private static final Log logger = Log.lookup(DependencyTrackerEx.class.getName());

    public DependencyTrackerEx() {
    }

    public ValueEval postProcessValueEval(OperationEvaluationContext ec, ValueEval opResult, boolean eval) {
        ValueEval opResultX = opResult;
        if(eval && opResult instanceof NameEval) {
            String name = ((NameEval)opResult).getFunctionName();

            try {
                opResultX = UserDefinedFunction.instance.evaluate(new ValueEval[]{new NameEval(FunctionResolverEx.EL_FUNCTION_KEY), new StringEval("${" + name + "}")}, ec);
            } catch (NotImplementedException var7) {
                logger.info(var7.getMessage(), var7);
            }
        }

        return opResultX;
    }

    public void addDependency(OperationEvaluationContext ec, Ptg[] ptgs) {
    }

    @Override
    public void clearIndirectRefPrecedent(OperationEvaluationContext ec) {

    }

    @Override
    public void setIndirectRefPrecedent(OperationEvaluationContext ec, ValueEval precedent) {

    }
}
