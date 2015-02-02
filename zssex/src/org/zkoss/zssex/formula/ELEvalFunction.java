//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import org.zkoss.poi.ss.formula.OperationEvaluationContext;
import org.zkoss.poi.ss.formula.eval.BoolEval;
import org.zkoss.poi.ss.formula.eval.ErrorEval;
import org.zkoss.poi.ss.formula.eval.EvaluationException;
import org.zkoss.poi.ss.formula.eval.NotImplementedException;
import org.zkoss.poi.ss.formula.eval.NumberEval;
import org.zkoss.poi.ss.formula.eval.OperandResolver;
import org.zkoss.poi.ss.formula.eval.StringEval;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.poi.ss.formula.functions.FreeRefFunction;
import org.zkoss.poi.ss.formula.functions.Function;
import org.zkoss.poi.ss.usermodel.DateUtil;
import org.zkoss.util.logging.Log;
import org.zkoss.xel.XelContext;
import org.zkoss.zssex.formula.XelContextHolder;

public class ELEvalFunction implements Function, FreeRefFunction {
    private final String _functionName;
    private static final Log logger = Log.lookup(ELEvalFunction.class.getName());

    public ELEvalFunction(String name) {
        this._functionName = name;
    }

    public boolean hasFunction() {
        XelContext ctx = XelContextHolder.getXelContext();
        if(ctx != null) {
            org.zkoss.xel.Function fn = ctx.getFunctionMapper().resolveFunction("zss", this._functionName);
            return fn != null;
        } else {
            return false;
        }
    }

    public ValueEval evaluate(ValueEval[] args, int srcRowIndex, int srcColumnIndex) {
        XelContext ctx = XelContextHolder.getXelContext();
        if(ctx == null) {
            throw new NotImplementedException(this._functionName + ", not in zk context");
        } else {
            org.zkoss.xel.Function fn = ctx.getFunctionMapper().resolveFunction("zss", this._functionName);
            if(fn != null) {
                Class retType = fn.getReturnType();
                Class[] paramTypes = fn.getParameterTypes();

                try {
                    if(ValueEval.class.isAssignableFrom(retType) && paramTypes.length == 3 && ValueEval[].class.isAssignableFrom(paramTypes[0]) && Integer.TYPE.equals(paramTypes[1]) && Integer.TYPE.equals(paramTypes[2])) {
                        return (ValueEval)fn.invoke((Object)null, new Object[]{args, Integer.valueOf(srcRowIndex), Integer.valueOf(srcColumnIndex)});
                    } else {
                        Object[] e = new Object[args.length];

                        for(int var14 = 0; var14 < args.length; ++var14) {
                            Class paramType = paramTypes[var14];
                            ValueEval ev = OperandResolver.getSingleValue(args[var14], srcRowIndex, srcColumnIndex);
                            Object param = this.coerceToObject(paramType, ev);
                            e[var14] = param;
                        }

                        Object var15 = fn.invoke((Object)null, e);
                        return this.coerceToValueEval(retType, var15);
                    }
                } catch (Exception var13) {
                    if(var13 instanceof EvaluationException) {
                        return ((EvaluationException)var13).getErrorEval();
                    } else if(var13 instanceof InvocationTargetException) {
                        Throwable te = ((InvocationTargetException)var13).getTargetException();
                        if(te instanceof EvaluationException) {
                            return ((EvaluationException)te).getErrorEval();
                        } else {
                            logger.warning("cannot invoke " + this._functionName + " for " + te);
                            return ErrorEval.VALUE_INVALID;
                        }
                    } else {
                        return ErrorEval.VALUE_INVALID;
                    }
                }
            } else {
                return ErrorEval.NAME_INVALID;
            }
        }
    }

    private Object coerceToObject(Class<?> type, ValueEval value) throws EvaluationException {
        if(ValueEval.class.isAssignableFrom(type)) {
            return value;
        } else if(String.class.isAssignableFrom(type)) {
            return OperandResolver.coerceValueToString(value);
        } else if(!Integer.TYPE.equals(type) && !Integer.class.isAssignableFrom(type)) {
            if(!Short.TYPE.equals(type) && !Short.class.isAssignableFrom(type)) {
                if(!Long.TYPE.equals(type) && !Long.class.isAssignableFrom(type)) {
                    if(!Byte.TYPE.equals(type) && !Byte.class.isAssignableFrom(type)) {
                        if(!Double.TYPE.equals(type) && !Double.class.isAssignableFrom(type)) {
                            if(!Float.TYPE.equals(type) && !Float.class.isAssignableFrom(type)) {
                                if(BigDecimal.class.isAssignableFrom(type)) {
                                    return new BigDecimal(OperandResolver.coerceValueToDouble(value));
                                } else if(BigInteger.class.isAssignableFrom(type)) {
                                    return new BigInteger("" + OperandResolver.coerceValueToLong(value));
                                } else if(Date.class.isAssignableFrom(type)) {
                                    return DateUtil.getJavaDate(OperandResolver.coerceValueToDouble(value));
                                } else {
                                    throw new EvaluationException(ErrorEval.VALUE_INVALID);
                                }
                            } else {
                                return new Float(OperandResolver.coerceValueToDouble(value));
                            }
                        } else {
                            return new Double(OperandResolver.coerceValueToDouble(value));
                        }
                    } else {
                        return new Byte((byte)OperandResolver.coerceValueToInt(value));
                    }
                } else {
                    return new Long(OperandResolver.coerceValueToLong(value));
                }
            } else {
                return new Short((short)OperandResolver.coerceValueToInt(value));
            }
        } else {
            return new Integer(OperandResolver.coerceValueToInt(value));
        }
    }

    private ValueEval coerceToValueEval(Class<?> type, Object value) throws EvaluationException {
        if(String.class.isAssignableFrom(type)) {
            return new StringEval((String)value);
        } else if(!Number.class.isAssignableFrom(type) && !Integer.TYPE.equals(type) && !Double.TYPE.equals(type) && !Float.TYPE.equals(type) && !Long.TYPE.equals(type) && !Short.TYPE.equals(type) && !Byte.TYPE.equals(type)) {
            if(Boolean.class.isAssignableFrom(type)) {
                return BoolEval.valueOf(((Boolean)value).booleanValue());
            } else if(value instanceof ValueEval) {
                return (ValueEval)value;
            } else {
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            }
        } else {
            return new NumberEval(((Number)value).doubleValue());
        }
    }

    public ValueEval evaluate(ValueEval[] args, OperationEvaluationContext ec) {
        return this.evaluate(args, ec.getRowIndex(), ec.getColumnIndex());
    }
}
