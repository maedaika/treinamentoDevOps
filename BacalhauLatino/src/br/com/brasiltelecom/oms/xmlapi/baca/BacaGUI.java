package br.com.brasiltelecom.oms.xmlapi.baca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
import br.com.brasiltelecom.oms.xmlapi.OMSConnectionFactory;

/**
 * @version 1.2
 * @author alessandro.maeda
 * @since 2015/12/02 Botogota
 * Classe principal 
 */
public class BacaGUI extends JFrame {
	
	
	ResourceBundle resource;
	private Font fonte;
	private JTextArea txtOrders = new JTextArea(20, 18);
	private JTextArea txtConsole = new JTextArea(20, 60);
	private JButton btnClearConsole = new JButton();
	private ConsoleLogger console = new ConsoleLogger(this.txtConsole);
	private static BacaGUI instance = null;
	private String username;
	private String password;
	private String server;
	private String port;
	
	JPanel pnlBotoes = new JPanel(new FlowLayout(1));
	private Properties serverConfig = new Properties();
	JButton btnLogin = new JButton();
	JTextField txtUsername = new JTextField(10);
	JPasswordField txtPassword = new JPasswordField(10);

	private BacaGUI() {
		this.resource = ResourceBundle.getBundle("resources/resource");
		try {
			this.serverConfig.load(ClassLoader.getSystemResourceAsStream("resources/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTitle(this.resource.getString("gui.title") + " ("
				+ this.serverConfig.getProperty("server") + ":"
				+ this.serverConfig.getProperty("port") + ")");
		setIconImage(new ImageIcon(ClassLoader.getSystemResource("resources/icone.jpg")).getImage());
		setDefaultCloseOperation(3);
		login();
	}

	public static BacaGUI getInstance() {
		if (instance == null) {
			instance = new BacaGUI();
		}
		return instance;
	}

	public void login() {
		JLabel lbl = new JLabel("Bacalhau Colombiano", 0);
		lbl.setFont(new Font("Courier New Bold", 3, 40));
		lbl.setForeground(new Color(26112));
		JLabel image = new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/splash.jpg")));
		JLabel lblUsername = new JLabel(this.resource.getString("gui.label.username"));
		JLabel lblPassword = new JLabel(this.resource.getString("gui.label.password"));
		this.btnLogin.setText(this.resource.getString("gui.label.login"));

		JPanel pnlLogin = new JPanel(new BorderLayout());
		JPanel pnlUsername = new JPanel(new FlowLayout(1));
		JPanel pnlPassword = new JPanel(new FlowLayout(1));
		this.txtUsername.setText(this.serverConfig.getProperty("username", ""));
		this.txtPassword.setText(this.serverConfig.getProperty("password", ""));
		pnlUsername.add(lblUsername);
		pnlUsername.add(this.txtUsername);
		pnlPassword.add(lblPassword);
		pnlPassword.add(this.txtPassword);
		pnlLogin.add(pnlUsername, "North");
		pnlLogin.add(pnlPassword, "Center");
		pnlLogin.add(this.btnLogin, "South");
		pnlUsername.setBackground(new Color(13421568));
		pnlPassword.setBackground(new Color(13421568));

		JPanel pnl = new JPanel(new BorderLayout());
		pnl.setBackground(new Color(13421568));
		pnl.add(image, "Center");
		pnl.add(lbl, "North");
		pnl.add(pnlLogin, "South");
		pnl.setBorder(BorderFactory.createEtchedBorder());
		JPanel login = new JPanel();

		Login listener = new Login(null);
		this.btnLogin.addActionListener(listener);
		this.txtUsername.addActionListener(listener);
		this.txtPassword.addActionListener(listener);

		setContentPane(pnl);
		pack();
		centraliza(this);
		setVisible(true);
	}

	public void init() {
		setContentPane(new JPanel(new BorderLayout()));
		this.fonte = new Font(this.resource.getString("gui.font.name"), 0,Integer.parseInt(this.resource.getString("gui.font.size")));
		setFont(this.fonte);

		iniciaJanela();
		adicionaModulos();
		pack();
		centraliza(this);
		setVisible(true);
	}

	private void iniciaJanela() {
		JSplitPane splitPane = new JSplitPane();
		JScrollPane scrlConsole = new JScrollPane();
		JScrollPane scrlOrders = new JScrollPane();

		scrlConsole.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				this.resource.getString("gui.label.console")));
		scrlOrders.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				this.resource.getString("gui.label.orders")));

		this.txtConsole.setFont(this.fonte);
		this.txtOrders.setFont(this.fonte);
		this.txtConsole.setEditable(false);

		scrlConsole.getViewport().add(this.txtConsole);
		scrlOrders.getViewport().add(this.txtOrders);

		splitPane.setBorder(BorderFactory.createRaisedBevelBorder());
		splitPane.setLeftComponent(scrlConsole);
		splitPane.setRightComponent(scrlOrders);

		this.btnClearConsole.setText(this.resource.getString("gui.label.clearConsole"));
		this.btnClearConsole.setIcon(new ImageIcon(ClassLoader.getSystemResource("resources/clear.png")));
		this.btnClearConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				BacaGUI.this.console.clear();
				BacaGUI.this.txtOrders.setText("");
			}
		});
		this.pnlBotoes.add(this.btnClearConsole);
		this.pnlBotoes.setBorder(BorderFactory.createRaisedBevelBorder());
		this.pnlBotoes.setPreferredSize(new Dimension(280, 70));

		JSplitPane splitFora = new JSplitPane(0);
		splitFora.add(splitPane);
		splitFora.add(this.pnlBotoes);
		getContentPane().add(splitFora);
	}

	private void adicionaModulos() {
		Properties modulos = new Properties();
		try {
			modulos.load(ClassLoader
					.getSystemResourceAsStream("resources/modules.properties"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		TreeSet set = new TreeSet(modulos.keySet());
		Iterator nomes = set.iterator();
		while (nomes.hasNext()) {
			String nome = (String) nomes.next();
			String nomeClasse = modulos.getProperty(nome);
			adicionaModulo(nome, nomeClasse);
		}
	}

	private void adicionaModulo(String nome, String nomeClasse) {
		JButton button = new JButton(nome);
		String nomeIcone = "";
		try {
			ActionListener modulo = (ActionListener) Class.forName(nomeClasse)
					.newInstance();
			button.addActionListener(modulo);
			nomeIcone = modulo.getClass().getName();
			nomeIcone = nomeIcone.substring(nomeIcone.lastIndexOf('.') + 1)
					+ ".png";
			this.pnlBotoes.add(button);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			Icon icon = new ImageIcon(
					ClassLoader.getSystemResource("resources/" + nomeIcone));
			if (icon != null) {
				button.setIcon(icon);
			}
		} catch (Exception ex) {
			System.err.println("Icone nao encontrado: " + nomeIcone);
		}
		System.out.println("#Carregando modulo: " + nome + " icone: "
				+ nomeIcone + " classe: " + nomeClasse);
	}

	public String getOrders() {
		return this.txtOrders.getText();
	}

	public ResourceBundle getResource() {
		return this.resource;
	}

	public Properties getServerConfig() {
		return this.serverConfig;
	}

	public ConsoleLogger getConsole() {
		return this.console;
	}

	public String getUsername() {
		return this.username;
	}

	public OMSConnection createConnection() {
		try {
			return OMSConnectionFactory.getInstance(this.server,
					Integer.parseInt(this.port)).getConnection(this.username,
					this.password);
		} catch (Exception ex) {
			this.console.println("Erro abrindo conexão!");
			this.console.println("server: " + this.server);
			this.console.println("port: " + this.port);
			this.console.println("username: " + this.username);
			ex.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		getInstance();
	}

	public static void centraliza(Container component) {
		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		Rectangle r = device.getDefaultConfiguration().getBounds();
		double x = r.getCenterX() - component.getSize().getWidth() / 2.0D;
		double y = r.getCenterY() - component.getSize().getHeight() / 2.0D;
		component.setLocation((int) x, (int) y);
	}

	private class Login implements ActionListener {
		Login(Login paramLogin) {
			this();
		}

		public void actionPerformed(ActionEvent e) {
			BacaGUI.this.password = new String(
					BacaGUI.this.txtPassword.getPassword());
			BacaGUI.this.username = BacaGUI.this.txtUsername.getText();
			BacaGUI.this.server = BacaGUI.this.serverConfig
					.getProperty("server");
			BacaGUI.this.port = BacaGUI.this.serverConfig.getProperty("port");
			try {
				OMSConnectionFactory.getInstance(BacaGUI.this.server,
						Integer.parseInt(BacaGUI.this.port)).getConnection(
						BacaGUI.this.username, BacaGUI.this.password);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(BacaGUI.this,
						"Erro efetuando Login:\n" + ex.getMessage());
				return;
			}
			BacaGUI.this.init();
		}

		private Login() {
		}
	}
}
