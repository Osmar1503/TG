package dao;

import java.util.LinkedList;
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

	public boolean addDefaultUser(){
		SQLiteDatabase db = conn.getReadableDatabase();
		try	{
			ContentValues initialValues = new ContentValues();
			initialValues.put("USER_NAME", "admin");
			initialValues.put("PASSWORD", "1503");
			initialValues.put("EMAIL", "suporte@dm.com.br");
			initialValues.put("PERMISSION", 1);
			db.insert("USER", null, initialValues);
			
			initialValues.put("USER_NAME", "user");
			initialValues.put("PASSWORD", "1503");
			initialValues.put("EMAIL", "user@dm.com.br");
			initialValues.put("PERMISSION", 0);
			db.insert("USER", null, initialValues);
			return true;
			
		}catch (Exception e) {
			message.writeLogCat("Falha ao Inserir Usuario Default");
			return false;
		}
		finally
		{
			db.close();
		}
	}
	
	public boolean remove(User user) {
		try{
			if (conn.getWritableDatabase().isOpen()){
				SQLiteDatabase db = conn.getWritableDatabase();
				db.delete("USER", "USER_NAME = ?", new String[] {user.getUser()});
				return true;
			}
			return false;
		}catch(Exception e){
			return false;
		}
	}

	public boolean update(User oldUser, User newUser) {
		if (conn.getWritableDatabase().isOpen()){
			SQLiteDatabase db = conn.getWritableDatabase();
			
			ContentValues initialValues = new ContentValues();
			initialValues.put("USER_NAME", newUser.getUser());
			initialValues.put("PASSWORD", newUser.getPassword());
			initialValues.put("EMAIL", newUser.getEmail());
			initialValues.put("PERMISSION", newUser.getPermission());
			
			db.update("USER", initialValues, "USER_NAME = ?", new String[]{oldUser.getUser()});
			return true;
		}
		return false;
	}

	public User search(String userName) {
		if (conn.getWritableDatabase().isOpen()){
			SQLiteDatabase db = conn.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from USER WHERE USER_NAME = ?", new String[]{userName});
			try{
				if (cursor.getCount() > 0){
					User user = new User();
					while (cursor.moveToNext()) {
						user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("USER_ID"))));
						user.setUser(cursor.getString(cursor.getColumnIndex("USER_NAME")));
						user.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));
						user.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
						user.setPermission(Integer.parseInt(cursor.getString(cursor.getColumnIndex("PERMISSION"))));
					}
					return user;
				}else{
					return null;
				}
			}catch(Exception e){
				return null;
			}
		}else{
			conn.getWritableDatabase();
			return null;
		}
	}
	
	public List<User> listUser(){
		List<User> userList = new LinkedList<User>();
		
		if (conn.getWritableDatabase().isOpen()){
			SQLiteDatabase db = conn.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from USER", null);
			try{
				if (cursor.getCount() > 0){
					while (cursor.moveToNext()) {
						User user = new User();

						user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("USER_ID"))));
						user.setUser(cursor.getString(cursor.getColumnIndex("USER_NAME")));
						user.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));
						user.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
						user.setPermission(Integer.parseInt(cursor.getString(cursor.getColumnIndex("PERMISSION"))));
						
						userList.add(user);
						
						message.printSpace();						
						message.writeLogCat("ID: " + user.getId());  
						message.writeLogCat("Nome: " + user.getUser());
						message.writeLogCat("Senha: " + user.getPassword());
						message.writeLogCat("Email: " + user.getEmail());
						message.writeLogCat("Permissão: " + user.getPermission());
			        }                 
				}
				cursor.close();
	        }catch(Exception e){
	        	message.writeLogCat("Falha ao obter valores: " + e.getMessage());
	        }
		}
		return userList;
	}

	public User search(String userName, String password) {
		if (conn.getWritableDatabase().isOpen()){
			SQLiteDatabase db = conn.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from USER WHERE USER_NAME = ? AND PASSWORD = ?", new String[]{userName, password});
			try{
				if (cursor.getCount() > 0){
					User user = new User();
					while (cursor.moveToNext()) {
						
						user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("USER_ID"))));
						user.setUser(cursor.getString(cursor.getColumnIndex("USER_NAME")));
						user.setPassword(cursor.getString(cursor.getColumnIndex("PASSWORD")));
						user.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
						user.setPermission(Integer.parseInt(cursor.getString(cursor.getColumnIndex("PERMISSION"))));
					}
					return user;
				}else{
					return null;
				}
			}catch(Exception e){
				return null;
			}
		}else{
			conn.getWritableDatabase();
			return null;
		}
	}
	
	public int countAdminPermissions(){
		int total = 0;
		if (conn.getWritableDatabase().isOpen()){
			SQLiteDatabase db = conn.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from USER WHERE PERMISSION = ?", new String[]{"1"});
			try{
				if (cursor.getCount() > 0){
					while (cursor.moveToNext()) {total++;}                 
				}
				cursor.close();
	        }catch(Exception e){
	        	message.writeLogCat("Falha ao obter valores: " + e.getMessage());
	        }
		}
		
		return total;
	}

}
