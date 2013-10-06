package controller;

import android.content.Context;
import dao.UserDaoImpl;


public class  Logon{
	
	public boolean validateLogin (String user, String password, Context context){
		
		UserDaoImpl userDao = new UserDaoImpl(context);
		
		if(userDao.search(user, password)){
			return true;
		}else{
			return false;
		}
	}
	
	public void logout(){}
	
}
