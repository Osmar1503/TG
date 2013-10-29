package dao;

import java.util.List;

import model.User;

public interface UserDao {

	public boolean add(User user);
	public boolean addDefaultUser();
	public boolean remove (User user);
	public boolean update(User oldUser, User newUser);
	public List<User> listUser();
	public User search(String userName, String password);
	public User search(String userName);
}
