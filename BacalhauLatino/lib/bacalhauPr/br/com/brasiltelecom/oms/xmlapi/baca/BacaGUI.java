package br.com.brasiltelecom.oms.xmlapi.baca;

import br.com.brasiltelecom.oms.xmlapi.OMSConnection;
import br.com.brasiltelecom.oms.xmlapi.OMSConnectionFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.ResourceBundle;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

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
			this.serverConfig.load(ClassLoader
					.getSystemResourceAsStream("resources/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setTitle(this.resource.getString("gui.title") + " ("
				+ this.serverConfig.getProperty("server") + ":"
				+ this.serverConfig.getProperty("port") + ")");
		setIconImage(new ImageIcon(
				ClassLoader.getSystemResource("resources/icone.jpg"))
				.getImage());
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
		JLabel lbl = new JLabel("Bacalhau", 0);
		lbl.setFont(new Font("Courier New Bold", 3, 40));
		lbl.setForeground(new Color(26112));
		JLabel image = new JLabel(new ImageIcon(
				ClassLoader.getSystemResource("resources/splash.jpg")));
		JLabel lblUsername = new JLabel(
				this.resource.getString("gui.label.username"));
		JLabel lblPassword = new JLabel(
				this.resource.getString("gui.label.password"));
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

		BacaGUI.Login listener = new BacaGUI.Login(this);
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

		this.fonte = new Font(this.resource.getString("gui.font.name"), 0,
				Integer.parseInt(this.resource.getString("gui.font.size")));
		setFont(this.fonte);

		iniciaJanela();
		adicionaModulos();
		pack();
		centraliza(this);
		setVisible(true);
	}

	private void iniciaJanela()
  {
    JSplitPane splitPane = new JSplitPane();
    JScrollPane scrlConsole = new JScrollPane();
    JScrollPane scrlOrders = new JScrollPane();
    
    scrlConsole.setBorder(BorderFactory.createTitledBorder(
      BorderFactory.createEtchedBorder(), this.resource.getString("gui.label.console")));
    scrlOrders.setBorder(BorderFactory.createTitledBorder(
      BorderFactory.createEtchedBorder(), this.resource.getString("gui.label.orders")));
    
    this.txtConsole.setFont(this.fonte);
    this.txtOrders.setFont(this.fonte);
    this.txtConsole.setEditable(false);
    
    scrlConsole.getViewport().add(this.txtConsole);
    scrlOrders.getViewport().add(this.txtOrders);
    
    splitPane.setBorder(BorderFactory.createRaisedBevelBorder());
    splitPane.setLeftComponent(scrlConsole);
    splitPane.setRightComponent(scrlOrders);
    
    this.btnClearConsole.setText(this.resource.getString("gui.label.clearConsole"));
    this.btnClearConsole.setIcon(new ImageIcon(
      ClassLoader.getSystemResource("resources/clear.png")));
    this.btnClearConsole.addActionListener(new BacaGUI.1(this));
    




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
		Enumeration nomes = modulos.keys();
		while (nomes.hasMoreElements()) {
			String nome = (String) nomes.nextElement();
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
			System.err.println("icone nao encontrado: " + nomeIcone);
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
			this.console.println("Erro abrindo conexaoo!");
			this.console.println("server: " + this.server);
			this.console.println("port: " + this.port);
			this.console.println("username: " + this.username);
			ex.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) 
	{
		getInstance();
	}

	public static void centraliza(Container component) {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice device = env.getDefaultScreenDevice();
		Rectangle r = device.getDefaultConfiguration().getBounds();
		double x = r.getCenterX() - component.getSize().getWidth() / 2.0D;
		double y = r.getCenterY() - component.getSize().getHeight() / 2.0D;
		component.setLocation((int) x, (int) y);
	}
}
