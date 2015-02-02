//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula.fn;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;
import org.zkoss.poi.ss.formula.eval.BlankEval;
import org.zkoss.poi.ss.formula.eval.ErrorEval;
import org.zkoss.poi.ss.formula.eval.EvaluationException;
import org.zkoss.poi.ss.formula.eval.NumberEval;
import org.zkoss.poi.ss.formula.eval.OperandResolver;
import org.zkoss.poi.ss.formula.eval.StringEval;
import org.zkoss.poi.ss.formula.eval.ValueEval;
import org.zkoss.poi.ss.formula.functions.Function;
import org.zkoss.poi.ss.formula.functions.NumericFunction;
import org.zkoss.poi.ss.formula.functions.TextFunction;
import org.zkoss.poi.ss.formula.functions.TextFunctionHelper;
import org.zkoss.poi.ss.usermodel.DateUtil;
import org.zkoss.poi.ss.usermodel.ZssContext;
import org.zkoss.xel.fn.CommonFns;
import org.zkoss.zss.model.SCell.CellType;
import org.zkoss.zss.model.sys.EngineFactory;
import org.zkoss.zss.model.sys.input.InputEngine;
import org.zkoss.zss.model.sys.input.InputParseContext;
import org.zkoss.zss.model.sys.input.InputResult;

public class TextFunctionImpl {
    public static final Function CHAR = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            int arg = TextFunctionHelper.evaluateIntArg(args[0], srcRowIndex, srcColumnIndex);
            return new StringEval(Character.toString((char)arg));
        }
    };
    public static final Function CODE = new NumericFunction() {
        protected double eval(ValueEval[] args, int srcCellRow, int srcCellCol) throws EvaluationException {
            String arg = TextFunctionHelper.evaluateStringArg(args[0], srcCellRow, srcCellCol);
            return (double)arg.charAt(0);
        }
    };
    public static final Function FIXED = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            BigDecimal number = new BigDecimal(TextFunctionHelper.evaluateDoubleArg(args[0], srcRowIndex, srcColumnIndex));
            int scale = 2;
            String s = "#,##0";
            if(args.length == 2) {
                scale = TextFunctionHelper.evaluateIntArg(args[1], srcRowIndex, srcColumnIndex);
            } else if(args.length == 3) {
                scale = TextFunctionHelper.evaluateIntArg(args[1], srcRowIndex, srcColumnIndex);
                ValueEval formatText = OperandResolver.getSingleValue(args[2], srcRowIndex, srcColumnIndex);
                Boolean i = OperandResolver.coerceValueToBoolean(formatText, false);
                if(i.booleanValue()) {
                    s = "###";
                }
            }

            StringBuffer var9 = new StringBuffer(s);
            if(scale > 0) {
                var9.append(".");
            }

            for(int var10 = 1; var10 < scale + 1; ++var10) {
                var9.append("0");
            }

            return new StringEval((new DecimalFormat(var9.toString())).format(number.setScale(scale, 4)));
        }
    };
    public static final Function PROPER = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            StringBuffer text = new StringBuffer(TextFunctionHelper.evaluateStringArg(args[0], srcRowIndex, srcColumnIndex));
            text.setCharAt(0, Character.toUpperCase(text.charAt(0)));

            for(int i = 1; i < text.length(); ++i) {
                if(Character.isLetter(text.charAt(i))) {
                    if(!Character.isLetter(text.charAt(i - 1))) {
                        text.setCharAt(i, Character.toUpperCase(text.charAt(i)));
                    } else if(Character.isUpperCase(text.charAt(i))) {
                        text.setCharAt(i, Character.toLowerCase(text.charAt(i)));
                    }
                }
            }

            return new StringEval(text.toString());
        }
    };
    public static final Function REPLACE = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            StringBuffer text = new StringBuffer(TextFunctionHelper.evaluateStringArg(args[0], srcRowIndex, srcColumnIndex));
            int start = TextFunctionHelper.evaluateIntArg(args[1], srcRowIndex, srcColumnIndex) - 1;
            int end = TextFunctionHelper.evaluateIntArg(args[2], srcRowIndex, srcColumnIndex) + start;
            String newChar = TextFunctionHelper.evaluateStringArg(args[0], srcRowIndex, srcColumnIndex);
            return new StringEval(text.replace(start, end, newChar).toString());
        }
    };
    public static final Function REPT = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            String text = TextFunctionHelper.evaluateStringArg(args[0], srcRowIndex, srcColumnIndex);
            String result = "";
            int ntimes = TextFunctionHelper.evaluateIntArg(args[1], srcRowIndex, srcColumnIndex);

            for(int i = 0; i < ntimes; ++i) {
                result = result.concat(text);
            }

            return new StringEval(result);
        }
    };
    public static final Function SUBSTITUTE = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            StringBuffer text = new StringBuffer(TextFunctionHelper.evaluateStringArg(args[0], srcRowIndex, srcColumnIndex));
            String oldText = TextFunctionHelper.evaluateStringArg(args[1], srcRowIndex, srcColumnIndex);
            String newText = TextFunctionHelper.evaluateStringArg(args[2], srcRowIndex, srcColumnIndex);
            int start = text.indexOf(oldText);
            String result = text.toString();
            if(start != -1) {
                int end = start + oldText.length();
                if(args.length == 4) {
                    for(int i = 0; i < CommonFns.toInt(args[3]) - 1; ++i) {
                        start = text.indexOf(oldText, end);
                        end = start + oldText.length();
                    }
                }

                result = text.replace(start, end, newText).toString();
            }

            return new StringEval(result);
        }
    };
    public static final Function LEFTB = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            String text = TextFunctionHelper.evaluateStringArg(args[0], srcRowIndex, srcColumnIndex);
            int num_bytes = TextFunctionHelper.evaluateIntArg(args[1], srcRowIndex, srcColumnIndex);
            if(num_bytes < 0) {
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            } else {
                StringBuilder result = new StringBuilder();
                char[] chars = text.toCharArray();
                int byteCount = 0;

                for(int i = 0; i < chars.length && byteCount != num_bytes; ++i) {
                    if(chars[i] < 256) {
                        ++byteCount;
                        result.append(chars[i]);
                    } else {
                        ++byteCount;
                        if(byteCount == num_bytes) {
                            result.append(" ");
                            break;
                        }

                        ++byteCount;
                        result.append(chars[i]);
                    }
                }

                return new StringEval(result.toString());
            }
        }
    };
    public static final Function REPLACEB = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            String text = TextFunctionHelper.evaluateStringArg(args[0], srcRowIndex, srcColumnIndex);
            int start_num = TextFunctionHelper.evaluateIntArg(args[1], srcRowIndex, srcColumnIndex);
            int num_bytes = TextFunctionHelper.evaluateIntArg(args[2], srcRowIndex, srcColumnIndex);
            String new_text = TextFunctionHelper.evaluateStringArg(args[3], srcRowIndex, srcColumnIndex);
            if(start_num > 0 && num_bytes >= 0) {
                if(text.equals("")) {
                    return new StringEval(new_text);
                } else {
                    StringBuilder result = new StringBuilder();
                    char[] old_chars = text.toCharArray();
                    int byteCount = 0;
                    int startByte = start_num;
                    int endByte = start_num + num_bytes - 1;
                    boolean cutEnd = false;
                    boolean cutStart = false;

                    for(int i = 0; i < old_chars.length; ++i) {
                        ++byteCount;
                        if(old_chars[i] < 256) {
                            if(startByte == byteCount) {
                                result.append(new_text);
                            }
                        } else {
                            if(startByte == byteCount) {
                                result.append(new_text);
                            }

                            if(endByte == byteCount) {
                                if(endByte >= startByte) {
                                    result.append(" ");
                                }

                                cutEnd = true;
                            }

                            ++byteCount;
                            if(startByte == byteCount) {
                                result.append(" ").append(new_text);
                                cutStart = true;
                            }
                        }

                        if(byteCount < startByte) {
                            result.append(old_chars[i]);
                        } else if(byteCount > endByte) {
                            if(cutEnd) {
                                cutEnd = false;
                            } else if(num_bytes != 0) {
                                result.append(old_chars[i]);
                            } else if(num_bytes == 0 && !(cutStart ^ cutEnd)) {
                                result.append(old_chars[i]);
                            }
                        }
                    }

                    return new StringEval(result.toString());
                }
            } else {
                throw new EvaluationException(ErrorEval.VALUE_INVALID);
            }
        }
    };
    public static final Function VALUE = new TextFunction() {
        protected ValueEval evaluateFunc(ValueEval[] args, int srcRowIndex, int srcColumnIndex) throws EvaluationException {
            ValueEval ve = OperandResolver.getSingleValue(args[0], srcRowIndex, srcColumnIndex);
            if(ve instanceof BlankEval) {
                return NumberEval.ZERO;
            } else {
                Locale locale = ZssContext.getCurrent().getLocale();
                InputEngine ie = EngineFactory.getInstance().createInputEngine();
                String editText = OperandResolver.coerceValueToString(ve);
                InputResult result = ie.parseInput(editText, "General", new InputParseContext(locale));
                Object resultVal = result.getValue();
                return (ValueEval)(result.getType() == CellType.NUMBER?(resultVal instanceof Date?new NumberEval(DateUtil.getExcelDate((Date)resultVal, false)):new NumberEval(((Double)resultVal).doubleValue())):(result.getType() == CellType.ERROR?ErrorEval.valueOf(((Byte)resultVal).byteValue()):ErrorEval.VALUE_INVALID));
            }
        }
    };

    public TextFunctionImpl() {
    }
}
