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
			progressControl.setMessage("Iniciando...");	
		}
		
		return false;
	}
	
	public boolean createTables() throws InterruptedException {
		for (int i = 0; i < 10000000; i++) {
			progressControl.setProgress(20);
			progressControl.setMessage("20%");	
		}
		return false;
	}

	public boolean existDefaultUser() throws InterruptedException {
		for (int i = 0; i < 10000000; i++) {
			progressControl.setProgress(50);
			progressControl.setMessage("50%");	
		}
		return false;
	}

	public boolean insertInitUser() throws InterruptedException {
		for (int i = 0; i < 10000000; i++) {
			progressControl.setProgress(70);
			progressControl.setMessage("70%");	
		}
		return false;
	}

	public boolean existDefaultTool() throws InterruptedException {
		return false;
	}
	
	public boolean insertInitTool() throws InterruptedException {
		for (int i = 0; i < 10000000; i++) {
			progressControl.setProgress(100);
			progressControl.setMessage("Finalizado");	
		}
		return false;
	}

}
