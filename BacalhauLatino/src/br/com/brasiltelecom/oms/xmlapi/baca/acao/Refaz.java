/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*   2:    */ 
/*   3:    */ import br.com.brasiltelecom.oms.data.Order;
/*   4:    */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*   5:    */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*   6:    */ import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
/*   7:    */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*   8:    */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;
/*   9:    */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils.Update;
/*  10:    */ import java.util.ResourceBundle;
/*  11:    */ import java.util.Set;
/*  12:    */ import java.util.TreeMap;
/*  13:    */ import javax.swing.AbstractButton;
/*  14:    */ import javax.swing.JCheckBox;
/*  15:    */ import javax.swing.JComboBox;
/*  16:    */ import javax.swing.JLabel;
/*  17:    */ import javax.swing.JOptionPane;
/*  18:    */ import org.w3c.dom.Element;
/*  19:    */ import org.w3c.dom.Node;
/*  20:    */ import org.w3c.dom.NodeList;
/*  21:    */ 
/*  22:    */ public class Refaz
/*  23:    */   extends AcaoAceita
/*  24:    */ {
/*  25:    */   String nomeStatus;
/*  26: 25 */   JComboBox cmbStatus = new JComboBox();
/*  27: 27 */   TreeMap status = new TreeMap();
/*  28: 29 */   JCheckBox completa = new JCheckBox();
/*  29:    */   AcceptComplete acceptComplete;
/*  30:    */   
/*  31:    */   public Refaz()
/*  32:    */   {
/*  33: 34 */     this.status.put("NOK", "NOK");
/*  34: 35 */     this.status.put("NÃO FEITO", "NAO_FEITO");
/*  35: 36 */     this.status.put("OK", "OK");
/*  36:    */     
/*  37: 38 */     this.cmbStatus = new JComboBox(this.status.keySet().toArray());
/*  38:    */   }
/*  39:    */   
/*  40:    */   public boolean inicializa()
/*  41:    */   {
/*  42: 42 */     JLabel lblStatus = new JLabel(this.baca.getResource().getString(
/*  43: 43 */       "gui.label.status"));
/*  44: 44 */     this.completa.setText(this.baca.getResource().getString("gui.label.completa"));
/*  45:    */     
/*  46: 46 */     Object[] msg = { lblStatus, this.cmbStatus, this.completa };
/*  47:    */     
/*  48: 48 */     int option = JOptionPane.showConfirmDialog(this.baca, msg, this.baca.getResource()
/*  49: 49 */       .getString("gui.title.trocaServico"), 2);
/*  50: 50 */     if (option == 0)
/*  51:    */     {
/*  52: 51 */       this.nomeStatus = ((String)this.cmbStatus.getSelectedItem());
/*  53: 52 */       this.nomeStatus = 
/*  54: 53 */         ((String)(this.status.get(this.nomeStatus) != null ? this.status.get(this.nomeStatus) : this.nomeStatus));
/*  55: 54 */       if (this.completa.isSelected())
/*  56:    */       {
/*  57: 55 */         this.acceptComplete = new AcceptCompleteEditavel();
/*  58: 56 */         return this.acceptComplete.inicializa();
/*  59:    */       }
/*  60: 58 */       return true;
/*  61:    */     }
/*  62: 61 */     return false;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void processaAceita(String[] args, Order order)
/*  66:    */     throws Exception
/*  67:    */   {
/*  68:    */     try
/*  69:    */     {
/*  70: 67 */       this.console.print("  - Alterando serviço " + order.getId() + "... ");
/*  71: 68 */       String update = criaStringUpdate(order);
/*  72: 69 */       OMSResponse response = this.connection.send(update);
/*  73: 70 */       order = OrderUtils.parseOrderResponse(response.getData());
/*  74: 71 */       this.console.println("[" + response.getMessage() + "]");
/*  75: 72 */       if (this.completa.isSelected())
/*  76:    */       {
/*  77: 73 */        // args[0] = order.getId();
/*  78: 74 */         //args[1] = order.getHist();
/*  79: 75 */         this.acceptComplete.processaAceita(args, order);
/*  80:    */       }
/*  81:    */       else
/*  82:    */       {
/*  83: 77 */         tentaReceber(order);
/*  84:    */       }
/*  85:    */     }
/*  86:    */     catch (Exception ex)
/*  87:    */     {
/*  88: 80 */       this.console.println("FALHA - " + ex.getMessage());
/*  89: 81 */       ex.printStackTrace();
/*  90: 82 */       if (order != null){
/*  92: 83 */         //args[0] = order.getId();
/*  93: 84 */         //args[1] = order.getHist();
/*  94:    */       }
/*  95: 86 */       throw ex;
/*  96:    */     }
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String criaStringUpdate(Order order)
/* 100:    */     throws Exception
/* 101:    */   {
/* 102: 91 */     String update = "<UpdateOrder.Request>";
/* 103: 92 */     update = update + OrderUtils.getIdHistCommonHeader(order);
/* 104:    */     
/* 105: 94 */     NodeList list = order.getRoot().getElementsByTagName("status_servico_smp");
/* 106: 96 */     for (int i = 0; i < list.getLength(); i++)
/* 107:    */     {
/* 108: 97 */       Element element = (Element)list.item(i);
/* 109: 98 */       update = update + "<Update path=\"" + OrderUtils.Update.getPath((Element)element.getParentNode()) + "/status_servico_smp\">";
/* 110: 99 */       update = update + this.nomeStatus + "</Update>";
/* 111:    */     }
/* 112:104 */     return update += "</UpdateOrder.Request>";
/* 113:    */   }
/* 114:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     br.com.brasiltelecom.oms.xmlapi.baca.acao.Refaz
 * JD-Core Version:    0.7.0.1
 */