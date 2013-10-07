package factory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import controller.Message;
import controller.ProgressControl;


public class ConnectionFactory extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "fatec";
	private static final int VERSION = 1;
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
		dropTables(db);
		createTables(db);
	}

	private void createTables(SQLiteDatabase db){
		String userTable = "CREATE TABLE USER (" +
				" USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_NAME TEXT NOT NULL, " +
				" PASSWORD TEXT NOT NULL, EMAIL TEXT, PERMISSION INTEGER);";
		
//		String toolTable = "CREATE TABLE TOOL (TOOL_ID INTEGER PRIMARY KEY AUTOINCREMENT, ACTION INTEGER, DESCRIPTION TEXT, Endereco TEXT)";
//		String logTable = "CREATE TABLE LOG (ID INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT, Telefone TEXT, Endereco TEXT)";
		
		db.beginTransaction();
		try{
			message.writeLogCat("Criando Tabela USER");
			ExecutarComandosSQL(db, userTable);
			message.writeLogCat("Tabela USER criada.");
//			ExecutarComandosSQL(db, toolTable);
//			Log.d("daniema", "Tabela TOOL criada.");
//			ExecutarComandosSQL(db, logTable);
//			Log.d("daniema", "Tabela LOG criada.");
			db.setTransactionSuccessful();
			
		}catch(Exception e){
			message.writeLogCat("Erro ao criar a tabela USER: " + e.getMessage());
		}finally{
			db.endTransaction();
		}
	}
	
	private void dropTables(SQLiteDatabase db){
		String userTable = "DROP TABLE USER";
		String toolTable = "DROP TABLE TOOL";
		String logTable = "DROP TABLE LOG";
		
		db.beginTransaction();
		try{
			ExecutarComandosSQL(db, userTable);
			message.writeLogCat("Tabela User removida ");
			ExecutarComandosSQL(db, toolTable);
			message.writeLogCat("Tabela tool removida ");
			ExecutarComandosSQL(db, logTable);
			message.writeLogCat("Tabela log removida ");
		}catch(Exception e){
			message.printSpace();
			message.writeLogCat("Falha ao Remover as Tabelas: " + e.getMessage());
			message.printSpace();
		}
		
	}
	
}

