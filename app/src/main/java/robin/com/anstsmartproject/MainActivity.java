package robin.com.anstsmartproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
import android.os.Build;
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
import android.widget.Toast;

import robin.debug.D;


@SuppressLint("HandlerLeak")
public class MainActivity extends Activity{
	

	private final int CONNECT_SUCCESS = 0x01;
	private final int CONNECT_FAILED = 0x02;
	private final int CONNECT_TIMEOUT = 0x03;
	private final int CONNECT_NO_PASS = 0x04;

	private final String PASSWORD_ACK = "allow_ack";	
	private final int USER_TYPE = 0xA1;	





	
	@SuppressLint("HandlerLeak")
	Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {

			switch (msg.what){
				case  CONNECT_SUCCESS:
					ItemManager.itemInit();
					String action = "robin.com.anstsmartproject.myListView";
					Intent intent = new Intent(action);
					startActivity(intent);
					finish();
					break;
				case  CONNECT_FAILED:
					Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
					break;
				case  CONNECT_TIMEOUT:
					Toast.makeText(MainActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
					break;
				case CONNECT_NO_PASS:
					Toast.makeText(MainActivity.this, "设备未获得许可", Toast.LENGTH_SHORT).show();
					break;

			}

		};
	};

	protected  void start_login()
	{
		if(!globalsocket.socket_init_flag){
			socket_init();
		}
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		windowHide.hide_status(getWindow().getDecorView());
		disableAPIDialog();
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
					//sendConnectSuccessMsg(CONNECT_SUCCESS);


					try {
						globalsocket.s_socket = new Socket();
						//优先连接内网
						SocketAddress address = new InetSocketAddress(globalsocket.LocalServerIP, globalsocket.LOCAL_SOCKET_SERVER_PORT);
						globalsocket.s_socket.connect(address, 1000);
						globalsocket.ips = globalsocket.s_socket.getInputStream();
						globalsocket.ops = globalsocket.s_socket.getOutputStream();
						if(globalsocket.ips !=null && globalsocket.ops !=null){
							globalsocket.socket_init_flag = true;
							connect_start();
						}else{
							D.Debug("socket falied!\n");
							globalsocket.s_socket = new Socket();
							SocketAddress Inetaddress = new InetSocketAddress(globalsocket.ServerIP, globalsocket.SOCKET_SERVER_PORT);
							try {
								Log.e("kfliu teset Inet 1","");
								globalsocket.s_socket.connect(Inetaddress, 4000);
								globalsocket.ips = globalsocket.s_socket.getInputStream();
								globalsocket.ops = globalsocket.s_socket.getOutputStream();
								if(globalsocket.ips !=null && globalsocket.ops !=null){
									globalsocket.socket_init_flag = true;
									connect_start();
								}else{
									D.Debug("socket falied!\n");
									sendConnectSuccessMsg(CONNECT_TIMEOUT);
								}
							} catch (IOException e1) {
								sendConnectSuccessMsg(CONNECT_TIMEOUT);
							} catch (JSONException e1) {
								e1.printStackTrace();
							}
							//sendConnectSuccessMsg(CONNECT_TIMEOUT);
						}
					} catch (IOException e) {
						D.Debug("socket exception!\n");//内网连不上，连外网
						Log.e("kfliu teset Inet 2","");
						globalsocket.s_socket = new Socket();
						SocketAddress Inetaddress = new InetSocketAddress(globalsocket.ServerIP, globalsocket.SOCKET_SERVER_PORT);
                        try {
                            globalsocket.s_socket.connect(Inetaddress, 4000);
                            globalsocket.ips = globalsocket.s_socket.getInputStream();
                            globalsocket.ops = globalsocket.s_socket.getOutputStream();
                            if(globalsocket.ips !=null && globalsocket.ops !=null){
                                globalsocket.socket_init_flag = true;
                                connect_start();
                            }else{
                                D.Debug("socket falied!\n");
                                sendConnectSuccessMsg(CONNECT_TIMEOUT);
                            }
                        } catch (IOException e1) {
                            sendConnectSuccessMsg(CONNECT_TIMEOUT);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}).start(); 
	}
	
	private void connect_failed_del() throws IOException
	{
		sendConnectSuccessMsg(CONNECT_NO_PASS);
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
		object.put("user_type", USER_TYPE);
		object.put("MacAdress",/*MyWifiMAC.getWifiMac()*/"54:25:ea:dc:ff:1c");//get MAC  as password
		if(globalsocket.ops != null){
			//buff = (object.toString()+"\n\r").getBytes("utf-8");
			JsonFunc.sendJsonObject(object);
			//globalsocket.ops.write(buff, 0, buff.length);
		}else
			Log.d("kfliu","ops is null:!"+object.toString());
		
		r_buff = new byte[1024];
		if(globalsocket.ips!= null){
			ret = globalsocket.ips.read(r_buff, 0, r_buff.length);
			if(ret < 0){
				//connect_failed_del();
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

	private void disableAPIDialog() {
		if (Build.VERSION.SDK_INT < 28) return;
		try {
			Class clazz = Class.forName("android.app.ActivityThread");
			Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
			currentActivityThread.setAccessible(true);
			Object activityThread = currentActivityThread.invoke(null);
			Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
			mHiddenApiWarningShown.setAccessible(true);
			mHiddenApiWarningShown.setBoolean(activityThread, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

