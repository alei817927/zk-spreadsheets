//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zpoiex.util;

import java.math.BigDecimal;

public class Objects {
    public static final Double ZERO_DOUBLE = new Double(0.0D);

    public Objects() {
    }

    public static final boolean equals(Object a, Object b) {
        if(a != b && (a == null || b == null || !a.equals(b))) {
            if(a instanceof BigDecimal && b instanceof BigDecimal) {
                return ((BigDecimal)a).compareTo((BigDecimal)b) == 0;
            } else if(a != null && a.getClass().isArray()) {
                int j;
                if(a instanceof Object[] && b instanceof Object[]) {
                    Object[] var16 = (Object[])((Object[])a);
                    Object[] var20 = (Object[])((Object[])b);
                    if(var16.length != var20.length) {
                        return false;
                    } else {
                        j = var16.length;

                        do {
                            --j;
                            if(j < 0) {
                                return true;
                            }
                        } while(equals(var16[j], var20[j]));

                        return false;
                    }
                } else if(a instanceof int[] && b instanceof int[]) {
                    int[] var14 = (int[])((int[])a);
                    int[] var19 = (int[])((int[])b);
                    if(var14.length != var19.length) {
                        return false;
                    } else {
                        j = var14.length;

                        do {
                            --j;
                            if(j < 0) {
                                return true;
                            }
                        } while(var14[j] == var19[j]);

                        return false;
                    }
                } else if(a instanceof byte[] && b instanceof byte[]) {
                    byte[] var12 = (byte[])((byte[])a);
                    byte[] var18 = (byte[])((byte[])b);
                    if(var12.length != var18.length) {
                        return false;
                    } else {
                        j = var12.length;

                        do {
                            --j;
                            if(j < 0) {
                                return true;
                            }
                        } while(var12[j] == var18[j]);

                        return false;
                    }
                } else if(a instanceof char[] && b instanceof char[]) {
                    char[] var10 = (char[])((char[])a);
                    char[] var17 = (char[])((char[])b);
                    if(var10.length != var17.length) {
                        return false;
                    } else {
                        j = var10.length;

                        do {
                            --j;
                            if(j < 0) {
                                return true;
                            }
                        } while(var10[j] == var17[j]);

                        return false;
                    }
                } else if(a instanceof long[] && b instanceof long[]) {
                    long[] var8 = (long[])((long[])a);
                    long[] var15 = (long[])((long[])b);
                    if(var8.length != var15.length) {
                        return false;
                    } else {
                        j = var8.length;

                        do {
                            --j;
                            if(j < 0) {
                                return true;
                            }
                        } while(var8[j] == var15[j]);

                        return false;
                    }
                } else if(a instanceof short[] && b instanceof short[]) {
                    short[] var7 = (short[])((short[])a);
                    short[] var13 = (short[])((short[])b);
                    if(var7.length != var13.length) {
                        return false;
                    } else {
                        j = var7.length;

                        do {
                            --j;
                            if(j < 0) {
                                return true;
                            }
                        } while(var7[j] == var13[j]);

                        return false;
                    }
                } else if(a instanceof double[] && b instanceof double[]) {
                    double[] var6 = (double[])((double[])a);
                    double[] var11 = (double[])((double[])b);
                    if(var6.length != var11.length) {
                        return false;
                    } else {
                        j = var6.length;

                        do {
                            --j;
                            if(j < 0) {
                                return true;
                            }
                        } while(Double.compare(var6[j], var11[j]) == 0);

                        return false;
                    }
                } else if(a instanceof float[] && b instanceof float[]) {
                    float[] var5 = (float[])((float[])a);
                    float[] var9 = (float[])((float[])b);
                    if(var5.length != var9.length) {
                        return false;
                    } else {
                        j = var5.length;

                        do {
                            --j;
                            if(j < 0) {
                                return true;
                            }
                        } while(Float.compare(var5[j], var9[j]) == 0);

                        return false;
                    }
                } else if(a instanceof boolean[] && b instanceof boolean[]) {
                    boolean[] as = (boolean[])((boolean[])a);
                    boolean[] bs = (boolean[])((boolean[])b);
                    if(as.length != bs.length) {
                        return false;
                    } else {
                        j = as.length;

                        do {
                            --j;
                            if(j < 0) {
                                return true;
                            }
                        } while(as[j] == bs[j]);

                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
