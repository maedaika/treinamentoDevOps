/*  1:   */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*  2:   */ 
/*  3:   */ import br.com.brasiltelecom.oms.data.Order;
/*  4:   */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*  5:   */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*  6:   */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*  7:   */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;
/*  8:   */ 
/*  9:   */ public class AcceptReceive
/* 10:   */   extends AcaoAceita
/* 11:   */ {
/* 12:   */   public void processaAceita(String[] args, Order order)
/* 13:   */   {
/* 14:22 */     OMSResponse response = null;
/* 15:23 */     String receiveOrder = OrderUtils.receiveOrderRequest(order);
/* 16:24 */     this.console.print("  - Recebendo a ordem " + order.getId() + "... ");
/* 17:   */     try
/* 18:   */     {
/* 19:26 */       response = this.connection.send(receiveOrder);
/* 20:27 */       order = OrderUtils.parseOrderResponse(response.getData());
/* 21:28 */       this.console.println("[" + response.getMessage() + "]");
/* 22:   */     }
/* 23:   */     catch (Exception ex)
/* 24:   */     {
/* 25:30 */       this.console.println("FALHA - " + ex.getMessage());
/* 26:31 */       return;
/* 27:   */     }
/* 28:   */   }
/* 29:   */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     br.com.brasiltelecom.oms.xmlapi.baca.acao.AcceptReceive
 * JD-Core Version:    0.7.0.1
 */