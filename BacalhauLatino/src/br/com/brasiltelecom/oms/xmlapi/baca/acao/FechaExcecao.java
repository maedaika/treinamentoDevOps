/*  1:   */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*  2:   */ 
/*  3:   */ import br.com.brasiltelecom.oms.data.Order;
/*  4:   */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*  5:   */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*  6:   */ import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
/*  7:   */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*  8:   */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;
/*  9:   */ import java.io.PrintStream;
/* 10:   */ import java.util.ResourceBundle;
/* 11:   */ import javax.swing.JLabel;
/* 12:   */ import javax.swing.JOptionPane;
/* 13:   */ import javax.swing.JTextField;
/* 14:   */ import javax.swing.text.JTextComponent;
/* 15:   */ 
/* 16:   */ public class FechaExcecao
/* 17:   */   extends Acao
/* 18:   */ {
/* 19:13 */   String solicitadoPor = "";
/* 20:15 */   String motivo = "";
/* 21:17 */   String corpo = "";
/* 22:19 */   JTextField txtSolicitadoPor = new JTextField();
/* 23:20 */   JTextField txtMotivo = new JTextField();
/* 24:   */   
/* 25:   */   public boolean inicializa()
/* 26:   */   {
/* 27:23 */     JLabel lblSolicitadoPor = new JLabel(this.baca.getResource().getString(
/* 28:24 */       "gui.label.solicitadoPor"));
/* 29:25 */     JLabel lblMotivo = new JLabel(this.baca.getResource().getString(
/* 30:26 */       "gui.label.motivo"));
/* 31:   */     
/* 32:28 */     Object[] msg = { lblSolicitadoPor, this.txtSolicitadoPor, lblMotivo, this.txtMotivo };
/* 33:   */     
/* 34:30 */     int option = JOptionPane.showConfirmDialog(this.baca, msg, this.baca.getResource()
/* 35:31 */       .getString("gui.title.mnemonicSelect"), 2);
/* 36:32 */     if (option != 0) {
/* 37:33 */       return false;
/* 38:   */     }
/* 39:35 */     this.solicitadoPor = this.txtSolicitadoPor.getText();
/* 40:36 */     this.motivo = this.txtMotivo.getText();
/* 41:37 */     this.corpo = 
/* 42:   */     
/* 43:39 */       ("<Add path=\"/\"><dados_os_finalizada_smp><solicitado_por_smp>" + this.solicitadoPor + "</solicitado_por_smp><motivo_smp>" + this.motivo + "</motivo_smp></dados_os_finalizada_smp></Add>");
/* 44:40 */     return true;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public void processa(String[] args)
/* 48:   */     throws Exception
/* 49:   */   {
/* 50:45 */     OMSResponse response = null;
/* 51:   */     try
/* 52:   */     {
/* 53:48 */       this.console.print("  - Recuperando Hist " + args[0] + "... ");
/* 54:49 */       String recuperaHist = OrderUtils.getOrderProcessHistory(
/* 55:50 */         Integer.parseInt(args[0]));
/* 56:51 */       response = this.connection.send(recuperaHist);
/* 57:52 */       OrderUtils.parseResponse(response.getData());
/* 58:53 */       long hist = OrderUtils.getLastHistory(response.getData());
/* 59:54 */       //args[1] = hist;
/* 60:55 */       this.console.println("[" + response.getMessage() + "]");
/* 61:   */       
/* 62:57 */       this.console.print("  - Aceitando Ordem " + args[0] + "... ");
/* 63:58 */       String aceitar = OrderUtils.getOrderRequest(args[0], args[1], true);
/* 64:59 */       response = this.connection.send(aceitar);
/* 65:60 */       Order order = OrderUtils.parseOrderResponse(response.getData());
/* 66:61 */       //args[0] = order.getId();
/* 67:62 */      // args[1] = order.getHist();
/* 68:63 */       this.console.println("[" + response.getMessage() + "]");
/* 69:   */       
/* 70:65 */       this.console.print("  - Modificando Ordem " + args[0] + "... ");
/* 71:66 */       String modificar = OrderUtils.updateOrderRequest(order, this.corpo);
/* 72:67 */       response = this.connection.send(modificar);
/* 73:68 */       order = OrderUtils.parseOrderResponse(response.getData());
/* 74:69 */       //args[0] = order.getId();
/* 75:70 */      // args[1] = order.getHist();
/* 76:71 */       this.console.println("[" + response.getMessage() + "]");
/* 77:   */       
/* 78:73 */       this.console.print("  - Completando Ordem " + args[0] + "... ");
/* 79:74 */       String completando = OrderUtils.completeOrderRequest(order, 
/* 80:75 */         "completo_manual");
/* 81:76 */       response = this.connection.send(completando);
/* 82:77 */       OrderUtils.parseResponse(response.getData());
/* 83:78 */       this.console.println("[" + response.getMessage() + "]");
/* 84:   */     }
/* 85:   */     catch (Exception ex)
/* 86:   */     {
/* 87:80 */       this.console.println("FALHA - " + ex.getMessage());
/* 88:81 */       tentaReceber(args);
/* 89:82 */       throw ex;
/* 90:   */     }
/* 91:   */   }
/* 92:   */   
/* 93:   */   public void tentaReceber(String[] args)
/* 94:   */   {
/* 95:   */     try
/* 96:   */     {
/* 97:88 */       OMSResponse res = this.connection.send(OrderUtils.receiveOrderRequest(args[0], args[1]));
/* 98:89 */       Order nova = OrderUtils.parseOrderResponse(res.getData());
/* 99:   */     }
/* :0:   */     catch (Exception ex)
/* :1:   */     {
/* :2:   */       Order nova;
/* :3:91 */       System.err.println("Erro ao tentar receber a ordem:" + args[0] + ";" + args[1]);
/* :4:   */     }
/* :5:   */   }
/* :6:   */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     br.com.brasiltelecom.oms.xmlapi.baca.acao.FechaExcecao
 * JD-Core Version:    0.7.0.1
 */