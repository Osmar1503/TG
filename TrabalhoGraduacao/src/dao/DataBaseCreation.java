package dao;

import controller.ProgressControl;

public class DataBaseCreation {
	private ProgressControl progressControl;
	
	public DataBaseCreation(ProgressControl progress) {
		progressControl = progress;
	}

	public boolean existDataBase() {
		return false;
	}

	public boolean createDataBase() throws InterruptedException {
		for (int i = 0; i < 10000000; i++) {
			progressControl.setProgress(10);
			progressControl.setMessage("Estou aqui");	
		}
		
		return false;
	}
	
	public boolean createTables() throws InterruptedException {
		for (int i = 0; i < 10000000; i++) {
			progressControl.setProgress(20);
			progressControl.setMessage("Estou aqui 1");	
		}
		return false;
	}

	public boolean existDefaultUser() throws InterruptedException {
		for (int i = 0; i < 10000000; i++) {
			progressControl.setProgress(100);
			progressControl.setMessage("Estou aqui 2");	
		}
		return false;
	}

	public boolean insertInitUser() throws InterruptedException {
		return false;
	}

	public boolean existDefaultTool() throws InterruptedException {
		return false;
	}
	
	public boolean insertInitTool() throws InterruptedException {
		return false;
	}

}
