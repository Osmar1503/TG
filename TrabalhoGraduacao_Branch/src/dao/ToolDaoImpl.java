package dao;

import java.util.List;

import model.Tool;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import controller.Message;
import factory.ConnectionFactory;

public class ToolDaoImpl implements ToolDao{

	private ConnectionFactory conn = null;
	private Message message = new Message(); 
	
	public ToolDaoImpl(Context context){
		conn = new ConnectionFactory(context);
	}
	
	public boolean add(Tool tool) {
		SQLiteDatabase db = conn.getReadableDatabase();
		try	{
			ContentValues initialValues = new ContentValues();
			initialValues.put("TYPE", String.valueOf((tool.getType())));
			initialValues.put("DESCRIPTION", tool.getDescription());
			db.insert("TOOL", null, initialValues);
			
			message.writeLogCat("Ferramenta: " + tool.getDescription() + " Inserido com sucesso.");			
			return true;
		}catch (Exception e) {
			message.writeLogCat("Falha ao Inserir Ferramenta: " + tool.getDescription());
			return false;
		}
		finally
		{
			db.close();
		}
	}

	public boolean remove(Tool tool) {return false;}

	public boolean update(Tool tool) {return false;}

	public Tool search(int toolId) {return null;}

	public List<Tool> listUser() {
		if (conn.getWritableDatabase().isOpen()){
			message.writeLogCat("Conexao aberta");
			
			SQLiteDatabase db = conn.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from TOOL", null);
			try{
				if (cursor.getCount() > 0){
					while (cursor.moveToNext()) {
						message.printSpace();
						
						try{
							message.writeLogCat( "Tool ID: " + String.valueOf((int)Integer.parseInt(cursor.getString(cursor.getColumnIndex("TOOL_ID")).toString())));
						}catch(Exception e){
							message.writeLogCat("Não foi possivel obter Tool ID...");
	        			}
	
						try{
							message.writeLogCat("Type: " + cursor.getString(cursor.getColumnIndex("TYPE")));
						}catch(Exception e){
							message.writeLogCat("Não foi possivel obter Type...");	
		        		}
						
						try{
							message.writeLogCat("Descrição: " + cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
						}catch(Exception e){
							message.writeLogCat("Não foi possivel obter Descrição...");
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

}
