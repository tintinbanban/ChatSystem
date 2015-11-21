package project.chat_system.controller;

// Interface symbolisant un état de l'utilisateur au sein du chatSystem
//-> utilisation du pattern State
public interface Etat {
	////////////////////////////////////////////
	// Methode(s)
	public void doAction(Controller controller);
}
