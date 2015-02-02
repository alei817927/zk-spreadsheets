package org.zkoss.zssex.xml;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;

public class GenericCertificateIsLockedException extends PropertyVetoException
{
  public GenericCertificateIsLockedException(PropertyChangeEvent paramPropertyChangeEvent)
  {
    super(paramPropertyChangeEvent.getPropertyName(), paramPropertyChangeEvent);
  }
}

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.xml.GenericCertificateIsLockedException
 * JD-Core Version:    0.6.1
 */