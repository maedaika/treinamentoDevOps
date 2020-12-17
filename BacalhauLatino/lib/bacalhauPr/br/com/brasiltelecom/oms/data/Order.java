/*   1:    */ package br.com.brasiltelecom.oms.data;
/*   2:    */ 
/*   3:    */ import org.w3c.dom.Element;
/*   4:    */ 
/*   5:    */ public class Order
/*   6:    */ {
/*   7:    */   private int id;
/*   8:    */   private int hist;
/*   9:    */   private String state;
/*  10:    */   private Element root;
/*  11:    */   
/*  12:    */   public Order(String id, String hist, String state, Element root)
/*  13:    */   {
/*  14: 15 */     this.id = Integer.parseInt(id);
/*  15: 16 */     this.hist = Integer.parseInt(hist);
/*  16: 17 */     this.state = state;
/*  17: 18 */     this.root = root;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public Order(String id, String hist, String state)
/*  21:    */   {
/*  22: 22 */     this(id, hist, state, null);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public Order(String id, String hist)
/*  26:    */   {
/*  27: 26 */     this(id, hist, null);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Order(String id, String hist, Element root)
/*  31:    */   {
/*  32: 30 */     this(id, hist, "UNKNOWN", root);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Order(int id, int hist, String state)
/*  36:    */   {
/*  37: 34 */     this(id, hist, state, null);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Order(int id, int hist, String state, Element root)
/*  41:    */   {
/*  42: 38 */     this.id = id;
/*  43: 39 */     this.hist = hist;
/*  44: 40 */     this.state = state;
/*  45: 41 */     this.root = root;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public Order() {}
/*  49:    */   
/*  50:    */   public int getHist()
/*  51:    */   {
/*  52: 52 */     return this.hist;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setHist(int hist)
/*  56:    */   {
/*  57: 60 */     this.hist = hist;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int getId()
/*  61:    */   {
/*  62: 67 */     return this.id;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void setId(int id)
/*  66:    */   {
/*  67: 75 */     this.id = id;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String getState()
/*  71:    */   {
/*  72: 82 */     return this.state;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setState(String state)
/*  76:    */   {
/*  77: 90 */     this.state = state;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Element getRoot()
/*  81:    */   {
/*  82: 95 */     return this.root;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setRoot(Element root)
/*  86:    */   {
/*  87:100 */     this.root = root;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public String toString()
/*  91:    */   {
/*  92:104 */     return "Order: " + this.id + ";" + this.hist;
/*  93:    */   }
/*  94:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.data.Order
 * JD-Core Version:    0.7.0.1
 */