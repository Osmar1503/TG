package controller;

import java.util.Random;


public class  Logon{
	
	public boolean validateLogin (String user, String password){
		Random random = new Random();
        boolean result = random.nextBoolean();
        return result;
	}
	
	public void logout(){}
	
}
