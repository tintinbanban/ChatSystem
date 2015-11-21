package project.chat_system.model;

import project.chat_system.model.Observable;

// Interface Observateur définissant le pattern 'Observer'
public interface Observateur {
	// Fonction qui actualise les données d'un oberveur fournies par un observable
	void actualiser(Observable o);
	
}
