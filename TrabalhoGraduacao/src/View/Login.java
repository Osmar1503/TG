package view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import controller.Logon;
import controller.Message;
import dm.trabalhograduacao.R;

public class Login extends Activity{
	
	private EditText txtUser;
	private EditText txtPassword;
	private TextView lblResponse;
	private Button btnEntry;
	private boolean permission;
	Message message = new Message();
		
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login);
        
    	initObjects();
    	applyButtonFunction();
	}

	private void applyButtonFunction(){
		btnEntry.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (ValidateFields()){
					Logon logon = new Logon();
					permission = logon.validateLogin(String.valueOf(txtUser.getText()), String.valueOf(txtPassword.getText()), getBaseContext());
					if (permission){
						callHomeActivity();
					}else{
						printMessage("Entrada não permitida. Tente novamente.");
						clearValues();
					}
				}
			}
		});
	}
	
	private void initObjects(){
		txtUser = (EditText) findViewById(R.id.txtUser);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		lblResponse = (TextView) findViewById(R.id.lblResponse);
		btnEntry = (Button) findViewById(R.id.btnEntry);
		permission = false;
		
		txtUser.setText("admin");
		txtPassword.setText("1503");
	}
	
	private void printMessage(String message){
		lblResponse.setText(message);
	}
	
	private boolean ValidateFields(){
		if(String.valueOf(txtUser.getText()).equals("")){
			printMessage("Informe seu nome de Usuário.");
			return false;
		}
		if(String.valueOf(txtPassword.getText()).equals("")) {
			printMessage("Informe sua Senha.");
			return false;
		}
		return true;
	}
	
	private void callHomeActivity(){
		Intent intent = new Intent(Login.this, Home.class);
		startActivityForResult(intent, 1);
//		finish();
	}
	
	private void clearValues(){
		txtPassword.setText("");
		txtUser.setText("");
		txtUser.requestFocus();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }	
}
