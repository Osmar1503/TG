package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import controller.ProgressControl;

public class DataBaseCreation extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "tg";
	private static final int VERSION = 1;
	private final Context context;
	private ProgressControl progressControl;
	
	public DataBaseCreation(Context context, ProgressControl progressControl){
		super(context, DATABASE_NAME, null, VERSION);
		this.context = context;
		this.progressControl = progressControl;
		this.progressControl.setProgress(15);
		Log.d("daniema", "Banco criado.");
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE Contatos (ID INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT, Telefone TEXT, Endereco TEXT)";
		db.beginTransaction();
		try{
			ExecutarComandosSQL(db, sql);
			db.setTransactionSuccessful();
			Log.d("daniema", "Tabela criada.");
		}catch(Exception e){
			Log.e("Erro ao atualizar as tabelas e testar os dados", e.toString());
		}finally{
			db.endTransaction();
		}
	}

	private void ExecutarComandosSQL(SQLiteDatabase db, String sql){
		db.execSQL(sql);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

	public long InserirContato(String nome, String telefone, String endereco){
		SQLiteDatabase db = getReadableDatabase();
		
		try
		{
			ContentValues initialValues = new ContentValues();
			initialValues.put("Nome", nome);
			initialValues.put("Telefone", telefone);
			initialValues.put("Endereco", endereco);
			db.insert("Contatos", null, initialValues);
			
			Log.d("daniema", "Dados inseridos com sucesso(" + nome + ", " + telefone + ", " + endereco + ")");			
			return 1;
			
		}
		finally
		{
			db.close();
		}
	}
	
	public void obterValores(){
		if (this.getWritableDatabase().isOpen()){
			Log.d("daniema", "Conexao aberta");
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery("select * from Contatos", null);
			try{
				if (cursor.getCount() > 0){
					while (cursor.moveToNext()) {
						printSpace();
						
						try{
							Log.d("daniema", "ID: " + String.valueOf((int)Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")).toString())));  
						}catch(Exception e){
							Log.d("daniema", "Não foi possivel obter ID...");
	        			}

						try{
							Log.d("daniema", "Nome: " + cursor.getString(cursor.getColumnIndex("Nome")));
						}catch(Exception e){
							Log.d("daniema", "Não foi possivel obter Nome...");	
		        		}
						
						try{
							Log.d("daniema", "Telefone: " + cursor.getString(cursor.getColumnIndex("Telefone")));
						}catch(Exception e){
							Log.d("daniema", "Não foi possivel obter Telefone...");
	        			}
    					
						try{
							Log.d("daniema", "Endereco: " + cursor.getString(cursor.getColumnIndex("Endereco")));
						}catch(Exception e){
							Log.d("daniema", "Não foi possivel obter Endereco...");
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
     
	}
	
	
	private void printSpace(){
		Log.d("daniema", " ");
		Log.d("daniema", "----------------------------------------------------------");
		Log.d("daniema", " ");
	}
	
	private void createTables(SQLiteDatabase db){
		final String TABLE_USER = "CREATE TABLE IF NOT EXISTS USER (" +
				" USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, USER_NAME TEXT NOT NULL, " +
				" PASSWORD TEXT NOT NULL, EMAIL TEXT);";
		db.execSQL(TABLE_USER);
		
		// TODO ESSE CODIGO DEVERÁ SER DE CADA DAO, CADA UMA CRIANDO SUA TABELA NA PRIMEIRA VEZ QUE EXECUTAR
		// COM SEUS METODOS CRUD. OLHAR EXEMPLO AGENDA NA PASTA DOWNLOADS.
	}	
	
}
