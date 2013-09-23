package dao;

import model.Tool;

public interface ToolDao {
	
	public boolean add(Tool tool);
	public boolean remove(Tool tool);
	public boolean update(Tool tool);
	public Tool search(int toolId); 
}
