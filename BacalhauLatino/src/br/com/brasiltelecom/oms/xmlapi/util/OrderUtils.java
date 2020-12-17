package br.com.brasiltelecom.oms.xmlapi.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.brasiltelecom.oms.data.OMSException;
import br.com.brasiltelecom.oms.data.Order;

public class OrderUtils {
	private static DocumentBuilderFactory xmlFactory;

	public static String getOrderRequest(Order order, boolean accept) {
		return

		"<GetOrder.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>"
				+ order.getId() + "</OrderID>" + "<OrderHistID>"
				+ order.getHist() + "</OrderHistID><Accept>" + accept
				+ "</Accept></GetOrder.Request>";
	}

	public static String getOrderRequest(String orderId, String orderHist,
			boolean accept) {
		return getOrderRequest(new Order(orderId, orderHist), accept);
	}

	public static String receiveOrderRequest(Order order) {
		// verificar depois
		return receiveOrderRequest(order);
	}

	public static String receiveOrderRequest(String orderId, String orderHist) {
		return

		"<ReceiveOrder.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>"
				+ orderId + "</OrderID>" + "<OrderHistID>" + orderHist
				+ "</OrderHistID></ReceiveOrder.Request>";
	}

	public static Order parseOrderResponse(String orderResponse)
			throws OMSException {
		Element orderXml = parseResponse(orderResponse);
		Node id = orderXml.getElementsByTagName("OrderID").item(0)
				.getChildNodes().item(0);
		Node hist = orderXml.getElementsByTagName("OrderHistID").item(0)
				.getChildNodes().item(0);
		Element root = (Element) orderXml.getElementsByTagName("_root").item(0);
		return new Order(id.getNodeValue(), hist.getNodeValue(), root);
	}

	public static String getOrderProcessHistory(int id) {
		return "<GetOrderProcessHistory.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>"
				+ id + "</OrderID></GetOrderProcessHistory.Request>";
	}

	public static String getOrderProcessHistory(Order order) {
		return "<GetOrderProcessHistory.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>"
				+ order.getId() + "</OrderID></GetOrderProcessHistory.Request>";
	}

	public static long getLastHistory(String xml) {
		int beg = xml.lastIndexOf("<OrderHistID>") + "<OrderHistID>".length();
		int end = xml.lastIndexOf("</OrderHistID>");
		if (end < 0) {
			return -1L;
		}
		return Long.parseLong(xml.substring(beg, end).trim());
	}

	public static Element parseResponse(String xml) throws OMSException {
		Element response = null;
		try {
			response = xmlFactory.newDocumentBuilder()
					.parse(new ByteArrayInputStream(xml.getBytes()))
					.getDocumentElement();
		} catch (Exception ex) {
			System.err.println("error parsing xml: " + xml);
			throw new OMSException("Erro ao ler o XML", ex);
		}
		response.normalize();
		String name = response.getTagName();
		if (name.endsWith(".Error")) {
			NamedNodeMap atributos = response.getFirstChild().getAttributes();
			Node codigo = atributos.getNamedItem("code");
			Node desc = atributos.getNamedItem("desc");
			System.err.println("erro:" + xml);
			throw new OMSException(desc.getNodeValue() + " ("
					+ codigo.getNodeValue() + ")");
		}
		return response;
	}

	public static String completeOrderRequest(Order order, String status) {
		return

		"<CompleteOrder.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>"
				+ order.getId() + "</OrderID>" + "<OrderHistID>"
				+ order.getHist() + "</OrderHistID>" + "<Status>" + status
				+ "</Status></CompleteOrder.Request>";
	}

	public static String completeOrderRequest(String orderId, String orderHist,
			String status) {
		return completeOrderRequest(new Order(orderId, orderHist), status);
	}

	public static String assignOrderRequest(String orderId, String orderHist,
			String user) {
		return

		"<AssignOrder.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>"
				+ orderId + "</OrderID>" + "<OrderHistID>" + orderHist
				+ "</OrderHistID><User>" + user
				+ "</User></AssignOrder.Request>";
	}

	public static String setException(String orderId, String orderHist,
			String exception) {
		return

		"<SetException.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>"
				+ orderId + "</OrderID><OrderHistID>" + orderHist
				+ "</OrderHistID><Status>" + exception
				+ "</Status></SetException.Request>";
	}

	public static String getIdHistCommonHeader(Order order) {
		return "<OrderID>" + order.getId() + "</OrderID><OrderHistID>"
				+ order.getHist() + "</OrderHistID>";
	}

	public static Collection findElement(Order order, String path,
			String value, String separator) {
		StringTokenizer tokenizer = new StringTokenizer(path, separator);
		String[] paths = new String[tokenizer.countTokens()];
		ArrayList elements = new ArrayList();
		for (int i = 0; tokenizer.hasMoreTokens(); i++) {
			paths[i] = tokenizer.nextToken();
		}
		NodeList list = order.getRoot().getElementsByTagName(
				paths[(paths.length - 1)]);
		for (int i = 0; i < list.getLength(); i++) {
			if ((list.item(i) instanceof Element)) {
				Element element = (Element) list.item(i);
				if ((matchNonIndexedPath(element, paths))
						&& (matchTextValue(element, value))) {
					elements.add(element);
				}
			}
		}
		return elements;
	}

	public static boolean matchTextValue(Element element, String value) {
		NodeList list = element.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			try {
				if (list.item(i).getNodeValue().equals(value)) {
					return true;
				}
			} catch (Exception localException) {
			}
		}
		return false;
	}

	public static boolean matchNonIndexedPath(Element element, String[] path) {
		return matchNonIndexedPath(element, path, path.length - 1);
	}

	public static boolean matchNonIndexedPath(Element element, String[] path,
			int index) {
		if (index < 0) {
			return element.getTagName().equals("_root");
		}
		if (element.getTagName().equals(path[index])) {
			return matchNonIndexedPath((Element) element.getParentNode(), path,
					index - 1);
		}
		return false;
	}

	public static String updateContent(Element root, String path, String value,
			String separator) {
		StringTokenizer tokenizer = new StringTokenizer(path, separator);
		String[] paths = new String[tokenizer.countTokens()];
		for (int i = 0; i < paths.length; i++) {
			paths[i] = tokenizer.nextToken();
		}
		Element last = root;
		Element lastExisting = last;
		int lastIndex = 0;
		while ((last != null) && (lastIndex < paths.length)) {
			last = Update.getLastPath(last, paths[lastIndex]);
			if (last != null) {
				lastExisting = last;
				lastIndex++;
			}
		}
		lastExisting = last != null ? last : lastExisting;

		String operation = lastIndex == paths.length ? "Update" : "Add";
		System.out.println("lastExisting:" + lastExisting.getTagName()
				+ ", lastIndex:" + lastIndex);
		System.out.println("operation:" + operation);
		System.out.println("updatePath:" + Update.getPath(lastExisting));
		System.out.println("updateBody:"
				+ Update.getUpdateBody(paths, lastIndex, value));
		return "<" + operation + " path=\"" + Update.getPath(lastExisting)
				+ "\">" + Update.getUpdateBody(paths, lastIndex, value) + "</"
				+ operation + ">";
	}

	public static String updateOrderRequest(Order order, String body) {
		return

		"<UpdateOrder.Request xmlns=\"urn:com:metasolv:oms:xmlapi:1\"><OrderID>"
				+ order.getId() + "</OrderID>" + "<OrderHistID>"
				+ order.getHist() + "</OrderHistID>" + body
				+ "</UpdateOrder.Request>";
	}

	public static class Update {
		protected static Element getLastPath(Element element, String lastPath) {
			int multiIndex = lastPath.indexOf('[');

			System.out.println("Procurando: " + lastPath + " em: "
					+ element.getTagName());
			if (multiIndex < 0) {
				System.out.println("Single Instance");
				return getLastPathSimple(element, lastPath);
			}
			int index = Integer.parseInt(lastPath.substring(multiIndex + 1,
					lastPath.indexOf(']')));

			System.out.println("Multi Instance: "
					+ lastPath.substring(0, multiIndex) + ", " + index);
			return getLastPathMulti(element, lastPath.substring(0, multiIndex),
					index);
		}

		protected static Element getLastPathMulti(Element element,
				String lastPath, int index) {
			NodeList list = element.getChildNodes();
			int match = 0;
			Element result = null;
			for (int i = 0; (match != index) && (i < list.getLength()); i++) {
				if ((list.item(i) instanceof Element)) {
					result = (Element) list.item(i);
					if ((result.getTagName() != null)
							&& (lastPath.equals(result.getTagName()))) {
						match++;
					}
				}
			}
			return match == index ? result : null;
		}

		protected static Element getLastPathSimple(Element element,
				String lastPath) {
			NodeList list = element.getChildNodes();
			boolean exist = false;
			Element result = null;
			for (int i = 0; i < list.getLength(); i++) {
				if ((list.item(i) instanceof Element)) {
					result = (Element) list.item(i);
					if ((result.getTagName() != null)
							&& (lastPath.equals(result.getTagName()))) {
						if (!result.hasAttribute("index")) {
							exist = true;
							break;
						}
						exist = false;
						break;
					}
				}
			}
			return exist ? result : null;
		}

		public static String getPath(Element element) {
			if ((element == null) || (element.getTagName() == null)
					|| (element.getTagName().equals("_root"))) {
				return "/";
			}
			String path = "";
			while ((element != null) && (element.getTagName() != null)
					&& (!element.getTagName().equals("_root"))) {
				String tagName = element.getTagName();
				String index = element.hasAttribute("index") ? "[@index='"
						+ element.getAttribute("index") + "']" : "";
				path = "/" + tagName + index + path;
				element = (Element) element.getParentNode();
			}
			return path;
		}

		public static String getUpdateBody(String[] paths, int lastIndex,
				String value) {
			if (lastIndex >= paths.length) {
				return value;
			}
			return

			"<" + paths[lastIndex] + ">"
					+ getUpdateBody(paths, lastIndex + 1, value) + "</"
					+ paths[lastIndex] + ">";
		}
	}

	public static void main(String[] args) {
		String xml = "<OrderID>3197</OrderID><Namespace>smp</Namespace><Version>1.0</Version><Summary><ActualDuration>256</ActualDuration><StartDate>2005-02-18T11:53:21 GMT-03:00</StartDate><CompleteDate></CompleteDate><ExpectedCompletionDate>2005-02-19T11:54:32 GMT-03:00</ExpectedCompletionDate></Summary><Transitions><Transition><TaskID>creation10014</TaskID><TaskType>creation</TaskType><TaskDescription>P224 ServiÃ§os SMP/CRM / SISGEN order entry task</TaskDescription><ExpectedCompletionDate>2005-02-18T11:53:21 GMT-03:00</ExpectedCompletionDate><ActualDuration>62</ActualDuration><StartDate>2005-02-18T11:53:21 GMT-03:00</StartDate><CompleteDate>2005-02-18T11:54:23 GMT-03:00</CompleteDate><OrderHistID>29517</OrderHistID><FromOrderHistID></FromOrderHistID><State>accepted</State><Status>n/a</Status><TransitionType>normal</TransitionType><User>suporte</User><ParentTaskOrderHistID/><DataNodeIndex/><DataNodeMnemonic/><DataNodeValue/></Transition><Transition><TaskID>creation10014</TaskID><TaskType>creation</TaskType><TaskDescription>P224 ServiÃ§os SMP/CRM / SISGEN order entry task</TaskDescription><ExpectedCompletionDate>2005-02-18T11:53:21 GMT-03:00</ExpectedCompletionDate><ActualDuration>0</ActualDuration><StartDate>2005-02-18T11:54:23 GMT-03:00</StartDate><CompleteDate>2005-02-18T11:54:23 GMT-03:00</CompleteDate><OrderHistID>29518</OrderHistID><FromOrderHistID>29517</FromOrderHistID><State>completed</State><Status>submit</Status><TransitionType>normal</TransitionType><User>suporte</User><ParentTaskOrderHistID/><DataNodeIndex/><DataNodeMnemonic/><DataNodeValue/></Transition><Transition><TaskID>e_pre_ativacao</TaskID><TaskType>rule</TaskType><TaskDescription>Ã? Pre AtivaÃ§Ã£o?</TaskDescription><ExpectedCompletionDate></ExpectedCompletionDate><ActualDuration>8</ActualDuration><StartDate>2005-02-18T11:54:23 GMT-03:00</StartDate><CompleteDate>2005-02-18T11:54:31 GMT-03:00</CompleteDate><OrderHistID>29519</OrderHistID><FromOrderHistID>29518</FromOrderHistID><State>received</State><Status>n/a</Status><TransitionType>normal</TransitionType><User>suporte</User><ParentTaskOrderHistID/><DataNodeIndex/><DataNodeMnemonic/><DataNodeValue/></Transition><Transition><TaskID>e_pre_ativacao</TaskID><TaskType>rule</TaskType><TaskDescription>Ã? Pre AtivaÃ§Ã£o?</TaskDescription><ExpectedCompletionDate></ExpectedCompletionDate><ActualDuration>0</ActualDuration><StartDate>2005-02-18T11:54:31 GMT-03:00</StartDate><CompleteDate>2005-02-18T11:54:31 GMT-03:00</CompleteDate><OrderHistID>29520</OrderHistID><FromOrderHistID>29519</FromOrderHistID><State>completed</State><Status>true</Status><TransitionType>normal</TransitionType><User>OMSHO2</User><ParentTaskOrderHistID/><DataNodeIndex/><DataNodeMnemonic/><DataNodeValue/></Transition><Transition><TaskID>s493_tratamento_de_erros_pre_ativacao_smp</TaskID><TaskType>subprocess</TaskType><TaskDescription>S493 Tratamento de Erros Pre AtivaÃ§Ã£o SMP</TaskDescription><ExpectedCompletionDate></ExpectedCompletionDate><ActualDuration>0</ActualDuration><StartDate>2005-02-18T11:54:31 GMT-03:00</StartDate><CompleteDate>2005-02-18T11:54:31 GMT-03:00</CompleteDate><OrderHistID>29521</OrderHistID><FromOrderHistID>29520</FromOrderHistID><State>received</State><Status>n/a</Status><TransitionType>normal</TransitionType><User>OMSHO2</User><ParentTaskOrderHistID/><DataNodeIndex/><DataNodeMnemonic/><DataNodeValue/></Transition><Transition><TaskID>regra_indicador_sla</TaskID><TaskType>rule</TaskType><TaskDescription>Regra Indicador SLA</TaskDescription><ExpectedCompletionDate></ExpectedCompletionDate><ActualDuration>1</ActualDuration><StartDate>2005-02-18T11:54:31 GMT-03:00</StartDate><CompleteDate>2005-02-18T11:54:32 GMT-03:00</CompleteDate><OrderHistID>29522</OrderHistID><FromOrderHistID>29521</FromOrderHistID><State>received</State><Status>n/a</Status><TransitionType>normal</TransitionType><User>OMSHO2</User><ParentTaskOrderHistID>29521</ParentTaskOrderHistID><DataNodeIndex/><DataNodeMnemonic/><DataNodeValue/></Transition><Transition><TaskID>regra_indicador_sla</TaskID><TaskType>rule</TaskType><TaskDescription>Regra Indicador SLA</TaskDescription><ExpectedCompletionDate></ExpectedCompletionDate><ActualDuration>0</ActualDuration><StartDate>2005-02-18T11:54:32 GMT-03:00</StartDate><CompleteDate>2005-02-18T11:54:32 GMT-03:00</CompleteDate><OrderHistID>29523</OrderHistID><FromOrderHistID>29522</FromOrderHistID><State>completed</State><Status>true</Status><TransitionType>normal</TransitionType><User>OMSHO2</User><ParentTaskOrderHistID>29521</ParentTaskOrderHistID><DataNodeIndex/><DataNodeMnemonic/><DataNodeValue/></Transition><Transition><TaskID>tratamento_de_erros_smp_oms</TaskID><TaskType>manual</TaskType><TaskDescription>Tratamento de Erros SMP (OMS)</TaskDescription><ExpectedCompletionDate>2005-02-19T11:54:32 GMT-03:00</ExpectedCompletionDate><ActualDuration>185</ActualDuration><StartDate>2005-02-18T11:54:32 GMT-03:00</StartDate><CompleteDate></CompleteDate><OrderHistID>29524</OrderHistID><FromOrderHistID>29523</FromOrderHistID><State>received</State><Status>n/a</Status><TransitionType>normal</TransitionType><User>OMSHO2</User><ParentTaskOrderHistID>29521</ParentTaskOrderHistID><DataNodeIndex/><DataNodeMnemonic/><DataNodeValue/></Transition></Transitions></GetOrderProcessHistory.Response>";
		System.out.println(getLastHistory(xml));
	}
}
