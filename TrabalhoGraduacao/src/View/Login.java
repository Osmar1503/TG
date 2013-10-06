package view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import controller.Logon;
import dm.trabalhograduacao.R;

public class Login extends Activity{
	
	private EditText txtUser;
	private EditText txtPassword;
	private TextView lblResponse;
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
				if (ValidateFields()){
					Logon logon = new Logon();
					permission = logon.validateLogin(String.valueOf(txtUser.getText()), String.valueOf(txtPassword.getText()), getBaseContext());
					if (permission){
						callCoreActivity();
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
	
	private void callCoreActivity(){
		Intent intent = new Intent(Login.this, Core.class);
		startActivityForResult(intent, 1);
		finish();
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
