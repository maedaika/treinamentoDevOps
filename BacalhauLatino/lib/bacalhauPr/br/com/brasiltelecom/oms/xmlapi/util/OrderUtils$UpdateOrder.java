/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.util;
/*   2:    */ 
/*   3:    */ import br.com.brasiltelecom.oms.data.Order;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ import java.util.StringTokenizer;
/*   6:    */ import org.w3c.dom.Element;
/*   7:    */ import org.w3c.dom.Node;
/*   8:    */ import org.w3c.dom.NodeList;
/*   9:    */ 
/*  10:    */ public class OrderUtils$UpdateOrder
/*  11:    */ {
/*  12:    */   int profundidade;
/*  13:    */   int ultimoExistente;
/*  14:    */   String valor;
/*  15:    */   String[] paths;
/*  16:    */   int[] instances;
/*  17:    */   String[] indexes;
/*  18:    */   Order order;
/*  19:    */   
/*  20:    */   public OrderUtils$UpdateOrder(Order order, String fatherPath, Element fatherElement, String path, String valor, String delim)
/*  21:    */   {
/*  22:302 */     System.out.println(order + " - " + fatherPath + " - " + fatherElement + 
/*  23:303 */       " - " + path + " - " + valor + " - " + delim);
/*  24:304 */     StringTokenizer tokenizerFather = new StringTokenizer(fatherPath, delim);
/*  25:305 */     StringTokenizer tokenizerChild = new StringTokenizer(path, delim);
/*  26:    */     
/*  27:307 */     this.order = order;
/*  28:308 */     this.valor = valor;
/*  29:    */     
/*  30:310 */     this.ultimoExistente = tokenizerFather.countTokens();
/*  31:311 */     this.profundidade = (this.ultimoExistente + tokenizerChild.countTokens());
/*  32:312 */     this.paths = new String[this.profundidade];
/*  33:313 */     this.indexes = new String[this.profundidade];
/*  34:314 */     this.instances = new int[this.profundidade];
/*  35:315 */     this.ultimoExistente = tokenizerFather.countTokens();
/*  36:    */     
/*  37:317 */     int count = 0;
/*  38:318 */     while (tokenizerFather.hasMoreTokens()) {
/*  39:319 */       this.paths[(count++)] = tokenizerFather.nextToken();
/*  40:    */     }
/*  41:321 */     while (tokenizerChild.hasMoreTokens()) {
/*  42:322 */       this.paths[(count++)] = tokenizerChild.nextToken();
/*  43:    */     }
/*  44:324 */     System.out.println("%completepath: 1ultimoExistente:" + this.ultimoExistente);
/*  45:325 */     parsePaths();
/*  46:326 */     for (int i = 0; i < this.profundidade; i++) {
/*  47:327 */       if (this.instances[i] >= 0) {
/*  48:328 */         this.indexes[i] = this.instances[i];
/*  49:    */       }
/*  50:    */     }
/*  51:331 */     System.out.println("%completepath: 2ultimoExistente:" + this.ultimoExistente);
/*  52:332 */     parseIndexes(fatherElement, this.ultimoExistente);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public OrderUtils$UpdateOrder(Order order, String path, String valor, String delim)
/*  56:    */   {
/*  57:336 */     System.out.println(order + " - " + path + " - " + valor + " - " + delim);
/*  58:337 */     StringTokenizer tokenizer = new StringTokenizer(path, delim);
/*  59:338 */     this.order = order;
/*  60:339 */     this.valor = valor;
/*  61:    */     
/*  62:341 */     this.profundidade = tokenizer.countTokens();
/*  63:342 */     this.paths = new String[this.profundidade];
/*  64:343 */     this.indexes = new String[this.profundidade];
/*  65:344 */     this.instances = new int[this.profundidade];
/*  66:345 */     for (int i = 0; tokenizer.hasMoreElements(); i++) {
/*  67:346 */       this.paths[i] = tokenizer.nextToken();
/*  68:    */     }
/*  69:350 */     parsePaths();
/*  70:    */   }
/*  71:    */   
/*  72:    */   public String getRequestSimplePath()
/*  73:    */   {
/*  74:360 */     parseIndexes(this.order.getRoot(), 0);
/*  75:361 */     return getRequest();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String getRequestCompletePath()
/*  79:    */   {
/*  80:365 */     return getRequest();
/*  81:    */   }
/*  82:    */   
/*  83:    */   private String getRequest()
/*  84:    */   {
/*  85:369 */     return 
/*  86:    */     
/*  87:    */ 
/*  88:    */ 
/*  89:373 */       "<UpdateOrder.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>" + this.order.getId() + "</OrderID>" + "<OrderHistID>" + this.order.getHist() + "</OrderHistID><" + getOperation() + " path=\"" + getPath(this.ultimoExistente) + "\">" + getBody(this.ultimoExistente) + "</" + getOperation() + "></UpdateOrder.Request>";
/*  90:    */   }
/*  91:    */   
/*  92:    */   public String getPath(int ultimoElemento)
/*  93:    */   {
/*  94:377 */     if (ultimoElemento < 1) {
/*  95:378 */       return "/";
/*  96:    */     }
/*  97:380 */     String path = "";
/*  98:381 */     for (int i = 0; i < ultimoElemento; i++)
/*  99:    */     {
/* 100:382 */       String index = (this.indexes[i] != null) && (!this.indexes[i].equalsIgnoreCase("")) ? "[@index='" + 
/* 101:383 */         this.indexes[i] + "']" : 
/* 102:384 */         "";
/* 103:385 */       path = path + "/" + this.paths[i] + index;
/* 104:    */     }
/* 105:387 */     return path;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getBody(int primeiroElemento)
/* 109:    */   {
/* 110:391 */     if (primeiroElemento >= this.profundidade) {
/* 111:392 */       return this.valor;
/* 112:    */     }
/* 113:394 */     String index = (this.indexes[primeiroElemento] != null) && 
/* 114:395 */       (!this.indexes[primeiroElemento].equals("")) ? " index=\"" + this.indexes[primeiroElemento] + "\"" : "";
/* 115:396 */     String update = "<" + this.paths[primeiroElemento] + index + ">";
/* 116:397 */     update = update + getBody(primeiroElemento + 1);
/* 117:398 */     update = update + "</" + this.paths[primeiroElemento] + ">";
/* 118:399 */     return update;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public String getOperation()
/* 122:    */   {
/* 123:404 */     if (this.ultimoExistente == this.paths.length) {
/* 124:405 */       return "Update";
/* 125:    */     }
/* 126:407 */     return "Add";
/* 127:    */   }
/* 128:    */   
/* 129:    */   public void parsePaths()
/* 130:    */   {
/* 131:412 */     for (int i = 0; i < this.paths.length; i++)
/* 132:    */     {
/* 133:413 */       this.instances[i] = getItemNumber(this.paths[i]);
/* 134:414 */       this.paths[i] = getTagName(this.paths[i]);
/* 135:    */     }
/* 136:416 */     Element root = this.order.getRoot();
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void parseIndexes(Element pai, int numero)
/* 140:    */   {
/* 141:420 */     this.ultimoExistente = numero;
/* 142:421 */     if (numero >= this.profundidade) {
/* 143:422 */       return;
/* 144:    */     }
/* 145:424 */     String tag = this.paths[numero];
/* 146:425 */     int instance = this.instances[numero];
/* 147:426 */     System.out.println("%fazendo parse numero:" + numero + " tag:" + tag + 
/* 148:427 */       " instance:" + instance + " pai:" + pai);
/* 149:428 */     Element filho = null;
/* 150:429 */     if (instance >= 0)
/* 151:    */     {
/* 152:430 */       NodeList list = pai.getChildNodes();
/* 153:431 */       int contInstance = 0;
/* 154:432 */       for (int i = 0; i < list.getLength(); i++)
/* 155:    */       {
/* 156:433 */         Element temp = (Element)list.item(i);
/* 157:434 */         System.out.println("%  ->" + temp);
/* 158:435 */         if ((temp != null) && (temp.getTagName() != null) && 
/* 159:436 */           (temp.getTagName().equals(tag)))
/* 160:    */         {
/* 161:437 */           contInstance++;
/* 162:438 */           if (contInstance == instance)
/* 163:    */           {
/* 164:439 */             filho = temp;
/* 165:440 */             break;
/* 166:    */           }
/* 167:    */         }
/* 168:    */       }
/* 169:444 */       System.out.println("%multi-> contInstance:" + contInstance + 
/* 170:445 */         " instance:" + instance);
/* 171:446 */       if (contInstance != instance) {
/* 172:447 */         throw new IllegalArgumentException("Index inválido: " + instance);
/* 173:    */       }
/* 174:449 */       this.indexes[numero] = filho.getAttribute("index");
/* 175:450 */       parseIndexes(filho, numero + 1);
/* 176:    */     }
/* 177:    */     else
/* 178:    */     {
/* 179:452 */       NodeList list = pai.getChildNodes();
/* 180:453 */       int contInstance = 0;
/* 181:454 */       boolean existe = false;
/* 182:455 */       System.out.print("%simple->pai:" + pai + " filhos:");
/* 183:456 */       for (int i = 0; i < list.getLength(); i++)
/* 184:    */       {
/* 185:457 */         Element temp = (Element)list.item(i);
/* 186:458 */         System.out.print(temp + ",");
/* 187:459 */         if ((temp != null) && (temp.getTagName() != null) && 
/* 188:460 */           (temp.getTagName().equals(tag))) {
/* 189:461 */           if ((!existe) && (!temp.hasAttribute("index")))
/* 190:    */           {
/* 191:462 */             filho = temp;
/* 192:463 */             existe = true;
/* 193:    */           }
/* 194:    */           else
/* 195:    */           {
/* 196:465 */             filho = null;
/* 197:466 */             existe = false;
/* 198:467 */             break;
/* 199:    */           }
/* 200:    */         }
/* 201:    */       }
/* 202:471 */       System.out.println("\n%simple-> existe?:" + existe);
/* 203:472 */       if (existe) {
/* 204:473 */         parseIndexes(filho, numero + 1);
/* 205:    */       } else {}
/* 206:    */     }
/* 207:    */   }
/* 208:    */   
/* 209:    */   private int getItemNumber(String path)
/* 210:    */   {
/* 211:481 */     int inicio = path.indexOf('[') + 1;
/* 212:482 */     int fim = path.indexOf(']');
/* 213:483 */     if (inicio == 0) {
/* 214:484 */       return -1;
/* 215:    */     }
/* 216:486 */     return Integer.parseInt(path.substring(inicio, fim));
/* 217:    */   }
/* 218:    */   
/* 219:    */   private String getTagName(String path)
/* 220:    */   {
/* 221:491 */     int inicio = path.indexOf('[');
/* 222:492 */     if (inicio < 0) {
/* 223:493 */       return path;
/* 224:    */     }
/* 225:495 */     return path.substring(0, path.indexOf('['));
/* 226:    */   }
/* 227:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.util.OrderUtils.UpdateOrder
 * JD-Core Version:    0.7.0.1
 */