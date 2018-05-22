package robin.com.anstsmartproject;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
public class WatchTVControler extends Activity implements OnClickListener{	
	
	private final int CMD_START_ID= 0x30;
	private boolean busy_flag = false;
	private final int cmd_group[]={
			R.id.power_button,
			R.id.silence_button,
			0x3b,
			0x3b,
			0x3b,
			0x3b,
			0x3b,
			0x3b,
			0x3b,
			0x3b,
			0x3b,
			R.id.ok_button,
			R.id.up_button,
			R.id.down_button,
			R.id.right_button,
			R.id.left_button,
			R.id.back_button,
			R.id.menu_button,
			0x3b,
			0x3b,
			0x3b
	};
	
	public List<ButtonItem> buttonList = new LinkedList<ButtonItem>();	//�½�����
	
	public void buttonInit()
	{			
		int i =0;
		for(i =0;i < cmd_group.length;i++){
			
			ButtonItem tempButton = new  ButtonItem();
			tempButton.setRid(cmd_group[i]);
			tempButton.setId(i);
			tempButton.setCmd(i+0x30);
			tempButton.setButton((Button)findViewById(cmd_group[i]));
			if(tempButton.getRid() == R.id.power_button){
				tempButton.getButton().setBackgroundColor(android.graphics.Color.RED);
			}
			if(tempButton.getButton() == null)
				continue;
			tempButton.getButton().setOnClickListener(this);
			buttonList.add(tempButton);
		}

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		this.getWindow().setBackgroundDrawableResource(R.drawable.my_backup);
		setContentView(R.layout.tv_controlller);	
		
		buttonInit();
		
	}
	
	
	private void send_cmd(final int send_cmd)
	{
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				JSONObject cmdObject = new JSONObject();
				int ret = -1;
				busy_flag = true;
				String r_string=null;
			    byte r_buff[]= new byte[1024];
				if(globalsocket.ops != null){				
					try {						
						cmdObject.put("tv_control_cmd", send_cmd);
						cmdObject.put("cmd_object_type",globalsocket.WATCH_TV_TYPE);
						byte buff[]= cmdObject.toString().getBytes("utf-8");
						globalsocket.ops.write(buff, 0, buff.length);
						while(true){
							ret = globalsocket.ips.read(r_buff, 0, r_buff.length);
							if(ret > 0){
								r_string = new String(r_buff,"utf-8").trim();
							}
							if(r_string != null && r_string.equals("cmd_linux_ack")){
								busy_flag =false;	
								break;
							}
							Thread.sleep(300);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start(); 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			//Log.d("kfliu","Button is click:"+(v.getId()));
			Iterator<ButtonItem> iterator = buttonList.iterator();//��������
			while(iterator.hasNext()){
				ButtonItem tempButtonItem = iterator.next();
				if(tempButtonItem.getRid() == v.getId()){
					if(!busy_flag)
						send_cmd(tempButtonItem.getCmd());	
				} 
			}
			//fix send control cmd
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
        if (keyCode == KeyEvent.KEYCODE_BACK
                 && event.getRepeatCount() == 0) {
        	finish();
            return true;
         }
         return super.onKeyDown(keyCode, event);
     }
	

	

}
