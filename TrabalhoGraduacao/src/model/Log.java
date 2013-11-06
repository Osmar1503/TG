package model;

import java.util.Calendar;

public class Log {
	
	private int id;
	private String UserName;
	private String ToolAction;
	private String date;
	
	public Log(){}
	
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getToolAction() {
		return ToolAction;
	}

	public void setToolAction(String toolAction) {
		ToolAction = toolAction;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	public void setActualDate(){
		this.date = generateDate();
	}
		
	public String generateDate(){
		Calendar c = Calendar.getInstance();
		String _date = "";
		_date += c.get(Calendar.DAY_OF_MONTH) + "/";
		_date += c.get(Calendar.MONTH)+1 + "/";
		_date += c.get(Calendar.YEAR) + " ";
		_date += c.get(Calendar.HOUR_OF_DAY) + ":";
		_date += c.get(Calendar.MINUTE) + ":";
		_date += c.get(Calendar.SECOND);
		
		return _date;
	}

}
