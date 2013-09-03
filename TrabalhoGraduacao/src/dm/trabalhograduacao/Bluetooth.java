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

public class Bluetooth extends Activity {

	private BluetoothAdapter btAdapter = null;
	private BluetoothSocket btSocket = null;
	private OutputStream outStream = null;
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private static String address = "00:13:01:09:07:78";
	
	public Bluetooth() throws Exception{
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		checkBluetoothState();
		configureSocketAndStream();
	}
	
    public void checkBluetoothState() throws Exception{
    	try{
			if (btAdapter == null){
				finish();
			}
			if (!btAdapter.isEnabled()){
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, 1);
			}
    	}catch(Exception e){
    	}
	}
    
    public void configureSocketAndStream() throws Exception{
		if(!address.equals("")){
			BluetoothDevice device = btAdapter.getRemoteDevice(address);
			try{
				btSocket = createBluetoothSocket(device);
			}catch(Exception e){
			}			
			btAdapter.cancelDiscovery();			
			try{
				btSocket.connect();
			}catch(IOException e){
				try{
					btSocket.close();
				}catch(IOException e1){
				}
			}
			try{
				outStream = btSocket.getOutputStream();
			}catch(IOException e){
			}
		}
	}
    
    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException{
		if (Build.VERSION.SDK_INT >= 10){
			try{
				final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});
				return (BluetoothSocket) m.invoke(device, MY_UUID);
			}catch(Exception e){
			}
		}
		return device.createRfcommSocketToServiceRecord(MY_UUID);
	}
    
    public void sendData(String message){
		byte[] msgBuffer = message.getBytes();
		try{
			outStream.write(msgBuffer);
		}catch(IOException e){			
			String msg = "Durante a escrita da mensagem: " + e.getMessage();
			if (address.equals("00:00:00:00:00:00")) {
				msg = msg + ".\n\nUpdate your server address from 00:00:00:00:00:00 to the correct address on line 35 in the java code";
				msg = msg + ".\n\nCheck that the SPP UUID: " + MY_UUID.toString() + "exists on server.\n\n";
			}
		}catch(Exception e1){
		}
	}
    
    public Runnable run(){
		while (!btSocket.isConnected()){
			try{
				btAdapter = BluetoothAdapter.getDefaultAdapter();
				checkBluetoothState();
				configureSocketAndStream();
			}catch (Exception e){}
		}
		return null;
	}
    
}
