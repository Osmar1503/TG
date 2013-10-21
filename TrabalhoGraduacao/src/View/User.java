package view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import controller.CadastreUser;
import dm.trabalhograduacao.R;

@SuppressWarnings("unused")
public class User extends Activity{
	
	private EditText edtUser, edtPassword;
	private Button btnCadastre;
	private RadioButton rbNormalType;
	
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
		edtUser = (EditText) findViewById(R.id.txtUserRegister);
		edtPassword = (EditText) findViewById(R.id.txtPasswordRegister);
		rbNormalType = (RadioButton) findViewById(R.id.rdNormalType);
		btnCadastre = (Button) findViewById(R.id.btnCadastre);
		
		btnCadastre.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
//				CadastreUser cadastreUser = new CadastreUser(getBaseContext());
//				
//				model.User user = new model.User();
//				user.setUser(edtUser.getText().toString());
//				user.setPassword(edtPassword.getText().toString());
//				if(rbNormalType.isChecked()) user.setPermission(1);
//				else user.setPermission(0);
//				
//				cadastreUser.registreUser(user);
			}
		});
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
