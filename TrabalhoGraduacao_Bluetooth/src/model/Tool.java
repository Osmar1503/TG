package model;

public class Tool {
	
	private Integer toolId;
	private Action type;
	private String description;
	
	public Integer getToolId() {
		return toolId;
	}
	public void setToolId(Integer toolId) {
		this.toolId = toolId;
	}
	public Action getType() {
		return type;
	}
	public void setType(Action type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
