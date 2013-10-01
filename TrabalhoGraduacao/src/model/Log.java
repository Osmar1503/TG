package model;

import java.util.Calendar;

public class Log {
	
	private int id;
	private int Userid;
	private int Toolid;
	private String date;
	
	public Log(){
		setDate(generateDate());
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return Userid;
	}

	public void setUserid(int userid) {
		Userid = userid;
	}

	public int getToolid() {
		return Toolid;
	}

	public void setToolid(int toolid) {
		Toolid = toolid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
		
	public String generateDate(){
		String _date = Calendar.DAY_OF_MONTH + "/" + Calendar.MONTH + "/" + Calendar.YEAR + " " + Calendar.HOUR_OF_DAY + ":" + 
					   Calendar.MINUTE + ":" + Calendar.SECOND;
		return _date;
	}

}
