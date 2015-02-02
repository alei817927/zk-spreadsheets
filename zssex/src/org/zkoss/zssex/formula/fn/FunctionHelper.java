//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula.fn;

import java.util.Locale;
import org.zkoss.poi.ss.format.Formatters;
import org.zkoss.poi.ss.usermodel.ZssContext;
import org.zkoss.zss.model.impl.sys.DateInputMask;

public final class FunctionHelper {
    public FunctionHelper() {
    }

    public static Object[] parseToDate(String txt) {
        ZssContext ctx = ZssContext.getCurrent();
        int twoDigitYearUpperBound = ctx.getTwoDigitYearUpperBound();
        Locale locale = ctx.getLocale();
        return (new DateInputMask()).parseDateInput(txt, locale);
    }

    private static boolean isStringFormat(String formatStr) {
        return "@".equals(formatStr);
    }

    public static Object[] editTextToValue(String txt, String formatStr) {
        if(txt != null) {
            if(formatStr != null && isStringFormat(formatStr)) {
                return new Object[]{new Integer(1), txt};
            } else if(txt.startsWith("=")) {
                return txt.trim().length() > 1?new Object[]{new Integer(2), txt.substring(1)}:new Object[]{new Integer(1), txt};
            } else if(!"true".equalsIgnoreCase(txt) && !"false".equalsIgnoreCase(txt)) {
                if(txt.startsWith("#")) {
                    byte err = getErrorCode(txt);
                    return err < 0?new Object[]{Integer.valueOf(1), txt}:new Object[]{Integer.valueOf(5), new Byte(err)};
                } else {
                    return parseEditTextToDoubleDateOrString(txt);
                }
            } else {
                return new Object[]{new Integer(4), Boolean.valueOf(txt)};
            }
        } else {
            return null;
        }
    }

    private static Object[] parseEditTextToDoubleDateOrString(String txt) {
        Locale locale = ZssContext.getCurrent().getLocale();
        char dot = Formatters.getDecimalSeparator(locale);
        char comma = Formatters.getGroupingSeparator(locale);
        String txt0 = txt;
        if(dot != 46 || comma != 44) {
            int ex = txt.lastIndexOf(dot);
            txt0 = txt.replace(comma, ',');
            if(ex >= 0) {
                txt0 = txt0.substring(0, ex) + '.' + txt0.substring(ex + 1);
            }
        }

        try {
            Double ex1 = Double.valueOf(Double.parseDouble(txt0));
            return new Object[]{new Integer(0), ex1};
        } catch (NumberFormatException var6) {
            return parseEditTextToDateOrString(txt);
        }
    }

    private static Object[] parseEditTextToDateOrString(String txt) {
        Object[] results = parseToDate(txt);
        return results[0] instanceof String?new Object[]{new Integer(1), results[0]}:new Object[]{Integer.valueOf(0), results[0], results[1]};
    }

    private static byte getErrorCode(String errString) {
        return (byte)("#NULL!".equals(errString)?0:("#DIV/0!".equals(errString)?7:("#VALUE!".equals(errString)?15:("#REF!".equals(errString)?23:("#NAME?".equals(errString)?29:("#NUM!".equals(errString)?36:("#N/A".equals(errString)?42:-1)))))));
    }
}
