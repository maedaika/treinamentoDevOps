/*  1:   */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*  2:   */ 
/*  3:   */ import br.com.brasiltelecom.oms.data.Order;
/*  4:   */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*  5:   */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*  6:   */ import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
/*  7:   */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*  8:   */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;
/*  9:   */ 
/* 10:   */ public class AssignComplete
/* 11:   */   extends Acao
/* 12:   */ {
/* 13:   */   String user;
/* 14:10 */   protected AcceptCompleteEditavel acceptComplete = new AcceptCompleteEditavel();
/* 15:   */   
/* 16:   */   public boolean inicializa()
/* 17:   */   {
/* 18:18 */     this.user = this.baca.getUsername();
/* 19:19 */     return this.acceptComplete.inicializa();
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void processa(String[] args)
/* 23:   */     throws Exception
/* 24:   */   {
/* 25:23 */     String request = OrderUtils.assignOrderRequest(args[0], args[1], this.user);
/* 26:24 */     OMSResponse response = null;
/* 27:25 */     Order order = null;
/* 28:26 */     this.console.print("  - Encaminhando ordem " + args[0] + "... ");
/* 29:   */     try
/* 30:   */     {
/* 31:28 */       response = this.connection.send(request);
/* 32:29 */       order = OrderUtils.parseOrderResponse(response.getData());
/* 33:30 */       this.console.println("[" + response.getMessage() + "]");
/* 34:31 */      // args[1] = order.getHist();
/* 35:32 */       this.acceptComplete.processa(args);
/* 36:   */     }
/* 37:   */     catch (Exception ex)
/* 38:   */     {
/* 39:34 */       this.console.println("FALHA - " + ex.getMessage());
/* 40:35 */       throw ex;
/* 41:   */     }
/* 42:   */   }
/* 43:   */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     br.com.brasiltelecom.oms.xmlapi.baca.acao.AssignComplete
 * JD-Core Version:    0.7.0.1
 */