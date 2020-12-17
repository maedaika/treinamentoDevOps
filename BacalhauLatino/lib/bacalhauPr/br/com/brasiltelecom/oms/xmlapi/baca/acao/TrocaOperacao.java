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
/*  24:    */ public class TrocaOperacao
/*  25:    */   extends AcaoAceita
/*  26:    */ {
/*  27:    */   String nomeServico;
/*  28:    */   String nomeOperacao;
/*  29: 35 */   JComboBox combo = new JComboBox();
/*  30: 37 */   JComboBox cmbOperacao = new JComboBox();
/*  31: 39 */   TreeMap valores = new TreeMap();
/*  32: 41 */   TreeMap operacao = new TreeMap();
/*  33: 43 */   JCheckBox completa = new JCheckBox();
/*  34:    */   AcceptComplete acceptComplete;
/*  35:    */   
/*  36:    */   public TrocaOperacao()
/*  37:    */   {
/*  38: 48 */     this.valores.put("BLACK LIST PRÉ-PAGO", "ELM_GPP_BLACK_LIST");
/*  39: 49 */     this.valores.put("BLOQUEIO 0300", "ELM_BLOQ_CHAM1");
/*  40: 50 */     this.valores.put("BLOQUEIO 0500/0900/900", "ELM_BLOQ_CHAM2");
/*  41: 51 */     this.valores.put("BLOQUEIO 0900", "ELM_BLOQ_CHAM3");
/*  42: 52 */     this.valores.put("BLOQUEIO 900", "ELM_BLOQ_CHAM4");
/*  43: 53 */     this.valores.put("BLOQUEIO DE IDENTIFICADOR DE CHAMADAS", "ELM_BLOQ_IDCHAM");
/*  44: 54 */     this.valores.put("BLOQUEIO DE LIGAÇÕES A COBRAR", "ELM_BLOQ_COBRAR");
/*  45: 55 */     this.valores.put("BLOQUEIO DE ROAMING", "ELM_BLOQ_ROAMING");
/*  46: 56 */     this.valores.put("BLOQUEIO GPRS", "ELM_BLOQ_GPRS");
/*  47: 57 */     this.valores.put("BLOQUEIO HOT LINE", "ELM_BLOQ_HOT_LINE");
/*  48: 58 */     this.valores.put("BLOQUEIO HOT LINE MALA DIRETA", "ELM_BLOQ_HOT_LINE_MA");
/*  49: 59 */     this.valores.put("BLOQUEIO LDI", "ELM_BLOQ_CHAM6");
/*  50: 60 */     this.valores.put("BLOQUEIO LDN", "ELM_BLOQ_CHAM5");
/*  51: 61 */     this.valores.put("BLOQUEIO ROAMING INTERNACIONAL", "ELM_BLOQ_ROAMINT");
/*  52: 62 */     this.valores.put("BLOQUEIO SMS", "ELM_BLOQ_SMS");
/*  53: 63 */     this.valores.put("CAIXA POSTAL", "ELM_CXPOSTAL");
/*  54: 64 */     this.valores.put("CHAMADA EM ESPERA", "ELM_CHAMESPERA");
/*  55: 65 */     this.valores.put("CONSULTA E CONFERÊNCIA", "ELM_CONSCONF");
/*  56: 66 */     this.valores.put("FREE CALL PRÉ-PAGO", "ELM_GPP_FREE_CALL");
/*  57: 67 */     this.valores.put("GPRS", "ELM_GPRS");
/*  58: 68 */     this.valores.put("GRUPO 14", "ELM_G14");
/*  59: 69 */     this.valores.put("IDENTIFICADOR DE CHAMADAS", "ELM_IDCHAMADA");
/*  60: 70 */     this.valores.put("IDENTIFICADOR DE CHAMADAS PRÉ-PAGO", "ELM_GPP_IDCHAMADA");
/*  61: 71 */     this.valores.put("INFORMAÇÕES DO CLIENTE", "ELM_NDS");
/*  62: 72 */     this.valores.put("INFORMAÇÕES DO SIMCARD", "ELM_INFO_SIMCARD");
/*  63: 73 */     this.valores.put("INFORMAÇÕES VPN", "ELM_INFO_VPN");
/*  64: 74 */     this.valores.put("MMS", "ELM_MMS");
/*  65: 75 */     this.valores.put("PLANO ALTERNATIVO PRÉ-PAGO DIURNO", "ELM_PL_ALT_PRE_DIURN");
/*  66: 76 */     this.valores.put("PLANO ALTERNATIVO PRÉ-PAGO NOTURNO", "ELM_PL_ALT_PRE_NOTUR");
/*  67: 77 */     this.valores.put("PLANO ALTERNATIVO PRÉ-PAGO REFERÊNCIA", "ELM_PL_ALT_PRE_REF");
/*  68: 78 */     this.valores.put("PLANO ALTERNATIVO PÓS-PAGO 14 CORPORATIVO", 
/*  69: 79 */       "ELM_PL_ALT_POS_CORP");
/*  70: 80 */     this.valores.put("PLANO ALTERNATIVO PÓS-PAGO 14 FRANQUIA INTELIGENTE", 
/*  71: 81 */       "ELM_PL_ALT_POS_FRAN_");
/*  72: 82 */     this.valores.put("PLANO ALTERNATIVO PÓS-PAGO CONTROLE", "ELM_PL_ALT_HIB_CTRL");
/*  73: 83 */     this.valores.put("PLANO ATENDENTE", "ELM_PL_HIB_ATENDENTE");
/*  74: 84 */     this.valores.put("PLANO BASICO - AUTO PLUS", "ELM_PL_BAS_POS_APLUS");
/*  75: 85 */     this.valores.put("PLANO BASICO PÓS-PAGO", "ELM_PL_BAS_POS");
/*  76: 86 */     this.valores.put("PLANO BRASIL CONTA 1", "ELM_PL_ALT_POS_MALAB");
/*  77: 87 */     this.valores.put("PLANO BRASIL CONTA 2", "ELM_PL_ALT_POS_MALAA");
/*  78: 88 */     this.valores.put("PLANO BRASIL CONTA 3", "ELM_PL_ALT_POS_LANCA");
/*  79: 89 */     this.valores.put("PLANO BÁSICO - CONDOR SUPER CENTER", "ELM_PL_BAS_POS_CONDO");
/*  80: 90 */     this.valores.put("PLANO BÁSICO - MAKRO", "ELM_PL_BAS_POS_MAKRO");
/*  81: 91 */     this.valores.put("PLANO BÁSICO - PLAZA VEÍCULOS", "ELM_PL_BAS_POS_PLAZA");
/*  82: 92 */     this.valores.put("PLANO BÁSICO - POLLOSHOP", "ELM_PL_BAS_POS_POLLO");
/*  83: 93 */     this.valores.put("PLANO BÁSICO - SENAI", "ELM_PL_BAS_POS_SENAI");
/*  84: 94 */     this.valores.put("PLANO BÁSICO - SERVOPA S/A INDÚSTRIA E COMÉRCIO   ", 
/*  85: 95 */       "ELM_PL_BAS_POS_SERVO");
/*  86: 96 */     this.valores.put("PLANO BÁSICO - VECODIL COM DE VEÍCULOS LTDA       ", 
/*  87: 97 */       "ELM_PL_BAS_POS_VECOD");
/*  88: 98 */     this.valores.put("PLANO COLABORADOR", "ELM_PL_POS_COLABORAD");
/*  89: 99 */     this.valores.put("PLANO INDICADO (NÃO COLABORADOR)", "ELM_PL_POS_INDICADO");
/*  90:100 */     this.valores.put("PLANO PF EMPRESAS 100", "ELM_PL_ALT_POS_PF100");
/*  91:101 */     this.valores.put("PLANO PF EMPRESAS 50", "ELM_PL_ALT_POS_PF50");
/*  92:102 */     this.valores.put("PLANO PF EMPRESAS OUTROS", "ELM_PL_ALT_POS_PFOT");
/*  93:103 */     this.valores.put("SIGA-ME", "ELM_SIGAME");
/*  94:104 */     this.valores.put("SMS", "ELM_SMS");
/*  95:105 */     this.valores.put("TELECONFERÊNCIA", "ELM_TELECONF");
/*  96:106 */     this.valores.put("UMS", "ELM_UMS");
/*  97:107 */     this.valores.put("VPN RAMAL", "ELM_VPN");
/*  98:108 */     this.valores.put("WAP", "ELM_WAP");
/*  99:    */     
/* 100:110 */     this.operacao.put("ADICIONAR", "Adicionar");
/* 101:111 */     this.operacao.put("BLOQUEAR", "bloquear");
/* 102:112 */     this.operacao.put("BLOQUEAR PARCIAL", "bloquear_parcial");
/* 103:113 */     this.operacao.put("DESBLOQUEAR", "desbloquear");
/* 104:114 */     this.operacao.put("MALA DIRETA", "mala_direta");
/* 105:115 */     this.operacao.put("MODIFICAR", "Modificar");
/* 106:116 */     this.operacao.put("NÃO ALTERADO", "nao_alterado");
/* 107:117 */     this.operacao.put("PRÉ ATIVAÇÃO", "pre_ativacao");
/* 108:118 */     this.operacao.put("RETIRAR", "Retirar");
/* 109:119 */     this.operacao.put("TROCA IMSI", "troca_imsi");
/* 110:120 */     this.operacao.put("TROCA MSISDN", "troca_msisdn");
/* 111:121 */     this.operacao.put("TROCA PLANO", "troca_plano");
/* 112:122 */     this.operacao.put("TROCA PLANO ANTIGO", "troca_plano_antigo");
/* 113:123 */     this.operacao.put("TROCA PLANO NOVO", "troca_plano_novo");
/* 114:    */     
/* 115:125 */     this.cmbOperacao = new JComboBox(this.operacao.keySet().toArray());
/* 116:126 */     this.combo = new JComboBox(this.valores.keySet().toArray());
/* 117:127 */     this.combo.setEditable(true);
/* 118:128 */     this.combo.addActionListener(CustomizaComboListener.getCustomiaComboListener(
/* 119:129 */       this, this.valores));
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean inicializa()
/* 123:    */   {
/* 124:133 */     JLabel lblNome = new JLabel(this.baca.getResource()
/* 125:134 */       .getString("gui.label.nomeServico"));
/* 126:135 */     JLabel lblOperacao = new JLabel(this.baca.getResource()
/* 127:136 */       .getString("gui.label.operacao"));
/* 128:137 */     this.completa.setText(this.baca.getResource().getString("gui.label.completa"));
/* 129:    */     
/* 130:139 */     Object[] msg = { lblNome, this.combo, lblOperacao, this.cmbOperacao, this.completa };
/* 131:    */     
/* 132:141 */     int option = JOptionPane.showConfirmDialog(this.baca, msg, 
/* 133:142 */       this.baca.getResource().getString("gui.title.trocaServico"), 2);
/* 134:143 */     if (option == 0)
/* 135:    */     {
/* 136:144 */       this.nomeServico = ((String)this.combo.getSelectedItem());
/* 137:    */       
/* 138:146 */       this.nomeServico = ((String)(this.valores.get(this.nomeServico) != null ? this.valores.get(this.nomeServico) : this.nomeServico));
/* 139:147 */       this.nomeOperacao = ((String)this.cmbOperacao.getSelectedItem());
/* 140:    */       
/* 141:149 */       this.nomeOperacao = ((String)(this.operacao.get(this.nomeOperacao) != null ? this.operacao.get(this.nomeOperacao) : this.nomeOperacao));
/* 142:150 */       if (this.completa.isSelected())
/* 143:    */       {
/* 144:151 */         this.acceptComplete = new AcceptCompleteEditavel();
/* 145:152 */         return this.acceptComplete.inicializa();
/* 146:    */       }
/* 147:154 */       return true;
/* 148:    */     }
/* 149:157 */     return false;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void processaAceita(String[] args, Order order)
/* 153:    */     throws Exception
/* 154:    */   {
/* 155:    */     try
/* 156:    */     {
/* 157:163 */       this.console.print("  - Alterando serviço " + order.getId() + "... ");
/* 158:164 */       String update = criaStringUpdate(order);
/* 159:165 */       System.out.println(update);
/* 160:166 */       OMSResponse response = this.connection.send(update);
/* 161:167 */       order = OrderUtils.parseOrderResponse(response.getData());
/* 162:168 */       this.console.println("[" + response.getMessage() + "]");
/* 163:169 */       if (this.completa.isSelected())
/* 164:    */       {
/* 165:170 */         args[0] = order.getId();
/* 166:171 */         args[1] = order.getHist();
/* 167:172 */         this.acceptComplete.processaAceita(args, order);
/* 168:    */       }
/* 169:    */       else
/* 170:    */       {
/* 171:174 */         tentaReceber(order);
/* 172:    */       }
/* 173:    */     }
/* 174:    */     catch (Exception ex)
/* 175:    */     {
/* 176:177 */       this.console.println("FALHA - " + ex.getMessage());
/* 177:178 */       ex.printStackTrace();
/* 178:179 */       if (order != null)
/* 179:    */       {
/* 180:180 */         args[0] = order.getId();
/* 181:181 */         args[1] = order.getHist();
/* 182:    */       }
/* 183:183 */       throw ex;
/* 184:    */     }
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String criaStringUpdate(Order order)
/* 188:    */     throws Exception
/* 189:    */   {
/* 190:188 */     String update = "<UpdateOrder.Request>";
/* 191:189 */     update = update + OrderUtils.getIdHistCommonHeader(order);
/* 192:    */     
/* 193:191 */     Iterator it = OrderUtils.findElement(order, 
/* 194:192 */       "dados_gerais_servicos_smp.servico_smp.nome_servico_smp", this.nomeServico, 
/* 195:193 */       ".").iterator();
/* 196:195 */     while (it.hasNext())
/* 197:    */     {
/* 198:196 */       Element element = (Element)it.next();
/* 199:197 */       update = update + "<Update path=\"" + OrderUtils.Update.getPath((Element)element.getParentNode()) + "/operacao_smp\">";
/* 200:198 */       update = update + this.nomeOperacao + "</Update>";
/* 201:    */     }
/* 202:201 */     return update += "</UpdateOrder.Request>";
/* 203:    */   }
/* 204:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.acao.TrocaOperacao
 * JD-Core Version:    0.7.0.1
 */