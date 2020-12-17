/*  1:   */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*  2:   */ 
/*  3:   */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*  4:   */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*  5:   */ import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
/*  6:   */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*  7:   */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;
/*  8:   */ import java.util.HashMap;
/*  9:   */ import java.util.ResourceBundle;
/* 10:   */ import java.util.Set;
/* 11:   */ import javax.swing.JComboBox;
/* 12:   */ import javax.swing.JOptionPane;
/* 13:   */ 
/* 14:   */ public class Excecao
/* 15:   */   extends Acao
/* 16:   */ {
/* 17:   */   String status;
/* 18:   */   String[] opcoesFantasia;
/* 19:16 */   HashMap opcoesMnemonico = new HashMap();
/* 20:   */   JComboBox combo;
/* 21:   */   
/* 22:   */   public Excecao()
/* 23:   */   {
/* 24:20 */     this.opcoesMnemonico.put("Fechamento Manual", "fechamento_manual_os_smp");
/* 25:21 */     this.opcoesMnemonico.put("Cancelamento de Serviços", "cancelamento_serviços_smp");
/* 26:22 */     this.combo = new JComboBox(this.opcoesMnemonico.keySet().toArray());
/* 27:   */   }
/* 28:   */   
/* 29:   */   public boolean inicializa()
/* 30:   */   {
/* 31:26 */     int option = JOptionPane.showConfirmDialog(this.baca, this.combo, 
/* 32:27 */       this.baca.getResource().getString("gui.title.mnemonicSelect"), 2);
/* 33:28 */     if (option != 0) {
/* 34:29 */       return false;
/* 35:   */     }
/* 36:31 */     this.status = ((String)this.opcoesMnemonico.get(this.combo.getSelectedItem()));
/* 37:32 */     if (this.status == null) {
/* 38:33 */       this.status = ((String)this.combo.getSelectedItem());
/* 39:   */     }
/* 40:35 */     return true;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void processa(String[] args)
/* 44:   */     throws Exception
/* 45:   */   {
/* 46:40 */     OMSResponse response = null;
/* 47:41 */     String exception = OrderUtils.setException(args[0], args[1], this.status);
/* 48:   */     
/* 49:43 */     this.console.print("  - Configurando Exceção " + args[0] + "... ");
/* 50:   */     try
/* 51:   */     {
/* 52:45 */       response = this.connection.send(exception);
/* 53:46 */       OrderUtils.parseResponse(response.getData());
/* 54:47 */       this.console.println("[" + response.getMessage() + "]");
/* 55:   */     }
/* 56:   */     catch (Exception ex)
/* 57:   */     {
/* 58:49 */       this.console.println("FALHA - " + ex.getMessage());
/* 59:50 */       throw ex;
/* 60:   */     }
/* 61:   */   }
/* 62:   */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.acao.Excecao
 * JD-Core Version:    0.7.0.1
 */