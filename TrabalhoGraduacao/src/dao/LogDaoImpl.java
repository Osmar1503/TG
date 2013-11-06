package dao;

import java.util.LinkedList;
import java.util.List;

import model.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import controller.Message;
import factory.ConnectionFactory;

public class LogDaoImpl implements LogDao{
	private ConnectionFactory conn = null;
	private Message message = new Message();
	
	public LogDaoImpl(Context context){
		conn = new ConnectionFactory(context);
	}
	
	public boolean add(Log log) {
		SQLiteDatabase db = conn.getReadableDatabase();
		try	{
			ContentValues initialValues = new ContentValues();
			
			initialValues.put("USER_NAME", log.getUserName());
			initialValues.put("TOOL_ACTION", log.getToolAction());
			initialValues.put("DATE", log.getDate());
			db.insert("LOG", null, initialValues);
			
			return true;
		}catch (Exception e) {
			message.writeLogCat("Falha ao Inserir log: " + e.getMessage());
			return false;
		}finally{
			db.close();
		}
	}

	public boolean clear() {
		try{
			if (conn.getWritableDatabase().isOpen()){
				SQLiteDatabase db = conn.getWritableDatabase();
				db.delete("LOG", null, null);
				return true;
			}
			return false;
		}catch(Exception e){
			return false;
		}
	}

	public List<Log> list() {
		List<Log> logList = new LinkedList<Log>();
		
		if (conn.getWritableDatabase().isOpen()){
			SQLiteDatabase db = conn.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from LOG", null);
			try{
				if (cursor.getCount() > 0){
					while (cursor.moveToNext()) {
						Log log = new Log();

						log.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("LOG_ID"))));
						log.setUserName(cursor.getString(cursor.getColumnIndex("USER_NAME")));
						log.setToolAction(cursor.getString(cursor.getColumnIndex("TOOL_ACTION")));
						log.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
						
						logList.add(log);
			        }                 
				}
				cursor.close();
	        }catch(Exception e){
	        	message.writeLogCat("Falha ao obter valores: " + e.getMessage());
	        	return null;
	        }
		}
		return logList;
	}

}
