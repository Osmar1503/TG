package controller;

import java.util.LinkedList;
import java.util.List;

import model.Tool;
import android.content.Context;
import dao.ToolDaoImpl;

public class ListOfTools {
	private Context context;
	
	public ListOfTools (Context context){
		this.context = context;
	}
	
	public List<Tool> getListOfTools(){
		List<Tool> list = new LinkedList<Tool>();
		
		ToolDaoImpl toolDao = new ToolDaoImpl(context);
		list = toolDao.listTool();
		
		return list;
	}
}
