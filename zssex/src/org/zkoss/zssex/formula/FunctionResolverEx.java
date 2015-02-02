//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.formula;

import java.lang.reflect.Field;
import org.zkoss.lang.Library;
import org.zkoss.poi.ss.formula.DependencyTracker;
import org.zkoss.poi.ss.formula.udf.UDFFinder;
import org.zkoss.util.logging.Log;
import org.zkoss.util.resource.ClassLocator;
import org.zkoss.xel.FunctionMapper;
import org.zkoss.xel.XelException;
import org.zkoss.xel.taglib.Taglib;
import org.zkoss.xel.util.TaglibMapper;
import org.zkoss.zss.model.sys.formula.FunctionResolver;
import org.zkoss.zssex.formula.DependencyTrackerEx;

public class FunctionResolverEx implements FunctionResolver {
    public static String EL_FUNCTION_KEY = "";
    private static final Log logger = Log.lookup(FunctionResolverEx.class.getName());
    private static final String TAGLIB_KEY = "http://www.zkoss.org/zss/functions";
    private static FunctionMapper _mapper;
    private static UDFFinder _udffinder;
    private static boolean _fail = false;

    public FunctionResolverEx() {
    }

    public UDFFinder getUDFFinder() {
        return _fail?null:_udffinder;
    }

    public FunctionMapper getFunctionMapper() {
        return _fail?null:_mapper;
    }

    public DependencyTracker getDependencyTracker() {
        return new DependencyTrackerEx();
    }

    static {
        if(_mapper == null) {
            String e = Library.getProperty("http://www.zkoss.org/zss/functions");
            if(e != null) {
                TaglibMapper field = new TaglibMapper();
                ClassLocator loc = new ClassLocator();
                String[] libs = e.split(",");

                for(int j = 0; j < libs.length; ++j) {
                    String lib = libs[j];
                    Taglib taglib = new Taglib("zss", "http://www.zkoss.org/zss/functions/" + lib.trim());

                    try {
                        field.load(taglib, loc);
                    } catch (XelException var9) {
                        logger.info(var9.getMessage(), var9);
                    }
                }

                _mapper = field;
            }
        }

        if(_udffinder == null) {
            try {
                Class var10 = Class.forName("org.zkoss.zssex.formula.ZKUDFFinder");
                Field var11 = var10.getField("instance");
                _udffinder = (UDFFinder)var11.get((Object)null);
                var10 = Class.forName("org.zkoss.zssex.formula.ELEval");
                var11 = var10.getField("NAME");
                EL_FUNCTION_KEY = (String)var11.get((Object)null);
            } catch (Exception var8) {
                logger.warning(var8.getMessage(), var8);
            }
        }

    }
}
