package br.com.brasiltelecom.oms.data;

public class OMSException
  extends Exception
{
  public OMSException(String msg)
  {
    super(msg);
  }
  
  public OMSException(String msg, Throwable cause)
  {
    super(msg, cause);
  }
}
