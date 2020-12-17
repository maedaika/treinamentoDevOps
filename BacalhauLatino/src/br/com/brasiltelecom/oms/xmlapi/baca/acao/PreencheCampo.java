/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*   2:    */ 
/*   3:    */ import br.com.brasiltelecom.oms.data.Order;
/*   4:    */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*   5:    */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*   6:    */ import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
/*   7:    */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*   8:    */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;
/*   9:    */ import java.awt.Component;
/*  10:    */ import java.awt.event.ActionEvent;
/*  11:    */ import java.awt.event.ActionListener;
/*  12:    */ import java.io.PrintStream;
/*  13:    */ import java.util.EventObject;
/*  14:    */ import java.util.HashMap;
/*  15:    */ import java.util.Properties;
/*  16:    */ import java.util.ResourceBundle;
/*  17:    */ import java.util.Set;
/*  18:    */ import javax.swing.AbstractButton;
/*  19:    */ import javax.swing.JCheckBox;
/*  20:    */ import javax.swing.JComboBox;
/*  21:    */ import javax.swing.JLabel;
/*  22:    */ import javax.swing.JOptionPane;
/*  23:    */ import javax.swing.JTextField;
/*  24:    */ import javax.swing.text.JTextComponent;
/*  25:    */ 
/*  26:    */ public class PreencheCampo
/*  27:    */   extends AcaoAceita
/*  28:    */ {
/*  29: 24 */   protected JComboBox combo = new JComboBox();
/*  30: 26 */   protected JTextField txtValor = new JTextField();
/*  31: 28 */   protected JCheckBox completa = new JCheckBox();
/*  32: 30 */   protected JCheckBox preencheOutro = new JCheckBox();
/*  33:    */   protected String campo;
/*  34:    */   protected String valor;
/*  35: 36 */   protected static HashMap mapa = new HashMap();
/*  36: 38 */   protected AcaoAceita proximaAcao = null;
/*  37:    */   
/*  38:    */   static
/*  39:    */   {
/*  40: 41 */     mapa.put("FIX", "/dados_gerais_da_os_smp/fix_smp");
/*  41: 42 */     mapa.put("Porta VLan", "/facilidades_automaticas[1]/ponta[1]/porta_vlan");
/*  42: 43 */     mapa.put("Tipo Equipamento", "/facilidades_automaticas[1]/ponta[1]/tipo_equipamento");
/*  43:    */   }
/*  44:    */   
/*  45:    */   public PreencheCampo()
/*  46:    */   {
/*  47: 49 */     CustomizaComboListener.getCustomiaComboListener(this, mapa);
/*  48: 50 */     CustomizaComboListener listener = 
/*  49: 51 */       CustomizaComboListener.getCustomiaComboListener(this, mapa);
/*  50: 52 */     this.combo = new JComboBox(mapa.keySet().toArray());
/*  51: 53 */     this.combo.addActionListener(listener);
/*  52: 54 */     this.combo.setEditable(true);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public PreencheCampo(JComboBox combo)
/*  56:    */   {
/*  57: 58 */     this.combo = combo;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public boolean inicializa()
/*  61:    */   {
/*  62: 62 */     JLabel lblCampo = new JLabel(this.baca.getResource().getString(
/*  63: 63 */       "gui.label.caminho"));
/*  64: 64 */     JLabel lblValor = new JLabel(this.baca.getResource()
/*  65: 65 */       .getString("gui.label.valor"));
/*  66:    */     
/*  67: 67 */     this.completa.setText(this.baca.getResource().getString("gui.label.completa"));
/*  68: 68 */     this.preencheOutro.setText(this.baca.getResource().getString("gui.label.preencheOutro"));
/*  69: 69 */     this.completa.addActionListener(new Seletor(null));
/*  70: 70 */     this.preencheOutro.addActionListener(new Seletor(null));
/*  71:    */     
/*  72: 72 */     Component[] msg = { lblCampo, this.combo, lblValor, this.txtValor, this.preencheOutro, this.completa };
/*  73:    */     
/*  74: 74 */     int option = JOptionPane.showConfirmDialog(this.baca, msg, this.baca.getResource()
/*  75: 75 */       .getString("gui.title.fieldEdition"), 2);
/*  76: 76 */     if (option != 0) {
/*  77: 77 */       return false;
/*  78:    */     }
/*  79: 79 */     this.valor = this.txtValor.getText();
/*  80: 80 */     this.campo = ((String)mapa.get(this.combo.getSelectedItem()));
/*  81: 81 */     if (this.campo == null) {
/*  82: 82 */       this.campo = ((String)this.combo.getSelectedItem());
/*  83:    */     }
/*  84: 84 */     if (this.preencheOutro.isSelected())
/*  85:    */     {
/*  86: 85 */       this.proximaAcao = new PreencheCampo(this.combo);
/*  87: 86 */       return this.proximaAcao.inicializa();
/*  88:    */     }
/*  89: 87 */     if (this.completa.isSelected())
/*  90:    */     {
/*  91: 88 */       this.proximaAcao = new AcceptCompleteEditavel();
/*  92: 89 */       return this.proximaAcao.inicializa();
/*  93:    */     }
/*  94: 91 */     this.proximaAcao = null;
/*  95: 92 */     return true;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void processaAceita(String[] args, Order order)
/*  99:    */     throws Exception
/* 100:    */   {
/* 101:    */     try
/* 102:    */     {
/* 103: 99 */       this.console.print("  - Preenchendo os campos " + order.getId() + "... ");
/* 104:100 */       String valorLocal = args.length < 3 ? this.valor : args[2];
/* 105:101 */       String update = OrderUtils.updateContent(order.getRoot(), this.campo, valorLocal, this.baca.getServerConfig().getProperty("separator"));
/* 106:102 */       update = OrderUtils.updateOrderRequest(order, update);
/* 107:103 */       System.out.println("=>update:" + update);
/* 108:104 */       OMSResponse response = this.connection.send(update);
/* 109:105 */       order = OrderUtils.parseOrderResponse(response.getData());
/* 110:106 */       this.console.println("[" + response.getMessage() + "]");
/* 111:107 */       if (this.proximaAcao != null) {
/* 112:108 */         this.proximaAcao.processaAceita(args, order);
/* 113:    */       }
/* 114:110 */       tentaReceber(order);
/* 115:    */     }
/* 116:    */     catch (Exception ex)
/* 117:    */     {
/* 118:112 */       this.console.println("FALHA - " + ex.getMessage());
/* 119:113 */       ex.printStackTrace();
/* 120:114 */       throw ex;
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private class Seletor
/* 125:    */     implements ActionListener
/* 126:    */   {
/* 127:    */     Seletor(Seletor paramSeletor)
/* 128:    */     {
/* 129:118 */       this();
/* 130:    */     }
/* 131:    */     
/* 132:    */     public void actionPerformed(ActionEvent e)
/* 133:    */     {
/* 134:120 */       if ((e.getSource().equals(PreencheCampo.this.preencheOutro)) && (PreencheCampo.this.preencheOutro.isSelected())) {
/* 135:121 */         PreencheCampo.this.completa.setSelected(false);
/* 136:122 */       } else if ((e.getSource().equals(PreencheCampo.this.completa)) && (PreencheCampo.this.completa.isSelected())) {
/* 137:123 */         PreencheCampo.this.preencheOutro.setSelected(false);
/* 138:    */       }
/* 139:    */     }
/* 140:    */     
/* 141:    */     private Seletor() {}
/* 142:    */   }
/* 143:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     br.com.brasiltelecom.oms.xmlapi.baca.acao.PreencheCampo
 * JD-Core Version:    0.7.0.1
 */