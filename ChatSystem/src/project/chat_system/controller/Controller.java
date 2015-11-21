package project.chat_system.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

import project.chat_system.model.Message;
import project.chat_system.model.User;
import project.chat_system.model.UserList;
import project.chat_system.view.GUI;

// Classe gérant les interactions entre l'utilisateur du chatSystem et la RemoteApp
//-> utilisation du pattern MVC
public class Controller {
	// //////////////////////////////////////////
	// Attribut(s)
	private UserList userList;
	private User localUser;
	private ChatNI udpNI;
	private ChatNI tcpNI;
	private GUI gui;
	private Etat etat;

	// //////////////////////////////////////////
	// Constructeur(s)
	public Controller(UserList userList, ChatNI udpNI, ChatNI tcpNI, GUI gui) {
		setUserList(userList);
		setUdpNI(udpNI);
		setTcpNI(tcpNI);
		setGui(gui);
		this.etat = null;
		this.udpNI.sendInstanceOfController(this);
		this.tcpNI.sendInstanceOfController(this);
	}

	// //////////////////////////////////////////
	// Getteur(s) & Setteur(s)
	public UserList getUserList() {
		return userList;
	}

	public void setUserList(UserList userList) {
		this.userList = userList;
	}

	public ChatNI getUdpNI() {
		return udpNI;
	}

	public void setUdpNI(ChatNI udpNI) {
		this.udpNI = udpNI;
	}

	public ChatNI getTcpNI() {
		return tcpNI;
	}

	public void setTcpNI(ChatNI tcpNI) {
		this.tcpNI = tcpNI;
	}

	public GUI getGui() {
		return gui;
	}

	public void setGui(GUI gui) {
		this.gui = gui;
	}

	public Etat getEtat() {
		return etat;
	}

	public void setEtat(Etat etat) {
		this.etat = etat;
	}

	public User getLocalUser() {
		return localUser;
	}

	public void setLocalUser(User localUser) {
		this.localUser = localUser;
	}

	// //////////////////////////////////////////
	// Méthode(s) spécifique(s)
	public void performConnect(String nickname) throws IOException {
		this.localUser = new User(nickname, InetAddress.getLocalHost().getHostAddress());
		this.udpNI.sendHello(this.localUser.getUsername());
	}
	
	private void addUser(String username) throws UnknownHostException {
		getUserList().getUserList().add(new User(username));
	}
	
	public void processMessage(Message message, InetAddress address) throws IOException {
		if (!message.getData().equals(this.localUser.getUsername())) {
			if (Message.isHelloAck(message)) {
				processHelloAck(message.getData());
			}
			if (Message.isHello(message)) {
				processHello(message.getData());
			}
		}
	}
	
	public void performMessage(String info) throws IOException {
		this.udpNI.sendMessage(this.localUser.getUsername(), info);
	}
	
	private void processHello(String username) throws IOException {
		processHelloAck(username);
		sendHelloAck(this.localUser.getUsername());
		//System.out.println("\n---------------\nprocessHello -> " + this.localUser.getUsername() + UserList.printUser(username));
	}

	private void sendHelloAck(String username) throws IOException {
		this.udpNI.sendHelloAck(username);
	}

	private void processHelloAck(String username) throws UnknownHostException {
		this.addUser(username);
		System.out.println("processHelloAck -> " + this.localUser.getUsername() + "\n--------------- " + UserList.printUser(username));
	}
	
	// //////////////////////////////////////////
	// Méthode(s) statique(s)
}
