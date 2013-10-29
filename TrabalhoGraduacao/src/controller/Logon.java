package controller;

import android.content.Context;
import dao.UserDaoImpl;
import session.Session;
import model.User;


public class  Logon{
	
	public boolean validateLogin (String userName, String password, Context context){
		UserDaoImpl userDao = new UserDaoImpl(context);
		User user = new User();
		
		user = userDao.search(userName, password); 
		
		if(user != null){
			Session.setUser(user);
			return true;
		}else{
			return false;
		}
	}
	
}
