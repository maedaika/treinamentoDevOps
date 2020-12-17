/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.baca;
/*   2:    */ 
/*   3:    */ import java.awt.Component;
/*   4:    */ import java.awt.Window;
/*   5:    */ import java.awt.event.ActionEvent;
/*   6:    */ import java.awt.event.ActionListener;
/*   7:    */ 
/*   8:    */ final class BacaGUI$2
/*   9:    */   implements ActionListener
/*  10:    */ {
/*  11:    */   final BacaGUI this$0;
/*  12:    */   
/*  13:    */   BacaGUI$2(BacaGUI paramBacaGUI)
/*  14:    */   {
/*  15:  1 */     this.this$0 = paramBacaGUI;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void actionPerformed(ActionEvent evt)
/*  19:    */   {
/*  20:272 */     BacaGUI.access$6(this.this$0).pack();
/*  21:273 */     BacaGUI.centraliza(BacaGUI.access$6(this.this$0));
/*  22:274 */     BacaGUI.access$6(this.this$0).setVisible(true);
/*  23:    */   }
/*  24:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI.2
 * JD-Core Version:    0.7.0.1
 */