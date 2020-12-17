/*  1:   */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*  2:   */ 
/*  3:   */ import br.com.brasiltelecom.oms.data.Order;
/*  4:   */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*  5:   */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*  6:   */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*  7:   */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;
/*  8:   */ import java.io.PrintStream;
/*  9:   */ 
/* 10:   */ public abstract class AcaoAceita
/* 11:   */   extends Acao
/* 12:   */ {
/* 13:   */   public void processa(String[] args)
/* 14:   */     throws Exception
/* 15:   */   {
/* 16:22 */     String orderID = args[0];
/* 17:23 */     String orderHist = args[1];
/* 18:24 */     String getOrder = OrderUtils.getOrderRequest(orderID, orderHist, true);
/* 19:25 */     this.console.print("  - Solicitando ordem " + orderID + "... ");
/* 20:26 */     Order order = null;
/* 21:27 */     OMSResponse response = null;
/* 22:   */     try
/* 23:   */     {
/* 24:30 */       response = this.connection.send(getOrder);
/* 25:31 */       order = OrderUtils.parseOrderResponse(response.getData());
/* 26:32 */       args[1] = String.valueOf(order.getHist());
/* 27:33 */       this.console.println("[" + response.getMessage() + "]");
/* 28:   */     }
/* 29:   */     catch (Exception ex)
/* 30:   */     {
/* 31:35 */       ex.printStackTrace();
/* 32:36 */       this.console.println("FALHA - " + ex.getMessage());
/* 33:37 */       tentaReceber(order);
/* 34:38 */       throw ex;
/* 35:   */     }
/* 36:   */     finally
/* 37:   */     {
/* 38:40 */       if (order != null) {
/* 39:41 */         args[1] = String.valueOf(order.getHist());
/* 40:   */       }
/* 41:   */     }
/* 42:   */     try
/* 43:   */     {
/* 44:46 */       processaAceita(args, order);
/* 45:   */     }
/* 46:   */     catch (Exception ex)
/* 47:   */     {
/* 48:48 */       ex.printStackTrace();
/* 49:49 */       tentaReceber(order);
/* 50:50 */       args[0] = order.getId();
/* 51:51 */       args[1] = order.getHist();
/* 52:52 */       throw ex;
/* 53:   */     }
/* 54:   */     finally
/* 55:   */     {
/* 56:54 */       if (order != null) {
/* 57:55 */         args[1] = String.valueOf(order.getHist());
/* 58:   */       }
/* 59:   */     }
/* 60:   */   }
/* 61:   */   
/* 62:   */   public abstract void processaAceita(String[] paramArrayOfString, Order paramOrder)
/* 63:   */     throws Exception;
/* 64:   */   
/* 65:   */   public void tentaReceber(Order order)
/* 66:   */   {
/* 67:   */     try
/* 68:   */     {
/* 69:64 */       OMSResponse res = this.connection.send(OrderUtils.receiveOrderRequest(order));
/* 70:65 */       Order nova = OrderUtils.parseOrderResponse(res.getData());
/* 71:66 */       order.setId(nova.getId());
/* 72:67 */       order.setHist(nova.getHist());
/* 73:   */     }
/* 74:   */     catch (Exception ex)
/* 75:   */     {
/* 76:69 */       System.err.println("Erro ao tentar receber a ordem:" + order);
/* 77:   */     }
/* 78:   */   }
/* 79:   */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.acao.AcaoAceita
 * JD-Core Version:    0.7.0.1
 */