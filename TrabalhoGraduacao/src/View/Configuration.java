package view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.widget.ProgressBar;
import android.widget.TextView;
import controller.ProgressControl;
import dao.DataBaseCreation;
import dm.trabalhograduacao.R;

public class Configuration extends Activity{
	private ProgressBar pgbConfiguration;
	private ProgressControl progressControl;
	private TextView txtStatus;
	private int progress;
	DataBaseCreation dbCreation;
	Handler h;
	
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration);
	
        try{        
        	initObjects();
        	startConfiguration();
        }catch (Exception e){}
	}

	private void initObjects(){
		pgbConfiguration = (ProgressBar) findViewById(R.id.pgbConfiguration);
		txtStatus = (TextView) findViewById(R.id.txtState);
		pgbConfiguration.setMax(100);
		progress = 0;
		progressControl = new ProgressControl();
		dbCreation = new DataBaseCreation(progressControl);
		h = new Handler();
	}
	
	private void startConfiguration() throws InterruptedException {
		new Thread(new Runnable() {
			public void run() {
				configureDataBase(dbCreation);
			}
		}).start();
		updateProgressBar();
	}
	
	public void configureDataBase(final DataBaseCreation dbCreation){
							try{
								dbCreation.existDataBase();
								dbCreation.createDataBase();
								dbCreation.createTables();
								
								dbCreation.existDefaultUser();
								dbCreation.insertInitUser();
								dbCreation.existDefaultTool();
								dbCreation.insertInitTool();
							}catch (Exception e){}
	}
	
	public void updateProgressBar() {
		new Thread(new Runnable() {
			public void run() {
				while (progress < 100){
					try{
						Thread.sleep(100);
					}catch (Exception e){}
					pgbConfiguration.post(new Runnable() {
						public void run() {
							pgbConfiguration.setProgress(progressControl.getProgress());
							if (progress <= 100) txtStatus.setText(progressControl.getMessage());
						}
					});
				}
			}
		}).start();
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}