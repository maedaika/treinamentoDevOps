package br.com.brasiltelecom.oms.xmlapi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class OMSConnection
{
  public static final String COOKIE_PROPERTY = "Cookie";
  public static final String REQUEST_METHOD = "POST";
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String CONTENT_TYPE_VALUE = "text/xml;charset=ISO-8859-1";
  public static final String CONTENT_LENGTH = "Content-Length";
  URL url;
  URL logout;
  String cookie;
  
  public OMSConnection(URL url, URL logout, String cookie)
  {
    this.url = url;
    this.logout = logout;
    this.cookie = cookie;
  }
  
  public synchronized OMSResponse send(String message)
    throws IOException
  {
    OMSResponse response = null;
    byte[] bytes = message.getBytes();
    HttpURLConnection connection = (HttpURLConnection)this.url.openConnection();
    connection.setAllowUserInteraction(false);
    connection.setDoOutput(true);
    connection.setDoInput(true);
    connection.setRequestMethod("POST");
    connection.setRequestProperty("Content-Type", "text/xml;charset=ISO-8859-1");
    connection.setRequestProperty("Content-Length", 
      String.valueOf(bytes.length));
    connection.setRequestProperty("Cookie", this.cookie);
    connection.connect();
    OutputStream out = connection.getOutputStream();
    out.write(bytes);
    response = new OMSResponse(connection);
    connection.disconnect();
    connection = null;
    return response;
  }
  
  public synchronized void close()
    throws IOException
  {
    HttpURLConnection connection = (HttpURLConnection)this.logout.openConnection();
    connection.setRequestProperty("Cookie", this.cookie);
    connection.connect();
    connection.disconnect();
  }
}
