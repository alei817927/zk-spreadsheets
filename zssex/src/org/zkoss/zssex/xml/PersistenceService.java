//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.xml;

import java.beans.Encoder;
import java.beans.ExceptionListener;
import java.beans.PersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.zkoss.zssex.xml.PersistenceServiceException;
import org.zkoss.zssex.xml.XMLConstants;

public class PersistenceService implements XMLConstants {
  private static final HashMap allPDs = new HashMap();
  public static int BUFSIZE = 10240;

  private static final ExceptionListener createExceptionListener() {
    return new ExceptionListener() {
      public void exceptionThrown(Exception var1) {
        throw var1 instanceof UndeclaredThrowableException?(UndeclaredThrowableException)var1:new UndeclaredThrowableException(var1);
      }
    };
  }

  public static final synchronized void setPersistenceDelegate(Class var0, PersistenceDelegate var1) {
    allPDs.put(var0, var1);
  }

  protected static synchronized void installPersistenceDelegates(Encoder var0) {
    Iterator var1 = allPDs.entrySet().iterator();

    while(var1.hasNext()) {
      Entry var2 = (Entry)var1.next();
      var0.setPersistenceDelegate((Class)var2.getKey(), (PersistenceDelegate)var2.getValue());
    }

  }

  public static void store(Object var0, OutputStream var1) throws NullPointerException, PersistenceServiceException {
    if(var1 == null) {
      throw new NullPointerException();
    } else {
      try {
        BufferedOutputStream var2 = null;
        XMLEncoder var3 = null;

        try {
          var2 = new BufferedOutputStream(var1, BUFSIZE);
          var3 = new XMLEncoder(var2);
          installPersistenceDelegates(var3);
          var3.setExceptionListener(createExceptionListener());
          if(var0 != null) {
            synchronized(var0) {
              var3.writeObject(var0);
            }
          } else {
            var3.writeObject(var0);
          }
        } finally {
          if(var3 != null) {
            try {
              var3.close();
            } catch (Throwable var14) {
              var2.close();
              throw var14;
            }
          } else if(var2 != null) {
            var2.close();
          } else {
            var1.close();
          }

        }

      } catch (UndeclaredThrowableException var17) {
        throw new PersistenceServiceException(var17.getCause());
      } catch (Throwable var18) {
        throw new PersistenceServiceException(var18);
      }
    }
  }

  public static void store(Object var0, File var1) throws NullPointerException, PersistenceServiceException {
    if(var1 == null) {
      throw new NullPointerException();
    } else {
      File var2 = null;
      boolean var3 = false;

      try {
        var2 = getRenamedFile(var1);
        var3 = var1.renameTo(var2);
        store(var0, (OutputStream)(new FileOutputStream(var1)));
        if(var3) {
          var2.delete();
        }

      } catch (Throwable var8) {
        Throwable var4 = var8;
        if(var3) {
          try {
            var1.delete();
          } catch (Throwable var7) {
            var4 = var7;
          }

          try {
            var2.renameTo(var1);
          } catch (Throwable var6) {
            var4 = var6;
          }
        }

        throw var4 instanceof PersistenceServiceException?(PersistenceServiceException)var4:new PersistenceServiceException(var4);
      }
    }
  }

  private static File getRenamedFile(File var0) {
    String var1 = var0.getPath();

    File var2;
    do {
      var1 = var1 + '~';
      var2 = new File(var1);
    } while(var2.exists());

    return var2;
  }

  public static byte[] store2ByteArray(Object var0) throws PersistenceServiceException {
    try {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      store(var0, (OutputStream)var1);
      return var1.toByteArray();
    } catch (PersistenceServiceException var2) {
      throw var2;
    } catch (Throwable var3) {
      throw new PersistenceServiceException(var3);
    }
  }

  public static String store2String(Object var0) throws PersistenceServiceException {
    try {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      store(var0, (OutputStream)var1);
      return var1.toString("UTF-8");
    } catch (UnsupportedEncodingException var2) {
      throw new AssertionError(var2);
    } catch (PersistenceServiceException var3) {
      throw var3;
    } catch (Throwable var4) {
      throw new PersistenceServiceException(var4);
    }
  }

  public static Object load(InputStream var0) throws NullPointerException, PersistenceServiceException {
    if(var0 == null) {
      throw new NullPointerException();
    } else {
      XMLDecoder var1 = null;

      Object var2;
      try {
        var1 = new XMLDecoder(new BufferedInputStream(var0, BUFSIZE), (Object)null, createExceptionListener());
        var2 = var1.readObject();
      } catch (UndeclaredThrowableException var12) {
        throw new PersistenceServiceException(var12.getCause());
      } catch (Throwable var13) {
        throw new PersistenceServiceException(var13);
      } finally {
        if(var1 != null) {
          try {
            var1.close();
          } catch (Throwable var11) {
            throw new PersistenceServiceException(var11);
          }
        }

      }

      return var2;
    }
  }

  public static Object load(File var0) throws NullPointerException, PersistenceServiceException {
    if(var0 == null) {
      throw new NullPointerException();
    } else {
      try {
        return load((InputStream)(new FileInputStream(var0)));
      } catch (PersistenceServiceException var2) {
        throw var2;
      } catch (Throwable var3) {
        throw new PersistenceServiceException(var3);
      }
    }
  }

  public static Object load(byte[] var0) throws NullPointerException, PersistenceServiceException {
    if(var0 == null) {
      throw new NullPointerException();
    } else {
      try {
        return load((InputStream)(new ByteArrayInputStream(var0)));
      } catch (PersistenceServiceException var2) {
        throw var2;
      } catch (Throwable var3) {
        throw new PersistenceServiceException(var3);
      }
    }
  }

  public static Object load(String var0) throws NullPointerException, PersistenceServiceException {
    if(var0 == null) {
      throw new NullPointerException();
    } else {
      try {
        return load((byte[])var0.getBytes("UTF-8"));
      } catch (UnsupportedEncodingException var2) {
        throw new AssertionError(var2);
      } catch (PersistenceServiceException var3) {
        throw var3;
      } catch (Throwable var4) {
        throw new PersistenceServiceException(var4);
      }
    }
  }

  protected PersistenceService() {
  }
}
