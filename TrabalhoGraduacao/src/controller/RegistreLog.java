package controller;

import model.Log;
import android.content.Context;
import dao.LogDaoImpl;

public class RegistreLog {
	
private Context context;
	
	public RegistreLog(Context context){
		this.context = context;
	}
	
	public boolean registre(Log log){
		LogDaoImpl logDao = new LogDaoImpl(context);
		if(logDao.add(log)) return true;
		return false;
	}
	
}
