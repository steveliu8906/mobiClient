package robin.com.anstsmartproject;

import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;
import android.os.Handler;
import java.io.IOException;


public class JsonFunc {
	public String jsonString;
	public int ERR = -1;
	public JsonFunc(String str){
		this.jsonString = str;
	}

	
	public int getIntByKey(String key)
	{
		JSONObject object;
		try {
			object = new JSONObject(jsonString);
			return  object.getInt(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ERR;
	}
	
	public String getStringByKey(String key)
	{
		JSONObject object;
		try {
			object = new JSONObject(jsonString);
			return  object.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void sendJsonObject(final JSONObject sendobj)
	{
		new Thread(new Runnable() {
			@Override
			public void run() {

				if(globalsocket.ops != null) {
					try {
						byte buff[] = (globalsocket.JsonHeader+sendobj.toString()).getBytes("utf-8");
						globalsocket.ops.write(buff, 0, buff.length);
						while(globalsocket.needAckFlag){
							String r_string=null;
							byte r_buff[]=new byte[128];
							int ret = -1;
							ret = globalsocket.ips.read(r_buff, 0, r_buff.length);
							if(ret > 0){
								r_string = new String(r_buff,"utf-8").trim();
							}
							if(r_string != null && r_string.equals("cmd_linux_ack")){
								globalsocket.needAckFlag =false;
								break;
							}
							Thread.sleep(300);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();


	}


	public static void sendJsonObject(final JSONObject sendobj, final Handler buttonHandler)
	{
		new Thread(new Runnable() {
			@Override
			public void run() {

				if(globalsocket.ops != null) {
					try {
						byte buff[] = (globalsocket.JsonHeader+sendobj.toString()).getBytes("utf-8");
						globalsocket.ops.write(buff, 0, buff.length);
						while(globalsocket.needAckFlag){
							String r_string=null;
							byte r_buff[]=new byte[128];
							int ret = -1;
							ret = globalsocket.ips.read(r_buff, 0, r_buff.length);
							if(ret > 0){
								r_string = new String(r_buff,"utf-8").trim();
							}
							if(r_string != null && r_string.equals("cmd_linux_ack")){
								globalsocket.needAckFlag =false;
								Message msg = new Message();
								msg.what = globalsocket.MSGNOBUSY;
                                buttonHandler.sendMessage(msg);
								break;
							}
							Thread.sleep(300);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();


	}
}
