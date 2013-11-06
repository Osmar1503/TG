package view;

import java.util.LinkedList;
import java.util.List;

import session.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import controller.ClearLog;
import controller.ListLog;
import dm.trabalhograduacao.R;

public class Log extends Activity{
	private ListView listLog;
	private ImageView imgClearLog;
	
	 @Override
	 public void onCreate(Bundle savedInstanceState){
		 super.onCreate(savedInstanceState);
		 requestWindowFeature(Window.FEATURE_NO_TITLE); 
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 setContentView(R.layout.log_list);
		 
		 initObjects();
		 try{        
			 ListLog listLog = new ListLog(this);
			 printList(listLog.list());
	     }catch (Exception e){}
		 
	 }
	 
	private void initObjects(){
		listLog = (ListView) findViewById(R.id.lstLog);
		imgClearLog = (ImageView) findViewById(R.id.imgClearLog);
		
		imgClearLog.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(Session.getUser().getPermission() == 1){
					
					AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
					alert.setTitle("Opções de Usuário");
					alert.setMessage("Deseja realmente limpar o Log ?");

					alert.setPositiveButton("Não", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {}
					});
					
					alert.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							ClearLog clear = new ClearLog(getBaseContext());
							if(clear.clear()) recreate();
							else Toast.makeText(getBaseContext(), "Não foi possível limpar log.", Toast.LENGTH_SHORT).show();
						}
					});
					alert.show();
				}else{
					Toast.makeText(getBaseContext(), "Acesso restrito.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	private void printList(List<model.Log> list){
		List<String> arrayLog = new LinkedList<String>();
		for(Object u : list){
			model.Log log = (model.Log) u;
			arrayLog.add(log.getUserName() + "\n" + log.getToolAction() + "\n" + log.getDate());
		}
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, arrayLog);
    	listLog = (ListView) findViewById(R.id.lstLog);
    	listLog.setAdapter(arrayAdapter);
	}	
	
    @Override
    public void onBackPressed(){
    	callHomeActivity();
    }
	
    public void callHomeActivity(){
    	Intent intent = new Intent(Log.this, Home.class);
		startActivityForResult(intent, 1);
		finish();
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	 
}
