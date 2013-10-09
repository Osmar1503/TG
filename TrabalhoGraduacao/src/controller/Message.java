package controller;

import android.util.Log;

public class Message {

	public void printSpace(){
		Log.d("daniema", " ");
		Log.d("daniema", "----------------------------------------------------------");
		Log.d("daniema", " ");
	}
	
	public void writeLogCat(String msg){
		Log.d("daniema", msg);
	}
}
