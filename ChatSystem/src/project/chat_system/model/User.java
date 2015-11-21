package project.chat_system.model;
// Classe contenant les spécificités d'un utilisateur du chatSystem
public class User {
	////////////////////////////////////////////
	// Attribut(s)
	private String nickname;
	private String ip;
	private String username;

	////////////////////////////////////////////
	// Constructeur(s)
	public User(String nickname, String ip) {
		setNickname(nickname);
		setIp(ip);
		this.username = new String();
		createUsername(nickname, ip);
	}
	
	public User(String username) {
		setUsername(username);
		setNickname(username.split(":")[0]);
		setIp(username.split(":")[1]);
	}

	////////////////////////////////////////////
	// Getteur(s) & Setteur(s)
	public String getNickname() {
		return nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getUsername() {
		return this.username;
	}
	 public void setUsername(String username) {
		 this.username = username;
	 }
	
	////////////////////////////////////////////
	// Méthode(s) spécifique(s)
	public void createUsername(String nickname, String ip) {
		setUsername(this.username.concat(nickname));
		setUsername(this.username.concat(":"));
		setUsername(this.username.concat(ip));
	}
}
