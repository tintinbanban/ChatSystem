package project.chat_system.model;


// Classe qui sert de fabrique pour instancier des objets message ou file
//-> utilisation du pattern Factory
public abstract class Content  {
	////////////////////////////////////////////
	// Attribut(s)
	private boolean pIsMessage = true;
	
	////////////////////////////////////////////
	// Méthode(s) à implémenter
	//-> implémentation du pattern Factory
	public abstract AbstractFactoryContent creerClasse(boolean pIsMessage);
	
	public void fabriqueMessageOrFile(boolean pIsMessage) {
		AbstractFactoryContent fabrication = creerClasse(pIsMessage);
		//fabrication.afficherClasse();
	}
}
