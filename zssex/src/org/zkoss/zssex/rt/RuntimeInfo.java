//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.zssex.rt;

import java.util.Date;
import org.zkoss.zssex.license.LicenseContent;

public final class RuntimeInfo extends LicenseContent {
  private String licId;
  private String licVer;
  private String userId;
  private String userName;
  private String companyId;
  private String companyName;
  private Long sessionCount;
  private String zssVer;
  private String sessionInfo;
  private long[] keySig;

  public RuntimeInfo() {
  }

  public String getLicId() {
    return this.licId;
  }

  public void setLicId(String var1) {
    this.licId = var1;
  }

  public String getLicVer() {
    return this.licVer;
  }

  public void setLicVer(String var1) {
    this.licVer = var1;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String var1) {
    this.userId = var1;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String var1) {
    this.userName = var1;
  }

  public String getCompanyId() {
    return this.companyId;
  }

  public void setCompanyId(String var1) {
    this.companyId = var1;
  }

  public String getCompanyName() {
    return this.companyName;
  }

  public void setCompanyName(String var1) {
    this.companyName = var1;
  }

  public Long getSessionCount() {
    return this.sessionCount;
  }

  public void setSessionCount(Long var1) {
    this.sessionCount = var1;
  }

  public String getZssVer() {
    return this.zssVer;
  }

  public void setZssVer(String var1) {
    this.zssVer = var1;
  }

  public String getSessionInfo() {
    return this.sessionInfo;
  }

  public void setSessionInfo(String var1) {
    this.sessionInfo = var1;
  }

  public long[] getKeySig() {
    return this.keySig;
  }

  public void setKeySig(long[] var1) {
    this.keySig = var1;
  }

  public Date getValidBegin() {
    return this.getNotBefore();
  }

  public Date getValidEnd() {
    return this.getNotAfter();
  }
}
