package controller;

import java.util.List;

import model.Log;
import android.content.Context;
import dao.LogDaoImpl;

public class ListLog {
	
private Context context;
	
	public ListLog (Context context){
		this.context = context;
	}
	
	public List<Log> list(){
		LogDaoImpl logDao = new LogDaoImpl(context);
		return logDao.list();
	}
	
}
