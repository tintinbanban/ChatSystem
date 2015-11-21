package project.chat_system.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import project.chat_system.model.Content;
import project.chat_system.model.Message;

//Classe traitant le protocole de communication UDP pour communiquer sur le chatSystem
//-> utilisation du pattern Template
public class UDP extends ChatNI implements Runnable {
	// //////////////////////////////////////////
	// Attribut(s)
	public final int udpPort = 9738;
	private InetAddress address;

	// //////////////////////////////////////////
	// Constructeur(s)
	public UDP(Content content) throws SocketException, UnknownHostException {
		super();
		super.setContent(content);
		super.packet = new DatagramPacket(super.buffer, super.buffer.length);
		super.datagramSocket = new DatagramSocket(this.udpPort);
		super.datagramSocket.setBroadcast(true);
	}

	// //////////////////////////////////////////
	// Getteur(s) & Setteur(s)
	public int getUdpPort() {
		return this.udpPort;
	}

	public InetAddress getAddress() {
		return address;
	}

	public void setAddress(InetAddress address) {
		this.address = address;
	}

	// //////////////////////////////////////////
	// Méthode(s) spécifique(s)
	private void send(Message message) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(message);
		oos.flush();
		byte[] buff = baos.toByteArray();

		ArrayList<InetAddress> broadcastList;
		try {
//			this.datagramSocket = new DatagramSocket(getUdpPort(),
//					getAddress());
			broadcastList = super.getBroadList();
			 System.out.println("getBroadList() : " +
			 broadcastList.toString());
			for (InetAddress broadcast : broadcastList) {
					//InetAddress iA = InetAddress.getByName("192.168.1.255");
					// System.out.println("Local port : " +
					// this.datagramSocket.getLocalPort());
					// System.out.println("Local Adresse : " +
					// this.datagramSocket.getLocalAddress().getHostAddress());
					// System.out.println("Port : " +
					// this.datagramSocket.getPort());
					// System.out.println("Adresse : " +
					// this.datagramSocket.getInetAddress().getHostAddress() +
					// "\n------------");
					this.datagramSocket.send(new DatagramPacket(buff,
							buff.length, broadcast, this.udpPort));
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void processUDPPacket(DatagramPacket p) {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(p.getData());
			ObjectInputStream ois = new ObjectInputStream(bais);
			super.getController().processMessage((Message) ois.readObject(),
					p.getAddress());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeSocket() {
		if (this.datagramSocket != null) {
			this.datagramSocket.close();
		}
	}

	// //////////////////////////////////////////
	// Méthode(s) redéfinie(s)
	// -> classe ChatNI
	@Override
	public void sendHello(String username) throws IOException {
		Message message = Message.createHello(username);
		this.send(message);
	}

	@Override
	public void sendHelloAck(String username) throws IOException {
		Message message = Message.createHelloAck(username);
		this.send(message);
	}
	
	@Override
	public void sendMessage(String username, String info) throws IOException {
		Message message = Message.createMessage(info);
		this.send(message);
	}
	
	// -> interface Runnable
	@Override
	public void run() {
		while (true) {
			try {
				this.datagramSocket.receive(super.packet);
				processUDPPacket(super.packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
