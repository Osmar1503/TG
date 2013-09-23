package view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import dm.trabalhograduacao.R;

public class Tools extends Activity{

	private ListView lstTools;
	
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tools);
        
        try{        
        	initObjects();
        }catch (Exception e){}
	}

	private void initObjects(){
		lstTools = (ListView) findViewById(R.id.lstTools);
	}
	
	public ListView getLstTools() {
		return lstTools;
	}

	public void setLstTools(ListView lstTools) {
		this.lstTools = lstTools;
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
