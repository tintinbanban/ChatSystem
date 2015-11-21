package project.chat_system.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

// Classe contenant la liste des utilisateurs connectés au chatSystem et avec qui on peut communiquer
public class UserList {
	////////////////////////////////////////////
	// Attribut(s)
	private HashSet<User> userList;

	////////////////////////////////////////////
	// Constructeur(s)
	public UserList() {
		setUserList(new HashSet<User>());
	}

	////////////////////////////////////////////
	// Getteur(s) & Setteur(s)
	public HashSet<User> getUserList() {
		return userList;
	}

	public void setUserList(HashSet<User> userList) {
		this.userList = userList;
	}
	
	////////////////////////////////////////////
	// Méthode(s) spécifique(s)
	public String printUsers() {
		StringBuilder sB = new StringBuilder();
		sB.append("\n________________________________________________");
		sB.append("\nLISTE DES UTILISATEURS CONNECTES");
		sB.append("\n________________________________________________");
		for (User u : userList) {
			sB.append("\n-> ");
			sB.append(u.getUsername());
		}
		return sB.toString();
	}
	
	public static String printUser(String username) {
		StringBuilder sB = new StringBuilder();
		sB.append("\n*____________________________________________________________________________________________*");
		sB.append("\nUN NOUVEL UTILISATEUR EST CONNECTE :	" + username);
		return sB.toString();
	}

	////////////////////////////////////////////
	// Méthode(s) redéfinie(s)
}
