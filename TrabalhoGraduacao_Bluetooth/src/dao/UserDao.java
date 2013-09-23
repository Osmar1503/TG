package dao;

import model.User;

public interface UserDao {

	public boolean add(User user);
	public boolean remove (User user);
	public boolean update(User user);
	public User search(int userId); 
}
