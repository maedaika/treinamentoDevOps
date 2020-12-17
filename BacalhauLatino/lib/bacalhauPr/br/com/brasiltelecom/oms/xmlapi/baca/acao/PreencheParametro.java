/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*   2:    */ 
/*   3:    */ import br.com.brasiltelecom.oms.data.Order;
/*   4:    */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*   5:    */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*   6:    */ import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
/*   7:    */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*   8:    */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.Properties;
/*  11:    */ import java.util.ResourceBundle;
/*  12:    */ import java.util.Set;
/*  13:    */ import java.util.TreeMap;
/*  14:    */ import javax.swing.AbstractButton;
/*  15:    */ import javax.swing.JCheckBox;
/*  16:    */ import javax.swing.JComboBox;
/*  17:    */ import javax.swing.JLabel;
/*  18:    */ import javax.swing.JOptionPane;
/*  19:    */ import org.w3c.dom.Element;
/*  20:    */ import org.w3c.dom.Node;
/*  21:    */ import org.w3c.dom.NodeList;
/*  22:    */ 
/*  23:    */ public class PreencheParametro
/*  24:    */   extends AcaoAceita
/*  25:    */ {
/*  26:    */   String nomeParametro;
/*  27: 20 */   JComboBox combo = new JComboBox();
/*  28: 22 */   static TreeMap valores = new TreeMap();
/*  29: 24 */   JCheckBox completa = new JCheckBox();
/*  30: 26 */   JCheckBox isCampoAntigo = new JCheckBox();
/*  31:    */   AcceptComplete acceptComplete;
/*  32:    */   
/*  33:    */   static
/*  34:    */   {
/*  35: 31 */     valores.put("simcard_imsi", "simcard_imsi");
/*  36:    */   }
/*  37:    */   
/*  38:    */   public PreencheParametro()
/*  39:    */   {
/*  40: 36 */     this.combo = new JComboBox(valores.keySet().toArray());
/*  41: 37 */     this.combo.setEditable(true);
/*  42: 38 */     this.combo.addActionListener(CustomizaComboListener.getCustomiaComboListener(
/*  43: 39 */       this, valores));
/*  44:    */   }
/*  45:    */   
/*  46:    */   public boolean inicializa()
/*  47:    */   {
/*  48: 43 */     JLabel lblNome = new JLabel(this.baca.getResource()
/*  49: 44 */       .getString("gui.label.nomeParametro"));
/*  50: 45 */     this.completa.setText(this.baca.getResource().getString("gui.label.completa"));
/*  51: 46 */     this.isCampoAntigo.setText(this.baca.getResource().getString("gui.label.antigo"));
/*  52:    */     
/*  53: 48 */     Object[] msg = { lblNome, this.combo, this.isCampoAntigo, this.completa };
/*  54:    */     
/*  55: 50 */     int option = 
/*  56: 51 */       JOptionPane.showConfirmDialog(this.baca, msg, this.baca.getResource()
/*  57: 52 */       .getString("gui.title.preencheParametro"), 2);
/*  58: 53 */     if (option == 0)
/*  59:    */     {
/*  60: 54 */       this.nomeParametro = ((String)this.combo.getSelectedItem());
/*  61: 55 */       if (this.completa.isSelected())
/*  62:    */       {
/*  63: 56 */         this.acceptComplete = new AcceptCompleteEditavel();
/*  64: 57 */         return this.acceptComplete.inicializa();
/*  65:    */       }
/*  66: 59 */       return true;
/*  67:    */     }
/*  68: 62 */     return false;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void processaAceita(String[] args, Order order)
/*  72:    */     throws Exception
/*  73:    */   {
/*  74:    */     try
/*  75:    */     {
/*  76: 68 */       this.console.print("  - Preenchendo os campos " + order.getId() + "... ");
/*  77: 69 */       String update = criaStringUpdate(order, args[2]);
/*  78: 70 */       System.out.println(update);
/*  79: 71 */       OMSResponse response = this.connection.send(update);
/*  80: 72 */       order = OrderUtils.parseOrderResponse(response.getData());
/*  81: 73 */       this.console.println("[" + response.getMessage() + "]");
/*  82: 74 */       if (this.completa.isSelected())
/*  83:    */       {
/*  84: 75 */         args[0] = order.getId();
/*  85: 76 */         args[1] = order.getHist();
/*  86: 77 */         this.acceptComplete.processaAceita(args, order);
/*  87:    */       }
/*  88:    */       else
/*  89:    */       {
/*  90: 79 */         tentaReceber(order);
/*  91:    */       }
/*  92:    */     }
/*  93:    */     catch (Exception ex)
/*  94:    */     {
/*  95: 82 */       this.console.println("FALHA - " + ex.getMessage());
/*  96: 83 */       ex.printStackTrace();
/*  97: 84 */       if (order != null)
/*  98:    */       {
/*  99: 85 */         args[0] = order.getId();
/* 100: 86 */         args[1] = order.getHist();
/* 101:    */       }
/* 102: 88 */       throw ex;
/* 103:    */     }
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String criaStringUpdate(Order order, String value)
/* 107:    */     throws Exception
/* 108:    */   {
/* 109: 93 */     Element root = order.getRoot();
/* 110: 94 */     String lookFor = this.isCampoAntigo.isSelected() ? "parametros_originais_smp" : "parametros_smp";
/* 111: 95 */     NodeList list = root.getElementsByTagName(lookFor);
/* 112: 96 */     String body = "";
/* 113: 97 */     for (int i = 0; i < list.getLength(); i++)
/* 114:    */     {
/* 115: 98 */       Element parametro = (Element)list.item(i);
/* 116: 99 */       NodeList nomes = parametro.getElementsByTagName("nome_parametro_smp");
/* 117:100 */       if ((nomes.getLength() > 0) && (nomes.item(0).hasChildNodes()))
/* 118:    */       {
/* 119:101 */         String nome = nomes.item(0).getChildNodes().item(0).getNodeValue();
/* 120:102 */         if ((nome != null) && (nome.equals(this.nomeParametro))) {
/* 121:103 */           body = 
/* 122:104 */             body + OrderUtils.updateContent(parametro, "valor_parametro_smp", value, this.baca.getServerConfig().getProperty("separator"));
/* 123:    */         }
/* 124:    */       }
/* 125:    */     }
/* 126:108 */     if (body.equals("")) {
/* 127:109 */       throw new Exception("Parâmetro '" + this.nomeParametro + "' não Encontrado!!!");
/* 128:    */     }
/* 129:111 */     return OrderUtils.updateOrderRequest(order, body);
/* 130:    */   }
/* 131:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.acao.PreencheParametro
 * JD-Core Version:    0.7.0.1
 */