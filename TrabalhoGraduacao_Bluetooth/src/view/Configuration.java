package view;

import dm.trabalhograduacao.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ProgressBar;

public class Configuration extends Activity{
	
	@SuppressWarnings("unused")
	private ProgressBar pgbConfiguration;
	
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration);
	
        try{        
        	initObjects();
        }catch (Exception e){}
	}

	private void initObjects(){
		pgbConfiguration = (ProgressBar) findViewById(R.id.pgbConfiguration);
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
