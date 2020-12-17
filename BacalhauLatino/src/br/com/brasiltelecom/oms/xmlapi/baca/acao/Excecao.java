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
/* 19:14 */   HashMap opcoesMnemonico = new HashMap();
/* 20:   */   JComboBox combo;
/* 21:   */   
/* 22:   */   public Excecao()
/* 23:   */   {
/* 24:18 */     this.opcoesMnemonico.put("Cancelamento de ServiÃ§os", 
/* 25:19 */       "cancelamento_serviÃ§os_smp");
/* 26:20 */     this.opcoesMnemonico.put("Redirecionamento", "redirecionamento");
/* 27:21 */     this.combo = new JComboBox(this.opcoesMnemonico.keySet().toArray());
/* 28:   */   }
/* 29:   */   
/* 30:   */   public boolean inicializa()
/* 31:   */   {
/* 32:25 */     int option = JOptionPane.showConfirmDialog(this.baca, this.combo, 
/* 33:26 */       this.baca.getResource().getString("gui.title.mnemonicSelect"), 
/* 34:27 */       2);
/* 35:28 */     if (option != 0) {
/* 36:29 */       return false;
/* 37:   */     }
/* 38:31 */     this.status = ((String)this.opcoesMnemonico.get(this.combo
/* 39:32 */       .getSelectedItem()));
/* 40:33 */     if (this.status == null) {
/* 41:34 */       this.status = ((String)this.combo.getSelectedItem());
/* 42:   */     }
/* 43:36 */     return true;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void processa(String[] args)
/* 47:   */     throws Exception
/* 48:   */   {
/* 49:40 */     OMSResponse response = null;
/* 50:41 */     String exception = OrderUtils.setException(args[0], args[1], 
/* 51:42 */       this.status);
/* 52:   */     
/* 53:44 */     this.console.print("  - Configurando ExceÃ§Ã£o " + args[0] + "... ");
/* 54:   */     try
/* 55:   */     {
/* 56:46 */       response = this.connection.send(exception);
/* 57:47 */       OrderUtils.parseResponse(response.getData());
/* 58:48 */       this.console.println("[" + response.getMessage() + "]");
/* 59:   */     }
/* 60:   */     catch (Exception ex)
/* 61:   */     {
/* 62:50 */       this.console.println("FALHA - " + ex.getMessage());
/* 63:51 */       throw ex;
/* 64:   */     }
/* 65:   */   }
/* 66:   */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     br.com.brasiltelecom.oms.xmlapi.baca.acao.Excecao
 * JD-Core Version:    0.7.0.1
 */