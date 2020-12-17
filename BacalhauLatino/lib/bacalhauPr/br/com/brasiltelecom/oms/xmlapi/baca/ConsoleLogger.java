/*  1:   */ package br.com.brasiltelecom.oms.xmlapi.baca;
/*  2:   */ 
/*  3:   */ import javax.swing.JTextArea;
/*  4:   */ import javax.swing.text.JTextComponent;
/*  5:   */ 
/*  6:   */ public class ConsoleLogger
/*  7:   */ {
/*  8:   */   JTextArea area;
/*  9:   */   
/* 10:   */   public ConsoleLogger(JTextArea area)
/* 11:   */   {
/* 12:16 */     this.area = area;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public void println(String line)
/* 16:   */   {
/* 17:20 */     this.area.append(line + "\n");
/* 18:21 */     this.area.setCaretPosition(this.area.getText().length() - 1);
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void print(String text)
/* 22:   */   {
/* 23:25 */     this.area.append(text);
/* 24:26 */     this.area.setCaretPosition(this.area.getText().length() - 1);
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void clear()
/* 28:   */   {
/* 29:30 */     this.area.setText("");
/* 30:   */   }
/* 31:   */   
/* 32:   */   public String getText()
/* 33:   */   {
/* 34:34 */     return this.area.getText();
/* 35:   */   }
/* 36:   */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger
 * JD-Core Version:    0.7.0.1
 */