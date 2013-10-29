package view;

import model.User;
import session.Session;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;
import controller.PersistenceUser;
import dm.trabalhograduacao.R;

public class UserCadastre extends Activity{
	
	private EditText edtUser, edtPassword;
	private Button btnCadastre;
	private RadioButton rbNormalType, rbManagerType;
	private ImageView imgAvailable;
	private PersistenceUser persistenceUser;
	private boolean userAvailable = true;
	private boolean isUpdate = false;
	private User userUpdate;
	
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.user);
        try{        
        	initObjects();
        	if(isUpdate()) populateFields();
        }catch (Exception e){}
	}

	private void initObjects(){
		edtUser = (EditText) findViewById(R.id.txtUserRegister);
		edtPassword = (EditText) findViewById(R.id.txtPasswordRegister);
		rbNormalType = (RadioButton) findViewById(R.id.rdNormalType);
		rbManagerType = (RadioButton) findViewById(R.id.rdManagerType);
		btnCadastre = (Button) findViewById(R.id.btnCadastre);
		imgAvailable = (ImageView) findViewById(R.id.imgAvailable);
		userUpdate = new User();
		imgAvailable.setImageDrawable(null);		
		persistenceUser = new PersistenceUser(getBaseContext());
		
		btnCadastre.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(verifyFields()){
					PersistenceUser persistenceUser = new PersistenceUser(getBaseContext());
					User user = new User();

					user.setUser(edtUser.getText().toString());
					user.setPassword(edtPassword.getText().toString());
					
					if(rbNormalType.isChecked()) user.setPermission(0);
					else user.setPermission(1);
					
					if(!isUpdate){
						persistenceUser.registre(user);
						callUserListActivity();
					}
					else{
						persistenceUser.Update(userUpdate, user);
						if(userUpdate.getUser().equals(Session.getUser().getUser())) callLoginActivity();
						else callUserListActivity();
					}
				}
			}
		});
		
		edtUser.setOnFocusChangeListener(new OnFocusChangeListener() {			
			public void onFocusChange(View v, boolean hasFocus) {
				if(!isUpdate){
					if (persistenceUser.isAvailable(edtUser.getText().toString())){
						imgAvailable.setImageResource(R.drawable.ok);
						userAvailable = true;
					}else{
						imgAvailable.setImageResource(R.drawable.nok);
						userAvailable = false;
					}
				}else {
					if(edtUser.getText().toString().equals(userUpdate.getUser())){
						imgAvailable.setImageResource(R.drawable.ok);
						userAvailable = true;
					}else{
						if (persistenceUser.isAvailable(edtUser.getText().toString())){
							imgAvailable.setImageResource(R.drawable.ok);
							userAvailable = true;
						}else{
							imgAvailable.setImageResource(R.drawable.nok);
							userAvailable = false;
						}
					}
				}
			}
		});
	
	}
	
	private boolean verifyFields(){
		if(!userAvailable){
			Toast.makeText(getBaseContext(), "Usuario informado não está disponível", Toast.LENGTH_SHORT).show();
			edtUser.requestFocus();
			return false;
		}
		
		if(edtUser.getText().toString().equals("")){
			Toast.makeText(getBaseContext(), "Informe o Usuário", Toast.LENGTH_SHORT).show();
			edtUser.requestFocus();
			return false;
		}
		
		if(edtPassword.getText().toString().equals("")){
			Toast.makeText(getBaseContext(), "Informe a senha", Toast.LENGTH_SHORT).show();
			edtPassword.requestFocus();
			return false;
		}
		return true;
	}
	
    private boolean isUpdate(){
		Intent intent = getIntent();
		Bundle params = intent.getExtras();  
		   
		if(params != null){   
			String userName = params.getString("userName");
			userUpdate = persistenceUser.getUserByName(userName);
			isUpdate = true;
		}else isUpdate = false;
		return isUpdate;
    }
    
    private void populateFields(){
    	edtUser.setText(userUpdate.getUser());
    	edtPassword.setText(userUpdate.getPassword());
    	if(userUpdate.getPermission() == 0) rbNormalType.setChecked(true);
		else rbManagerType.setChecked(true);
    }
    
    private void callUserListActivity() {
		Intent intent = new Intent(UserCadastre.this, UserList.class);
		startActivityForResult(intent, 1);
		finish();
	}
    
    private void callLoginActivity(){
    	Intent intent = new Intent(UserCadastre.this, Login.class);
		startActivityForResult(intent, 1);
		finish();
    }
    
    @Override
    public void onBackPressed(){
    	callHomeActivity();
    }
    
    public void callHomeActivity(){
    	Intent intent = new Intent(UserCadastre.this, UserList.class);
		startActivityForResult(intent, 1);
		finish();
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
