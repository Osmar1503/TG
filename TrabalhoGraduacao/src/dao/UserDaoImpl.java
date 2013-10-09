package dao;

import java.util.List;

import model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import controller.Message;
import factory.ConnectionFactory;

public class UserDaoImpl implements UserDao{
	private ConnectionFactory conn = null;
	private Message message = new Message();
	
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
			
			message.writeLogCat("Usuario: " + user.getUser() + " Inserido com sucesso.");			
			return true;
			
		}catch (Exception e) {
			message.writeLogCat("Falha ao Inserir Usuario: " + user.getUser());
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
			message.writeLogCat("Conexao aberta");
			
			SQLiteDatabase db = conn.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from USER", null);
			try{
				if (cursor.getCount() > 0){
					while (cursor.moveToNext()) {
						message.printSpace();
						
						try{
							message.writeLogCat("ID: " + String.valueOf((int)Integer.parseInt(cursor.getString(cursor.getColumnIndex("USER_ID")).toString())));  
						}catch(Exception e){
							message.writeLogCat("Não foi possivel obter ID...");
	        			}
	
						try{
							message.writeLogCat("Nome: " + cursor.getString(cursor.getColumnIndex("USER_NAME")));
						}catch(Exception e){
							message.writeLogCat("Não foi possivel obter Nome...");	
		        		}
						
						try{
							message.writeLogCat("Senha: " + cursor.getString(cursor.getColumnIndex("PASSWORD")));
						}catch(Exception e){
							message.writeLogCat("Não foi possivel obter Senha...");
	        			}
						
						try{
							message.writeLogCat("Email: " + cursor.getString(cursor.getColumnIndex("EMAIL")));
						}catch(Exception e){
							message.writeLogCat("Não foi possivel obter Email...");
	        			}
						
						try{
							message.writeLogCat("Permissão: " + String.valueOf((int)Integer.parseInt(cursor.getString(cursor.getColumnIndex("PERMISSION")))));
						}catch(Exception e){
							message.writeLogCat("Não foi possivel obter Permissao...");
	        			}
						
			        }                 
				}
				cursor.close();
	        }catch(Exception e){
	        	message.writeLogCat("Falha ao obter valores: " + e.getMessage());
	        }
		}else{
			message.writeLogCat("Conexao não está aberta");
		}
	
			return null;
	}

	public boolean search(String userName, String password) {
		if (conn.getWritableDatabase().isOpen()){
			message.writeLogCat("conexao aberta no search.");
			
			SQLiteDatabase db = conn.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from USER WHERE USER_NAME = ? AND PASSWORD = ?", new String[]{userName, password});
			
			message.writeLogCat("comando select escrito");
			try{
				if (cursor.getCount() > 0){
					message.writeLogCat("contem registro");
					return true;
				}else{
					message.writeLogCat("não existe registro");
					return false;
				}
			}catch(Exception e){
				message.writeLogCat("Não foi possível verificar usuario");
				return false;
			}
		}else{
			conn.getWritableDatabase();
			return false;
		}
	}
	
}
