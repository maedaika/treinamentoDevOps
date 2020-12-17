package br.com.brasiltelecom.oms.xmlapi.baca.acao;

import br.com.brasiltelecom.oms.data.Order;
import br.com.brasiltelecom.oms.xmlapi.OMSResponse;
import br.com.brasiltelecom.oms.xmlapi.util.OrderUtils;

public class AcaoAceita extends Acao {
	public void processa(String[] args) throws Exception {
		String orderID = args[0];
		String orderHist = args[1];
		String getOrder = OrderUtils.getOrderRequest(orderID, orderHist, true);
		this.console.print("  - Solicitando ordem " + orderID + "... ");
		Order order = null;
		OMSResponse response = null;
		try {
			response = this.connection.send(getOrder);
			order = OrderUtils.parseOrderResponse(response.getData());
			args[1] = String.valueOf(order.getHist());
			this.console.println("[" + response.getMessage() + "]");
		} catch (Exception ex) {
			ex.printStackTrace();
			this.console.println("FALHA - " + ex.getMessage());
			tentaReceber(order);
			throw ex;
		} finally {
			if (order != null) {
				args[1] = String.valueOf(order.getHist());
			}
		}
		try {
			processaAceita(args, order);
		} catch (Exception ex) {
			ex.printStackTrace();
			//Comentado depois da decopilacao
			// args[0] = order.getId();
			// args[1] = order.getHist();
			throw ex;
		} finally {
			if (order != null) {
				args[1] = String.valueOf(order.getHist());
			}
		}
	}

	public void processaAceita(String[] args, Order order) throws Exception {
	}

	public void tentaReceber(Order order) {
		try {
			OMSResponse res = this.connection.send(OrderUtils
					.receiveOrderRequest(order));
			Order nova = OrderUtils.parseOrderResponse(res.getData());
			order.setId(nova.getId());
			order.setHist(nova.getHist());
		} catch (Exception ex) {
			System.err.println("Erro ao tentar receber a ordem:" + order);
		}
	}
}
