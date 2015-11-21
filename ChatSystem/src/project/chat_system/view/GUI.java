package project.chat_system.view;
import project.chat_system.controller.Controller;
import project.chat_system.model.UserList;

// Classe gérant la partie interface graphique pour l'utilisateur
public class GUI {
	////////////////////////////////////////////
	// Attribut(s)
	private Controller controller;

	////////////////////////////////////////////
	// Constructeur(s)
	public GUI(Controller conntroller) {
		setController(conntroller);
	}
	
	////////////////////////////////////////////
	// Getteur(s) & Setteur(s)
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	////////////////////////////////////////////
	// Méthode(s) spécifique(s)
	public void refreshUserList(UserList userList) {
		
	}
}
