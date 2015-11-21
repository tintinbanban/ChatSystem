package project.chat_system.model;

import java.text.MessageFormat;

// Classe se chargeant d'instancier des objets de type message pour la communication
//-> utilisation du pattern Factory
public class ContentMessageOrFile extends Content {
	// //////////////////////////////////////////
	// CONSTANTE(S)
	final public static boolean MESSAGE = true;
	final public static boolean FILE = false;

	// //////////////////////////////////////////
	// Méthode(s) redéfinie(s)
	@Override
	public AbstractFactoryContent creerClasse(boolean pIsMessage) {
		if (pIsMessage) {
			return new Message();
		} else {
			return new File();
		}
	}
}
