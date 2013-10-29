package view;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import dm.trabalhograduacao.R;

public class Core extends Activity {
	private ImageView imgLight;
	private boolean imgLightClicked = false;
	private ImageView imgHome, imgTool, imgUser, imgBluetooth, imgLock;
	private static final String TAG = "daniema";	
	private final int REQUEST_ENABLE_BT = 1;
	private BluetoothAdapter btAdapter = null;
	private BluetoothSocket btSocket = null;
	private OutputStream outStream = null;
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private static String address = "00:13:01:09:07:78";
	private boolean disconnected = true;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        
        try{
	        initObjects();
	        connectSystem();
        }catch (Exception e){}
    }

    private void initObjects() throws Exception{
    	try{
	    	imgLight = (ImageView) findViewById(R.id.imgLight);
	    	findViewById(R.id.imgLight).setKeepScreenOn(true);
	    	
	    	imgHome = (ImageView) findViewById(R.id.imgHome);
	    	imgTool = (ImageView) findViewById(R.id.imgTool);
	    	imgUser = (ImageView) findViewById(R.id.imgUser);
	    	imgBluetooth = (ImageView) findViewById(R.id.imgBluetooth);
	    	imgLock = (ImageView) findViewById(R.id.imgLock);
	    	
	    	btAdapter = BluetoothAdapter.getDefaultAdapter();
	    	
	    	imgLight.setOnClickListener(new OnClickListener() {
	 			public void onClick(View v) {
	 				if (imgLightClicked){
	 					sendData("0");
	 					imgLightClicked = false ;
	 				}else{
	 					sendData("9");
	 					imgLightClicked = true ;
	 				}
	 				alterImage(imgLightClicked);
	 			}
	 		});
	    	
	    	imgHome.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					callHomeActivity();
				}
			});
	    	
	    	imgTool.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					callToolsActivity();
				}
			});
	    	
	    	imgUser.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					callUserActivity();
				}
			});
	    	
	    	imgBluetooth.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if(disconnected == false){
						try{
							btSocket.close();
							disconnected = true;
							
							imgBluetooth.setImageResource(R.drawable.bluetooh_off);
						}catch (Exception e){}
						Toast.makeText(getBaseContext(), "Desconectado do Sistema", Toast.LENGTH_SHORT).show();
					}
					else {
						imgBluetooth.setImageResource(R.drawable.bluetooh_on);
						connectSystem();
					}
				}
			});
	    	
	    	imgLock.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					callLoginActivity();
				}
			});
   	
    	}catch (Exception e){
    		Toast.makeText(getBaseContext(), "Falha ao Inicializar os Objetos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    	}
	}

    private void connectSystem(){
		final ProgressDialog dialog = ProgressDialog.show(this, "Aguarde", "Conectando Dispositivo ao Sistema de Controle", false, true);
        new Thread(){
    		@Override
    		public void run(){
		        try {
					checkBTState();
			        configureSocketAndStream();
			        dialog.cancel();
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
        }.start();
    }
    
    private void alterImage(boolean status){
    	if(status) imgLight.setImageResource(R.drawable.lamp_9); 
    	else imgLight.setImageResource(R.drawable.lamp_0);
    }
    
    private void checkBTState() throws Exception{
		try{
			if (btAdapter == null){
				Toast.makeText(getBaseContext(), "Desculpas, mas seu dispositivo não suporta a tecnologia Bluetooth.", Toast.LENGTH_SHORT).show();
				finish();
			}
			if (!btAdapter.isEnabled()){
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
		}catch(Exception e){
			Toast.makeText(getBaseContext(), "Falha no checar estado do bluetooth: " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
    
    private void configureSocketAndStream() throws Exception{
		if(!address.equals("")){
			BluetoothDevice device = btAdapter.getRemoteDevice(address);
			try{
				btSocket = createBluetoothSocket(device);
			}catch(Exception e){}
			
			btAdapter.cancelDiscovery();			
			try{
				btSocket.connect();
				disconnected = false;
			}catch(IOException e){
				try{
					btSocket.close();
				}catch(IOException e1){}
			}
			try{
				outStream = btSocket.getOutputStream();
			}catch(IOException e){}
		}
	}
    
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException{
		if (Build.VERSION.SDK_INT >= 10){
			try{
				final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});
				return (BluetoothSocket) m.invoke(device, MY_UUID);
			}catch(Exception e){
				Log.e(TAG, "Não pode criar Conexão", e);
				Toast.makeText(getBaseContext(), "Falha no createBluetoothSocket: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
		return device.createRfcommSocketToServiceRecord(MY_UUID);
	}
    
    private void sendData(String message){
		byte[] msgBuffer = message.getBytes();
		try{
			outStream.write(msgBuffer);
		}catch(IOException e){}
		catch(Exception e1){}
	}  
    
    public void callHomeActivity() {
		Intent intent = new Intent(Core.this, Home.class);
		startActivityForResult(intent, 1);
		finish();
	}
    
    public void callToolsActivity() {
		Intent intent = new Intent(Core.this, Tools.class);
		startActivityForResult(intent, 1);
		finish();
	}
    
    public void callUserActivity() {
		Intent intent = new Intent(Core.this, UserList.class);
		startActivityForResult(intent, 1);
		finish();
	}
    
    public void callLoginActivity() {
		Intent intent = new Intent(Core.this, Login.class);
		startActivityForResult(intent, 1);
		finish();
	}
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	protected void onResume(){
		super.onResume();
	}
	
	protected void onStart(){
		super.onStart();
	}
	
	protected void onStop(){
		super.onStop();
	}
	
	protected void onPause(){
		super.onPause();
	}
	
	protected void onRestart(){
		super.onRestart();
	}
	
	protected void onDestroy(){
		try{
			btSocket.close();
		}catch (Exception e){}
		super.onDestroy();
	}
	
}
