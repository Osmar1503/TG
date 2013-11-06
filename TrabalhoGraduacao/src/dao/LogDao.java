package dao;

import java.util.List;

import model.Log;

public interface LogDao {
	
	public boolean add(Log log);
	public boolean clear();
	public List<Log> list(); 
}
