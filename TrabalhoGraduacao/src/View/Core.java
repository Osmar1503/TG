package view;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import dm.trabalhograduacao.R;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.Toast;

public class Core extends Activity {
	private ImageView imgLight;
	private boolean imgClicked = false;
	
	private static final String TAG = "daniema";	
	private final int REQUEST_ENABLE_BT = 1;
	private BluetoothAdapter btAdapter = null;
	private BluetoothSocket btSocket = null;
	private OutputStream outStream = null;
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private static String address = "00:13:01:09:07:78";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        try{
	        initObjects();
	        checkBTState();
	        configureSocketAndStream();
        }catch (Exception e){}
        
        imgLight.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (imgClicked){
					sendData("0");
					imgClicked = false ;
				}else{
					sendData("9");
					imgClicked = true ;
				}
			}
		});
    }

    private void initObjects() throws Exception{
    	try{
	    	imgLight = (ImageView) findViewById(R.id.imgLight);
	    	findViewById(R.id.imgLight).setKeepScreenOn(true);
	    	btAdapter = BluetoothAdapter.getDefaultAdapter();
    	}catch (Exception e){
    		Toast.makeText(getBaseContext(), "Falha ao Inicializar os Objetos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    	}
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
				Toast.makeText(getBaseContext(), "Tudo certo com o estado do Bluetooth: ", Toast.LENGTH_SHORT).show();
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
