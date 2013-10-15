package dao;

import java.util.List;

import model.User;

public interface UserDao {

	public boolean add(User user);
	public boolean addDefaultUser();
	public boolean remove (User user);
	public boolean update(User user);
	public List<User> listUser();
	public boolean search(String userName, String password);
}
