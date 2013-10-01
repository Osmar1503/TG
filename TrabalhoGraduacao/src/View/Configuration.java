package view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;
import controller.ProgressControl;
import dao.DataBaseCreation;
import dm.trabalhograduacao.R;

public class Configuration extends Activity {
	private ProgressBar pgbConfiguration;
	private ProgressControl progressControl;
	private TextView txtStatus;
	private int progress;
	DataBaseCreation dbCreation;
	
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
		
	}

	private void startConfiguration() throws InterruptedException {
		new Thread(new Runnable() {
			public void run() {
//				configureDataBase();
				dbCreation = new DataBaseCreation(getBaseContext(), progressControl);
//				dbCreation.InserirContato("Daniema", "12", "Rua 1");
				try {
					Log.d("daniema", "Parado");
					Thread.sleep(2000);
					Log.d("daniema", "Contiuando");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				dbCreation.obterValores();
				callLoginActivity();
			}
		}).start();

		updateProgressBar();
	}

	public void configureDataBase() {
		try {
//			dbCreation.createTables();
//			dbCreation.existAdminUser();
//			dbCreation.insertInitUser();
//			dbCreation.existDefaultTool();
//			dbCreation.insertInitTool();
		} catch (Exception e) {}
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