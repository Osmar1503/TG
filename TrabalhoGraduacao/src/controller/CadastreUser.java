package controller;

import dao.UserDaoImpl;
import model.User;
import android.content.Context;

public class CadastreUser {
	private Context context;
	
	public CadastreUser(Context context){
		this.context = context;
	}
	
	public boolean registreUser(User user){
		UserDaoImpl userDao = new UserDaoImpl(context);
		userDao.add(user);
		
		return true;
	}
	
	
}
