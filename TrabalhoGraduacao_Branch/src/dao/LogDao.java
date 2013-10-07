package dao;

import model.Log;

public interface LogDao {
	
	public boolean add(Log log);
	public boolean remove(Log log);
	public boolean update(Log log);
	public Log search(int logId); 
}
