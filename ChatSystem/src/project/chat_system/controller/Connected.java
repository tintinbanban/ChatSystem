package project.chat_system.controller;

// Classe indiquant l'état suivant de l'utilisateur vis-à-vis de l'application : connecté
//-> utilisation du pattern State
public class Connected implements Etat {
	////////////////////////////////////////////
	// Méthode(s) redéfinie(s)
	@Override
	public void doAction(Controller controller) {
		System.out.println("L'utilisateur est connecté");
		controller.setEtat(this);
	}
}
