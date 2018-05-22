package robin.com.anstsmartproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class myListView extends  Activity {
	
	
		private int back_times = 0;


		private void hide_status()
		{
			 /*全屏显示 隐藏标题栏 隐藏导航栏 沉浸模式*/
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
					View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);

		}



		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);	
			//this.getWindow().setBackgroundDrawableResource(R.drawable.my_backup);
			hide_status();
			setContentView(R.layout.choose_action);
			
			ListView myListView = (ListView) findViewById(R.id.ListView);
			
			BaseAdapter cartAdapter = new MyAdapter(this,globalsocket.orderList);
			
			myListView.setAdapter(cartAdapter);
			
			myListView.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
	            	back_times = 0;
	            	if(i == 0){
	            		
						String action = "robin.com.anstsmartproject.WatchTVControler";
						Intent intent = new Intent(action);
						startActivity(intent);
					}
	            }
	        });
			
		}

		@Override
		public void onResume() {
			   super.onResume();
				hide_status();
		}


		public boolean onKeyDown(int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_BACK
						 && event.getRepeatCount() == 0) {
					//do something...
						Log.d("kfliu","back_times =: "+back_times);
						if(back_times == 0)
							Toast.makeText(myListView.this, "再按一次退出", Toast.LENGTH_SHORT).show();
						else
							System.exit(0);
						back_times ++;
					return true;
				 }
				 return super.onKeyDown(keyCode, event);
			 }
		


}
