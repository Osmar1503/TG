package dao;

import java.util.LinkedList;
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
			
			initialValues.put("ACTION", String.valueOf((tool.getType())));
			initialValues.put("DESCRIPTION", tool.getDescription());
			db.insert("TOOL", null, initialValues);
			
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

	public List<Tool> listTool() {
		List<Tool> toolList = new LinkedList<Tool>();
		
		if (conn.getWritableDatabase().isOpen()){
			SQLiteDatabase db = conn.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from TOOL", null);
			try{
				if (cursor.getCount() > 0){
					while (cursor.moveToNext()) {
						Tool tool = new Tool();
						
						tool.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("TOOL_ID"))));
						tool.setDescription(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
						tool.setType(cursor.getString(cursor.getColumnIndex("ACTION")));
						
						toolList.add(tool);
						
						message.printSpace();
						message.writeLogCat( "Tool ID: " + tool.getId());
						message.writeLogCat("Action: " + tool.getType());
						message.writeLogCat("Descrição: " + tool.getDescription());
			        }                 
				}else{
					message.writeLogCat("Não Existem registros");
				}
				cursor.close();
	        }catch(Exception e){
	        	message.writeLogCat("Falha ao obter valores: " + e.getMessage());
	        }
		}
		return toolList;
	}
}
