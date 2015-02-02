//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.util;

public class PictureHelper {
    public PictureHelper() {
    }

    public static int getPictureFormat(String ext) {
        if(!"jpg".equals(ext) && !"jpeg".equals(ext)) {
            if("png".equals(ext)) {
                return 6;
            } else {
                throw new RuntimeException("Unsupport picture format:" + ext);
            }
        } else {
            return 5;
        }
    }
}
