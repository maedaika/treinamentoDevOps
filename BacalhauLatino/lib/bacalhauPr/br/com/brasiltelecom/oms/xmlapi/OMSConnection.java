/*  1:   */ package br.com.brasiltelecom.oms.xmlapi;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.OutputStream;
/*  5:   */ import java.io.PrintStream;
/*  6:   */ import java.net.HttpURLConnection;
/*  7:   */ import java.net.URL;
/*  8:   */ import java.net.URLConnection;
/*  9:   */ 
/* 10:   */ public class OMSConnection
/* 11:   */ {
/* 12:   */   public static final String COOKIE_PROPERTY = "Cookie";
/* 13:   */   public static final String REQUEST_METHOD = "POST";
/* 14:   */   public static final String CONTENT_TYPE = "Content-Type";
/* 15:   */   public static final String CONTENT_TYPE_VALUE = "text/xml;charset=ISO-8859-1";
/* 16:   */   public static final String CONTENT_LENGTH = "Content-Length";
/* 17:   */   URL url;
/* 18:   */   String cookie;
/* 19:   */   
/* 20:   */   public OMSConnection(URL url, String cookie)
/* 21:   */   {
/* 22:27 */     this.url = url;
/* 23:28 */     this.cookie = cookie;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public synchronized OMSResponse send(String message)
/* 27:   */     throws IOException
/* 28:   */   {
/* 29:32 */     System.out.println("OMSConnection: sending:" + message);
/* 30:33 */     OMSResponse response = null;
/* 31:34 */     byte[] bytes = message.getBytes();
/* 32:35 */     HttpURLConnection connection = (HttpURLConnection)this.url.openConnection();
/* 33:36 */     connection.setAllowUserInteraction(false);
/* 34:37 */     connection.setDoOutput(true);
/* 35:38 */     connection.setDoInput(true);
/* 36:39 */     connection.setRequestMethod("POST");
/* 37:40 */     connection.setRequestProperty("Content-Type", "text/xml;charset=ISO-8859-1");
/* 38:41 */     connection.setRequestProperty("Content-Length", 
/* 39:42 */       String.valueOf(bytes.length));
/* 40:43 */     connection.setRequestProperty("Cookie", this.cookie);
/* 41:44 */     connection.connect();
/* 42:45 */     OutputStream out = connection.getOutputStream();
/* 43:46 */     out.write(bytes);
/* 44:47 */     response = new OMSResponse(connection);
/* 45:48 */     connection.disconnect();
/* 46:49 */     connection = null;
/* 47:50 */     return response;
/* 48:   */   }
/* 49:   */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.OMSConnection
 * JD-Core Version:    0.7.0.1
 */