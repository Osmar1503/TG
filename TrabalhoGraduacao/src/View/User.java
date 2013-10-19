package view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import dm.trabalhograduacao.R;

public class User extends Activity{
	

	@SuppressWarnings("unused")
	private EditText user;
	@SuppressWarnings("unused")
	private EditText password;
	@SuppressWarnings("unused")
	private EditText retryPassword;
	@SuppressWarnings("unused")
	private Button btnSign;
	@SuppressWarnings("unused")
	private RadioGroup rdgType;
	@SuppressWarnings("unused")
	private RadioButton rbNormalType;
	@SuppressWarnings("unused")
	private RadioButton rbManagerType;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

}
