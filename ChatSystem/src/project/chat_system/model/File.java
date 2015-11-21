package project.chat_system.model;


//Classe définissant le contenu d'un objet message
//-> utilisation du pattern Factory
public class File implements AbstractFactoryContent {
	////////////////////////////////////////////
	// Méthode(s) redéfinie(s)
	@Override
	public void afficherClasse() {
		System.out.println("[DEBUG] -> Objet de classe 'File'");
	}

}
