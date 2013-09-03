package dm.trabalhograduacao;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

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
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class Main extends Activity {
	private SeekBar sbLight;
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
        
        sbLight.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				updateImage(progress);
				sendData(String.valueOf((progress)));
			}
        	
        	public void onStartTrackingTouch(SeekBar seekBar) {}
        	
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
        
        imgLight.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (imgClicked){
					sbLight.setProgress(0);
					sendData("0");
					imgClicked = false ;
				}else{
					sbLight.setProgress(100);
					sendData("9");
					imgClicked = true ;
				}
			}
		});
    }

    private void initObjects() throws Exception{
    	try{
	    	sbLight = (SeekBar) findViewById(R.id.sbLight);
	    	sbLight.setMax(9);
	    	imgLight = (ImageView) findViewById(R.id.imgLight);
	    	findViewById(R.id.imgLight).setKeepScreenOn(true);
	    	btAdapter = BluetoothAdapter.getDefaultAdapter();
//	    	Bluetooth b = new Bluetooth();
//	    	Thread thr = new Thread(b.run());
//	    	thr.start();
	    	
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
			}catch(Exception e){
				Toast.makeText(getBaseContext(), "Falha configureSocket: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			}			
			btAdapter.cancelDiscovery();			
			Log.d(TAG, "...Conectando");
			try{
				btSocket.connect();
				Log.d(TAG, "...Conectado");
			}catch(IOException e){
				try{
					btSocket.close();
				}catch(IOException e1){
					Toast.makeText(getBaseContext(), "Falha no conect configureSocket: " + e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			}
			Log.d(TAG, "...Socket Criado");
			try{
				outStream = btSocket.getOutputStream();
			}catch(IOException e){
				Toast.makeText(getBaseContext(), "Falha configureSocket: " + e.getMessage(), Toast.LENGTH_SHORT).show();
			}
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
		}catch(IOException e){			
			String msg = "Durante a escrita da mensagem: " + e.getMessage();
			if (address.equals("00:00:00:00:00:00")) {
				msg = msg + ".\n\nUpdate your server address from 00:00:00:00:00:00 to the correct address on line 35 in the java code";
				msg = msg + ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + "exists on server.\n\n";
				Toast.makeText(getBaseContext(), "Mensagem do Sistema: " + msg, Toast.LENGTH_SHORT).show();
			}
		}catch(Exception e1){
			Toast.makeText(getBaseContext(), "Mensagem: " + message + " - Enviado" + e1.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}  
    
    private void updateImage(int progress){
    	if (progress == 0){
    		imgLight.setImageResource(R.drawable.lamp_0);
    	}else if (progress == 1){
    		imgLight.setImageResource(R.drawable.lamp_1);
    	}else if (progress == 2){
    		imgLight.setImageResource(R.drawable.lamp_2);
    	}else if (progress == 3){
    		imgLight.setImageResource(R.drawable.lamp_3);
    	}else if (progress == 4){
    		imgLight.setImageResource(R.drawable.lamp_4);
    	}else if (progress == 5){
    		imgLight.setImageResource(R.drawable.lamp_5);
    	}else if (progress == 6){
    		imgLight.setImageResource(R.drawable.lamp_6);
    	}else if (progress == 7){
    		imgLight.setImageResource(R.drawable.lamp_7);
    	}else if (progress == 8){
    		imgLight.setImageResource(R.drawable.lamp_8);
    	}else if (progress == 9){
    		imgLight.setImageResource(R.drawable.lamp_9);
    	}
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
