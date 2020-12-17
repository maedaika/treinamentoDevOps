/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.util;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.Element;
/*   4:    */ import org.w3c.dom.Node;
/*   5:    */ import org.w3c.dom.NodeList;
/*   6:    */ 
/*   7:    */ public class OrderUtils$Update
/*   8:    */ {
/*   9:    */   protected static Element getLastPath(Element element, String lastPath)
/*  10:    */   {
/*  11:287 */     int multiIndex = lastPath.indexOf('[');
/*  12:288 */     if (multiIndex < 0) {
/*  13:289 */       return getLastPathSimple(element, lastPath);
/*  14:    */     }
/*  15:291 */     int index = Integer.parseInt(lastPath.substring(multiIndex + 1, 
/*  16:292 */       lastPath.indexOf(']')));
/*  17:293 */     return getLastPathMulti(element, lastPath.substring(0, multiIndex), 
/*  18:294 */       index);
/*  19:    */   }
/*  20:    */   
/*  21:    */   protected static Element getLastPathMulti(Element element, String lastPath, int index)
/*  22:    */   {
/*  23:313 */     NodeList list = element.getChildNodes();
/*  24:314 */     int match = 0;
/*  25:315 */     Element result = null;
/*  26:316 */     for (int i = 0; (match != index) && (i < list.getLength()); i++) {
/*  27:317 */       if ((list.item(i) instanceof Element))
/*  28:    */       {
/*  29:320 */         result = (Element)list.item(i);
/*  30:321 */         if ((result.getTagName() != null) && (lastPath.equals(result.getTagName()))) {
/*  31:322 */           match++;
/*  32:    */         }
/*  33:    */       }
/*  34:    */     }
/*  35:325 */     return match == index ? result : null;
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected static Element getLastPathSimple(Element element, String lastPath)
/*  39:    */   {
/*  40:339 */     NodeList list = element.getChildNodes();
/*  41:340 */     boolean exist = false;
/*  42:341 */     Element result = null;
/*  43:342 */     for (int i = 0; i < list.getLength(); i++) {
/*  44:343 */       if ((list.item(i) instanceof Element))
/*  45:    */       {
/*  46:346 */         result = (Element)list.item(i);
/*  47:347 */         if ((result.getTagName() != null) && (lastPath.equals(result.getTagName())))
/*  48:    */         {
/*  49:348 */           if (!result.hasAttribute("index"))
/*  50:    */           {
/*  51:349 */             exist = true;
/*  52:350 */             break;
/*  53:    */           }
/*  54:352 */           exist = false;
/*  55:353 */           break;
/*  56:    */         }
/*  57:    */       }
/*  58:    */     }
/*  59:357 */     return exist ? result : null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static String getPath(Element element)
/*  63:    */   {
/*  64:366 */     if ((element == null) || (element.getTagName() == null) || 
/*  65:367 */       (element.getTagName().equals("_root"))) {
/*  66:368 */       return "/";
/*  67:    */     }
/*  68:371 */     String path = "";
/*  69:372 */     while ((element != null) && (element.getTagName() != null) && (!
/*  70:373 */       element.getTagName().equals("_root")))
/*  71:    */     {
/*  72:374 */       String tagName = element.getTagName();
/*  73:375 */       String index = element.hasAttribute("index") ? "[@index='" + 
/*  74:376 */         element.getAttribute("index") + "']" : "";
/*  75:377 */       path = "/" + tagName + index + path;
/*  76:378 */       element = (Element)element.getParentNode();
/*  77:    */     }
/*  78:380 */     return path;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public static String getUpdateBody(String[] paths, int lastIndex, String value)
/*  82:    */   {
/*  83:404 */     if (lastIndex >= paths.length) {
/*  84:405 */       return value;
/*  85:    */     }
/*  86:407 */     return 
/*  87:    */     
/*  88:409 */       "<" + paths[lastIndex] + ">" + getUpdateBody(paths, lastIndex + 1, value) + "</" + paths[lastIndex] + ">";
/*  89:    */   }
/*  90:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.util.OrderUtils.Update
 * JD-Core Version:    0.7.0.1
 */