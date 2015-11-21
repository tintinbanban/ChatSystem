package project.chat_system.controller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

import project.chat_system.model.Content;

// Classe responsable de l'acheminement des données de l'utilisateur (via le controller) vers la RemoteApp et vice versa 
//-> utilisation du pattern Factory et du pattern Template
public abstract class ChatNI {
	////////////////////////////////////////////
	// Attribut(s)
	private Content content;
	private Controller controller;
	protected DatagramPacket packet;
	final static int taille = 4096;
	static byte buffer[] = new byte[taille];
	protected DatagramSocket datagramSocket;
	
	////////////////////////////////////////////
	// Getteur(s) & Setteur(s)
	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public DatagramPacket getPacket() {
		return packet;
	}

	public void setPacket(DatagramPacket packet) {
		this.packet = packet;
	}

	public DatagramSocket getDatagramSocket() {
		return datagramSocket;
	}

	public void setDatagramSocket(DatagramSocket datagramSocket) {
		this.datagramSocket = datagramSocket;
	}

	////////////////////////////////////////////
	// Méthode(s) spécifique(s)
	//-> implémentation du pattern Template
	public abstract void sendHello(String username) throws IOException;
	
	public abstract void sendHelloAck(String username) throws IOException;
	
	public abstract void sendMessage(String username, String info) throws IOException;
	
	public void sendInstanceOfController(Controller controller) {
		setController(controller);
	}
	
	public ArrayList<InetAddress> getBroadList() throws SocketException{
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();    
        ArrayList<InetAddress> broadcastList = new ArrayList<InetAddress>();
        // récupération de toutes les adresses de broadcast
        while(interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isLoopback())
                continue;    
            for (InterfaceAddress interfaceAddress :networkInterface.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (broadcast == null)
                    continue;
                broadcastList.add(broadcast);
            }
        }
        return broadcastList;
    }
}
