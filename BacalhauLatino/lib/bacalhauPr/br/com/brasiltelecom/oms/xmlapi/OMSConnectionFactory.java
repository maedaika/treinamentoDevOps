/*  1:   */ package br.com.brasiltelecom.oms.xmlapi;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import java.io.OutputStream;
/*  5:   */ import java.net.HttpURLConnection;
/*  6:   */ import java.net.URL;
/*  7:   */ import java.net.URLConnection;
/*  8:   */ import java.net.URLEncoder;
/*  9:   */ import java.util.HashMap;
/* 10:   */ 
/* 11:   */ public class OMSConnectionFactory
/* 12:   */ {
/* 13:   */   public static final String XMLAPI_ROOT = "/oms/XMLAPI";
/* 14:   */   public static final String XMLAPI_LOGIN = "/oms/XMLAPI/login";
/* 15:23 */   private static HashMap factorys = new HashMap();
/* 16:   */   private String url;
/* 17:   */   
/* 18:   */   private OMSConnectionFactory(String url)
/* 19:   */   {
/* 20:28 */     this.url = url;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static OMSConnectionFactory getInstance(String server, int port)
/* 24:   */   {
/* 25:32 */     String url = "http://" + server.toLowerCase().trim() + ":" + port;
/* 26:33 */     OMSConnectionFactory factory = (OMSConnectionFactory)factorys.get(url);
/* 27:34 */     if (factory == null)
/* 28:   */     {
/* 29:35 */       factory = new OMSConnectionFactory(url);
/* 30:36 */       factorys.put(url, factory);
/* 31:   */     }
/* 32:38 */     return factory;
/* 33:   */   }
/* 34:   */   
/* 35:   */   private static String encode(String message)
/* 36:   */   {
/* 37:   */     try
/* 38:   */     {
/* 39:43 */       return URLEncoder.encode(message, "ISO-8859-1");
/* 40:   */     }
/* 41:   */     catch (Exception ex) {}
/* 42:45 */     return message;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public OMSConnection getConnection(String user, String pass)
/* 46:   */     throws IOException
/* 47:   */   {
/* 48:50 */     URL urlLogin = new URL(this.url + "/oms/XMLAPI/login");
/* 49:51 */     URL urlRoot = new URL(this.url + "/oms/XMLAPI");
/* 50:52 */     String message = encode("username") + "=" + encode(user) + "&" + encode("password") + 
/* 51:53 */       "=" + encode(pass);
/* 52:54 */     byte[] bytes = message.getBytes();
/* 53:55 */     String cookie = "";
/* 54:   */     
/* 55:57 */     HttpURLConnection connection = (HttpURLConnection)urlLogin.openConnection();
/* 56:58 */     connection.setAllowUserInteraction(false);
/* 57:59 */     connection.setDoOutput(true);
/* 58:60 */     connection.setDoInput(true);
/* 59:61 */     connection.setRequestMethod("POST");
/* 60:62 */     connection.setRequestProperty("Content-Type", 
/* 61:63 */       "application/x-www-form-urlencoded");
/* 62:64 */     connection.setRequestProperty("Content-Length", 
/* 63:65 */       String.valueOf(bytes.length));
/* 64:66 */     connection.connect();
/* 65:67 */     OutputStream out = connection.getOutputStream();
/* 66:68 */     out.write(bytes);
/* 67:69 */     int code = connection.getResponseCode();
/* 68:71 */     if (code == 200)
/* 69:   */     {
/* 70:72 */       String receivedcookie = connection.getHeaderField("Set-Cookie");
/* 71:73 */       if (receivedcookie == null) {
/* 72:74 */         throw new IOException("Server did not return session cookie");
/* 73:   */       }
/* 74:76 */       cookie = receivedcookie.substring(0, receivedcookie.indexOf(';'));
/* 75:   */     }
/* 76:   */     else
/* 77:   */     {
/* 78:78 */       throw new IOException("HTTP response code != 200 OK :" + code);
/* 79:   */     }
/* 80:81 */     return new OMSConnection(urlRoot, cookie);
/* 81:   */   }
/* 82:   */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.OMSConnectionFactory
 * JD-Core Version:    0.7.0.1
 */