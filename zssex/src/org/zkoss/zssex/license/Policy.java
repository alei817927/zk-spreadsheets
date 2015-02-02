package org.zkoss.zssex.license;

public class Policy
{
  private static Policy current;

  public static void setCurrent(Policy paramPolicy)
  {
    current = paramPolicy;
  }

  public static Policy getCurrent()
  {
    if (current == null)
      current = new Policy();
    return current;
  }

  public void checkPwd(String paramString)
  {
    int i = paramString.length();
    if (paramString == null)
      throw new IllegalPasswordException();
    if (i < 6)
      throw new IllegalPasswordException();
    int j = 0;
    int k = 0;
    for (int m = 0; m < i; m++)
    {
      char c = paramString.charAt(m);
      if (Character.isLetter(c))
        j = 1;
      else if (Character.isDigit(c))
        k = 1;
    }
    if ((j == 0) || (k == 0))
      throw new IllegalPasswordException();
  }
}

/* Location:           C:\Users\alei\Desktop\src\3.5\zssex-3.5.0.RC.jar
 * Qualified Name:     org.zkoss.zssex.license.Policy
 * JD-Core Version:    0.6.1
 */