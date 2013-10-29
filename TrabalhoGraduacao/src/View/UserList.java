package view;

import java.util.LinkedList;
import java.util.List;

import model.User;
import session.Session;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import controller.PersistenceUser;
import dm.trabalhograduacao.R;

public class UserList extends Activity implements OnItemLongClickListener{
	ImageView imgAddUser;
	ListView listViewUsers;
	
	@Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.userlist);
        
        initObjects();
        
        try{        
        	PersistenceUser persistenceUser = new PersistenceUser(this);
        	printList(persistenceUser.list());
        }catch (Exception e){}
	}
	
	private void initObjects(){
		imgAddUser = (ImageView) findViewById(R.id.imgAddUser);
		
		imgAddUser.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(Session.getUser().getPermission() == 1) callUserCadastreActivity();
				else Toast.makeText(getBaseContext(), "Acesso restrito.", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void printList(List<User> list){
		List<String> listUser = new LinkedList<String>();
		for(Object u : list){
			User user = (User) u;
			listUser.add(user.getUser());
		}
		
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, android.R.id.text1, listUser);
    	listViewUsers = (ListView) findViewById(R.id.lstUsers);
    	listViewUsers.setAdapter(arrayAdapter);
		listViewUsers.setOnItemLongClickListener(this);
	}	
	
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
		if(Session.getUser().getPermission() == 1){
			
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Opções de Usuário");
			alert.setMessage("O que deseja fazer ?");
			alert.setIcon(R.drawable.add_user_ico);
			alert.setPositiveButton("Excluir", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String item = (String) listViewUsers.getAdapter().getItem(arg2);
					if(listViewUsers.getCount() > 1){
						removeSelectedUser(item);
						recreate();
					}
					else Toast.makeText(getBaseContext(), "Adicione outro usuário com permissão de Administrador para poder remover este usuário", Toast.LENGTH_LONG).show();	
				}
			});
			
			alert.setNegativeButton("Alterar", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					String item = (String) listViewUsers.getAdapter().getItem(arg2);
					callUserCadastreActivity(item);
				}
			});
			alert.show();
		}else{
			Toast.makeText(getBaseContext(), "Acesso restrito", Toast.LENGTH_SHORT).show();
		}
		return false;
	}
	
    private void callUserCadastreActivity() {
		Intent intent = new Intent(UserList.this, UserCadastre.class);
		startActivityForResult(intent, 1);
		finish();
	}
	
    private void callUserCadastreActivity(String userName) {
    	  Intent intent = new Intent(getBaseContext(), UserCadastre.class);
          Bundle params = new Bundle();
          
          params.putString("userName", userName);
          intent.putExtras(params);
          startActivity(intent);
          finish();
    }
    	
    private void removeSelectedUser(final String userName){
    	
    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Excluir");
		alert.setMessage("Deseja realmente Excluir ?");
		alert.setIcon(R.drawable.remove_user_ico);
		alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				PersistenceUser persistenceUser = new PersistenceUser(getBaseContext());
				
				if(persistenceUser.remove(userName)){
					Toast.makeText(getBaseContext(), "Usuário removido", Toast.LENGTH_SHORT).show();
					recreate();
				}
				else Toast.makeText(getBaseContext(), "Usuário não pode ser removido", Toast.LENGTH_SHORT).show();
			}
		});
		
		alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {}
		});
		
		alert.show();
		
    }
    
    @Override
    public void onBackPressed(){
    	callHomeActivity();
    }
    
    public void callHomeActivity(){
    	Intent intent = new Intent(UserList.this, Home.class);
		startActivityForResult(intent, 1);
		finish();
    }
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	
}
