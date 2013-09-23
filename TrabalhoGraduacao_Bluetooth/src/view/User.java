package view;

import android.app.Activity;
import android.os.Bundle;
import android.test.suitebuilder.annotation.Suppress;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import dm.trabalhograduacao.R;

public class User extends Activity{
	
	private EditText user;
	private EditText password;
	private EditText retryPassword;
	private Button btnSign;
	private RadioGroup rdgType;
	private RadioButton rbNormalType;
	private RadioButton rbManagerType;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
	
        try{        
        	initObjects();
        }catch (Exception e){}
	}

	private void initObjects(){
		user = (EditText) findViewById(R.id.txtUserRegister);
		password = (EditText) findViewById(R.id.txtPasswordRegister);
		rdgType = (RadioGroup) findViewById(R.id.rdgType);
		rbNormalType = (RadioButton) findViewById(R.id.rdNormalType);
		rbManagerType = (RadioButton) findViewById(R.id.rdManagerType);
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
