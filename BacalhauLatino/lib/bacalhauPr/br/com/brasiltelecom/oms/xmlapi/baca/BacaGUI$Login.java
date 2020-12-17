/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.baca;
/*   2:    */ 
/*   3:    */ import br.com.brasiltelecom.oms.xmlapi.OMSConnectionFactory;
/*   4:    */ import java.awt.event.ActionEvent;
/*   5:    */ import java.awt.event.ActionListener;
/*   6:    */ import java.util.Properties;
/*   7:    */ import javax.swing.JOptionPane;
/*   8:    */ import javax.swing.JPasswordField;
/*   9:    */ import javax.swing.text.JTextComponent;
/*  10:    */ 
/*  11:    */ class BacaGUI$Login
/*  12:    */   implements ActionListener
/*  13:    */ {
/*  14:    */   final BacaGUI this$0;
/*  15:    */   
/*  16:    */   BacaGUI$Login(BacaGUI paramBacaGUI)
/*  17:    */   {
/*  18:343 */     this.this$0 = paramBacaGUI;
/*  19:    */   }
/*  20:    */   
/*  21:    */   public void actionPerformed(ActionEvent e)
/*  22:    */   {
/*  23:346 */     BacaGUI.access$0(this.this$0, new String(this.this$0.txtPassword.getPassword()));
/*  24:347 */     BacaGUI.access$1(this.this$0, this.this$0.txtUsername.getText());
/*  25:348 */     BacaGUI.access$3(this.this$0, BacaGUI.access$2(this.this$0).getProperty("server"));
/*  26:349 */     BacaGUI.access$4(this.this$0, BacaGUI.access$2(this.this$0).getProperty("port"));
/*  27:    */     try
/*  28:    */     {
/*  29:352 */       OMSConnectionFactory.getInstance(BacaGUI.access$5(this.this$0), Integer.parseInt(BacaGUI.access$6(this.this$0))).getConnection(BacaGUI.access$7(this.this$0), BacaGUI.access$8(this.this$0));
/*  30:    */     }
/*  31:    */     catch (Exception ex)
/*  32:    */     {
/*  33:354 */       JOptionPane.showMessageDialog(this.this$0, "Erro efetuando Login:\n" + 
/*  34:355 */         ex.getMessage());
/*  35:356 */       return;
/*  36:    */     }
/*  37:358 */     this.this$0.init();
/*  38:    */   }
/*  39:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI.Login
 * JD-Core Version:    0.7.0.1
 */