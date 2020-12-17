/*  1:   */ package br.com.brasiltelecom.oms.xmlapi;
/*  2:   */ 
/*  3:   */ import java.io.BufferedReader;
/*  4:   */ import java.io.IOException;
/*  5:   */ import java.io.InputStream;
/*  6:   */ import java.io.InputStreamReader;
/*  7:   */ import java.io.PrintStream;
/*  8:   */ import java.net.HttpURLConnection;
/*  9:   */ import java.net.URLConnection;
/* 10:   */ import java.nio.charset.Charset;
/* 11:   */ 
/* 12:   */ public class OMSResponse
/* 13:   */ {
/* 14:   */   int code;
/* 15:   */   String message;
/* 16:   */   String data;
/* 17:   */   
/* 18:   */   public OMSResponse(HttpURLConnection con)
/* 19:   */     throws IOException
/* 20:   */   {
/* 21:25 */     this.code = con.getResponseCode();
/* 22:26 */     this.message = con.getResponseMessage();
/* 23:27 */     InputStream in = con.getInputStream();
/* 24:28 */     InputStreamReader reader = new InputStreamReader(in, Charset.forName("ISO-8859-1"));
/* 25:29 */     BufferedReader buffer = new BufferedReader(reader);
/* 26:30 */     StringBuffer s = new StringBuffer();
/* 27:31 */     for (String c = buffer.readLine(); c != null; c = buffer.readLine()) {
/* 28:32 */       s.append(c);
/* 29:   */     }
/* 30:35 */     this.data = s.toString();
/* 31:36 */     if (!this.data.trim().startsWith("<?xml")) {
/* 32:37 */       this.data = ("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>" + this.data);
/* 33:   */     }
/* 34:39 */     System.out.println("OMSResponse: data:" + this.data);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int getCode()
/* 38:   */   {
/* 39:46 */     return this.code;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public String getData()
/* 43:   */   {
/* 44:52 */     return this.data;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public String getMessage()
/* 48:   */   {
/* 49:58 */     return this.message;
/* 50:   */   }
/* 51:   */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.OMSResponse
 * JD-Core Version:    0.7.0.1
 */