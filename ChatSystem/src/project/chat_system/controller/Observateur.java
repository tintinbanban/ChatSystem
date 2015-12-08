package project.chat_system.controller;


// Interface Observateur définissant le pattern 'Observer'
public interface Observateur {
	// Fonction qui actualise les données d'un oberveur fournies par un observable
	void actualiser(Observable o);
	
}
