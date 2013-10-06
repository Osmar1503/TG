package dao;

import java.util.List;

import model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import factory.ConnectionFactory;

public class UserDaoImpl implements UserDao{
	private ConnectionFactory conn = null;
	
	public UserDaoImpl(Context context){
		conn = new ConnectionFactory(context);
	}
	
	public boolean add(User user) {
		SQLiteDatabase db = conn.getReadableDatabase();
		try	{
			ContentValues initialValues = new ContentValues();
			initialValues.put("USER_NAME", user.getUser());
			initialValues.put("PASSWORD", user.getPassword());
			initialValues.put("EMAIL", user.getEmail());
			initialValues.put("PERMISSION", user.getPermission());
			db.insert("USER", null, initialValues);
			
			Log.d("daniema", "Usuario: " + user.getUser() + " Inserido com sucesso.");			
			return true;
			
		}catch (Exception e) {
			Log.d("daniema", "Falha ao Inserir Usuario: " + user.getUser());
			return false;
		}
		finally
		{
			db.close();
		}
	}

	public boolean remove(User user) {
		return false;
	}

	public boolean update(User user) {
		return false;
	}

	public User search(int id) {
		return null;
	}
	
	public List<User> listUser(){
		if (conn.getWritableDatabase().isOpen()){
		Log.d("daniema", "Conexao aberta");
		SQLiteDatabase db = conn.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from USER", null);
		try{
//			" USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_NAME TEXT NOT NULL, " +
//			" PASSWORD TEXT NOT NULL, EMAIL TEXT, PERMISSION INTEGER);";
			
			if (cursor.getCount() > 0){
				while (cursor.moveToNext()) {
					printSpace();
					
					try{
						Log.d("daniema", "ID: " + String.valueOf((int)Integer.parseInt(cursor.getString(cursor.getColumnIndex("USER_ID")).toString())));  
					}catch(Exception e){
						Log.d("daniema", "Não foi possivel obter ID...");
        			}

					try{
						Log.d("daniema", "Nome: " + cursor.getString(cursor.getColumnIndex("USER_NAME")));
					}catch(Exception e){
						Log.d("daniema", "Não foi possivel obter Nome...");	
	        		}
					
					try{
						Log.d("daniema", "Senha: " + cursor.getString(cursor.getColumnIndex("PASSWORD")));
					}catch(Exception e){
						Log.d("daniema", "Não foi possivel obter Senha...");
        			}
					
					try{
						Log.d("daniema", "Email: " + cursor.getString(cursor.getColumnIndex("EMAIL")));
					}catch(Exception e){
						Log.d("daniema", "Não foi possivel obter Email...");
        			}
					
					try{
						Log.d("daniema", "Permissão: " + String.valueOf((int)Integer.parseInt(cursor.getString(cursor.getColumnIndex("PERMISSION")))));
					}catch(Exception e){
						Log.d("daniema", "Não foi possivel obter Permissao...");
        			}
					
		        }                 
			}
			cursor.close();
        }catch(Exception e){
        	Log.d("daniema", "Falha ao obter valores: " + e.getMessage());
        }
	}else{
		Log.d("daniema", "Conexao não está aberta");
	}

		return null;
	}

	private void printSpace(){
		Log.d("daniema", " ");
		Log.d("daniema", "----------------------------------------------------------");
		Log.d("daniema", " ");
	}
	
	private void escreve(String msg){
		Log.d("daniema", msg);
	}

	public boolean search(String userName, String password) {
		if (conn.getWritableDatabase().isOpen()){
			escreve("conexao aberta no search.");
			SQLiteDatabase db = conn.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from USER WHERE USER_NAME = ? AND PASSWORD = ?", new String[]{userName, password});
			escreve("comando select escrito");
			try{
				if (cursor.getCount() > 0){
					escreve("contem registro");
					return true;
				}else{
					escreve("não existe registro");
					return false;
				}
			}catch(Exception e){
				Log.d("daniema", "Não foi possível verificar usuario");
				return false;
			}
		}else{
			conn.getWritableDatabase();
			return false;
		}
	}
	
}
