package view;

import java.util.LinkedList;
import java.util.List;

import model.Tool;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import controller.ListOfTools;
import dm.trabalhograduacao.R;

public class Tools extends Activity{

	
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.tools);
        
        try{        
        	ListOfTools listOfTools = new ListOfTools(this);
        	printList(listOfTools.getListOfTools());
        }catch (Exception e){}
	}

	
	private void printList(List<Tool> list){
		List<String> listTool = new LinkedList<String>();
		for(Object i : list){
			Tool t = (Tool) i;
			listTool.add(t.getDescription());
		}
		
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, listTool);
    	ListView listViewTools = (ListView) findViewById(R.id.lstTools);
    	listViewTools.setAdapter(ad);
	}	

    @Override
    public void onBackPressed(){
    	callHomeActivity();
    }
    
    public void callHomeActivity(){
    	Intent intent = new Intent(Tools.this, Home.class);
		startActivityForResult(intent, 1);
		finish();
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
}
