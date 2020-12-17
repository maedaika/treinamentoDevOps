/*  1:   */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*  2:   */ 
/*  3:   */ import br.com.brasiltelecom.oms.data.Order;
/*  4:   */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*  5:   */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*  6:   */ import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
/*  7:   */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*  8:   */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;
/*  9:   */ import java.util.HashMap;
/* 10:   */ import java.util.ResourceBundle;
/* 11:   */ import java.util.Set;
/* 12:   */ import javax.swing.JComboBox;
/* 13:   */ import javax.swing.JOptionPane;
/* 14:   */ 
/* 15:   */ public class AcceptComplete
/* 16:   */   extends AcaoAceita
/* 17:   */ {
/* 18:   */   String status;
/* 19:   */   String[] opcoesFantasia;
/* 20:17 */   HashMap opcoesMnemonico = new HashMap();
/* 21:   */   JComboBox combo;
/* 22:   */   
/* 23:   */   public AcceptComplete()
/* 24:   */   {
/* 25:21 */     this.opcoesMnemonico.put("Enviar para o Asap", "erro_corrigido_correcao_automatica");
/* 26:22 */     this.opcoesMnemonico.put("Erro Corrigido Manualmente", "sucesso_correcao_manual");
/* 27:23 */     this.combo = new JComboBox(this.opcoesMnemonico.keySet().toArray());
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean inicializa()
/* 31:   */   {
/* 32:27 */     int option = JOptionPane.showConfirmDialog(this.baca, this.combo, 
/* 33:28 */       this.baca.getResource().getString("gui.title.mnemonicSelect"), 2);
/* 34:29 */     if (option != 0) {
/* 35:30 */       return false;
/* 36:   */     }
/* 37:32 */     this.status = ((String)this.opcoesMnemonico.get(this.combo.getSelectedItem()));
/* 38:33 */     if (this.status == null) {
/* 39:34 */       this.status = ((String)this.combo.getSelectedItem());
/* 40:   */     }
/* 41:36 */     return true;
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void processaAceita(String[] args, Order order)
/* 45:   */     throws Exception
/* 46:   */   {
/* 47:41 */     OMSResponse response = null;
/* 48:42 */     String completeOrder = OrderUtils.completeOrderRequest(order, this.status);
/* 49:   */     
/* 50:44 */     this.console.print("  - Completando ordem " + order.getId() + "... ");
/* 51:   */     try
/* 52:   */     {
/* 53:46 */       response = this.connection.send(completeOrder);
/* 54:47 */       OrderUtils.parseResponse(response.getData());
/* 55:48 */       this.console.println("[" + response.getMessage() + "]");
/* 56:   */     }
/* 57:   */     catch (Exception ex)
/* 58:   */     {
/* 59:50 */       this.console.println("FALHA - " + ex.getMessage());
/* 60:51 */       throw ex;
/* 61:   */     }
/* 62:   */   }
/* 63:   */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.acao.AcceptComplete
 * JD-Core Version:    0.7.0.1
 */