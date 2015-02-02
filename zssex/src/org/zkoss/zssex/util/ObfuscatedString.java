//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

public final class ObfuscatedString {
  private static final String UTF8 = new String(new char[]{'U', 'T', 'F', '8'});
  private final long[] obfuscated;

  public static void main(String[] var0) {
    for(int var1 = 0; var1 < var0.length; ++var1) {
      System.out.println(obfuscate(var0[var1]));
    }

  }

  public static String obfuscate(String var0) {
    if(-1 != var0.indexOf(0)) {
      throw new IllegalArgumentException((new ObfuscatedString(new long[]{2598583114197433456L, -2532951909540716745L, 1850312903926917213L, -7324743161950196342L, 3319654553699491298L})).toString());
    } else {
      byte[] var1;
      try {
        var1 = var0.getBytes(UTF8);
      } catch (UnsupportedEncodingException var12) {
        throw new AssertionError(var12);
      }

      Random var4 = new Random();

      long var2;
      do {
        var2 = var4.nextLong();
      } while(var2 == 0L);

      var4 = new Random(var2);
      StringBuffer var5 = new StringBuffer((new ObfuscatedString(new long[]{-6733388613909857970L, -557652741307719956L, 563088487624542180L, 5623833171491374716L, -2309350771052518321L, 2627844803624578169L})).toString());
      appendHexLiteral(var5, var2);
      int var6 = var1.length;

      for(int var7 = 0; var7 < var6; var7 += 8) {
        long var8 = var4.nextLong();
        long var10 = toLong(var1, var7) ^ var8;
        var5.append(", ");
        appendHexLiteral(var5, var10);
      }

      var5.append((new ObfuscatedString(new long[]{4756003162039514438L, -7241174029104351587L, 2576762727660584163L, 2432800632635846553L})).toString());
      var5.append(var0.replaceAll("\\\\", (new ObfuscatedString(new long[]{7866777055383403009L, -5101749501440392498L})).toString()).replaceAll("\"", (new ObfuscatedString(new long[]{-8797265930671803829L, -5738757606858957305L})).toString()));
      var5.append((new ObfuscatedString(new long[]{-4228881123273879289L, 1823585417647083411L})).toString());
      return var5.toString();
    }
  }

  private static void appendHexLiteral(StringBuffer var0, long var1) {
    var0.append('0');
    var0.append('x');
    var0.append(Long.toHexString(var1).toUpperCase());
    var0.append('L');
  }

  private static long toLong(byte[] var0, int var1) {
    int var2 = Math.min(var0.length, var1 + 8);
    long var3 = 0L;
    int var5 = var2;

    while(true) {
      --var5;
      if(var5 < var1) {
        return var3;
      }

      var3 <<= 8;
      var3 |= (long)(var0[var5] & 255);
    }
  }

  private static void toBytes(long var0, byte[] var2, int var3) {
    int var4 = Math.min(var2.length, var3 + 8);

    for(int var5 = var3; var5 < var4; ++var5) {
      var2[var5] = (byte)((int)var0);
      var0 >>= 8;
    }

  }

  public ObfuscatedString(long[] var1) {
    this.obfuscated = (long[])((long[])var1.clone());
    this.obfuscated[0] = var1[0];
  }

  public String toString() {
    int var1 = this.obfuscated.length;
    byte[] var2 = new byte[8 * (var1 - 1)];
    long var3 = this.obfuscated[0];
    Random var5 = new Random(var3);

    for(int var6 = 1; var6 < var1; ++var6) {
      long var7 = var5.nextLong();
      toBytes(this.obfuscated[var6] ^ var7, var2, 8 * (var6 - 1));
    }

    String var10;
    try {
      var10 = new String(var2, UTF8);
    } catch (UnsupportedEncodingException var9) {
      throw new AssertionError(var9);
    }

    int var11 = var10.indexOf(0);
    return -1 == var11?var10:var10.substring(0, var11);
  }
}
