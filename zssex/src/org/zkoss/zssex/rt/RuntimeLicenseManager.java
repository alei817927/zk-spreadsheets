//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.rt;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.zkoss.util.Cleanups;
import org.zkoss.util.Dates;
import org.zkoss.util.Cleanups.Cleanup;
import org.zkoss.util.logging.Log;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zssex.license.LicenseContent;
import org.zkoss.zssex.license.LicenseManager;
import org.zkoss.zssex.license.LicenseParam;
import org.zkoss.zssex.rt.Refresh;
import org.zkoss.zssex.rt.Runtime;
import org.zkoss.zssex.util.ObfuscatedString;

public final class RuntimeLicenseManager extends LicenseManager {
  private static final String ZK_FRAMEWORK = (new ObfuscatedString(new long[]{1155035670506610891L, -6277540554980205858L, 1737211322686313071L})).toString();
  private final Object _lock = new Object();
  private volatile Timer _timer;
  private volatile WebApp _wapp;
  private List _contents;
  private volatile String _dirName;
  private volatile long _latest;
  private final Refresh _refresh;
  private SimpleDateFormat _dateFormat;
  private static int HOUR = 3600000;
  private volatile boolean _inJarLicense;

  private RuntimeLicenseManager(LicenseParam var1, Refresh var2) {
    super(var1);
    this._dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
    this._refresh = var2;
  }

  public boolean install(String var1) {
    File var2 = new File(var1);
    if(!var2.isDirectory()) {
      return false;
    } else {
      File[] var3 = var2.listFiles();
      this._dirName = var1;
      this.install0(var3);
      return true;
    }
  }

  LicenseContent install(byte[] var1) throws Exception {
    return this.install(var1, this.getLicenseNotary());
  }

  public static void info(String var0) {
    var0 = "\n" + var0 + "\n";
    Log var1 = Log.lookup("global");
    if(var1.infoable()) {
      var1.info(var0);
    } else {
      System.out.println(var0);
    }

  }

  public static void log(String var0) {
    var0 = "\n" + var0 + "\n";
    Log var1 = Log.lookup("root");
    if(var1.errorable()) {
      var1.error(var0);
    } else {
      System.err.println(var0);
    }

  }

  public boolean install(byte[] var1, String var2) {
    this._dirName = var2;
    Object var3 = this._lock;
    synchronized(this._lock) {
      this._contents = new ArrayList(1);
      HashMap var4 = new HashMap();

      try {
        LicenseContent var5 = this.install((byte[])var1);
        if(var5 != null) {
          boolean var6 = false;
          Object var7 = var5.getExtra();
          if(!(var7 instanceof Map)) {
            log(Runtime.WARNING_INVALID_FILE + var2);
            var6 = true;
          }

          Map var8 = (Map)var7;
          if(!this._refresh.isTargetSubject(var8)) {
            var6 = true;
          }

          String var9 = (String)var8.get(Runtime.VERIFICATION_NUMBER);
          if(var9 == null || var4.containsKey(var9)) {
            log(var8.get(Runtime.WARNING_NUMBER) + var2);
            var6 = true;
          }

          var4.put(var9, Boolean.TRUE);
          if(!this._refresh.checkPackage(var8)) {
            log(var8.get(Runtime.WARNING_PACKAGE) + var2);
            var6 = true;
          }

          if(!this._refresh.checkVersion(var8)) {
            log(var8.get(Runtime.WARNING_VERSION) + var2);
            var6 = true;
          }

          if(Dates.today().after(this.getExpiryDate(var5))) {
            log(var8.get(Runtime.WARNING_EXPIRY) + var2);
            var6 = true;
          }

          if(!var6) {
            this._contents.add(var5);
          }
        }
      } catch (Exception var11) {
        log((new ObfuscatedString(new long[]{4307748939090133289L, 3763454470649753422L, -6872730590590579063L, -4639536792232415440L, -5784189857893158816L, 7307901420180268778L, -627643376646394353L, -7420280774293424959L, 2656306477204072694L, -2499488659900337288L, -8059070027533673028L, 7471492367409620715L, 822853263747496262L, -2451111032939294087L, 5181597043837345221L, -5675751368559367806L, 5049843703446154799L, 9141315134085574909L, -6165825001738499911L, -8027923838117731411L, -6842157638029734249L})).toString());
      }

      if(this._refresh != null) {
        this._refresh.refresh(this._contents);
      }

      if(!this._contents.isEmpty() && var1 != null && var1.length != 0) {
        this._inJarLicense = true;
      } else {
        log((new ObfuscatedString(new long[]{-6899918244677316866L, 2946753050424552565L, -1166500401249160465L, -6361152391287683152L, -1012658505712928448L, 2102941868302252760L, 3901913952577343105L, 1861727708660677834L, -3196033244540681827L, -1179475304557705732L, -8292713655681148352L, 1846152504906808191L, -5915587415450046371L, 7423933818392029259L, -8624164244204572166L, -1535967842794712539L, 8072868225759836711L, -3862751912709119543L})).toString());
      }

      return true;
    }
  }

  private void install0(File[] var1) {
    Object var2 = this._lock;
    synchronized(this._lock) {
      this._contents = new ArrayList(var1.length);
      HashMap var4 = new HashMap();
      this._latest = 0L;

      for(int var5 = 0; var5 < var1.length; ++var5) {
        File var6 = var1[var5];
        this._latest += var6.lastModified() + var6.length();

        try {
          LicenseContent var7 = this.install((File)var6);
          if(var7 != null) {
            Object var8 = var7.getExtra();
            if(!(var8 instanceof Map)) {
              log(Runtime.WARNING_INVALID_FILE + var6.getPath());
            } else {
              Map var9 = (Map)var8;
              if(this._refresh.isTargetSubject(var9)) {
                String var10 = (String)var9.get(Runtime.VERIFICATION_NUMBER);
                if(var10 != null && !var4.containsKey(var10)) {
                  var4.put(var10, Boolean.TRUE);
                  if(!this._refresh.checkPackage(var9)) {
                    log(var9.get(Runtime.WARNING_PACKAGE) + var6.getPath());
                  } else if(!this._refresh.checkVersion(var9)) {
                    log(var9.get(Runtime.WARNING_VERSION) + var6.getPath());
                  } else if(Dates.today().after(this.getExpiryDate(var7))) {
                    log(var9.get(Runtime.WARNING_EXPIRY) + var6.getPath());
                  } else {
                    this._contents.add(var7);
                  }
                } else {
                  log(var9.get(Runtime.WARNING_NUMBER) + var6.getPath());
                }
              }
            }
          }
        } catch (Exception var12) {
          log((new ObfuscatedString(new long[]{4307748939090133289L, 3763454470649753422L, -6872730590590579063L, -4639536792232415440L, -5784189857893158816L, 7307901420180268778L, -627643376646394353L, -7420280774293424959L, 2656306477204072694L, -2499488659900337288L, -8059070027533673028L, 7471492367409620715L, 822853263747496262L, -2451111032939294087L, 5181597043837345221L, -5675751368559367806L, 5049843703446154799L, 9141315134085574909L, -6165825001738499911L, -8027923838117731411L, -6842157638029734249L})).toString() + var1[var5].getPath());
        }
      }

      this._latest *= (long)var1.length;
      if(this._refresh != null) {
        this._refresh.refresh(this._contents);
      }

      if(this._contents.isEmpty() && this._latest == 0L) {
        log((new ObfuscatedString(new long[]{-6899918244677316866L, 2946753050424552565L, -1166500401249160465L, -6361152391287683152L, -1012658505712928448L, 2102941868302252760L, 3901913952577343105L, 1861727708660677834L, -3196033244540681827L, -1179475304557705732L, -8292713655681148352L, 1846152504906808191L, -5915587415450046371L, 7423933818392029259L, -8624164244204572166L, -1535967842794712539L, 8072868225759836711L, -3862751912709119543L})).toString());
      }

    }
  }

  public long getDelay() {
    Object var1 = this._lock;
    synchronized(this._lock) {
      if(this._contents.isEmpty()) {
        return (long)HOUR;
      } else {
        int var2 = 0;

        for(int var3 = 0; var3 < this._contents.size(); ++var3) {
          int var4 = ((Integer)((Map)((LicenseContent)this._contents.get(var3)).getExtra()).get(Runtime.CHECK_PERIOD)).intValue();
          if(var2 == 0) {
            var2 = var4;
          } else if(var2 > var4) {
            var2 = var4;
          }
        }

        return (long)(var2 * HOUR);
      }
    }
  }

  public void startScheduler() {
    if(this._timer == null) {
      Object var1 = this._lock;
      synchronized(this._lock) {
        if(this._timer == null) {
          this._timer = new Timer();
          Cleanups.add(new Cleanup() {
            public void cleanup() {
              RuntimeLicenseManager.this.stopScheduler();
            }
          });
        }
      }

      this.check();
    }
  }

  public void stopScheduler() {
    Object var1 = this._lock;
    synchronized(this._lock) {
      if(this._timer != null) {
        this._timer.cancel();
        this._timer = null;
      }

    }
  }

  private LicenseContent getMaximum() {
    LicenseContent var1 = null;
    Object var2 = this._lock;
    synchronized(this._lock) {
      for(int var3 = 0; var3 < this._contents.size(); ++var3) {
        LicenseContent var4 = (LicenseContent)this._contents.get(var3);
        if(var1 == null || this.getExpiryDate(var1).before(this.getExpiryDate(var4))) {
          var1 = var4;
        }
      }

      return var1;
    }
  }

  private Date getNotBefore(LicenseContent var1) {
    try {
      return this._dateFormat.parse((String)((Map)var1.getExtra()).get(Runtime.ISSUE_DATE));
    } catch (ParseException var3) {
      return null;
    }
  }

  private Date getExpiryDate(LicenseContent var1) {
    try {
      return this._dateFormat.parse((String)((Map)var1.getExtra()).get(Runtime.EXPIRY_DATE));
    } catch (ParseException var3) {
      return null;
    }
  }

  private void checkLatest() throws FileNotFoundException {
    if(!this._inJarLicense) {
      File[] var1 = (new File(this._dirName)).listFiles();
      if(var1 == null) {
        throw new FileNotFoundException();
      } else {
        long var2 = 0L;

        for(int var4 = 0; var4 < var1.length; ++var4) {
          File var5 = var1[var4];
          var2 += var5.lastModified() + var5.length();
        }

        var2 *= (long)var1.length;
        LicenseContent var6 = this.getMaximum();
        if(this._latest != var2 || var6 != null && Dates.today().after(this.getExpiryDate(var6))) {
          this.install0(var1);
        }

      }
    }
  }

  private void check() {
    try {
      this.checkLatest();
      if(this._timer != null) {
        this._timer.schedule(new TimerTask() {
          public void run() {
            RuntimeLicenseManager.this.check();
          }
        }, this.getDelay());
      }
    } catch (FileNotFoundException var2) {
      log(Runtime.EVAL_ONLY + ". " + Runtime.WARNING_EVALUATION);
      if(this._wapp != null) {
        this._wapp.setAttribute(Runtime.ZK_NOTICE, Runtime.getEvalNotice(this._wapp));
      }

      this.stopScheduler();
    }

  }

  public static RuntimeLicenseManager getInstance(LicenseParam var0) {
    return new RuntimeLicenseManager(var0, new Refresh() {
      public Object refresh(List var1) {
        this.printInfo(var1);
        return null;
      }

      public boolean checkPackage(Map var1) {
        return this.isValidPackage(this.getPackage(), (String)var1.get(Runtime.PACKAGE));
      }

      public boolean checkVersion(Map var1) {
        String var2 = (String)((String)var1.get(Runtime.VERSION));
        return var2 == null || "3.5.0".startsWith(var2);
      }

      private boolean isValidPackage(String var1, String var2) {
        return var2 == null?false:(!var1.equals(var2) && var2.equals("EP")?var1.equals("PP"):false);
      }

      private String getPackage() {
        return WebApps.getFeature("ee")?"EP":"PP";
      }

      public boolean isTargetSubject(Map var1) {
        String var2 = (String)var1.get(Runtime.LICENSE_VERSION);
        if("1.0".equals(var2)) {
          return true;
        } else {
          String var3 = (String)var1.get(Runtime.LICENSE_SUBJECT);
          return RuntimeLicenseManager.ZK_FRAMEWORK.equals(var3);
        }
      }

      private void printInfo(List var1) {
        for(int var2 = 0; var2 < var1.size(); ++var2) {
          LicenseContent var3 = (LicenseContent)var1.get(var2);
          Object var4 = var3.getExtra();
          if(var4 instanceof Map) {
            Map var5 = (Map)var4;
            if(this.isTargetSubject(var5)) {
              RuntimeLicenseManager.log((String)((Map)var3.getExtra()).get(Runtime.INFORMATION));
            }
          }
        }

      }
    });
  }

  public static RuntimeLicenseManager getInstance(LicenseParam var0, Refresh var1) {
    return new RuntimeLicenseManager(var0, var1);
  }

  public void setWapp(WebApp var1) {
    this._wapp = var1;
  }
}
