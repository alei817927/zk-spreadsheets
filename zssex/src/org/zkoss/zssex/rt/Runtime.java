//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.rt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.prefs.Preferences;
import javax.security.auth.x500.X500Principal;
import org.zkoss.io.Files;
import org.zkoss.lang.Library;
import org.zkoss.lang.Strings;
import org.zkoss.util.Dates;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.DesktopCleanup;
import org.zkoss.zk.ui.util.DesktopInit;
import org.zkoss.zk.ui.util.EventInterceptor;
import org.zkoss.zk.ui.util.ExecutionInit;
import org.zkoss.zk.ui.util.SessionCleanup;
import org.zkoss.zk.ui.util.SessionInit;
import org.zkoss.zss.model.SBook;
import org.zkoss.zss.ui.Spreadsheet;
import org.zkoss.zssex.license.CipherParam;
import org.zkoss.zssex.license.KeyStoreParam;
import org.zkoss.zssex.license.LicenseContent;
import org.zkoss.zssex.license.LicenseParam;
import org.zkoss.zssex.rt.Refresh;
import org.zkoss.zssex.rt.RtInfo;
import org.zkoss.zssex.rt.RuntimeInfo;
import org.zkoss.zssex.rt.RuntimeLicenseManager;
import org.zkoss.zssex.util.ObfuscatedString;
import org.zkoss.zul.Window;

public final class Runtime {
  static final String UNIVERSAL_ACTIVE_CODE = (new ObfuscatedString(new long[]{-8406957250436102276L, 7739080853276730995L, 2446544582485916568L, 4141763441450886800L, 9163385588230618037L, 3078723322430394289L})).toString();
  static final String ACTIVE_CODE = (new ObfuscatedString(new long[]{2927261568102559820L, 5003116366233426486L, -19977163214018644L})).toString();
  static final String LICENSE_SUBJECT = (new ObfuscatedString(new long[]{3902422651215371349L, 8624385350820619417L, -642393240400619653L})).toString();
  static final String UP_TIME = (new ObfuscatedString(new long[]{7735875781470822017L, 7449797441543358756L})).toString();
  static final String SESSION_COUNT = (new ObfuscatedString(new long[]{3229559844559651902L, 4507837234696327046L, 7727269190283665611L})).toString();
  static final String USER_NAME = (new ObfuscatedString(new long[]{-7156956517562311570L, -4569457626895068567L, -9130454531809692735L})).toString();
  static final String COMPANY_ID = (new ObfuscatedString(new long[]{-3429614419320347732L, 5284083613633888871L, -2962259370126176133L})).toString();
  static final String COMPANY_UNIT = (new ObfuscatedString(new long[]{4535911707703337843L, -5339041739265159617L, -8006648055853745059L})).toString();
  static final String COMPANY_NAME = (new ObfuscatedString(new long[]{-7340139527016707886L, -5203243759892677212L, 8714822623115369524L})).toString();
  static final String COMPANY_CITY = (new ObfuscatedString(new long[]{-6925611054058810462L, 2143563004714040551L, 1994584055053707741L})).toString();
  static final String COMPANY_ADDRESS = (new ObfuscatedString(new long[]{-8691848786421899489L, -4370996558863340632L, -2709933490946981238L})).toString();
  static final String COMPANY_ZIPCODE = (new ObfuscatedString(new long[]{-4934501068656857753L, -6913828373145765012L, 1063193634233753528L})).toString();
  static final String COUNTRY = (new ObfuscatedString(new long[]{-4504969373906269801L, 5248137891553083335L})).toString();
  static final String PROJECT_NAME = (new ObfuscatedString(new long[]{830623621279886032L, 459360910074040759L, 4178520520701877821L})).toString();
  static final String PRODUCT_NAME = (new ObfuscatedString(new long[]{5504882338648710617L, -4761731334749763195L, 996375918662338065L})).toString();
  static final String PACKAGE = (new ObfuscatedString(new long[]{-8439029564924938530L, -4878278112849633009L})).toString();
  static final String VERSION = (new ObfuscatedString(new long[]{-4847689528984584834L, 2493253216426408014L})).toString();
  static final String ISSUE_DATE = (new ObfuscatedString(new long[]{-4228764154858292882L, -6898004159031332466L, 6328666951570048917L})).toString();
  static final String EXPIRY_DATE = (new ObfuscatedString(new long[]{-7233890858958970371L, -3423973165832030856L, -5810612950970282077L})).toString();
  static final String TERM = (new ObfuscatedString(new long[]{-6725301182108235475L, -8691110408124621856L})).toString();
  static final String VERIFICATION_NUMBER = (new ObfuscatedString(new long[]{3823288740853721680L, -6436340937747658512L, 891079956768101415L, 751513662528431611L})).toString();
  static final String INFORMATION = (new ObfuscatedString(new long[]{378870925371295609L, 1418863983102047429L, -5017007170548372422L})).toString();
  static final String KEY_SIGNATURE = (new ObfuscatedString(new long[]{-2573177027008659676L, 5066716785755217927L, 5769746383701090690L})).toString();
  static final String CHECK_PERIOD = (new ObfuscatedString(new long[]{-2439022525501632135L, 6139476070014855270L, -297657911147084449L})).toString();
  static final String LICENSE_DIRECTORY_PROPERTY = (new ObfuscatedString(new long[]{-1882551029428330572L, -8700125708888889285L, 2264098143846024619L, 8696977543803722267L, -3002719197410454636L, -5542261237218261145L})).toString();
  static final String LICENSE_VERSION = (new ObfuscatedString(new long[]{-7080462743270045357L, 6928867389785115158L, -154565539896996742L})).toString();
  static final String WARNING_EXPIRY = (new ObfuscatedString(new long[]{-2088056424898980973L, -3616911578495445651L, -8353968737700076168L})).toString();
  static final String WARNING_PACKAGE = (new ObfuscatedString(new long[]{7436618834759965309L, 8220698497085578148L, -6394078374620879850L})).toString();
  static final String WARNING_VERSION = (new ObfuscatedString(new long[]{7417971821667979026L, 7464186339852802771L, 7986314911006223431L})).toString();
  static final String WARNING_COUNT = (new ObfuscatedString(new long[]{-1510608780643214737L, 6313704540210276937L, 4115504365890483558L})).toString();
  static final String WARNING_NUMBER = (new ObfuscatedString(new long[]{8367990676393660796L, -7163797910637480555L, -8349581027556623805L})).toString();
  static final String ZK_SPREADSHEET = (new ObfuscatedString(new long[]{-7746426490831650241L, 7328409540860500523L, 3962011379583748974L})).toString();
  public static final String WARNING_EVALUATION = (new ObfuscatedString(new long[]{6293244219968982799L, -645771368638276165L, -7164593841888995318L, 3056658804758229281L, 2012533240900282186L, 4720458417030845876L, -649430094189861973L, -7918770929915836186L, 8851560671444236411L, 5606256511022648529L, 5213202847092904516L, -4102203601017740787L, -1032764338499136803L, 4632877787254627128L, 8652922073494088803L, 1017530493307865386L, 6363365592583820006L, -7614762895336268528L, -6558480611654840415L, -4743081489003502949L, -5862139290226956563L, -4583888483821845908L, -8745462703135464897L, -4873155536263193767L, -3881387260484276693L, 7581140259249343580L, -4703654983362907694L, 3642210020844866491L, -1873892967537094428L, 7431679954734481217L, 3464433087932489116L, -6024453854700727835L, 1210446632208205992L, -9057692879567092531L, 7829162361400083263L, -1875570117283775615L, -380845106451795279L, -1051776431017832137L, 4471385941321627797L, 495484629773446438L, 8597275517967444933L, 4298086683676932604L, 3253256358929010255L, -1508885386650347107L, -2790008182791469194L})).toString();
  public static final String WARNING_INVALID_FILE = (new ObfuscatedString(new long[]{-2705066582748869478L, -1756369395929404428L, 1927453613245088482L, 6002136807036289424L, -6927777686023936085L, 1793080898558536723L, 2768920254900639255L, -8466497755674134272L, 3853433474927584867L, -7035948665085087086L, 4139673979409662216L, 4556685628643930044L, -1505202977284950338L, 4372657461200298826L, 870565935216128439L, 715552231683639969L, -8440532278422812207L, 2275407799776572744L, -8628004182635503887L, 1441870243200925175L, 6760545480909666478L, -6868020178348304123L, -7081375598839201987L, 7578485673433694811L, -2865398942871624260L, 4146894525708705472L, -3083914055986203134L})).toString();
  private static boolean _ck;
  private static final String DEFAULT_LICENSE_DIRECTORY = (new ObfuscatedString(new long[]{-5042984495264000326L, -7075224333823389221L, 2737249336978399967L, -2368002581952540878L})).toString();
  private static final String UTEST = (new ObfuscatedString(new long[]{-6252666169139603543L, -43763135309346748L, -5412364431233735340L, -1342277457322500721L, -3233034723380275725L, 8333057255774856663L, -7004928009668210393L})).toString();
  private static final String ESCHERAGGREGATE_IMPL = (new ObfuscatedString(new long[]{-6126016284039316910L, -5242181267678831515L, 3626005944004206838L, 8355131686565987587L, 1442182626534055123L, 3784556152988695497L, -6440494607607095147L})).toString();
  private static final String PROPRIETARY_GENERIAL_LICENSE_MESSAGE = (new ObfuscatedString(new long[]{8687672675324615030L, 6974276017866410924L, -54408951563328269L, 1919025824881822275L, 4056804593999289924L, 8268887351767894544L, -7907347785639257682L, 5348266878285613458L, 9170251802629670478L, -178155118790286114L, -7436895360513424806L, 7238353810495048571L, 1815967861380757394L, -7245088744669434235L, 7561060452439247619L, 2675581904895234316L, 8910120902489320537L, -8317856655829063406L, -6709112102887734470L, 8084505830623492634L, -8082985207712961046L, 8373082252647841474L, 6920850384363350133L, -4683685184343535582L, 1808955967615490765L, -7245721664619701261L, 3064504328759503159L, 1445713604789928806L, -5734377333902951730L, -7335696427778581172L, 2044984899327533251L, 289908991521128671L, 6043245487464141144L, -3246968454849379990L, 8609249277951659125L, -7542591340226936823L, 8632074547019543920L, 19935619298247111L, -5491527588063712851L, -3415302836971054271L, -4034567936238106941L, -4891666774854318751L, -2017240472327840352L, 3284304887141034121L, 2990661414457933335L, -5038005971647856719L, 2800404947862271021L, 3220419313119528087L, -7285666076420666047L, 6583508232649678598L, -8781627935766011482L, -2572001931590620959L})).toString();
  private static final String PROPRIETARY_GENERIAL_LICENSE_FILE_PATH = (new ObfuscatedString(new long[]{-588626787276688448L, -8082369530139198771L, 7476193481897207895L, 8239467896693800010L, -5277821817479319019L, -1246743107081427441L, -9043650899684883692L})).toString();
  private static final ThreadLocal<Boolean> _pass = new ThreadLocal();
  static final String ZK_NOTICE = (new ObfuscatedString(new long[]{-8347842002405430398L, 3574087500300642980L, -6273607823570371127L, -5524402949239665762L})).toString();
  private static final String ZSS = (new ObfuscatedString(new long[]{-8173181667746542109L, -7543777648745715290L, 1084351454888844032L})).toString();
  static final String EVAL_ONLY = (new ObfuscatedString(new long[]{8330515038476062730L, -435229286498387605L, -3853053404258091660L})).toString();
  private static final String ZSS_EE = (new ObfuscatedString(new long[]{4874696179159791875L, 6828159996905510620L, 8997802664571284853L, 5297845084022046979L})).toString();
  private static final String MD5STR = (new ObfuscatedString(new long[]{-5121064899839768052L, 3334273322282769989L})).toString();
  private static final long[] KEY_SIG_CONST = new long[]{-8824876816566242848L, -406877062005977456L, -3332332678304402562L, -2945723887764541455L};
  private static final String PUB_STORE = (new ObfuscatedString(new long[]{2986000131224251471L, 5740577535729811732L, -1784932956938255785L, -5053419831224178707L})).toString();
  private static final String SUBJECT = (new ObfuscatedString(new long[]{-5414105211624051485L, 5101764520790868466L, 7905655771772154682L, 7706749818695188070L})).toString();
  private static final String KEY_NODE = (new ObfuscatedString(new long[]{3709353003282642825L, -2748448393049160943L, 3173082663019039626L})).toString();
  private static final String V0 = (new ObfuscatedString(new long[]{6780048183396145217L, -3514785424911510459L})).toString();
  private static final String V1 = (new ObfuscatedString(new long[]{-8556922120573852888L, 708543790670807158L})).toString();
  private static final String ALIAS = (new ObfuscatedString(new long[]{-2751357802016299199L, 4066217211348802619L, -2064662869185498634L, 4043793295118034741L, 6227175189710674534L})).toString();
  private static final String STORE_PASS = (new ObfuscatedString(new long[]{-8677088790027852212L, 4602056908258993522L, 8019503246186939872L, -1944004741470673738L, -7033589056015316549L})).toString();
  private static KeyStoreParam _keystoreParam = new KeyStoreParam() {
    public InputStream getStream() throws IOException {
      InputStream var1 = Thread.currentThread().getContextClassLoader().getResourceAsStream(Runtime.PUB_STORE);
      if(var1 == null) {
        throw new FileNotFoundException(Runtime.PUB_STORE);
      } else {
        return var1;
      }
    }

    public String getAlias() {
      return Runtime.ALIAS;
    }

    public String getStorePwd() {
      return Runtime.STORE_PASS;
    }

    public String getKeyPwd() {
      return null;
    }
  };
  private static CipherParam _cipherParam = new CipherParam() {
    public String getKeyPwd() {
      return (new ObfuscatedString(new long[]{-9017617134232705315L, -3067316756544620689L, -7174741455541659722L, 9223059116147577819L, -7389013047307896124L})).toString();
    }
  };
  private static LicenseParam _licenseParam = new LicenseParam() {
    public String getSubject() {
      return Runtime.SUBJECT;
    }

    public Preferences getPreferences() {
      return null;
    }

    public KeyStoreParam getKeyStoreParam() {
      return Runtime._keystoreParam;
    }

    public CipherParam getCipherParam() {
      return Runtime._cipherParam;
    }
  };
  private static final RuntimeLicenseManager _licManager;
  private static WebApp _wapp;

  public Runtime() {
  }

  public static final void token(Object var0, Object var1) {
    token0(var0, var1);
  }

  public static final boolean token(Object var0) {
    return token0(var0);
  }

  private static final boolean token0(Object var0) {
    return var0 instanceof Execution?((Execution)var0).getAttribute(token1(var0)) != null:(var0 instanceof Session?((Session)var0).getAttribute(token1(var0)) != null:var0 == null && ESCHERAGGREGATE_IMPL.equals(Library.getProperty(UTEST)));
  }

  private static final void token0(Object var0, Object var1) {
    if(var0 instanceof Execution) {
      ((Execution)var1).setAttribute(token1(var0), Boolean.TRUE);
    } else if(var0 instanceof Session) {
      ((Session)var1).setAttribute(token1(var0), Boolean.TRUE);
    }

  }

  private static final String token1(Object var0) {
    String var1 = "mgws" + (var0 instanceof Execution?((Execution)var0).getDesktop().getId():(var0 instanceof Desktop?((Desktop)var0).getId():((Session)var0).getRemoteAddr())) + "wysb";
    Execution var2 = Executions.getCurrent();
    if(var2 != null) {
      Desktop var3 = var2.getDesktop();
      MessageDigest var4 = (MessageDigest)var3.getAttribute("md_" + var3.getId());
      if(var4 != null) {
        var4.reset();

        try {
          var4.update(var1.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var7) {
          ;
        }

        byte[] var5 = var4.digest();
        BigInteger var6 = new BigInteger(1, var5);
        return var6.toString(36);
      }
    }

    return var1;
  }

  public static final Object init(Object var0) {
    init0(var0);
    return new RtInfo() {
      public void verify(Execution var1) {
      }

      public void verify(Session var1) {
      }
    };
  }

  private static final void init0(Object var0) {
    if(var0 instanceof WebApp) {
      try {
        ((WebApp)var0).getConfiguration().removeListener(Init.class);
        ((WebApp)var0).getConfiguration().addListener(Init.class);
      } catch (Exception var2) {
        ;
      }
    } else if(var0 instanceof Session) {
      init1().init1((Session)var0, (Object)null);
    }

  }

  private static Init1 init1() {
    return Init1._init1;
  }

  private static final String read(String var0) {
    try {
      InputStream var1 = Runtime.class.getResourceAsStream("/metainfo/zss/" + var0);
      if(var1 != null) {
        return new String(Files.readAll(var1));
      }
    } catch (Throwable var2) {
      ;
    }

    return null;
  }

  public static final boolean init(WebApp var0, boolean var1) {
    boolean var2 = true;
    if(var1 && !_ck) {
      _ck = true;
      var0.setAttribute(ACTIVE_CODE, getActiveCode());

      try {
        var2 = init1(var0);
        var0.getConfiguration().addListener(Init.class);
        var2 = var2 && ((Boolean)_pass.get()).booleanValue();
        _pass.remove();
      } catch (Exception var4) {
        var4.printStackTrace();
        var2 = false;
      }

      if(!var2 && var0 != null) {
        var0.setAttribute(ZK_NOTICE, getEvalNotice(var0));
      }
    }

    return var2;
  }

  static String getEvalNotice(WebApp var0) {
    StringBuffer var1 = new StringBuffer();
    Object var2 = var0.getAttribute(ZK_NOTICE);
    if(var2 != null) {
      var1.append(var2);
    }

    var1.append(" -->\n").append("<!-- ").append(ZSS).append(" ").append("3.5.0").append(" EE ");
    String var3 = read("build");
    if(var3 != null) {
      var1.append(var3.replace('\n', ' '));
    }

    var1.append(EVAL_ONLY);
    return var1.toString();
  }

  private static final String bytestr(byte[] var0) {
    if(var0 != null) {
      StringBuffer var1 = new StringBuffer(var0.length * 2);

      for(int var2 = 0; var2 < var0.length; ++var2) {
        byte var3 = var0[var2];
        String var4 = Integer.toHexString(var3 & 255);
        if(var4.length() < 2) {
          var1.append('0');
        }

        var1.append(var4);
      }

      return var1.toString();
    } else {
      return null;
    }
  }

  private static final String uuidToMD5(long var0, long var2) {
    String var4 = null;

    try {
      Enumeration var5 = NetworkInterface.getNetworkInterfaces();

      while(var5.hasMoreElements()) {
        NetworkInterface var6 = (NetworkInterface)var5.nextElement();
        if(isUp(var6) && !isVirtual(var6)) {
          for(Enumeration var7 = var6.getInetAddresses(); var7.hasMoreElements(); var4 = null) {
            InetAddress var8 = (InetAddress)var7.nextElement();
            var4 = var8.getHostName();
            if(!"localhost".equals(var4)) {
              break;
            }
          }

          if(var4 != null) {
            break;
          }
        }
      }
    } catch (SocketException var21) {
      ;
    }

    try {
      long var22 = -8556922120573852888L;
      long var23 = -7110043422976597898L;
      if(var4 == null) {
        var4 = "ZK Host";
      }

      byte[] var9 = var4.getBytes("UTF-8");
      long var10 = 0L;

      int var12;
      for(var12 = 0; var12 < var9.length && var12 < 8; ++var12) {
        var10 |= ((long)var9[var12] & 255L) << var12 * 8;
      }

      var23 ^= var10;
      if(var9.length > 8) {
        var10 = 0L;
        var12 = 0;

        for(int var13 = var9.length - 8; var12 < var13 && var12 < 8; ++var12) {
          var10 |= ((long)var9[var12 + 8] & 255L) << var12 * 8;
        }

        var22 ^= var10;
      }

      String var24 = (new UUID(var0 ^ var22, var2 ^ var23)).toString();
      MessageDigest var25 = MessageDigest.getInstance(MD5STR);
      if(var25 != null) {
        var25.reset();
        var25.update(var24.getBytes("UTF-8"));
        byte[] var14 = var25.digest();
        BigInteger var15 = new BigInteger(1, var14);
        String var16 = var15.toString(36);
        StringBuffer var17 = new StringBuffer(32);

        for(int var18 = 0; var18 < var16.length(); ++var18) {
          if(var18 > 0 && var18 % 5 == 0) {
            var17.append("-");
          }

          var17.append(var16.charAt(var18));
        }

        return var17.toString().toUpperCase();
      }
    } catch (UnsupportedEncodingException var19) {
      ;
    } catch (NoSuchAlgorithmException var20) {
      ;
    }

    return null;
  }

  private static boolean isUp(NetworkInterface var0) {
    Class[] var1 = new Class[0];

    try {
      Class var2 = Class.forName("java.net.NetworkInterface");
      Method var3 = var2.getDeclaredMethod("isUp", var1);
      return ((Boolean)var3.invoke(var0, (Object[])null)).booleanValue();
    } catch (Exception var4) {
      return false;
    }
  }

  private static boolean isVirtual(NetworkInterface var0) {
    Class[] var1 = new Class[0];

    try {
      Class var2 = Class.forName("java.net.NetworkInterface");
      Method var3 = var2.getDeclaredMethod("isVirtual", var1);
      return ((Boolean)var3.invoke(var0, (Object[])null)).booleanValue();
    } catch (Exception var4) {
      return false;
    }
  }

  private static final String getActiveCode() {
    Preferences var0 = _licenseParam.getPreferences();
    if(var0 != null) {
      long var1 = var0.getLong(V0, 0L);
      long var3 = var0.getLong(V1, 0L);
      if(var1 != 0L || var3 != 0L) {
        return uuidToMD5(var3, var1);
      }

      UUID var5 = UUID.randomUUID();
      long var6 = var5.getMostSignificantBits();
      long var8 = var5.getLeastSignificantBits();
      var0.putLong(V0, var8);
      var0.putLong(V1, var6);
      if(var8 == var0.getLong(V0, 0L) && var6 == var0.getLong(V1, 0L)) {
        return uuidToMD5(var6, var8);
      }
    }

    return null;
  }

  private static int stringHash(String var0) {
    if(var0 == null) {
      return 0;
    } else {
      int var1 = 0;
      int var2 = var0.length();
      int var3 = 0;

      for(int var4 = 0; var4 < var2; ++var4) {
        var3 = 31 * var3 + var0.charAt(var1++);
      }

      return var3;
    }
  }

  public static long getCheckSum(RuntimeInfo var0) {
    long var1 = 0L;
    X500Principal var3 = var0.getHolder();
    var1 += var3 == null?0L:(long)stringHash(var3.getName());
    X500Principal var4 = var0.getIssuer();
    var1 += var4 == null?0L:(long)stringHash(var4.getName());
    int var5 = var0.getConsumerAmount();
    var1 += (long)var5;
    String var6 = var0.getConsumerType();
    var1 += (long)stringHash(var6);
    String var7 = var0.getInfo();
    var1 += (long)stringHash(var7);
    Date var8 = var0.getIssued();
    var1 += var8.getTime();
    Date var9 = var0.getNotBefore();
    var1 += var9 == null?0L:var9.getTime();
    Date var10 = var0.getNotAfter();
    var1 += var10 == null?0L:var10.getTime();
    String var11 = var0.getSubject();
    var1 += (long)stringHash(var11);
    String var12 = var0.getLicId();
    var1 += (long)stringHash(var12);
    String var13 = var0.getLicVer();
    var1 += (long)stringHash(var13);
    String var14 = var0.getUserId();
    var1 += (long)stringHash(var14);
    String var15 = var0.getUserName();
    var1 += (long)stringHash(var15);
    String var16 = var0.getCompanyId();
    var1 += (long)stringHash(var16);
    String var17 = var0.getCompanyName();
    var1 += (long)stringHash(var17);
    long var18 = var0.getSessionCount().longValue();
    var1 += var18;
    String var20 = var0.getZssVer();
    var1 += (long)stringHash(var20);
    return var1;
  }

  private static byte[] getInJarLic() throws Exception {
    InputStream var0 = Runtime.class.getResourceAsStream(PROPRIETARY_GENERIAL_LICENSE_FILE_PATH);
    ArrayList var1 = new ArrayList();
    boolean var2 = false;

    try {
      int var8;
      while((var8 = var0.read()) > -1) {
        var1.add(Integer.valueOf(var8));
      }

      byte[] var3 = new byte[var1.size()];

      for(int var4 = 0; var4 < var3.length; ++var4) {
        var3[var4] = (byte)((Integer)var1.get(var4)).intValue();
      }

      byte[] var9 = var3;
      return var9;
    } finally {
      var0.close();
    }
  }

  private static final boolean init1(WebApp var0) throws Exception {
    _wapp = var0;
    String var1 = Library.getProperty(LICENSE_DIRECTORY_PROPERTY);
    boolean var2 = false;
    if(var1 != null) {
      var2 = _licManager.install(var1);
    } else {
      URL var3 = Runtime.class.getResource(DEFAULT_LICENSE_DIRECTORY);
      URL var4 = Runtime.class.getResource(PROPRIETARY_GENERIAL_LICENSE_FILE_PATH);
      if(var3 != null) {
        var2 = _licManager.install(var3.getFile());
      } else if(var4 != null) {
        var2 = _licManager.install(getInJarLic(), var4.getFile());
      }
    }

    if(var2) {
      _licManager.setWapp(var0);
      _licManager.startScheduler();
      return true;
    } else {
      init1().init2((List)null);
      return false;
    }
  }

  static {
    _licManager = RuntimeLicenseManager.getInstance(_licenseParam, new Refresh() {
      public boolean checkVersion(Map var1) {
        String var2 = (String)var1.get(Runtime.VERSION);
        return Strings.isBlank(var2) || "3.5.0".startsWith(var2);
      }

      public boolean checkPackage(Map var1) {
        String var2 = (String)var1.get(Runtime.PACKAGE);
        return "EE".equals(var2);
      }

      public boolean isTargetSubject(Map var1) {
        String var2 = (String)var1.get(Runtime.LICENSE_SUBJECT);
        return Runtime.ZK_SPREADSHEET.equals(var2);
      }

      public Object refresh(List var1) {
        Runtime.init1().init2(var1);
        this.printInfo(var1);
        return null;
      }

      private void printInfo(List var1) {
        for(int var2 = 0; var2 < var1.size(); ++var2) {
          LicenseContent var3 = (LicenseContent)var1.get(var2);
          Object var4 = var3.getExtra();
          if(var4 instanceof Map) {
            Map var5 = (Map)var4;
            if(this.isTargetSubject(var5)) {
              String var6 = (String)((Map)var3.getExtra()).get(Runtime.INFORMATION);
              if(!var6.equals(Runtime.PROPRIETARY_GENERIAL_LICENSE_MESSAGE)) {
                this.info((String)((Map)var3.getExtra()).get(Runtime.INFORMATION));
              }
            }
          }
        }

      }

      private void info(String var1) {
        RuntimeLicenseManager.info(var1);
      }
    });
  }

  static final class LicenseEvent extends Event {
    private static final long serialVersionUID = 201011241551L;

    private LicenseEvent(Event var1) {
      super("onLicense", var1.getTarget());
    }
  }

  private static final class Init1 {
    private static final int FREQ = 3;
    private static final String WIN = (new ObfuscatedString(new long[]{5321153595986820916L, 6399668227042605266L, -5602491487233658872L, 2066377234886365691L, 203726272017906087L, -3409963818121804467L, -290525610284193589L, 5360349214446654186L, 7126235572874868104L, 6443646263932522040L, -2888496393462639896L})).toString() + (new ObfuscatedString(new long[]{8156116825536686571L, -7933011286180755110L, 8429279178335537173L, 5140564363110783572L, -4587992236270359981L, -1850871503295801036L, -7368992273418428298L, -9139769631895394533L, 901440478462063480L, 1371905052599390164L, -5107498036593385759L, -5563648405980080527L, 2015340681356193425L, 916270472538173639L, 7391668993988648096L, 5028903049363742715L, -2699427368479864385L, -2350362590362555178L})).toString();
    private static final String FORMULA_ENGINE = (new ObfuscatedString(new long[]{-6946940861330031386L, -8200137308179568909L, 5015398788645700413L, -6979074645241798869L, 6586954149477648878L, -5741372767674611000L})).toString();
    private static final String FORMULA_ENGINE_IMPL = (new ObfuscatedString(new long[]{-2418284299785054334L, 1479105741032879169L, 2328236503429654979L, 7851089442375934165L, -5116229586923382654L, 6258337476103361659L})).toString();
    private static final String FUNCTION_RESOLVER = (new ObfuscatedString(new long[]{3650953078381263648L, -7978655657373329998L, -7486081239882828487L, -956655162816755011L, 4587601644781096426L, -2328435719969713472L, 583188415702729932L})).toString();
    private static final String FUNCTION_RESOLVER_IMPL = (new ObfuscatedString(new long[]{1648457497419151688L, 8456168057038146110L, 2205843546792627726L, -8038274678378822470L, -3972581075115221739L, 8469001692587560038L, -8519618469700958897L})).toString();
    private static final String BOOK_SERIES_BUILDER = (new ObfuscatedString(new long[]{4400999526834038266L, 6680169581966688822L, -824020121173969257L, -2005943408017573892L, -3284079652096457332L, 5855883971906819748L, -5857099058607209459L})).toString();
    private static final String BOOK_SERIES_BUILDER_IMPL = (new ObfuscatedString(new long[]{-5726508211724793893L, 8569992118082504769L, 9181478058935831292L, 7086305829456764418L, 7742005198616659484L, -6440406587327967888L, 1624927702067762223L})).toString();
    private static final String EXPORTER_FACTORY = (new ObfuscatedString(new long[]{-4472343079140613160L, 7867973633219393851L, -5503447674221945233L, -262016254903301586L, 2049687033864576615L, -3887098640313149202L, 6848988432547480775L, -7221212047718775950L})).toString();
    private static final String EXPORTER_FACTORY_IMPL = (new ObfuscatedString(new long[]{3590763058016225734L, 493700663196015039L, 6092765942829109086L, -145160051114152404L, 8687165099806515870L, -9142161292280721010L, -1683109134765802500L, 3298554575513285863L, 3369350718315707033L, -7298225110369326910L, -4824650480167033094L, 572408024759060548L, 2590476670503222817L, -3780751709081089808L, -4269615409217637680L})).toString();
    private static final String FUNCTIONS = (new ObfuscatedString(new long[]{-9107950682079466537L, 6044098016258491637L, 4752071218608557149L, -1415194819165792974L, 1037012558002603534L, -286936863091228478L})).toString();
    private static final String FUNCTIONS_IMPL = (new ObfuscatedString(new long[]{4840889417712456605L, -932238802067396981L, 1643890773373654566L, -4649133848658165041L, 5968888824247950491L, -7413278661051192632L, 2152792200966762843L, -7577240830758492503L, -6433816155231039363L, 8826133207976952688L})).toString();
    private static final String WIDGET_HANDLER = (new ObfuscatedString(new long[]{-4996559347472087276L, -7766264256509841091L, 4421449018288163029L, 1989371856569290729L, 6120813221538358633L, -4795921775128256392L})).toString();
    private static final String WIDGET_HANDLER_IMPL = (new ObfuscatedString(new long[]{7936749289072989851L, 8075841980845954183L, 3847322226299015273L, 6066080613497293258L, 1620474641933788511L, -1548118456145909793L, -1418230738701484438L})).toString();
    private static final String WIDGET_LOADER = (new ObfuscatedString(new long[]{5817538321180584438L, 8865508355996080707L, 4886347968546669731L, 435526161500103674L, -8511131547026821770L, 5742632421264337498L})).toString();
    private static final String WIDGET_LOADER_IMPL = (new ObfuscatedString(new long[]{-933027784286545804L, -6380004711020883432L, -8028339963317745976L, 1225777306763359407L, 7409495186831786856L, -241248085354424324L, -5246784403500936783L, 2337188762834458524L})).toString();
    private static final Init1 _init1 = new Init1();
    private boolean _stoplicense;
    private boolean _stopuptime;
    private int _inModal;
    private final long _uptime = (new Date()).getTime();
    private final Init2 _init2;
    private long _sess;
    private final Map _init3;
    private final Map _init4;
    private static final String MD5STR = (new ObfuscatedString(new long[]{-5121064899839768052L, 3334273322282769989L})).toString();
    private static final String MD = (new ObfuscatedString(new long[]{2345590606122015185L, -5069608179148319545L})).toString();
    private static final String TITLE = (new ObfuscatedString(new long[]{6290325967589686282L, 5925669707962544096L})).toString();
    private static final String BORDER = (new ObfuscatedString(new long[]{1517003640222100403L, -5881717338632855477L})).toString();
    private static final String MODE = (new ObfuscatedString(new long[]{2063230648775789892L, -7765346573662599107L, -3183841938540764871L})).toString();
    private static final String WIDTH = (new ObfuscatedString(new long[]{-2028303217529921622L, 6478417485293582031L})).toString();
    private static final String HEIGHT = (new ObfuscatedString(new long[]{2974696421778732584L, -3701582788618461277L})).toString();
    private static final String ARG = (new ObfuscatedString(new long[]{3689490926743112102L, -8583085698292004623L})).toString();
    private static final String UPTIME_EXP = (new ObfuscatedString(new long[]{836794356891112142L, 3595395733708118761L, -7978618701001773448L, -7711157499660008450L, 9143275964856405829L, -8978572650370272471L, -1885816955389459868L, -2850716340587082295L})).toString();
    private static final String TRIAL_EXP = (new ObfuscatedString(new long[]{6254089562974914622L, -3227072154423754334L, -4406587499255443939L, -6016561217416014811L, 1053770341113116349L, 6895376768732924359L})).toString();
    private static final String SESSION_EXP = (new ObfuscatedString(new long[]{1982171942942774777L, -8787355027292006585L, -1691495013518805258L, -4266241046547582566L, 6831110504825143746L, -5394459224874436555L, 3957820018767187526L, 1032975061284734343L})).toString();
    private static final String UPTIME_INFO = (new ObfuscatedString(new long[]{-140629977948033438L, 8411039821423448906L, -3474650949739896324L, 2772224587032503194L})).toString();
    private static final String SESSION_INFO = (new ObfuscatedString(new long[]{-282132617423556107L, 2757430579228438986L, -2300236627084260024L, 5486533064596330289L})).toString();
    private static final String LIC_INFO = (new ObfuscatedString(new long[]{-1327671782532263310L, 840490956016971870L, 3061901067660749900L})).toString();
    private static final String MASK_HEAD = (new ObfuscatedString(new long[]{2610432342261686015L, 2813741495629532862L, -1181939021078443460L, -7375419926652952589L})).toString();
    private static final String MASK_BODY = (new ObfuscatedString(new long[]{-776441849887137687L, -9143613716996647403L, 4380415369023607826L, 1307662236470395652L, -689297554901065354L, -4590698981075227578L, 8398261592963428925L, -2272630810702424214L, 5009103648726399196L, -176307024327010546L})).toString();
    private static final String MASK_FOOT = (new ObfuscatedString(new long[]{812264288056794049L, -2332289316366425421L})).toString();

    private Init1() {
      Library.setProperty(FORMULA_ENGINE, FORMULA_ENGINE_IMPL);
      Library.setProperty(FUNCTION_RESOLVER, FUNCTION_RESOLVER_IMPL);
      Library.setProperty(BOOK_SERIES_BUILDER, BOOK_SERIES_BUILDER_IMPL);
      Library.setProperty(EXPORTER_FACTORY, EXPORTER_FACTORY_IMPL);
      Library.setProperty(FUNCTIONS, FUNCTIONS_IMPL);
      Library.setProperty(WIDGET_HANDLER, WIDGET_HANDLER_IMPL);
      Library.setProperty(WIDGET_LOADER, WIDGET_LOADER_IMPL);
      this._init2 = new Init2();
      this._init3 = new HashMap();
      this._init4 = new HashMap();
    }

    private void init2(List var1) {
      this._init2.init2(var1);
      Runtime._pass.set(Boolean.valueOf(!this._init2._keys.isEmpty()));
    }

    private final void afterProcessEvent0(Event var1) {
      if(!this.validate(var1)) {
        ++this._inModal;
      }

    }

    private final Event beforePostEvent0(Event var1) {
      return var1;
    }

    private final Event beforeProcessEvent0(Event var1) {
      Component var2 = var1.getTarget();
      if(var2 instanceof Spreadsheet) {
        if(this._stopuptime) {
          this.maskSheetUptime(var2.getUuid());
          return null;
        }

        if(this._stoplicense) {
          this.maskSheetLicense(var2.getUuid());
          return null;
        }

        Session var3 = Sessions.getCurrent();
        if(var3 != null) {
          this.init1(var3, (Object)null);
        }
      }

      return var1;
    }

    private final Event beforeSendEvent0(Event var1) {
      return var1;
    }

    private final void init1(Session var1, Object var2) {
      if(var2 == null && !this._init3.containsKey(var1)) {
        ++this._sess;
        Runtime.token0(var1, var1);
        this._init3.put(var1, var1);
        long var3 = !this._init2._keys.isEmpty() && this._init2._sessioncount != null?((Number)this._init2._sessioncount).longValue():0L;
        if(this._sess > var3) {
          this._init4.put(var1, var1);
        }
      }

    }

    private final void cleanup1(Session var1) {
      if(this._init3.containsKey(var1)) {
        --this._sess;
        this._init3.remove(var1);
        this._init4.remove(var1);
      }

    }

    private final void init0(Desktop var1, Object var2) {
      try {
        MessageDigest var3 = MessageDigest.getInstance(MD5STR);
        var1.setAttribute(MD + var1.getId(), var3);
      } catch (NoSuchAlgorithmException var4) {
        ;
      }

      Init var5 = new Init();
      var1.setAttribute(Runtime.token1(var1), var5);
      var1.addListener(var5);
    }

    private final void cleanup0(Desktop var1) {
      Object var2 = var1.getAttribute(Runtime.token1(var1));
      var1.removeListener(var2);
    }

    private final void init2(Execution var1, Execution var2) {
      Runtime.token0(var1, var1);
    }

    private final boolean validate(Event var1) {
      return !this.validUptime(var1) || !this.validLicense(var1) || !this.validSession(var1);
    }

    private final boolean validLicense(Event var1) {
      if(this._init2._keys.isEmpty()) {
        this.complainLicense(var1);
        return false;
      } else {
        long var2 = (new Date()).getTime();
        if(((Date)this._init2._validbegin).getTime() <= var2 && var2 <= ((Date)this._init2._validend).getTime()) {
          return true;
        } else {
          this.complainLicense(var1);
          return false;
        }
      }
    }

    private final boolean validSession(Event var1) {
      if(!this._init2._keys.isEmpty() && this._init2._sessioncount != null) {
        Session var2 = Sessions.getCurrent();
        boolean var3 = this._init3.containsKey(var2);
        boolean var4 = Runtime.token0(var2);
        if(var3 && !var4) {
          Runtime.token0(var2, var2);
        }

        if(!var3 && var4) {
          this.init1(var2, (Object)null);
        }

        long var5 = ((Number)this._init2._sessioncount).longValue();
        if(var5 < this._sess && this._init4.containsKey(var2)) {
          this.complainSession(var1);
          return false;
        } else {
          return true;
        }
      } else {
        this.complainSession(var1);
        return false;
      }
    }

    private final boolean validUptime(Event var1) {
      if(!this._init2._keys.isEmpty() && this._init2._uptime != null) {
        long var2 = (new Date()).getTime();
        if(this._uptime <= var2 && var2 <= this._uptime + ((Long)this._init2._uptime).longValue()) {
          return true;
        } else {
          this.complainUptime(var1);
          return false;
        }
      } else {
        this.complainLicense(var1);
        return false;
      }
    }

    private final void showWin(Page var1, String var2) {
      Window var3 = new Window(TITLE, BORDER, true);
      var3.setMode(MODE);
      var3.setWidth(WIDTH);
      var3.setHeight(HEIGHT);
      var3.setPage(var1);
      HashMap var4 = new HashMap();
      var4.put(ARG, var2);
      Component[] var5 = Executions.getCurrent().createComponentsDirectly(WIN, "zul", var4);

      for(int var6 = 0; var6 < var5.length; ++var6) {
        var5[var6].setParent(var3);
      }

    }

    private final void complainLicense(Event var1) {
      ++this._inModal;
      if(!this._stoplicense && (this._inModal & 3) == 0) {
        Component var2 = var1.getTarget();
        if(var2 instanceof Spreadsheet) {
          Page var3 = var2.getPage();
          if(var3 != null) {
            this.showWin(var3, TRIAL_EXP);
            this._stoplicense = true;
            this.maskSheetLicense(var2.getUuid());
            SBook var4 = ((Spreadsheet)var2).getSBook();
            if(var4 != null) {
              var4.setShareScope("disable");
            }
          }
        }
      }

    }

    private final void complainUptime(Event var1) {
      ++this._inModal;
      if(!this._stopuptime && (this._inModal & 3) == 0) {
        Component var2 = var1.getTarget();
        if(var2 instanceof Spreadsheet) {
          Page var3 = var2.getPage();
          if(var3 != null) {
            this.showWin(var3, UPTIME_EXP);
            this._stopuptime = true;
            this.maskSheetUptime(var2.getUuid());
            SBook var4 = ((Spreadsheet)var2).getSBook();
            if(var4 != null) {
              var4.setShareScope("disable");
            }
          }
        }
      }

    }

    private final void complainSession(Event var1) {
      ++this._inModal;
      if((this._inModal & 3) == 0) {
        Component var2 = var1.getTarget();
        if(var2 instanceof Spreadsheet) {
          Page var3 = var2.getPage();
          if(var3 != null) {
            this.showWin(var3, SESSION_EXP);
            this.maskSheetSession(var2.getUuid());
          }
        }
      }

    }

    private final void maskSheetUptime(String var1) {
      this.maskSheet(var1, UPTIME_INFO);
    }

    private final void maskSheetSession(String var1) {
      this.maskSheet(var1, SESSION_INFO);
    }

    private final void maskSheetLicense(String var1) {
      this.maskSheet(var1, LIC_INFO);
    }

    private final void maskSheet(String var1, String var2) {
      Clients.evalJavaScript(MASK_HEAD + var1 + MASK_BODY + var2 + MASK_FOOT);
    }
  }

  private static final class Init2 {
    private static final BigInteger _pkey = new BigInteger("123");
    private static final String EVAL_LIC_ID = (new ObfuscatedString(new long[]{-2799362229550136139L, -8991339801923478651L, 2054766831045471090L})).toString();
    private static final String EVAL_LIC_VER = (new ObfuscatedString(new long[]{4515837249784318634L, -483965117545718936L})).toString();
    private static final String EVAL_USER_NAME = (new ObfuscatedString(new long[]{6700787382705499563L, 2359114071391082446L, 1949693015831576717L})).toString();
    private static final String EVAL_COMPANY_ID = (new ObfuscatedString(new long[]{8997629646135421336L, -7459597106373371040L, 3324566901651534592L})).toString();
    private static final String EVAL_COMPANY_NAME = (new ObfuscatedString(new long[]{-7105953592593293452L, -8116579615415252408L, -3117127378177258899L, 9036340616621476990L})).toString();
    private static final Long EVAL_SESSION_COUNT = Long.valueOf(9223372036854775807L);
    private SimpleDateFormat _dateFormat;
    private Calendar _calendar;
    private Map _keys;
    private Object _licid;
    private Object _licversion;
    private Object _username;
    private Object _companyid;
    private Object _companyname;
    private Object _validbegin;
    private Object _validend;
    private Object _sessioncount;
    private Object _zssversion;
    private Object _uptime;

    private Init2() {
      this._dateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
      this._calendar = Calendar.getInstance();
      this._keys = new HashMap();
    }

    private Date getNotBefore(Map var1) {
      try {
        return this._dateFormat.parse((String)((String)var1.get(Runtime.ISSUE_DATE)));
      } catch (ParseException var3) {
        return new Date(9223372036854775807L);
      }
    }

    private Date getExpiryDate(Map var1) {
      try {
        return this._dateFormat.parse((String)((String)var1.get(Runtime.EXPIRY_DATE)));
      } catch (ParseException var3) {
        return new Date(-9223372036854775808L);
      }
    }

    private long getSessionCount(LicenseContent var1) {
      return "Session".equalsIgnoreCase(var1.getConsumerType())?(long)var1.getConsumerAmount():0L;
    }

    private long getUpTime(Map var1) {
      Long var2 = (Long)var1.get(Runtime.UP_TIME);
      return var2 == null?0L:var2.longValue();
    }

    private void init2(List var1) {
      if(var1 == null) {
        this.installEval();
      } else {
        Date var3 = Dates.today();
        boolean var4 = false;
        Date var5 = new Date(9223372036854775807L);
        Date var6 = new Date(-9223372036854775808L);
        Date var7 = new Date(-9223372036854775808L);
        long var8 = 0L;
        long var10 = 0L;
        String var12 = (String)Runtime._wapp.getAttribute(Runtime.ACTIVE_CODE);
        Iterator var13 = var1.iterator();

        while(var13.hasNext()) {
          LicenseContent var14 = (LicenseContent)var13.next();
          Object var15 = var14.getExtra();
          if(var15 instanceof Map) {
            Map var16 = (Map)var15;
            Date var17 = this.getNotBefore(var16);
            Date var18 = this.getExpiryDate(var16);
            if(!var3.before(var17) && !var3.after(var18)) {
              if(var17.before(var5)) {
                var5 = var17;
              }

              if(var18.after(var6)) {
                var6 = var18;
              }

              String var19 = (String)var16.get(Runtime.ACTIVE_CODE);
              Date var20 = var14.getIssued();
              if(var20.after(var7) && (Runtime.UNIVERSAL_ACTIVE_CODE.equals(var19) || var12 != null && var12.equals(var19))) {
                var4 = true;
                var7 = var20;
                this._zssversion = var16.get(Runtime.VERSION);
                this._username = var16.get(Runtime.USER_NAME);
                this._companyid = var16.get(Runtime.COMPANY_ID);
                this._companyname = var16.get(Runtime.COMPANY_NAME);
                this._keys = var16;
                this._licid = var16.get(Runtime.VERIFICATION_NUMBER);
                this._licversion = var16.get(Runtime.LICENSE_VERSION);
                var8 += this.getSessionCount(var14);
                long var21 = this.getUpTime(var16);
                if(var10 < var21) {
                  var10 = var21;
                }
              }
            }
          }
        }

        if(var4) {
          this._validbegin = var5;
          this._validend = var6;
          this._uptime = Long.valueOf(var10 <= 0L?Long.valueOf(var6.getTime() - var5.getTime() + (long)(new Random((new Date()).getTime())).nextInt(1800000)).longValue():var10);
          this._sessioncount = var8 > 0L?Long.valueOf(var8):EVAL_SESSION_COUNT;
        }

        if(!var4 || !this.validateLicenseFile()) {
          this.installEval();
        }

      }
    }

    private final void installEval() {
      this._username = EVAL_USER_NAME;
      this._companyid = EVAL_COMPANY_ID;
      this._companyname = EVAL_COMPANY_NAME;
      this._validbegin = new Date();
      this._uptime = Long.valueOf((long)(43200000 + (new Random((new Date()).getTime())).nextInt(1800000)));
      this._validend = new Date(((Date)this._validbegin).getTime() + ((Long)this._uptime).longValue() + 3600000L);
      this._sessioncount = EVAL_SESSION_COUNT;
      this._keys = new HashMap();
      this._keys.put(Runtime.COMPANY_ID, this._companyid);
    }

    private final boolean validateLicenseFile() {
      return this._licid != null && this._licversion != null && this._username != null && this._companyid != null && this._companyname != null && this._validbegin != null && this._validend != null && this._sessioncount != null && this._zssversion != null && this._uptime != null;
    }

    private final String encode(byte[] var1) {
      try {
        return new String(var1, "UTF-8");
      } catch (UnsupportedEncodingException var3) {
        return "";
      }
    }

    private final byte[] decryption(byte[] var1) {
      return var1;
    }
  }

  private abstract static class Init0 implements ExecutionInit, DesktopInit, DesktopCleanup, SessionInit, SessionCleanup, EventInterceptor {
    private Init0() {
      this.init1();
    }

    private final void init0(Desktop var1, Object var2) throws Exception {
      this.init1().init0(var1, var2);
    }

    private final void cleanup0(Desktop var1) throws Exception {
      this.init1().cleanup0(var1);
    }

    private final void init1(Session var1, Object var2) throws Exception {
      this.init1().init1(var1, var2);
    }

    private final void cleanup1(Session var1) throws Exception {
      this.init1().cleanup1(var1);
    }

    private final void init2(Execution var1, Execution var2) throws Exception {
      this.init1().init2(var1, var2);
    }

    private final Init1 init1() {
      return Init1._init1;
    }

    private final void afterProcessEvent0(Event var1) {
      this.init1().afterProcessEvent0(var1);
    }

    private final Event beforePostEvent0(Event var1) {
      return this.init1().beforePostEvent0(var1);
    }

    private final Event beforeProcessEvent0(Event var1) {
      return this.init1().beforeProcessEvent0(var1);
    }

    private final Event beforeSendEvent0(Event var1) {
      return this.init1().beforeSendEvent0(var1);
    }
  }

  public static final class Init extends Init0 {
    public Init() {
      super();
    }

    public void init(Desktop var1, Object var2) throws Exception {
      super.init0(var1, var2);
    }

    public void cleanup(Desktop var1) throws Exception {
      super.cleanup0(var1);
    }

    public void init(Session var1, Object var2) throws Exception {
      super.init1(var1, var2);
    }

    public void cleanup(Session var1) throws Exception {
      super.cleanup1(var1);
    }

    public void init(Execution var1, Execution var2) throws Exception {
      super.init2(var1, var2);
    }

    public void afterProcessEvent(Event var1) {
      super.afterProcessEvent0(var1);
    }

    public Event beforePostEvent(Event var1) {
      return super.beforePostEvent0(var1);
    }

    public Event beforeProcessEvent(Event var1) {
      return super.beforeProcessEvent0(var1);
    }

    public Event beforeSendEvent(Event var1) {
      return super.beforeSendEvent0(var1);
    }
  }
}
