package org.zkoss.zssex.license;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.zkoss.zssex.util.ObfuscatedString;
import org.zkoss.zssex.xml.GenericCertificate;
import org.zkoss.zssex.xml.PersistenceService;
import org.zkoss.zssex.xml.PersistenceServiceException;

public class PrivacyGuard
{
  private static final String PBE_WITH_MD5_AND_DES = new ObfuscatedString(new long[] { 2860604316472308139L, 5030391952891038168L, -6110818099732428353L }).toString();
  private CipherParam param;
  private Cipher cipher;
  private SecretKey key;
  private AlgorithmParameterSpec algoParamSpec;

  protected PrivacyGuard()
  {
  }

  public PrivacyGuard(CipherParam paramCipherParam)
  {
    setCipherParam0(paramCipherParam);
  }

  public CipherParam getCipherParam()
  {
    return this.param;
  }

  public void setCipherParam(CipherParam paramCipherParam)
  {
    setCipherParam0(paramCipherParam);
  }

  private void setCipherParam0(CipherParam paramCipherParam)
  {
    if (paramCipherParam == null)
      throw new NullPointerException(LicenseNotary.PARAM);
    Policy.getCurrent().checkPwd(paramCipherParam.getKeyPwd());
    this.param = paramCipherParam;
    this.cipher = null;
    this.key = null;
    this.algoParamSpec = null;
  }

  public byte[] cert2key(GenericCertificate paramGenericCertificate)
    throws Exception
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(new CipherOutputStream(localByteArrayOutputStream, getCipher4Encryption()));
    try
    {
      PersistenceService.store(paramGenericCertificate, localGZIPOutputStream);
    }
    catch (PersistenceServiceException localPersistenceServiceException)
    {
      throw new AssertionError(localPersistenceServiceException);
    }
    return localByteArrayOutputStream.toByteArray();
  }

  public GenericCertificate key2cert(byte[] paramArrayOfByte)
    throws Exception
  {
    GZIPInputStream localGZIPInputStream = new GZIPInputStream(new ByteArrayInputStream(getCipher4Decryption().doFinal(paramArrayOfByte)));
    GenericCertificate localGenericCertificate;
    try
    {
      localGenericCertificate = (GenericCertificate)PersistenceService.load(localGZIPInputStream);
    }
    finally
    {
      try
      {
        localGZIPInputStream.close();
      }
      catch (IOException localIOException2)
      {
      }
    }
    return localGenericCertificate;
  }

  /** @deprecated */
  protected Cipher getCipher4Encryption()
  {
    Cipher localCipher = getCipher();
    try
    {
      localCipher.init(1, this.key, this.algoParamSpec);
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      throw new AssertionError(localInvalidKeyException);
    }
    catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException)
    {
      throw new AssertionError(localInvalidAlgorithmParameterException);
    }
    return localCipher;
  }

  /** @deprecated */
  protected Cipher getCipher4Decryption()
  {
    Cipher localCipher = getCipher();
    try
    {
      localCipher.init(2, this.key, this.algoParamSpec);
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      throw new AssertionError(localInvalidKeyException);
    }
    catch (InvalidAlgorithmParameterException localInvalidAlgorithmParameterException)
    {
      throw new AssertionError(localInvalidAlgorithmParameterException);
    }
    return localCipher;
  }

  /** @deprecated */
  protected Cipher getCipher()
  {
    if (this.cipher != null)
      return this.cipher;
    this.algoParamSpec = new PBEParameterSpec(new byte[] { -50, -5, -34, -84, 5, 2, 25, 113 }, 2005);
    try
    {
      PBEKeySpec localPBEKeySpec = new PBEKeySpec(getCipherParam().getKeyPwd().toCharArray());
      SecretKeyFactory localSecretKeyFactory = SecretKeyFactory.getInstance(PBE_WITH_MD5_AND_DES);
      this.key = localSecretKeyFactory.generateSecret(localPBEKeySpec);
      this.cipher = Cipher.getInstance(PBE_WITH_MD5_AND_DES);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new AssertionError(localNoSuchAlgorithmException);
    }
    catch (InvalidKeySpecException localInvalidKeySpecException)
    {
      throw new AssertionError(localInvalidKeySpecException);
    }
    catch (NoSuchPaddingException localNoSuchPaddingException)
    {
      throw new AssertionError(localNoSuchPaddingException);
    }
    return this.cipher;
  }
}

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.license.PrivacyGuard
 * JD-Core Version:    0.6.1
 */