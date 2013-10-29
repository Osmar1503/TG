package view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import dm.trabalhograduacao.R;

public class Home extends Activity{
	private ImageView imgHome, imgUser, imgTool, imgAbout;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.home);
		
		initObjects();
	}
	
	private void initObjects(){
		imgHome = (ImageView) findViewById(R.id.imgIndexHome);
		imgUser = (ImageView) findViewById(R.id.imgIndexUser);
		imgTool = (ImageView) findViewById(R.id.imgIndexTool);
		imgAbout = (ImageView) findViewById(R.id.imgIndexAbout);
		
		imgHome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				callControlActivity();
			}
		});
		
		imgUser.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				callUserActivity();
			}
		});
		
		imgTool.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				callToolActivity();
			}
		});
		
		imgAbout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				callAboutActivity();
			}
		});
	}
	
	public void callControlActivity() {
		Intent intent = new Intent(Home.this, Core.class);
		startActivityForResult(intent, 1);
		finish();
	}
	    
	public void callToolActivity() {
		Intent intent = new Intent(Home.this, Tools.class);
		startActivityForResult(intent, 1);
		finish();
	}
    
    public void callUserActivity() {
		Intent intent = new Intent(Home.this, UserList.class);
		startActivityForResult(intent, 1);
		finish();
	}
    
    public void callAboutActivity() {
		Intent intent = new Intent(Home.this, About.class);
		startActivityForResult(intent, 1);
		finish();
	}
    
    public void callLoginActivity(){
    	Intent intent = new Intent(Home.this, Login.class);
		startActivityForResult(intent, 1);
		finish();
    }
	
    @Override
    public void onBackPressed(){
    	callLoginActivity();
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
