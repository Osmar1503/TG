package factory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import controller.Message;
import controller.ProgressControl;


public class ConnectionFactory extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "fatec";
	private static final int VERSION = 2;
	private final Context context;
	private ProgressControl progressControl;
	private Message message = new Message();
	
	public ConnectionFactory(Context context){
		super(context, DATABASE_NAME, null, VERSION);
		this.context = context;
		message.writeLogCat("Banco criado.");
	}
	
	public ConnectionFactory(Context context, ProgressControl progressControl){
		super(context, DATABASE_NAME, null, VERSION);
		this.context = context;
		this.progressControl = progressControl;
		this.progressControl.setProgress(15);
		message.writeLogCat("Banco de Dados: " + DATABASE_NAME + ", Versão: " + VERSION + " criado com sucesso.");
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		message.writeLogCat("Iniciando processo de criação das tabelas.");
		createTables(db);
		message.writeLogCat("Criação das tabelas encerrada..");
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

		
		db.beginTransaction();
		try{
			ExecutarComandosSQL(db, userTable);
			message.writeLogCat("Tabela USER criada.");
			
			db.setTransactionSuccessful();
			
		}catch(Exception e){
			message.writeLogCat("Erro ao criar a tabela User: " + e.getMessage());
		}finally{
			db.endTransaction();
		}
		
		db.beginTransaction();
		try{
			ExecutarComandosSQL(db, toolTable);
			message.writeLogCat("Tabela TOOL criada.");
			
			db.setTransactionSuccessful();
			
		}catch(Exception e){
			message.writeLogCat("Erro ao criar a tabela Tool: " + e.getMessage());
		}finally{
			db.endTransaction();
		}
	}
	
	private void dropTables(SQLiteDatabase db){
		String userTable = "DROP TABLE USER;";
		String toolTable = "DROP TABLE TOOL;";
		
		db.beginTransaction();
		try{
			ExecutarComandosSQL(db, userTable);
			message.writeLogCat("Tabela User removida ");
		}catch(Exception e){
			message.printSpace();
			message.writeLogCat("Falha ao Remover a Tabela User: " + e.getMessage());
			message.printSpace();
		}finally{
			db.endTransaction();
		}
		
		
		db.beginTransaction();
		try{
			ExecutarComandosSQL(db, toolTable);
			message.writeLogCat("Tabela tool removida ");
		}catch(Exception e){
			message.printSpace();
			message.writeLogCat("Falha ao Remover a Tabela Tool: " + e.getMessage());
			message.printSpace();
		}finally{
			db.endTransaction();
		}
	}
}

