/* 1:  */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/* 2:  */ 
/* 3:  */ import javax.swing.JComboBox;
/* 4:  */ 
/* 5:  */ public class AcceptCompleteEditavel
/* 6:  */   extends AcceptComplete
/* 7:  */ {
/* 8:  */   public AcceptCompleteEditavel()
/* 9:  */   {
/* ::7 */     this.combo.setEditable(true);
/* ;:8 */     this.combo.addActionListener(CustomizaComboListener.getCustomiaComboListener(
/* <:9 */       this, this.opcoesMnemonico));
/* =:  */   }
/* >:  */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     br.com.brasiltelecom.oms.xmlapi.baca.acao.AcceptCompleteEditavel
 * JD-Core Version:    0.7.0.1
 */