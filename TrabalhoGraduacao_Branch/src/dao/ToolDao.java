package dao;

import java.util.List;

import model.Tool;

public interface ToolDao {
	
	public boolean add(Tool tool);
	public boolean remove(Tool tool);
	public boolean update(Tool tool);
	public List<Tool> listUser();
	public Tool search(int toolId); 
}
