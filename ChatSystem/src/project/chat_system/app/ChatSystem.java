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
public class ChatSystem {
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
	// Méthode(s) statique(s) de classe
	public static ChatSystem getInstance() throws SocketException,
			UnknownHostException {
		return new ChatSystem();
	}

	// //////////////////////////////////////////
	// METHODE TEST
	public static void main(String[] args) {
		try {
			ChatSystem chatU1 = new ChatSystem();
			//ChatSystem chatU2 = new ChatSystem();
			
			new Thread(chatU1.getGui()).start();
			//new Thread(chatU2.getGui()).start();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
