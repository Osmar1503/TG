package view;

import model.Action;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import dao.ToolDaoImpl;
import dao.UserDaoImpl;
import dm.trabalhograduacao.R;
import factory.ConnectionFactory;

public class Configuration extends Activity {
	private ProgressBar pgbConfiguration;
	private Context context;
	private boolean endProcess = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.configuration);

		initObjects();
		try {
			startConfiguration();
		} catch (Exception e) {
			Log.d("daniema", "Erro: " + e.getMessage());
		}
	}

	private void initObjects() {
		pgbConfiguration = (ProgressBar) findViewById(R.id.pgbConfiguration);
		pgbConfiguration.getIndeterminateDrawable().setColorFilter(Color.rgb(0, 255, 255), android.graphics.PorterDuff.Mode.MULTIPLY);
		context = getBaseContext();
	}

	private void startConfiguration() throws InterruptedException {
		updateProgressBar();
		
		new Thread(new Runnable() {
			public void run() {
				ConnectionFactory con = new ConnectionFactory(context);

				UserDaoImpl userDao = new UserDaoImpl(context);
//				userDao.addDefaultUser();
				userDao.listUser();
				
				timeToRead();
								
				model.Tool tool = new model.Tool();
				ToolDaoImpl toolDao = new ToolDaoImpl(context);
				
				tool.setDescription("Acender Lampada");
				tool.setType(String.valueOf(Action.TURN_ON_LAMP));
//				toolDao.add(tool);
				
				timeToRead();
				
				tool.setDescription("Apagar Lampada");
				tool.setType(String.valueOf(Action.TURN_OFF_LAMP));
//				toolDao.add(tool);
				
				toolDao.listTool();
				
				timeToRead();
				
				con.close();
				
				endProcess = true;
				callLoginActivity();
			}
		}).start();
	}
	
	public void updateProgressBar() {
		 new Thread(new Runnable() {
			 public void run() {
				 while (!endProcess) {}
			 }
		 }).start();
	}

	private void timeToRead(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void callLoginActivity() {
		Intent intent = new Intent(Configuration.this, Login.class);
		startActivityForResult(intent, 1);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}