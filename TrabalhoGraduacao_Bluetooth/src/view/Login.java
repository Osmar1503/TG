package view;

import dm.trabalhograduacao.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity{
	
	@SuppressWarnings("unused")
	private EditText user;
	@SuppressWarnings("unused")
	private EditText password;
	@SuppressWarnings("unused")
	private Button btnEntry;
		
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
	
        try{        
        	initObjects();
        }catch (Exception e){}
	}

	private void initObjects(){
		user = (EditText) findViewById(R.id.txtUser);
		password = (EditText) findViewById(R.id.txtPassword);
		btnEntry = (Button) findViewById(R.id.btnEntry);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
	protected void onResume(){
		super.onResume();
	}
	
	protected void onStart(){
		super.onStart();
	}
	
	protected void onStop(){
		super.onStop();
	}
	
	protected void onPause(){
		super.onPause();
	}
	
	protected void onRestart(){
		super.onRestart();
	}
	
	protected void onDestroy(){
	}
	
}
