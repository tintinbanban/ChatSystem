package project.chat_system.controller;

// Classe indiquant l'état suivant de l'utilisateur vis-à-vis de l'application : déconnecté
//-> utilisation du pattern State
public class Disconnected implements Etat {
	////////////////////////////////////////////
	// Méthode(s) redéfinie(s)
	@Override
	public void doAction(Controller controller) {
		System.out.println("L'utilisateur est déconnecté");
		controller.setEtat(this);
	}
}
