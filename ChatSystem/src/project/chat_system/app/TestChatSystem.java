package project.chat_system.app;

import java.io.ObjectInputStream.GetField;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import project.chat_system.controller.UDP;
import project.chat_system.model.User;

public class TestChatSystem {

	public static void main(String[] args) {
		// Déclarations
		ChatSystem chatU1;
		ChatSystem chatU2;
		ChatSystem chatU3;
		ChatSystem chatU4;

//		try {
			// Instanciations
//			chatU1 = ChatSystem.getInstance();
//			chatU1.setUDPAddress(InetAddress.getByName(InetAddress.getLocalHost().getHostAddress()));
//			chatU2 = ChatSystem.getInstance();
//			chatU2.setUDPAddress(InetAddress.getByName("198.168.1.1"));
//			chatU3 = ChatSystem.getInstance();
//			chatU3.setUDPAddress(InetAddress.getByName("198.168.1.2"));
//			chatU4 = ChatSystem.getInstance();
//			chatU4.setUDPAddress(InetAddress.getByName("198.168.1.18"));

			// Initialisation des nicknames
//			chatU1.getGui()
//					.getController()
//					.setLocalUser(
//							new User("toto", chatU1.getUDPAddress().getHostAddress()));
//			chatU2.getGui()
//					.getController()
//					.setLocalUser(
//							new User("aristote",chatU2.getUDPAddress()
//									.getHostAddress()));
//			chatU3.getGui()
//					.getController()
//					.setLocalUser(
//							new User("tarzan", chatU3.getUDPAddress()
//									.getHostAddress()));
//			chatU4.getGui()
//					.getController()
//					.setLocalUser(
//							new User("jane", chatU4.getUDPAddress()
//									.getHostAddress()));

			// Lancement de threads
//			new Thread(chatU1).start();
//			new Thread(chatU2).start();
//			new Thread(chatU3).start();
//			new Thread(chatU4).start();
//			while (true) {
//			}
//		} catch (SocketException | UnknownHostException e) {
//			e.printStackTrace();
//		}
	}
}
