/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*   2:    */ 
/*   3:    */ import java.awt.event.ActionEvent;
/*   4:    */ import java.awt.event.ActionListener;
/*   5:    */ import java.util.EventObject;
/*   6:    */ import javax.swing.AbstractButton;
/*   7:    */ 
/*   8:    */ class PreencheCampo$Seletor
/*   9:    */   implements ActionListener
/*  10:    */ {
/*  11:    */   final PreencheCampo this$0;
/*  12:    */   
/*  13:    */   PreencheCampo$Seletor(PreencheCampo paramPreencheCampo)
/*  14:    */   {
/*  15:115 */     this.this$0 = paramPreencheCampo;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void actionPerformed(ActionEvent e)
/*  19:    */   {
/*  20:117 */     if ((e.getSource().equals(this.this$0.preencheOutro)) && (this.this$0.preencheOutro.isSelected())) {
/*  21:118 */       this.this$0.completa.setSelected(false);
/*  22:119 */     } else if ((e.getSource().equals(this.this$0.completa)) && (this.this$0.completa.isSelected())) {
/*  23:120 */       this.this$0.preencheOutro.setSelected(false);
/*  24:    */     }
/*  25:    */   }
/*  26:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.acao.PreencheCampo.Seletor
 * JD-Core Version:    0.7.0.1
 */