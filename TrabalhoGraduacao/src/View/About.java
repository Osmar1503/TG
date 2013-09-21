package view;

import dm.trabalhograduacao.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class About extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
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
