/*   1:    */ package br.com.brasiltelecom.oms.xmlapi.baca.acao;
/*   2:    */ 
/*   3:    */ import br.com.brasiltelecom.oms.data.Order;
/*   4:    */ import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
/*   5:    */ import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
/*   6:    */ import br.com.brasiltelecom.oms.xmlapi.baca.BacaGUI;
/*   7:    */ import br.com.brasiltelecom.oms.xmlapi.baca.ConsoleLogger;
/*   8:    */ import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;
/*   9:    */ import java.io.PrintStream;
/*  10:    */ import java.util.ResourceBundle;
/*  11:    */ import javax.swing.JLabel;
/*  12:    */ import javax.swing.JOptionPane;
/*  13:    */ import javax.swing.JScrollPane;
/*  14:    */ import javax.swing.JTextArea;
/*  15:    */ import javax.swing.JTextField;
/*  16:    */ import javax.swing.text.JTextComponent;
/*  17:    */ import org.w3c.dom.Element;
/*  18:    */ import org.w3c.dom.Node;
/*  19:    */ import org.w3c.dom.NodeList;
/*  20:    */ 
/*  21:    */ public class AdicionaDescricao
/*  22:    */   extends AcaoAceita
/*  23:    */ {
/*  24:    */   String codigoErro;
/*  25:    */   String descricaoErro;
/*  26:    */   
/*  27:    */   public boolean inicializa()
/*  28:    */   {
/*  29: 24 */     JLabel lblCodigoErro = new JLabel(this.baca.getResource()
/*  30: 25 */       .getString("gui.label.codigoErro"));
/*  31: 26 */     JLabel lblDescricaoErro = new JLabel(this.baca.getResource()
/*  32: 27 */       .getString("gui.label.descricaoErro"));
/*  33: 28 */     JTextField txtCodigoErro = new JTextField(10);
/*  34: 29 */     JTextArea txtDescricaoErro = new JTextArea(10, 3);
/*  35:    */     
/*  36: 31 */     Object[] msg = { lblCodigoErro, txtCodigoErro, lblDescricaoErro, 
/*  37: 32 */       new JScrollPane(txtDescricaoErro) };
/*  38:    */     
/*  39: 34 */     int option = JOptionPane.showConfirmDialog(this.baca, msg, 
/*  40: 35 */       this.baca.getResource().getString("gui.title.preencheErro"), 2);
/*  41: 36 */     if (option == 0)
/*  42:    */     {
/*  43: 37 */       this.codigoErro = txtCodigoErro.getText();
/*  44: 38 */       this.descricaoErro = txtDescricaoErro.getText();
/*  45: 39 */       return true;
/*  46:    */     }
/*  47: 41 */     return false;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void processaAceita(String[] args, Order order)
/*  51:    */     throws Exception
/*  52:    */   {
/*  53: 46 */     String update = criaStringUpdate(order);
/*  54: 47 */     System.out.println(update);
/*  55:    */     try
/*  56:    */     {
/*  57: 49 */       this.console.print("  - Preenchendo os campos " + order.getId() + "... ");
/*  58: 50 */       OMSResponse response = this.connection.send(update);
/*  59: 51 */       order = OrderUtils.parseOrderResponse(response.getData());
/*  60: 52 */       this.console.println("[" + response.getMessage() + "]");
/*  61: 53 */       tentaReceber(order);
/*  62:    */     }
/*  63:    */     catch (Exception ex)
/*  64:    */     {
/*  65: 55 */       this.console.println("FALHA - " + ex.getMessage());
/*  66: 56 */       ex.printStackTrace();
/*  67: 57 */       throw ex;
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   protected String criaStringUpdate(Order order)
/*  72:    */   {
/*  73: 62 */     Element root = order.getRoot();
/*  74: 63 */     String update = "<UpdateOrder.Request><OrderID>" + order.getId() + 
/*  75: 64 */       "</OrderID>" + "<OrderHistID>" + order.getHist() + "</OrderHistID>";
/*  76: 65 */     NodeList statusServicos = root.getElementsByTagName("status_servico_smp");
/*  77: 66 */     for (int i = 0; i < statusServicos.getLength(); i++) {
/*  78:    */       try
/*  79:    */       {
/*  80: 68 */         Node statusServico = statusServicos.item(i);
/*  81: 69 */         String status = statusServico.getChildNodes().item(0).getNodeValue();
/*  82: 70 */         if ((status != null) && (status.equalsIgnoreCase("NOK"))) {
/*  83: 71 */           update = update + criaUpdateServico((Element)statusServico.getParentNode());
/*  84:    */         }
/*  85:    */       }
/*  86:    */       catch (Exception ex)
/*  87:    */       {
/*  88: 74 */         System.err.println(ex.getMessage());
/*  89:    */       }
/*  90:    */     }
/*  91: 78 */     update = update + "</UpdateOrder.Request>";
/*  92: 79 */     return update;
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected String criaUpdateServico(Element servico)
/*  96:    */   {
/*  97: 83 */     String update = "";
/*  98:    */     
/*  99: 85 */     String index = servico.getAttribute("index");
/* 100: 86 */     index = (index != null) && (!index.equals("")) ? "[@index='" + index + "']" : 
/* 101: 87 */       "";
/* 102: 88 */     NodeList listaDesc = servico.getElementsByTagName("descricao_erro_smp");
/* 103: 89 */     NodeList listaCod = servico.getElementsByTagName("codigo_erro_smp");
/* 104: 90 */     boolean existeDesc = listaDesc.getLength() > 0;
/* 105: 91 */     boolean existeCod = listaCod.getLength() > 0;
/* 106: 92 */     String operacaoDesc = existeDesc ? "Update" : "Add";
/* 107: 93 */     String operacaoCod = existeCod ? "Update" : "Add";
/* 108: 94 */     update = update + "<" + operacaoCod + 
/* 109: 95 */       " path=\"/dados_gerais_servicos_smp/servico_smp" + index + 
/* 110: 96 */       "/codigo_erro_smp\">" + this.codigoErro + "</" + operacaoCod + "><" + 
/* 111: 97 */       operacaoDesc + " path=\"/dados_gerais_servicos_smp/servico_smp" + 
/* 112: 98 */       index + "/descricao_erro_smp\">" + this.descricaoErro + "</" + 
/* 113: 99 */       operacaoDesc + ">";
/* 114:    */     
/* 115:101 */     return update;
/* 116:    */   }
/* 117:    */ }


/* Location:           D:\Pains\TRABALHO\OMS\BacalhauGUI_PRD.jar
 * Qualified Name:     lib.bacalhauPr.br.com.brasiltelecom.oms.xmlapi.baca.acao.AdicionaDescricao
 * JD-Core Version:    0.7.0.1
 */