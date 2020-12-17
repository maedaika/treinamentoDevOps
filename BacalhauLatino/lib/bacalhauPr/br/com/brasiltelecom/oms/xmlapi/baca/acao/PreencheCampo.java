/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*   2:    */ 
/*   3:    */ import br.com.brasiltelecom.oms.data.Order;
/*   4:    */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*   5:    */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*   6:    */ import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
/*   7:    */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*   8:    */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;
/*   9:    */ import java.awt.Component;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import java.util.HashMap;
/*  12:    */ import java.util.Properties;
/*  13:    */ import java.util.ResourceBundle;
/*  14:    */ import java.util.Set;
/*  15:    */ import javax.swing.AbstractButton;
/*  16:    */ import javax.swing.JCheckBox;
/*  17:    */ import javax.swing.JComboBox;
/*  18:    */ import javax.swing.JLabel;
/*  19:    */ import javax.swing.JOptionPane;
/*  20:    */ import javax.swing.JTextField;
/*  21:    */ import javax.swing.text.JTextComponent;
/*  22:    */ 
/*  23:    */ public class PreencheCampo
/*  24:    */   extends AcaoAceita
/*  25:    */ {
/*  26: 24 */   protected JComboBox combo = new JComboBox();
/*  27: 26 */   protected JTextField txtValor = new JTextField();
/*  28: 28 */   protected JCheckBox completa = new JCheckBox();
/*  29: 30 */   protected JCheckBox preencheOutro = new JCheckBox();
/*  30:    */   protected String campo;
/*  31:    */   protected String valor;
/*  32: 36 */   protected static HashMap mapa = new HashMap();
/*  33: 38 */   protected AcaoAceita proximaAcao = null;
/*  34:    */   
/*  35:    */   static
/*  36:    */   {
/*  37: 41 */     mapa.put("FIX", "/dados_gerais_da_os_smp/fix_smp");
/*  38:    */   }
/*  39:    */   
/*  40:    */   public PreencheCampo()
/*  41:    */   {
/*  42: 47 */     CustomizaComboListener.getCustomiaComboListener(this, mapa);
/*  43: 48 */     CustomizaComboListener listener = 
/*  44: 49 */       CustomizaComboListener.getCustomiaComboListener(this, mapa);
/*  45: 50 */     this.combo = new JComboBox(mapa.keySet().toArray());
/*  46: 51 */     this.combo.addActionListener(listener);
/*  47: 52 */     this.combo.setEditable(true);
/*  48:    */   }
/*  49:    */   
/*  50:    */   public PreencheCampo(JComboBox combo)
/*  51:    */   {
/*  52: 56 */     this.combo = combo;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean inicializa()
/*  56:    */   {
/*  57: 60 */     JLabel lblCampo = new JLabel(this.baca.getResource()
/*  58: 61 */       .getString("gui.label.caminho"));
/*  59: 62 */     JLabel lblValor = new JLabel(
/*  60: 63 */       this.baca.getResource().getString("gui.label.valor"));
/*  61:    */     
/*  62: 65 */     this.completa.setText(this.baca.getResource().getString("gui.label.completa"));
/*  63: 66 */     this.preencheOutro.setText(this.baca.getResource().getString("gui.label.preencheOutro"));
/*  64: 67 */     this.completa.addActionListener(new PreencheCampo.Seletor(this));
/*  65: 68 */     this.preencheOutro.addActionListener(new PreencheCampo.Seletor(this));
/*  66:    */     
/*  67: 70 */     Component[] msg = { lblCampo, this.combo, lblValor, this.txtValor, this.preencheOutro, this.completa };
/*  68:    */     
/*  69: 72 */     int option = JOptionPane.showConfirmDialog(this.baca, msg, 
/*  70: 73 */       this.baca.getResource().getString("gui.title.fieldEdition"), 2);
/*  71: 74 */     if (option != 0) {
/*  72: 75 */       return false;
/*  73:    */     }
/*  74: 77 */     this.valor = this.txtValor.getText();
/*  75: 78 */     this.campo = ((String)mapa.get(this.combo.getSelectedItem()));
/*  76: 79 */     if (this.campo == null) {
/*  77: 80 */       this.campo = ((String)this.combo.getSelectedItem());
/*  78:    */     }
/*  79: 82 */     if (this.preencheOutro.isSelected())
/*  80:    */     {
/*  81: 83 */       this.proximaAcao = new PreencheCampo(this.combo);
/*  82: 84 */       return this.proximaAcao.inicializa();
/*  83:    */     }
/*  84: 85 */     if (this.completa.isSelected())
/*  85:    */     {
/*  86: 86 */       this.proximaAcao = new AcceptCompleteEditavel();
/*  87: 87 */       return this.proximaAcao.inicializa();
/*  88:    */     }
/*  89: 89 */     this.proximaAcao = null;
/*  90: 90 */     return true;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void processaAceita(String[] args, Order order)
/*  94:    */     throws Exception
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98: 97 */       this.console.print("  - Preenchendo os campos " + order.getId() + "... ");
/*  99: 98 */       String update = OrderUtils.updateContent(order.getRoot(), this.campo, this.valor, this.baca.getServerConfig().getProperty("separator"));
/* 100: 99 */       update = OrderUtils.updateOrderRequest(order, update);
/* 101:100 */       System.out.println("=>update:" + update);
/* 102:101 */       OMSResponse response = this.connection.send(update);
/* 103:102 */       order = OrderUtils.parseOrderResponse(response.getData());
/* 104:103 */       this.console.println("[" + response.getMessage() + "]");
/* 105:104 */       if (this.proximaAcao != null) {
/* 106:105 */         this.proximaAcao.processaAceita(args, order);
/* 107:    */       }
/* 108:107 */       tentaReceber(order);
/* 109:    */     }
/* 110:    */     catch (Exception ex)
/* 111:    */     {
/* 112:109 */       this.console.println("FALHA - " + ex.getMessage());
/* 113:110 */       ex.printStackTrace();
/* 114:111 */       throw ex;
/* 115:    */     }
/* 116:    */   }
/* 117:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.acao.PreencheCampo
 * JD-Core Version:    0.7.0.1
 */