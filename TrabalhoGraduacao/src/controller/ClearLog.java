package controller;

import dao.LogDaoImpl;
import android.content.Context;

public class ClearLog {
	private Context context;
	
	public ClearLog(Context context){
		this.context = context;
	}
	
	public boolean clear(){
		LogDaoImpl logDao = new LogDaoImpl(context);
		return logDao.clear();
	}
	
}
