package session;

import model.User;

public final class Session {

	private static Session session;
	private static User user = new User();
	
	
	private Session(){}
	
	public static synchronized Session getInstance(){
		if(session == null){
			session = new Session();
		}
		
		return session;
	}
	
	
	public static void setUser(User newUser){
		user = newUser; 
	}
	
	public static User getUser() {
		return user;
	}
}
