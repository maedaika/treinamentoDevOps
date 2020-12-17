package br.com.brasiltelecom.oms.xmlapi.baca.acao;

import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import br.com.brasiltelecom.oms.data.Order;
import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;

public class AcceptComplete
  extends AcaoAceita
{
  String status;
  String[] opcoesFantasia;
  HashMap opcoesMnemonico = new HashMap();
  JComboBox combo;
  
  public AcceptComplete()
  {
    this.opcoesMnemonico.put("Enviar para o Asap", "erro_corrigido_correcao_automatica");
    this.opcoesMnemonico.put("Erro Corrigido Manualmente", "sucesso_correcao_manual");
    this.combo = new JComboBox(this.opcoesMnemonico.keySet().toArray());
  }
  
  public boolean inicializa()
  {
    int option = JOptionPane.showConfirmDialog(this.baca, this.combo, this.baca.getResource()
      .getString("gui.title.mnemonicSelect"), 2);
    if (option != 0) {
      return false;
    }
    this.status = ((String)this.opcoesMnemonico.get(this.combo.getSelectedItem()));
    if (this.status == null) {
      this.status = ((String)this.combo.getSelectedItem());
    }
    return true;
  }
  
  public void processaAceita(String[] args, Order order)
    throws Exception
  {
    OMSResponse response = null;
    String completeOrder = OrderUtils.completeOrderRequest(order, this.status);
    
    this.console.print("  - Completando ordem " + order.getId() + "... ");
    try
    {
      response = this.connection.send(completeOrder);
      OrderUtils.parseResponse(response.getData());
      this.console.println("[" + response.getMessage() + "]");
    }
    catch (Exception ex)
    {
      this.console.println("FALHA - " + ex.getMessage());
      throw ex;
    }
  }
}
