package br.com.brasiltelecom.oms.xmlapi;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

public class OMSConnectionFactory {
	public static final String XMLAPI_ROOT = "/oms/XMLAPI";
	public static final String XMLAPI_LOGIN = "/oms/XMLAPI/login";
	public static final String XMLAPI_LOGOUT = "/oms/XMLAPI/logout";
	private static HashMap factorys = new HashMap();
	private String url;

	private OMSConnectionFactory(String url) {
		this.url = url;
	}

	public static OMSConnectionFactory getInstance(String server, int port) {
		String url = "http://" + server.toLowerCase().trim() + ":" + port;
		OMSConnectionFactory factory = (OMSConnectionFactory) factorys.get(url);
		if (factory == null) {
			factory = new OMSConnectionFactory(url);
			factorys.put(url, factory);
		}
		return factory;
	}

	private static String encode(String message) {
		try {
			return URLEncoder.encode(message, "ISO-8859-1");
		} catch (Exception ex) {
		}
		return message;
	}

	public OMSConnection getConnection(String user, String pass)
			throws IOException {
		URL urlLogin = new URL(this.url + "/oms/XMLAPI/login");
		URL urlRoot = new URL(this.url + "/oms/XMLAPI");
		URL urlLogout = new URL(this.url + "/oms/XMLAPI/logout");
		String message = encode("username") + "=" + encode(user) + "&"
				+ encode("password") + "=" + encode(pass);
		byte[] bytes = message.getBytes();
		String cookie = "";

		HttpURLConnection connection = (HttpURLConnection) urlLogin
				.openConnection();
		connection.setAllowUserInteraction(false);
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length",
				String.valueOf(bytes.length));
		connection.connect();
		OutputStream out = connection.getOutputStream();
		out.write(bytes);
		int code = connection.getResponseCode();
		if (code == 200) {
			String receivedcookie = connection.getHeaderField("Set-Cookie");
			if (receivedcookie == null) {
				throw new IOException("Server did not return session cookie");
			}
			cookie = receivedcookie.substring(0, receivedcookie.indexOf(';'));
		} else {
			throw new IOException("HTTP response code != 200 OK :" + code);
		}
		return new OMSConnection(urlRoot, urlLogout, cookie);
	}
}
