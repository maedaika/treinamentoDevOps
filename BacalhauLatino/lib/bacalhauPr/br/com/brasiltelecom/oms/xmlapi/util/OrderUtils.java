/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.util;
/*   2:    */ 
/*   3:    */ import br.com.brasiltelecom.oms.data.OMSException;
/*   4:    */ import br.com.brasiltelecom.oms.data.Order;
/*   5:    */ import java.io.ByteArrayInputStream;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Collection;
/*   9:    */ import java.util.StringTokenizer;
/*  10:    */ import javax.xml.parsers.DocumentBuilder;
/*  11:    */ import javax.xml.parsers.DocumentBuilderFactory;
/*  12:    */ import org.w3c.dom.Document;
/*  13:    */ import org.w3c.dom.Element;
/*  14:    */ import org.w3c.dom.NamedNodeMap;
/*  15:    */ import org.w3c.dom.Node;
/*  16:    */ import org.w3c.dom.NodeList;
/*  17:    */ 
/*  18:    */ public class OrderUtils
/*  19:    */ {
/*  20: 24 */   private static DocumentBuilderFactory xmlFactory = ;
/*  21:    */   
/*  22:    */   public static String getOrderRequest(Order order, boolean accept)
/*  23:    */   {
/*  24: 51 */     return 
/*  25:    */     
/*  26:    */ 
/*  27: 54 */       "<GetOrder.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>" + order.getId() + "</OrderID>" + "<OrderHistID>" + order.getHist() + "</OrderHistID><Accept>" + accept + "</Accept></GetOrder.Request>";
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static String getOrderRequest(String orderId, String orderHist, boolean accept)
/*  31:    */   {
/*  32: 70 */     return getOrderRequest(new Order(orderId, orderHist), accept);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static String receiveOrderRequest(Order order)
/*  36:    */   {
/*  37: 81 */     return receiveOrderRequest(order.getId(), order.getHist());
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static String receiveOrderRequest(String orderId, String orderHist)
/*  41:    */   {
/*  42: 94 */     return 
/*  43:    */     
/*  44: 96 */       "<ReceiveOrder.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>" + orderId + "</OrderID>" + "<OrderHistID>" + orderHist + "</OrderHistID></ReceiveOrder.Request>";
/*  45:    */   }
/*  46:    */   
/*  47:    */   public static Order parseOrderResponse(String orderResponse)
/*  48:    */     throws OMSException
/*  49:    */   {
/*  50:108 */     Element orderXml = parseResponse(orderResponse);
/*  51:109 */     Node id = orderXml.getElementsByTagName("OrderID").item(0).getChildNodes()
/*  52:110 */       .item(0);
/*  53:111 */     Node hist = orderXml.getElementsByTagName("OrderHistID").item(0)
/*  54:112 */       .getChildNodes().item(0);
/*  55:113 */     Element root = (Element)orderXml.getElementsByTagName("_root").item(0);
/*  56:114 */     return new Order(id.getNodeValue(), hist.getNodeValue(), root);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static Element parseResponse(String xml)
/*  60:    */     throws OMSException
/*  61:    */   {
/*  62:118 */     Element response = null;
/*  63:    */     try
/*  64:    */     {
/*  65:121 */       response = 
/*  66:122 */         xmlFactory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes())).getDocumentElement();
/*  67:    */     }
/*  68:    */     catch (Exception ex)
/*  69:    */     {
/*  70:124 */       System.err.println("error parsing xml: " + xml);
/*  71:125 */       throw new OMSException("Erro ao ler o XML", ex);
/*  72:    */     }
/*  73:127 */     response.normalize();
/*  74:128 */     String name = response.getTagName();
/*  75:129 */     if (name.endsWith(".Error"))
/*  76:    */     {
/*  77:130 */       NamedNodeMap atributos = response.getFirstChild().getAttributes();
/*  78:131 */       Node codigo = atributos.getNamedItem("code");
/*  79:132 */       Node desc = atributos.getNamedItem("desc");
/*  80:133 */       System.err.println("erro:" + xml);
/*  81:134 */       throw new OMSException(desc.getNodeValue() + " (" + codigo.getNodeValue() + 
/*  82:135 */         ")");
/*  83:    */     }
/*  84:137 */     return response;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public static String completeOrderRequest(Order order, String status)
/*  88:    */   {
/*  89:141 */     return 
/*  90:    */     
/*  91:    */ 
/*  92:144 */       "<CompleteOrder.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>" + order.getId() + "</OrderID>" + "<OrderHistID>" + order.getHist() + "</OrderHistID>" + "<Status>" + status + "</Status>" + "</CompleteOrder.Request>";
/*  93:    */   }
/*  94:    */   
/*  95:    */   public static String completeOrderRequest(String orderId, String orderHist, String status)
/*  96:    */   {
/*  97:149 */     return completeOrderRequest(new Order(orderId, orderHist), status);
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static String assignOrderRequest(String orderId, String orderHist, String user)
/* 101:    */   {
/* 102:154 */     return 
/* 103:    */     
/* 104:156 */       "<AssignOrder.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>" + orderId + "</OrderID>" + "<OrderHistID>" + orderHist + "</OrderHistID><User>" + user + "</User></AssignOrder.Request>";
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static String setException(String orderId, String orderHist, String exception)
/* 108:    */   {
/* 109:161 */     return 
/* 110:    */     
/* 111:    */ 
/* 112:164 */       "<SetException.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>" + orderId + "</OrderID><OrderHistID>" + orderHist + "</OrderHistID><Status>" + exception + "</Status></SetException.Request>";
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static String getIdHistCommonHeader(Order order)
/* 116:    */   {
/* 117:168 */     return 
/* 118:169 */       "<OrderID>" + order.getId() + "</OrderID><OrderHistID>" + order.getHist() + "</OrderHistID>";
/* 119:    */   }
/* 120:    */   
/* 121:    */   public static Collection findElement(Order order, String path, String value, String separator)
/* 122:    */   {
/* 123:174 */     StringTokenizer tokenizer = new StringTokenizer(path, separator);
/* 124:175 */     String[] paths = new String[tokenizer.countTokens()];
/* 125:176 */     ArrayList elements = new ArrayList();
/* 126:177 */     for (int i = 0; tokenizer.hasMoreTokens(); i++) {
/* 127:178 */       paths[i] = tokenizer.nextToken();
/* 128:    */     }
/* 129:181 */     NodeList list = order.getRoot().getElementsByTagName(
/* 130:182 */       paths[(paths.length - 1)]);
/* 131:184 */     for (int i = 0; i < list.getLength(); i++) {
/* 132:185 */       if ((list.item(i) instanceof Element))
/* 133:    */       {
/* 134:188 */         Element element = (Element)list.item(i);
/* 135:189 */         if ((matchNonIndexedPath(element, paths)) && 
/* 136:190 */           (matchTextValue(element, value))) {
/* 137:191 */           elements.add(element);
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:195 */     return elements;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public static boolean matchTextValue(Element element, String value)
/* 145:    */   {
/* 146:199 */     NodeList list = element.getChildNodes();
/* 147:200 */     for (int i = 0; i < list.getLength(); i++) {
/* 148:    */       try
/* 149:    */       {
/* 150:202 */         if (list.item(i).getNodeValue().equals(value)) {
/* 151:203 */           return true;
/* 152:    */         }
/* 153:    */       }
/* 154:    */       catch (Exception localException) {}
/* 155:    */     }
/* 156:208 */     return false;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public static boolean matchNonIndexedPath(Element element, String[] path)
/* 160:    */   {
/* 161:212 */     return matchNonIndexedPath(element, path, path.length - 1);
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static boolean matchNonIndexedPath(Element element, String[] path, int index)
/* 165:    */   {
/* 166:217 */     if (index < 0) {
/* 167:218 */       return element.getTagName().equals("_root");
/* 168:    */     }
/* 169:219 */     if (element.getTagName().equals(path[index])) {
/* 170:220 */       return matchNonIndexedPath((Element)element.getParentNode(), path, 
/* 171:221 */         index - 1);
/* 172:    */     }
/* 173:223 */     return false;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static String updateContent(Element root, String path, String value, String separator)
/* 177:    */   {
/* 178:233 */     StringTokenizer tokenizer = new StringTokenizer(path, separator);
/* 179:234 */     String[] paths = new String[tokenizer.countTokens()];
/* 180:235 */     for (int i = 0; i < paths.length; i++) {
/* 181:236 */       paths[i] = tokenizer.nextToken();
/* 182:    */     }
/* 183:239 */     Element last = root;
/* 184:240 */     Element lastExisting = last;
/* 185:241 */     int lastIndex = 0;
/* 186:244 */     while ((last != null) && (lastIndex < paths.length))
/* 187:    */     {
/* 188:245 */       last = OrderUtils.Update.getLastPath(last, paths[lastIndex]);
/* 189:246 */       if (last != null)
/* 190:    */       {
/* 191:247 */         lastExisting = last;
/* 192:248 */         lastIndex++;
/* 193:    */       }
/* 194:    */     }
/* 195:252 */     lastExisting = last != null ? last : lastExisting;
/* 196:    */     
/* 197:254 */     String operation = lastIndex == paths.length ? "Update" : "Add";
/* 198:255 */     System.out.println("lastExisting:" + lastExisting.getTagName() + 
/* 199:256 */       ", lastIndex:" + lastIndex);
/* 200:257 */     System.out.println("operation:" + operation);
/* 201:258 */     System.out.println("updatePath:" + OrderUtils.Update.getPath(lastExisting));
/* 202:259 */     System.out.println("updateBody:" + 
/* 203:260 */       OrderUtils.Update.getUpdateBody(paths, lastIndex, value));
/* 204:261 */     return "<" + operation + " path=\"" + OrderUtils.Update.getPath(lastExisting) + "\">" + 
/* 205:262 */       OrderUtils.Update.getUpdateBody(paths, lastIndex, value) + "</" + operation + 
/* 206:263 */       ">";
/* 207:    */   }
/* 208:    */   
/* 209:    */   public static String updateOrderRequest(Order order, String body)
/* 210:    */   {
/* 211:267 */     return 
/* 212:    */     
/* 213:269 */       "<UpdateOrder.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>" + order.getId() + "</OrderID>" + "<OrderHistID>" + order.getHist() + "</OrderHistID>" + body + "</UpdateOrder.Request>";
/* 214:    */   }
/* 215:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.util.OrderUtils
 * JD-Core Version:    0.7.0.1
 */