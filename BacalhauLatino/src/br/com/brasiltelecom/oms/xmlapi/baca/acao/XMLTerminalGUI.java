/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*   2:    */ 
/*   3:    */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*   4:    */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*   5:    */ import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
/*   6:    */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*   7:    */ import java.awt.BorderLayout;
/*   8:    */ import java.awt.Component;
/*   9:    */ import java.awt.Container;
/*  10:    */ import java.awt.FlowLayout;
/*  11:    */ import java.awt.Window;
/*  12:    */ import java.awt.event.ActionEvent;
/*  13:    */ import java.awt.event.ActionListener;
/*  14:    */ import java.util.EventObject;
/*  15:    */ import java.util.Hashtable;
/*  16:    */ import java.util.Iterator;
/*  17:    */ import java.util.Properties;
/*  18:    */ import java.util.ResourceBundle;
/*  19:    */ import java.util.Set;
/*  20:    */ import java.util.TreeMap;
/*  21:    */ import javax.swing.AbstractButton;
/*  22:    */ import javax.swing.ImageIcon;
/*  23:    */ import javax.swing.JButton;
/*  24:    */ import javax.swing.JComboBox;
/*  25:    */ import javax.swing.JComponent;
/*  26:    */ import javax.swing.JDialog;
/*  27:    */ import javax.swing.JPanel;
/*  28:    */ import javax.swing.JScrollPane;
/*  29:    */ import javax.swing.JTextArea;
/*  30:    */ import javax.swing.text.JTextComponent;
/*  31:    */ 
/*  32:    */ public class XMLTerminalGUI
/*  33:    */   implements ActionListener, Runnable
/*  34:    */ {
/*  35: 30 */   BacaGUI baca = BacaGUI.getInstance();
/*  36: 32 */   JTextArea txtMessage = new JTextArea(8, 30);
/*  37: 34 */   JButton execute = new JButton();
/*  38:    */   ConsoleLogger console;
/*  39: 38 */   JComboBox comboTemplates = new JComboBox();
/*  40: 40 */   Properties templates = new Properties();
/*  41:    */   JDialog dialog;
/*  42:    */   
/*  43:    */   public XMLTerminalGUI()
/*  44:    */   {
/*  45: 45 */     this.dialog = new JDialog(BacaGUI.getInstance());
/*  46: 46 */     this.console = this.baca.getConsole();
/*  47: 47 */     iniciaJanela();
/*  48: 48 */     iniciaTemplates();
/*  49:    */   }
/*  50:    */   
/*  51:    */   private void iniciaJanela()
/*  52:    */   {
/*  53: 52 */     ResourceBundle resource = this.baca.getResource();
/*  54: 53 */     Container content = this.dialog.getContentPane();
/*  55: 54 */     content.setLayout(new BorderLayout());
/*  56:    */     
/*  57: 56 */     this.comboTemplates.setFont(this.baca.getFont());
/*  58: 57 */     this.comboTemplates.addActionListener(this);
/*  59: 58 */     content.add(this.comboTemplates, "North");
/*  60:    */     
/*  61: 60 */     JScrollPane scrlCenter = new JScrollPane(this.txtMessage);
/*  62: 61 */     this.txtMessage.setFont(this.baca.getFont());
/*  63: 62 */     content.add(scrlCenter);
/*  64:    */     
/*  65: 64 */     JPanel botoes = new JPanel(new FlowLayout(1));
/*  66: 65 */     this.execute.setText(resource.getString("gui.label.executar"));
/*  67: 66 */     this.execute.setIcon(new ImageIcon(
/*  68: 67 */       ClassLoader.getSystemResource("resources/execute.png")));
/*  69:    */     
/*  70: 69 */     botoes.add(this.execute);
/*  71: 70 */     content.add(botoes, "South");
/*  72:    */     
/*  73: 72 */     this.execute.addActionListener(this);
/*  74:    */   }
/*  75:    */   
/*  76:    */   private void iniciaTemplates()
/*  77:    */   {
/*  78:    */     try
/*  79:    */     {
/*  80: 77 */       this.templates.load(
/*  81: 78 */         ClassLoader.getSystemResourceAsStream("resources/templates.properties"));
/*  82:    */     }
/*  83:    */     catch (Exception localException) {}
/*  84: 81 */     TreeMap mapa = new TreeMap(this.templates);
/*  85: 82 */     Iterator nomes = mapa.keySet().iterator();
/*  86: 83 */     while (nomes.hasNext())
/*  87:    */     {
/*  88: 84 */       String nome = (String)nomes.next();
/*  89: 85 */       String template = (String)this.templates.get(nome);
/*  90: 86 */       this.templates.put(nome, template);
/*  91: 87 */       this.comboTemplates.addItem(nome);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void actionPerformed(ActionEvent evt)
/*  96:    */   {
/*  97:107 */     if (evt.getSource().equals(this.execute))
/*  98:    */     {
/*  99:108 */       new Thread(this, "Terminal").start();
/* 100:    */     }
/* 101:109 */     else if (evt.getSource().equals(this.comboTemplates))
/* 102:    */     {
/* 103:110 */       Object template = this.templates.get(this.comboTemplates.getSelectedItem());
/* 104:111 */       this.txtMessage.setText((String)template);
/* 105:    */     }
/* 106:    */     else
/* 107:    */     {
/* 108:113 */       BacaGUI.centraliza(this.dialog);
/* 109:114 */       this.dialog.pack();
/* 110:115 */       this.dialog.setVisible(true);
/* 111:    */     }
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void run()
/* 115:    */   {
/* 116:120 */     this.dialog.setVisible(false);
/* 117:121 */     OMSConnection con = this.baca.createConnection();
/* 118:    */     try
/* 119:    */     {
/* 120:123 */       String message = this.txtMessage.getText();
/* 121:124 */       this.console.println("Enviando:");
/* 122:125 */       this.console.println(message);
/* 123:126 */       OMSResponse response = con.send(message);
/* 124:127 */       this.console.println("Recebido:");
/* 125:128 */       this.console.println(format(response.getData()));
/* 126:129 */       this.console.println("Código/Messagem: " + response.getCode() + "/" + 
/* 127:130 */         response.getMessage());
/* 128:    */     }
/* 129:    */     catch (Exception ex)
/* 130:    */     {
/* 131:132 */       ex.printStackTrace();
/* 132:133 */       this.console.println("Erro executando o comando!");
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public static String format(String xml)
/* 137:    */   {
/* 138:138 */     StringBuffer saida = new StringBuffer(xml.length());
/* 139:139 */     int ident = 0;
/* 140:140 */     for (int i = 0; i < xml.length(); i++)
/* 141:    */     {
/* 142:141 */       char c = xml.charAt(i);
/* 143:142 */       switch (c)
/* 144:    */       {
/* 145:    */       case '/': 
/* 146:144 */         if ((xml.charAt(i - 1) == '<') || (xml.charAt(i + 1) == '>')) {
/* 147:145 */           ident--;
/* 148:    */         }
/* 149:146 */         break;
/* 150:    */       case '<': 
/* 151:148 */         if (xml.charAt(i + 1) != '/') {
/* 152:149 */           ident++;
/* 153:    */         }
/* 154:    */         break;
/* 155:    */       }
/* 156:153 */       saida.append(c);
/* 157:154 */       if ((c == '>') && 
/* 158:155 */         (i < xml.length() - 1) && (xml.charAt(i + 1) == '<')) {
/* 159:156 */         if (xml.charAt(i + 2) != '/') {
/* 160:157 */           saida.append(feed(ident));
/* 161:    */         } else {
/* 162:159 */           saida.append(feed(ident - 1));
/* 163:    */         }
/* 164:    */       }
/* 165:    */     }
/* 166:165 */     return saida.toString();
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static String feed(int tabs)
/* 170:    */   {
/* 171:169 */     String result = "\n";
/* 172:170 */     for (int i = 0; i < tabs; i++) {
/* 173:171 */       result = result + "\t";
/* 174:    */     }
/* 175:173 */     return result;
/* 176:    */   }
/* 177:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     br.com.brasiltelecom.oms.xmlapi.baca.acao.XMLTerminalGUI
 * JD-Core Version:    0.7.0.1
 */