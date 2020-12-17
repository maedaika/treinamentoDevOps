/*  1:   */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*  2:   */ 
/*  3:   */ import java.awt.event.ActionEvent;
/*  4:   */ import java.awt.event.ActionListener;
/*  5:   */ import java.util.EventObject;
/*  6:   */ import java.util.Map;
/*  7:   */ import java.util.prefs.Preferences;
/*  8:   */ import javax.swing.JComboBox;
/*  9:   */ 
/* 10:   */ public class CustomizaComboListener
/* 11:   */   implements ActionListener
/* 12:   */ {
/* 13:   */   Preferences memoria;
/* 14:   */   Map mapa;
/* 15:   */   
/* 16:   */   protected CustomizaComboListener(Preferences memoria, Map mapa)
/* 17:   */   {
/* 18:18 */     this.memoria = memoria;
/* 19:19 */     this.mapa = mapa;
/* 20:20 */     load();
/* 21:   */   }
/* 22:   */   
/* 23:   */   public static CustomizaComboListener getCustomiaComboListener(Object obj, Map mapa)
/* 24:   */   {
/* 25:25 */     Preferences p = Preferences.userNodeForPackage(obj.getClass());
/* 26:26 */     return new CustomizaComboListener(p, mapa);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void load()
/* 30:   */   {
/* 31:   */     try
/* 32:   */     {
/* 33:31 */       String[] keys = this.memoria.keys();
/* 34:32 */       for (int i = 0; i < keys.length; i++) {
/* 35:33 */         this.mapa.put(keys[i], this.memoria.get(keys[i], keys[i]));
/* 36:   */       }
/* 37:   */     }
/* 38:   */     catch (Exception ex)
/* 39:   */     {
/* 40:36 */       ex.printStackTrace();
/* 41:   */     }
/* 42:   */   }
/* 43:   */   
/* 44:   */   public void actionPerformed(ActionEvent evt)
/* 45:   */   {
/* 46:41 */     JComboBox combo = (JComboBox)evt.getSource();
/* 47:42 */     String item = (String)combo.getSelectedItem();
/* 48:43 */     String valor = (String)this.mapa.get(item);
/* 49:44 */     if (valor == null)
/* 50:   */     {
/* 51:45 */       this.mapa.put(item, item);
/* 52:46 */       combo.addItem(item);
/* 53:   */     }
/* 54:   */   }
/* 55:   */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     br.com.brasiltelecom.oms.xmlapi.baca.acao.CustomizaComboListener
 * JD-Core Version:    0.7.0.1
 */