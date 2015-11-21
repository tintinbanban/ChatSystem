package project.chat_system.app;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import project.chat_system.controller.Connected;
import project.chat_system.controller.Controller;
import project.chat_system.controller.TCP;
import project.chat_system.controller.UDP;
import project.chat_system.model.Content;
import project.chat_system.model.ContentMessageOrFile;
import project.chat_system.model.UserList;
import project.chat_system.view.GUI;

// Classe principale de lancement de l'application 
//-> utilisation du pattern Singleton
public class ChatSystem implements Runnable {
	// //////////////////////////////////////////
	// Attribut(s)
	private GUI gui;
	private UserList userList;
	private Controller controller;
	private UDP udpNI;
	private TCP tcpNI;
	private Content contentMsg;
	private Content contentFile;

	// //////////////////////////////////////////
	// Constructeur(s)
	private ChatSystem() throws SocketException, UnknownHostException {
		this.userList = new UserList();
		this.contentMsg = new ContentMessageOrFile();
		this.contentMsg.fabriqueMessageOrFile(ContentMessageOrFile.MESSAGE);
		this.udpNI = new UDP(this.contentMsg);
		this.contentFile = new ContentMessageOrFile();
		this.contentFile.fabriqueMessageOrFile(ContentMessageOrFile.FILE);
		this.tcpNI = new TCP(this.contentFile);
		this.controller = new Controller(this.userList, this.udpNI, this.tcpNI,
				this.gui);
		this.gui = new GUI(this.controller);
	}

	// //////////////////////////////////////////
	// Getteur(s) & Setteur(s)
	public GUI getGui() {
		return gui;
	}

	public void setGui(GUI gui) {
		this.gui = gui;
	}

	// //////////////////////////////////////////
	// Méthode(s) spécifique(s)
	public int connect(String nickname) {
		try {
			this.gui.getController().performConnect(nickname);
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	public void setUDPAddress(InetAddress address) {
		this.udpNI.setAddress(address);
	}
	
	public InetAddress getUDPAddress() {
		return this.udpNI.getAddress();
	}
	
	// //////////////////////////////////////////
	// Méthode(s) statique(s) de classe
	public static ChatSystem getInstance() throws SocketException,
			UnknownHostException {
		return new ChatSystem();
	}

	// //////////////////////////////////////////
	// Méthode(s) redéfinie(s)
	@Override
	public void run() {
		if (connect(this.controller.getLocalUser().getNickname()) != 1) {
			// -> mise en place du pattern state
			Connected connected = new Connected();
			connected.doAction(controller);
			// -> lancement d'un thread pour écouter les paquets que l'on reçoit
			new Thread((UDP) this.controller.getUdpNI()).start();
		}
	}
}
