package br.com.brasiltelecom.oms.xmlapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class OMSResponse
{
  int code;
  String message;
  String data;
  
  public OMSResponse(HttpURLConnection con)
    throws IOException
  {
    this.code = con.getResponseCode();
    this.message = con.getResponseMessage();
    InputStream in = con.getInputStream();
    InputStreamReader reader = new InputStreamReader(in, Charset.forName("ISO-8859-1"));
    BufferedReader buffer = new BufferedReader(reader);
    StringBuffer s = new StringBuffer();
    for (String c = buffer.readLine(); c != null; c = buffer.readLine()) {
      s.append(c);
    }
    this.data = s.toString();
    if (!this.data.trim().startsWith("<?xml")) {
      this.data = ("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + this.data);
    }
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public String getData()
  {
    return this.data;
  }
  
  public String getMessage()
  {
    return this.message;
  }
}
