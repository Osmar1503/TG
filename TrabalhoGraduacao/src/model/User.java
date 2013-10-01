package model;

public class User {
	
	private int Id;
	private String user;
	private String password;
	private String email;
	
	public void setUser(String user) {
		this.user = user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getEmail() {
		return email;
	}

	public int getUserId() {
		return Id;
	}

	public void setUserId(int userId) {
		this.Id = userId;
	}
	
}
