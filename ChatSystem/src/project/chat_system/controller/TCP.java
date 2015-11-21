package project.chat_system.controller;

import java.io.IOException;

import project.chat_system.model.Content;

// Classe traitant le protocole de communication TCP pour communiquer sur le chatSystem
//-> utilisation du pattern Template
public class TCP extends ChatNI {
	// //////////////////////////////////////////
	// Constructeur(s)
	public TCP(Content content) {
		super();
		super.setContent(content);
	}

	// //////////////////////////////////////////
	// Méthode(s) redéfinie(s)
	@Override
	public void sendHello(String nickname) {

	}

	@Override
	public void sendHelloAck(String username) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(String username, String info) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
