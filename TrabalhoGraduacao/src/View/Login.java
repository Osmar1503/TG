package view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import controller.Logon;
import dm.trabalhograduacao.R;

public class Login extends Activity{
	
	private EditText user;
	private EditText password;
	private Button btnEntry;
	private boolean permission;
		
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    	initObjects();
    	applyButtonFunction();
	}

	private void applyButtonFunction(){
		btnEntry.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Logon logon = new Logon();
				permission = logon.validateLogin(String.valueOf(user.getText()), String.valueOf(password.getText()));
				printMessage();
			}
		});
	}
	
	private void initObjects(){
		user = (EditText) findViewById(R.id.txtUser);
		password = (EditText) findViewById(R.id.txtPassword);
		btnEntry = (Button) findViewById(R.id.btnEntry);
		permission = false;
	}
	
	private void printMessage(){
		if (!permission){
			Toast.makeText(getBaseContext(), "Entrada não permitida. Tente novamente.", Toast.LENGTH_SHORT).show();
		}
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
