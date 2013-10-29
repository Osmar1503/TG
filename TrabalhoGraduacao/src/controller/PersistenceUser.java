package controller;

import java.util.LinkedList;
import java.util.List;

import model.User;
import android.content.Context;
import dao.UserDaoImpl;

public class PersistenceUser {
	private Context context;
	
	public PersistenceUser(Context context){
		this.context = context;
	}
	
	public boolean registre(User user){
		UserDaoImpl userDao = new UserDaoImpl(context);
		
		if(userDao.search(user.getUser()) == null)			
			if(userDao.add(user)) return true;
		return false;
	}
	
	public boolean remove(String userName){
		UserDaoImpl userDao = new UserDaoImpl(context);

		User user = new User();
		user.setUser(userName);
		
		if(userDao.remove(user)) return true;
		else return false;
	}
	
	public boolean Update(User oldUser, User newUser){
		UserDaoImpl userDao = new UserDaoImpl(context);
		
		if(userDao.update(oldUser, newUser)) return true;
		else return false;
	}
	
	public List<User> list(){
		List<User> list = new LinkedList<User>();
		
		UserDaoImpl userDao = new UserDaoImpl(context);
		list = userDao.listUser();
		
		return list;
	}
	
	public int TotalAdminAccont(){
		try{
			UserDaoImpl userDao = new UserDaoImpl(context);
			int total = userDao.countAdminPermissions();
			return total;
		}catch(Exception e){
			return -1;
		}
	}
	
	public boolean isAvailable(String userName){
		UserDaoImpl userDao = new UserDaoImpl(context);
		
		if(userDao.search(userName) == null) return true;
		return false;
	}
	
	public User getUserByName(String userName){
		UserDaoImpl userDao = new UserDaoImpl(context);
		return userDao.search(userName);
	}
	
}
