package view;

import model.Action;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;
import controller.ProgressControl;
import dao.ToolDaoImpl;
import dao.UserDaoImpl;
import dm.trabalhograduacao.R;
import factory.ConnectionFactory;

public class Configuration extends Activity {
	private ProgressBar pgbConfiguration;
	private ProgressControl progressControl;
	private TextView txtStatus;
	private int progress;
	private Context context;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		txtStatus = (TextView) findViewById(R.id.txtState);
		pgbConfiguration.setMax(100);
		progress = 0;
		progressControl = new ProgressControl();
		context = getBaseContext();
	}

	private void startConfiguration() throws InterruptedException {
		new Thread(new Runnable() {
			public void run() {
				ConnectionFactory con = new ConnectionFactory(getBaseContext());
				
				model.User user =  new model.User();
				UserDaoImpl userDao = new UserDaoImpl(context);
				
				user.setUser("paixao");
				user.setPassword("1503");
				user.setEmail("osmar_salles@hotmail.com");
				user.setPermission(1);
//				userDao.add(user);

				userDao.listUser();
				
				model.Tool tool = new model.Tool();
				ToolDaoImpl toolDao = new ToolDaoImpl(getBaseContext());
				
				tool.setDescription("Acender Lampada");
				tool.setType(String.valueOf(Action.TURN_ON_LAMP));
//				toolDao.add(tool);
				
				tool.setDescription("Apagar Lampada");
				tool.setType(String.valueOf(Action.TURN_OFF_LAMP));
//				toolDao.add(tool);
				
				toolDao.listTool();
				
				con.close();
				
				callLoginActivity();
			}
		}).start();

		updateProgressBar();
	}

	public void updateProgressBar() {
		 new Thread(new Runnable() {
		 public void run() {
			 while (progress < 100) {
				try {
					Thread.sleep(100);
				} catch (Exception e) {}
					pgbConfiguration.post(new Runnable() {
						public void run() {
							pgbConfiguration.setProgress(progressControl.getProgress());
							if (progress <= 100) txtStatus.setText(progressControl.getProgress() + "%");
						}
					});
			 	}
			 }
		 }).start();
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