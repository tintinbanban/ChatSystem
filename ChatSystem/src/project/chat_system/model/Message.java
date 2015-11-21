package project.chat_system.model;

import java.io.Serializable;

/**
 * Created by ValentinC on 22/10/2015.
 *
 */
public class Message implements Serializable, AbstractFactoryContent {
	////////////////////////////////////////////
	// Attribut(s)
    private static enum header_type{
        hello, helloAck, bye, message
    }
    private header_type header;
    private String data;

    ////////////////////////////////////////////
    // Constructeur(s)
    public Message() {
    	
    }
    
    private Message(header_type header,String message){
        this.header=header;
        this.data = message;
    }

    private Message(header_type header){
        this.header=header;
        this.data = null;
    }
    
    
    ////////////////////////////////////////////
    // Getteur(s) & Setteur(s)
	public header_type getHeader() {
		return header;
	}

	public void setHeader(header_type header) {
		this.header = header;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	////////////////////////////////////////////
    // Méthode(s) statique(s)
    public static Message createHello(String nickname){
        return new Message(header_type.hello, nickname);
    }

    public static Message createBye(){
        return new Message(header_type.bye);
    }

    public static Message createHelloAck(String nickname){
        return new Message(header_type.helloAck,nickname);
    }

    public static Message createMessage(String message){
        return new Message(header_type.message,message);
    }
    
    public static boolean isHelloAck(Message message) {
    	if (message.header.name().matches("helloAck")) {
    		return true;
    	}
    	else
    		return false;
    }
    
    public static boolean isHello(Message message) {
    	if (message.header.name().matches("hello")) {
    		return true;
    	}
    	else
    		return false;
    }
    
	////////////////////////////////////////////
    // Méthode(s) redéfinie(s)
	@Override
	public void afficherClasse() {
		System.out.println("[DEBUG] -> Objet de classe 'Message'");
	}
}