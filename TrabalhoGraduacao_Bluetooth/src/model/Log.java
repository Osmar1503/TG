package model;

import java.util.Calendar;

public class Log {
	
	private Integer userId, toolId;
	private String date;
	
	public Log(){
		setUserId(0);
		setToolId(0);
		setDate(generateDate());
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getToolId() {
		return toolId;
	}

	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String generateDate(){
		String _date = Calendar.DAY_OF_MONTH + "/" + Calendar.MONTH + "/" + Calendar.YEAR + " " + Calendar.HOUR_OF_DAY + ":" + 
					   Calendar.MINUTE + ":" + Calendar.SECOND;
		return _date;
	}
}
