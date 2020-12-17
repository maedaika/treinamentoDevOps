/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*   2:    */ 
/*   3:    */ import br.com.brasiltelecom.oms.data.Order;
/*   4:    */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*   5:    */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*   6:    */ import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
/*   7:    */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*   8:    */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;
/*   9:    */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils.Update;
/*  10:    */ import java.io.PrintStream;
/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.Iterator;
/*  13:    */ import java.util.ResourceBundle;
/*  14:    */ import java.util.Set;
/*  15:    */ import java.util.TreeMap;
/*  16:    */ import javax.swing.AbstractButton;
/*  17:    */ import javax.swing.JCheckBox;
/*  18:    */ import javax.swing.JComboBox;
/*  19:    */ import javax.swing.JLabel;
/*  20:    */ import javax.swing.JOptionPane;
/*  21:    */ import org.w3c.dom.Element;
/*  22:    */ import org.w3c.dom.Node;
/*  23:    */ 
/*  24:    */ public class TrocaStatus
/*  25:    */   extends AcaoAceita
/*  26:    */ {
/*  27:    */   String nomeServico;
/*  28:    */   String nomeStatus;
/*  29: 25 */   JComboBox combo = new JComboBox();
/*  30: 27 */   JComboBox cmbStatus = new JComboBox();
/*  31: 29 */   TreeMap valores = new TreeMap();
/*  32: 31 */   TreeMap status = new TreeMap();
/*  33: 33 */   JCheckBox completa = new JCheckBox();
/*  34:    */   AcceptComplete acceptComplete;
/*  35:    */   
/*  36:    */   public TrocaStatus()
/*  37:    */   {
/*  38: 38 */     this.valores.put("BLACK LIST PRÉ-PAGO", "ELM_GPP_BLACK_LIST");
/*  39: 39 */     this.valores.put("BLOQUEIO 0300", "ELM_BLOQ_CHAM1");
/*  40: 40 */     this.valores.put("BLOQUEIO 0500/0900/900", "ELM_BLOQ_CHAM2");
/*  41: 41 */     this.valores.put("BLOQUEIO 0900", "ELM_BLOQ_CHAM3");
/*  42: 42 */     this.valores.put("BLOQUEIO 900", "ELM_BLOQ_CHAM4");
/*  43: 43 */     this.valores.put("BLOQUEIO DE IDENTIFICADOR DE CHAMADAS", "ELM_BLOQ_IDCHAM");
/*  44: 44 */     this.valores.put("BLOQUEIO DE LIGAÇÕES A COBRAR", "ELM_BLOQ_COBRAR");
/*  45: 45 */     this.valores.put("BLOQUEIO DE ROAMING", "ELM_BLOQ_ROAMING");
/*  46: 46 */     this.valores.put("BLOQUEIO GPRS", "ELM_BLOQ_GPRS");
/*  47: 47 */     this.valores.put("BLOQUEIO HOT LINE", "ELM_BLOQ_HOT_LINE");
/*  48: 48 */     this.valores.put("BLOQUEIO HOT LINE MALA DIRETA", "ELM_BLOQ_HOT_LINE_MA");
/*  49: 49 */     this.valores.put("BLOQUEIO LDI", "ELM_BLOQ_CHAM6");
/*  50: 50 */     this.valores.put("BLOQUEIO LDN", "ELM_BLOQ_CHAM5");
/*  51: 51 */     this.valores.put("BLOQUEIO ROAMING INTERNACIONAL", "ELM_BLOQ_ROAMINT");
/*  52: 52 */     this.valores.put("BLOQUEIO SMS", "ELM_BLOQ_SMS");
/*  53: 53 */     this.valores.put("CAIXA POSTAL", "ELM_CXPOSTAL");
/*  54: 54 */     this.valores.put("CHAMADA EM ESPERA", "ELM_CHAMESPERA");
/*  55: 55 */     this.valores.put("CONSULTA E CONFERÊNCIA", "ELM_CONSCONF");
/*  56: 56 */     this.valores.put("FREE CALL PRÉ-PAGO", "ELM_GPP_FREE_CALL");
/*  57: 57 */     this.valores.put("GPRS", "ELM_GPRS");
/*  58: 58 */     this.valores.put("GRUPO 14", "ELM_G14");
/*  59: 59 */     this.valores.put("IDENTIFICADOR DE CHAMADAS", "ELM_IDCHAMADA");
/*  60: 60 */     this.valores.put("IDENTIFICADOR DE CHAMADAS PRÉ-PAGO", "ELM_GPP_IDCHAMADA");
/*  61: 61 */     this.valores.put("INFORMAÇÕES DO CLIENTE", "ELM_NDS");
/*  62: 62 */     this.valores.put("INFORMAÇÕES DO SIMCARD", "ELM_INFO_SIMCARD");
/*  63: 63 */     this.valores.put("INFORMAÇÕES VPN", "ELM_INFO_VPN");
/*  64: 64 */     this.valores.put("MMS", "ELM_MMS");
/*  65: 65 */     this.valores.put("PLANO ALTERNATIVO PRÉ-PAGO DIURNO", "ELM_PL_ALT_PRE_DIURN");
/*  66: 66 */     this.valores.put("PLANO ALTERNATIVO PRÉ-PAGO NOTURNO", "ELM_PL_ALT_PRE_NOTUR");
/*  67: 67 */     this.valores.put("PLANO ALTERNATIVO PRÉ-PAGO REFERÊNCIA", "ELM_PL_ALT_PRE_REF");
/*  68: 68 */     this.valores.put("PLANO ALTERNATIVO PÓS-PAGO 14 CORPORATIVO", 
/*  69: 69 */       "ELM_PL_ALT_POS_CORP");
/*  70: 70 */     this.valores.put("PLANO ALTERNATIVO PÓS-PAGO 14 FRANQUIA INTELIGENTE", 
/*  71: 71 */       "ELM_PL_ALT_POS_FRAN_");
/*  72: 72 */     this.valores.put("PLANO ALTERNATIVO PÓS-PAGO CONTROLE", "ELM_PL_ALT_HIB_CTRL");
/*  73: 73 */     this.valores.put("PLANO ATENDENTE", "ELM_PL_HIB_ATENDENTE");
/*  74: 74 */     this.valores.put("PLANO BASICO - AUTO PLUS", "ELM_PL_BAS_POS_APLUS");
/*  75: 75 */     this.valores.put("PLANO BASICO PÓS-PAGO", "ELM_PL_BAS_POS");
/*  76: 76 */     this.valores.put("PLANO BRASIL CONTA 1", "ELM_PL_ALT_POS_MALAB");
/*  77: 77 */     this.valores.put("PLANO BRASIL CONTA 2", "ELM_PL_ALT_POS_MALAA");
/*  78: 78 */     this.valores.put("PLANO BRASIL CONTA 3", "ELM_PL_ALT_POS_LANCA");
/*  79: 79 */     this.valores.put("PLANO BÁSICO - CONDOR SUPER CENTER", "ELM_PL_BAS_POS_CONDO");
/*  80: 80 */     this.valores.put("PLANO BÁSICO - MAKRO", "ELM_PL_BAS_POS_MAKRO");
/*  81: 81 */     this.valores.put("PLANO BÁSICO - PLAZA VEÍCULOS", "ELM_PL_BAS_POS_PLAZA");
/*  82: 82 */     this.valores.put("PLANO BÁSICO - POLLOSHOP", "ELM_PL_BAS_POS_POLLO");
/*  83: 83 */     this.valores.put("PLANO BÁSICO - SENAI", "ELM_PL_BAS_POS_SENAI");
/*  84: 84 */     this.valores.put("PLANO BÁSICO - SERVOPA S/A INDÚSTRIA E COMÉRCIO   ", 
/*  85: 85 */       "ELM_PL_BAS_POS_SERVO");
/*  86: 86 */     this.valores.put("PLANO BÁSICO - VECODIL COM DE VEÍCULOS LTDA       ", 
/*  87: 87 */       "ELM_PL_BAS_POS_VECOD");
/*  88: 88 */     this.valores.put("PLANO COLABORADOR", "ELM_PL_POS_COLABORAD");
/*  89: 89 */     this.valores.put("PLANO INDICADO (NÃO COLABORADOR)", "ELM_PL_POS_INDICADO");
/*  90: 90 */     this.valores.put("PLANO PF EMPRESAS 100", "ELM_PL_ALT_POS_PF100");
/*  91: 91 */     this.valores.put("PLANO PF EMPRESAS 50", "ELM_PL_ALT_POS_PF50");
/*  92: 92 */     this.valores.put("PLANO PF EMPRESAS OUTROS", "ELM_PL_ALT_POS_PFOT");
/*  93: 93 */     this.valores.put("SIGA-ME", "ELM_SIGAME");
/*  94: 94 */     this.valores.put("SMS", "ELM_SMS");
/*  95: 95 */     this.valores.put("TELECONFERÊNCIA", "ELM_TELECONF");
/*  96: 96 */     this.valores.put("UMS", "ELM_UMS");
/*  97: 97 */     this.valores.put("VPN RAMAL", "ELM_VPN");
/*  98: 98 */     this.valores.put("WAP", "ELM_WAP");
/*  99:    */     
/* 100:100 */     this.status.put("NOK", "NOK");
/* 101:101 */     this.status.put("NÃO FEITO", "NAO_FEITO");
/* 102:102 */     this.status.put("OK", "OK");
/* 103:    */     
/* 104:104 */     this.cmbStatus = new JComboBox(this.status.keySet().toArray());
/* 105:105 */     this.combo = new JComboBox(this.valores.keySet().toArray());
/* 106:106 */     this.combo.setEditable(true);
/* 107:107 */     this.combo.addActionListener(CustomizaComboListener.getCustomiaComboListener(
/* 108:108 */       this, this.valores));
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean inicializa()
/* 112:    */   {
/* 113:112 */     JLabel lblNome = new JLabel(this.baca.getResource()
/* 114:113 */       .getString("gui.label.nomeServico"));
/* 115:114 */     JLabel lblStatus = new JLabel(this.baca.getResource()
/* 116:115 */       .getString("gui.label.status"));
/* 117:116 */     this.completa.setText(this.baca.getResource().getString("gui.label.completa"));
/* 118:    */     
/* 119:118 */     Object[] msg = { lblNome, this.combo, lblStatus, this.cmbStatus, this.completa };
/* 120:    */     
/* 121:120 */     int option = JOptionPane.showConfirmDialog(this.baca, msg, 
/* 122:121 */       this.baca.getResource().getString("gui.title.trocaServico"), 2);
/* 123:122 */     if (option == 0)
/* 124:    */     {
/* 125:123 */       this.nomeServico = ((String)this.combo.getSelectedItem());
/* 126:    */       
/* 127:125 */       this.nomeServico = ((String)(this.valores.get(this.nomeServico) != null ? this.valores.get(this.nomeServico) : this.nomeServico));
/* 128:126 */       this.nomeStatus = ((String)this.cmbStatus.getSelectedItem());
/* 129:    */       
/* 130:128 */       this.nomeStatus = ((String)(this.status.get(this.nomeStatus) != null ? this.status.get(this.nomeStatus) : this.nomeStatus));
/* 131:129 */       if (this.completa.isSelected())
/* 132:    */       {
/* 133:130 */         this.acceptComplete = new AcceptCompleteEditavel();
/* 134:131 */         return this.acceptComplete.inicializa();
/* 135:    */       }
/* 136:133 */       return true;
/* 137:    */     }
/* 138:136 */     return false;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void processaAceita(String[] args, Order order)
/* 142:    */     throws Exception
/* 143:    */   {
/* 144:    */     try
/* 145:    */     {
/* 146:142 */       this.console.print("  - Alterando serviço " + order.getId() + "... ");
/* 147:143 */       String update = criaStringUpdate(order);
/* 148:144 */       System.out.println(update);
/* 149:145 */       OMSResponse response = this.connection.send(update);
/* 150:146 */       order = OrderUtils.parseOrderResponse(response.getData());
/* 151:147 */       this.console.println("[" + response.getMessage() + "]");
/* 152:148 */       if (this.completa.isSelected())
/* 153:    */       {
/* 154:149 */         args[0] = order.getId();
/* 155:150 */         args[1] = order.getHist();
/* 156:151 */         this.acceptComplete.processaAceita(args, order);
/* 157:    */       }
/* 158:    */       else
/* 159:    */       {
/* 160:153 */         tentaReceber(order);
/* 161:    */       }
/* 162:    */     }
/* 163:    */     catch (Exception ex)
/* 164:    */     {
/* 165:156 */       this.console.println("FALHA - " + ex.getMessage());
/* 166:157 */       ex.printStackTrace();
/* 167:158 */       if (order != null)
/* 168:    */       {
/* 169:159 */         args[0] = order.getId();
/* 170:160 */         args[1] = order.getHist();
/* 171:    */       }
/* 172:162 */       throw ex;
/* 173:    */     }
/* 174:    */   }
/* 175:    */   
/* 176:    */   public String criaStringUpdate(Order order)
/* 177:    */     throws Exception
/* 178:    */   {
/* 179:167 */     String update = "<UpdateOrder.Request>";
/* 180:168 */     update = update + OrderUtils.getIdHistCommonHeader(order);
/* 181:    */     
/* 182:170 */     Iterator it = OrderUtils.findElement(order, 
/* 183:171 */       "dados_gerais_servicos_smp.servico_smp.nome_servico_smp", this.nomeServico, 
/* 184:172 */       ".").iterator();
/* 185:174 */     while (it.hasNext())
/* 186:    */     {
/* 187:175 */       Element element = (Element)it.next();
/* 188:176 */       update = update + "<Update path=\"" + OrderUtils.Update.getPath((Element)element.getParentNode()) + "/status_servico_smp\">";
/* 189:177 */       update = update + this.nomeStatus + "</Update>";
/* 190:    */     }
/* 191:180 */     return update += "</UpdateOrder.Request>";
/* 192:    */   }
/* 193:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.acao.TrocaStatus
 * JD-Core Version:    0.7.0.1
 */