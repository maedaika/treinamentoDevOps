/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*   2:    */ 
/*   3:    */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*   4:    */ import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
/*   5:    */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*   6:    */ import java.awt.event.ActionEvent;
/*   7:    */ import java.awt.event.ActionListener;
/*   8:    */ import java.text.DateFormat;
/*   9:    */ import java.text.SimpleDateFormat;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Date;
/*  12:    */ import java.util.Properties;
/*  13:    */ import java.util.StringTokenizer;
/*  14:    */ 
/*  15:    */ public abstract class Acao
/*  16:    */   implements ActionListener, Runnable
/*  17:    */ {
/*  18:    */   ConsoleLogger console;
/*  19:    */   OMSConnection connection;
/*  20: 21 */   BacaGUI baca = BacaGUI.getInstance();
/*  21: 23 */   ArrayList sucessos = new ArrayList();
/*  22: 25 */   ArrayList falhas = new ArrayList();
/*  23: 27 */   private int quantidade = 0;
/*  24:    */   Date termino;
/*  25:    */   Date inicio;
/*  26: 33 */   String separator = ";";
/*  27:    */   
/*  28:    */   public Acao()
/*  29:    */   {
/*  30: 37 */     this.console = this.baca.getConsole();
/*  31: 38 */     this.separator = this.baca.getServerConfig().getProperty("separator");
/*  32: 39 */     this.connection = this.baca.createConnection();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public void actionPerformed(ActionEvent e)
/*  36:    */   {
/*  37: 43 */     new Thread(this).start();
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void run()
/*  41:    */   {
/*  42: 47 */     boolean flag = inicializa();
/*  43: 48 */     if (flag) {
/*  44: 49 */       processa();
/*  45:    */     }
/*  46:    */   }
/*  47:    */   
/*  48:    */   protected boolean inicializa()
/*  49:    */   {
/*  50: 54 */     return true;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void processa()
/*  54:    */   {
/*  55: 58 */     SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
/*  56: 59 */     this.quantidade = 0;
/*  57: 60 */     this.sucessos.clear();
/*  58: 61 */     this.falhas.clear();
/*  59: 62 */     this.inicio = new Date();
/*  60: 63 */     this.console.println("");
/*  61: 64 */     this.console.println("Iniciando processamento - " + format.format(this.inicio));
/*  62: 65 */     String ordens = this.baca.getOrders();
/*  63: 66 */     StringTokenizer tokenizer = new StringTokenizer(ordens, "\n");
/*  64: 67 */     while (tokenizer.hasMoreTokens())
/*  65:    */     {
/*  66: 68 */       this.quantidade += 1;
/*  67: 69 */       String ordem = tokenizer.nextToken().trim();
/*  68: 70 */       StringTokenizer parametros = new StringTokenizer(ordem, this.separator);
/*  69: 71 */       String[] args = new String[parametros.countTokens()];
/*  70: 72 */       for (int i = 0; parametros.hasMoreTokens(); i++) {
/*  71: 73 */         args[i] = parametros.nextToken();
/*  72:    */       }
/*  73:    */       try
/*  74:    */       {
/*  75: 76 */         processa(args);
/*  76: 77 */         this.sucessos.add(args);
/*  77:    */       }
/*  78:    */       catch (Exception ex)
/*  79:    */       {
/*  80: 79 */         this.console.println("Erro: " + ex.getMessage());
/*  81: 80 */         this.falhas.add(args);
/*  82: 81 */         ex.printStackTrace();
/*  83:    */       }
/*  84:    */     }
/*  85: 84 */     this.termino = new Date();
/*  86: 85 */     geraRelatorio();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void geraRelatorio()
/*  90:    */   {
/*  91: 89 */     this.console.println("Fim da execução");
/*  92: 90 */     this.console.println("----------------- RELATÓRIO -----------------");
/*  93: 91 */     this.console.println("- Duração do script:....... " + (this.termino.getTime() - this.inicio.getTime()) + " ms");
/*  94: 92 */     this.console.println("- No. acões processadas:... " + this.quantidade);
/*  95: 93 */     this.console.println("- No. de sucessos:......... " + this.sucessos.size() + " (" + 100 * this.sucessos.size() / this.quantidade + "%)");
/*  96: 94 */     this.console.println("- No. de falhas:........... " + this.falhas.size() + " (" + 100 * this.falhas.size() / this.quantidade + "%)");
/*  97: 96 */     if (this.falhas.size() > 0) {
/*  98: 97 */       geraRelatorioFalhas();
/*  99:    */     }
/* 100:100 */     this.console.println("------------------- F I M -------------------");
/* 101:101 */     this.console.println("");
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void geraRelatorioFalhas()
/* 105:    */   {
/* 106:105 */     char separator = this.separator.charAt(0);
/* 107:106 */     StringBuffer buffer = new StringBuffer();
/* 108:107 */     this.console.println("- Açoes com Falha:");
/* 109:108 */     for (int i = 0; i < this.falhas.size(); i++)
/* 110:    */     {
/* 111:109 */       String[] args = (String[])this.falhas.get(i);
/* 112:110 */       for (int j = 0; j < args.length; j++) {
/* 113:111 */         buffer.append(args[j]).append(separator);
/* 114:    */       }
/* 115:113 */       buffer.deleteCharAt(buffer.length() - 1);
/* 116:114 */       buffer.append('\n');
/* 117:    */     }
/* 118:116 */     this.console.println(buffer.toString());
/* 119:    */   }
/* 120:    */   
/* 121:    */   public abstract void processa(String[] paramArrayOfString)
/* 122:    */     throws Exception;
/* 123:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.acao.Acao
 * JD-Core Version:    0.7.0.1
 */