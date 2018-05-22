package robin.com.anstsmartproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


@SuppressLint("HandlerLeak")
public class MainActivity extends Activity{
	

	private final int CONNECT_SUCCESS = 0x01;
	private final int CONNECT_FAILED = 0x02;		
	private final String PASSWORD_ACK = "allow_ack";	
	private final int USER_TYPE = 0xA1;	
	
	private final String objectName[] = {
		 "this subname"
	};


	private void itemInit()
	{
		
		int i = 1;
		for(i =0 ;i<1;i++){
			OrderItem myItem = new OrderItem();
			String tmpString = MyWifiMAC.getWifiMac();
			myItem.setName(tmpString== null?"null":tmpString);
			myItem.setPicture(R.drawable.p1);
			globalsocket.orderList.add(myItem);
		}
	}
	
	@SuppressLint("HandlerLeak")
	Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what == CONNECT_SUCCESS){
				itemInit();
				String action = "robin.com.anstsmartproject.myListView";
				Intent intent = new Intent(action);
				startActivity(intent);
				finish();
			}else if(msg.what == CONNECT_FAILED){
				//setButton(true);
			}
		};
	};



	protected  void start_login()
	{
		if(!globalsocket.socket_init_flag){
			socket_init();
		}
	}

	protected  void hid_status_bar()
    {
        View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
				View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        hid_status_bar();
		start_login();
	}
	
	
	private void sendConnectSuccessMsg(int sendMsg)
	{
		Message msg = new Message();
		msg.what = sendMsg;
		myHandler.sendMessage(msg);
	}
	
	
	public void socket_init()
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.d("kfliu","socket start....!\n");
				if(!globalsocket.socket_init_flag){
					sendConnectSuccessMsg(CONNECT_SUCCESS);
						/*
						globalsocket.s_socket = new Socket();
						SocketAddress address = new InetSocketAddress(globalsocket.ServerIP, globalsocket.SOCKET_SERVER_PORT);
						globalsocket.s_socket.connect(address, 5000);
						globalsocket.ips = globalsocket.s_socket.getInputStream();
						globalsocket.ops = globalsocket.s_socket.getOutputStream();

						if(globalsocket.ips !=null && globalsocket.ops !=null){

							globalsocket.socket_init_flag = true;
							Log.d("kfliu","socket success!\n");
							connect_start();
						}else{
							sendConnectSuccessMsg(CONNECT_FAILED);
						}*/
				}
			}
		}).start(); 
	}
	
	private void connect_failed_del() throws IOException
	{
		sendConnectSuccessMsg(CONNECT_FAILED);
		globalsocket.socket_init_flag =false;
		globalsocket.ips.close();
		globalsocket.ops.close();
		globalsocket.s_socket.close();
		
	}
	
	private void connect_start() throws JSONException, IOException
	{
		JSONObject object = new JSONObject();
		byte r_buff[];
		byte buff[];
		int ret = -1;
		object.put("user_tpye", USER_TYPE);
		
		if(globalsocket.ops != null){
			buff = (object.toString()+"\n\r").getBytes("utf-8");
			globalsocket.ops.write(buff, 0, buff.length);
		}else
			Log.d("kfliu","ops is null:!"+object.toString());
		
		r_buff = new byte[1024];
		if(globalsocket.ips!= null){
			ret = globalsocket.ips.read(r_buff, 0, r_buff.length);
			if(ret < 0){
				connect_failed_del();
				Log.d("kfliu","read error!");
			}else{					
				String r_string = new String(r_buff,"utf-8").trim();
				if(r_string.equals(PASSWORD_ACK)){
					sendConnectSuccessMsg(CONNECT_SUCCESS);
				}else{
					connect_failed_del();
				}
			}
		}
		
	}


	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {	
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
        	finish();
            return true;
         }
         return super.onKeyDown(keyCode, event);
     }



//--------------------






}

