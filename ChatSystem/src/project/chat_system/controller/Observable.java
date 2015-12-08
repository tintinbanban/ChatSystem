package project.chat_system.controller;


// Interface Observable définissant le pattern 'Oberver'
public interface Observable {
	// Fonction pour ajouter un observateur à la liste
	void ajouterObservateur(Observateur o);
	
	// Fonction pour enlever un observateur de la liste
	void supprimerObservateur(Observateur o);
	
	// Fonction qui notifie les observateurs d'un changement d'état de l'observable
	void notifierObservateurs();
}
