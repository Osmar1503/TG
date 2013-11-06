package factory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import controller.Message;


public class ConnectionFactory extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "fatec";
	private static final int VERSION = 1;
	private Message message = new Message();
	
	public ConnectionFactory(Context context){
		super(context, DATABASE_NAME, null, VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables(db);
	}

	private void ExecutarComandosSQL(SQLiteDatabase db, String sql){
		db.execSQL(sql);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		message.writeLogCat("*** Banco de Dados: " + DATABASE_NAME + ", Versão Atual: " + oldVersion + " Versão Nova: " + newVersion + " criado com sucesso.");
		dropTables(db);
		onCreate(db);
	}

	private void createTables(SQLiteDatabase db){
		String userTable = "CREATE TABLE USER (" +
				" USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_NAME TEXT NOT NULL, " +
				" PASSWORD TEXT NOT NULL, EMAIL TEXT, PERMISSION INTEGER);";
		
		String toolTable = "CREATE TABLE TOOL (" +
				" TOOL_ID INTEGER PRIMARY KEY AUTOINCREMENT, ACTION TEXT, DESCRIPTION TEXT);";

		String logTable = "CREATE TABLE LOG (" +
				" LOG_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_NAME INTEGER, TOOL_ACTION INTEGER, DATE TEXT);";
		
		db.beginTransaction();
		try{
			ExecutarComandosSQL(db, userTable);
			ExecutarComandosSQL(db, toolTable);
			ExecutarComandosSQL(db, logTable);
			
			db.setTransactionSuccessful();
		}catch(Exception e){
			message.writeLogCat("Erro ao criar tabela: " + e.getMessage());
		}finally{
			db.endTransaction();
		}
	}
	
	private void dropTables(SQLiteDatabase db){
		String userTable = "DROP TABLE USER;";
		String toolTable = "DROP TABLE TOOL;";
		String logTable = "DROP TABLE LOG;";
		
		db.beginTransaction();
		try{
			ExecutarComandosSQL(db, userTable);
			ExecutarComandosSQL(db, toolTable);
			ExecutarComandosSQL(db, logTable);
		}catch(Exception e){
			message.printSpace();
			message.writeLogCat("Falha ao Remover a Tabela: " + e.getMessage());
			message.printSpace();
		}finally{
			db.endTransaction();
		}
	}
}

