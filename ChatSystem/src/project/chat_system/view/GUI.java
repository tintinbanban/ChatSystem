package project.chat_system.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import project.chat_system.controller.Connected;
import project.chat_system.controller.Controller;
import project.chat_system.controller.Observable;
import project.chat_system.controller.Observateur;
import project.chat_system.controller.UDP;
import project.chat_system.model.UserList;

// Classe gérant la partie interface graphique pour l'utilisateur
public class GUI extends JFrame implements ActionListener, KeyListener,
		Runnable, WindowListener, MouseListener, Observateur {
	// //////////////////////////////////////////
	// Attribut(s)
	private Controller controller;
	// Composants
	private JLabel loginL;
	private JLabel sendL;
	private JLabel receiveL;
	private JTextField nicknameTF;
	protected JTextArea messSendTA;
	protected JTextArea messRecTA;
	private JButton sendB;
	private JButton connectB;
	protected BufferedWriter writer;
	protected BufferedReader reader;
	private JScrollPane scrollTASend;
	private JScrollPane scrollTARec;
	private Thread udpNIThread = null;
	private Thread tcpNIThread = null;
	private int RET_CONNECT = 1;
	private JList<String> userList;
	private JScrollPane scrollUl;
	private JLabel uListL;
	
	// //////////////////////////////////////////
	// Constructeur(s)
	public GUI(Controller conntroller) {
		setController(conntroller);
		initLogin();
	}

	// //////////////////////////////////////////
	// Getteur(s) & Setteur(s)
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	// //////////////////////////////////////////
	// Méthode(s) spécifique(s)
	// Fournit la fenetre de login au chat system
	private void initLogin() {
		// Instanciation des composants
		loginL = new JLabel("Nickname : ");
		nicknameTF = new JTextField();
		nicknameTF.setColumns(10);
		connectB = new JButton("Connect");

		// Layout(s)
		FlowLayout fLayout = new FlowLayout(FlowLayout.CENTER, 25, 10);
		this.setLayout(fLayout);

		// Disposition des composants
		this.add(loginL);
		this.add(nicknameTF);
		this.add(connectB);

		// Ajout d'evenements
		connectB.addActionListener(this);
		this.addWindowListener(this);
		nicknameTF.addKeyListener(this);

		// Ajustement de la fenetre
		this.setPreferredSize(new Dimension(400, 90));
		this.pack();
		this.setLocationRelativeTo(null);
		// Personnalisation de la fenetre
		this.setTitle("ChatSystem : Login form");
	}

	// Fournit la fenetre de chat pour dialoguer
	private void initChat() {
		// Variables locales
		int marg = 10;
		int wPgl = 370;
		int h = 340;
		String []data = new String[1];
		data[0] = "nobody ...";
		// Instanciation des composants
		sendL = new JLabel("message to send");
		receiveL = new JLabel("message to receive");
		sendB = new JButton("send");
		messSendTA = new JTextArea();
		messRecTA = new JTextArea();
		scrollTASend = new JScrollPane(messSendTA,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollTARec = new JScrollPane(messRecTA,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		userList = new JList<String>(data);
		scrollUl = new JScrollPane(userList);
		uListL = new JLabel("User List :");

		// Layout(s)
		getContentPane().removeAll();
		GridLayout gLayout = new GridLayout(2, 2);

		// Disposition des composants
		JPanel pGLayout = new JPanel();
		pGLayout.setLayout(gLayout);
		JPanel pFLayout = new JPanel();
		pFLayout.setLayout(new BoxLayout(pFLayout, BoxLayout.Y_AXIS));
		JPanel pBoxLayout = new JPanel();
		pBoxLayout.setLayout(new BoxLayout(pBoxLayout, BoxLayout.Y_AXIS));
		pBoxLayout.add(sendL);
		pBoxLayout.add(sendB);
		pGLayout.add(pBoxLayout);
		pGLayout.add(scrollTASend);
		pGLayout.add(receiveL);
		pGLayout.add(scrollTARec);
		pFLayout.add(uListL);
		pFLayout.add(scrollUl);
		this.setLayout(null);
		this.add(pGLayout);
		// Positionnement du panel contenant le GridLayout : partie chat
		pGLayout.setBounds(marg, marg, wPgl, h);
		this.add(pFLayout);
		// Positionnement du panel contenant le BoxLayout : partie userList
		pFLayout.setBounds(wPgl + 2*marg, marg, 180, h);
		// Ajout d'evenements
		sendB.addActionListener(this);
		this.addWindowListener(this);
		messRecTA.setEditable(false);
		userList.addMouseListener(this);

		// Ajustement de la fenetre
		this.setPreferredSize(new Dimension(600, 400));
		this.pack();

		// Personnalisation de la fenetre
		this.setTitle("ChatSystem : " + this.getController().getLocalUser().getUsername() + " [connected]");

		// Afficher la fenetre
		this.setVisible(true);
	}

	public void refreshUserList(UserList userList) {
		String []data = (String[]) userList.getUserList().toArray();
	}

	private int connect() {
		try {
			if (!nicknameTF.getText().isEmpty()) {
				this.getController().performConnect(nicknameTF.getText());

				// -> mise en place du pattern state
				Connected connected = new Connected();
				connected.doAction(controller);
				// -> affichage etat LOCAL USER
				System.out.println("--> LOCAL USER : "
						+ getController().getLocalUser().getUsername());
				// -> lancement d'un thread pour écouter les paquets que l'on
				// reçoit
				udpNIThread = new Thread((Runnable) this.getController()
						.getUdpNI());
				udpNIThread.start();
			} else {
				JOptionPane.showMessageDialog(this, "Champ 'Nickname' vide...",
						"ERREUR", JOptionPane.ERROR_MESSAGE);
			}
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}

	// //////////////////////////////////////////
	// Methode(s) redefinie(s)
	// //////////////////////////////////////////
	// REDEFINITION WindowsListener
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if (udpNIThread != null)
			udpNIThread.stop();
		this.controller.getUdpNI().getDatagramSocket().close();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	// /////////////////////////////////////////////
	// REACTION au clic sur le bouton 'connect'
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(connectB))
			RET_CONNECT = connect();
		if (arg0.getSource().equals(sendB)) {
			if (!messSendTA.getText().isEmpty()) {
				try {
					this.getController().performMessage(messSendTA.getText());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		}
	}

	// /////////////////////////////////////////////
	// METHODE RUN
	@Override
	public void run() {
		// Afficher la fenetre
		this.show();
		// On attend que la connexion soit etablie
		while (RET_CONNECT != 0) {
		}
		initChat();
	}

	// /////////////////////////////////////////////
	// REACTION a l'appui sur une touche du clavier 
	@Override
	public void keyPressed(KeyEvent arg0) {
		if ((arg0.getKeyCode() == KeyEvent.VK_ENTER)) {
			RET_CONNECT = connect();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	// /////////////////////////////////////////////
	// REACTION a l'action de la souris sur l'application
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
            int index = userList.locationToIndex(e.getPoint());
            System.out.println("Double clicked on Item " + index);
         }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actualiser(Observable o) {
		if (o instanceof Controller) {
			Controller c = (Controller) o;
			this.refreshUserList(c.getUserList());
		}
		
	}
}
