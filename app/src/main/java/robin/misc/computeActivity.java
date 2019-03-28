package robin.misc;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import robin.com.anstsmartproject.JsonFunc;
import robin.com.anstsmartproject.R;
import robin.com.anstsmartproject.globalsocket;
import robin.com.anstsmartproject.windowHide;

/**
 * Created by smdt_kfliu on 2018/5/24.
 */

public class computeActivity extends Activity  implements View.OnClickListener {

    Button pwoerPCButton,minPCButton;

    public void setButtonStatus(boolean flag){

        pwoerPCButton.setEnabled(flag);
        minPCButton.setEnabled(flag);

        if(flag){
            pwoerPCButton.setBackgroundColor(Color.GREEN);
            minPCButton.setBackgroundColor(Color.GREEN);
        }else{
            pwoerPCButton.setBackgroundColor(Color.RED);
            minPCButton.setBackgroundColor(Color.RED);
        }
    }

    Handler buttonHandler = new Handler(){
        public void handleMessage(Message msg) {

            switch (msg.what){
                case globalsocket.MSGNOBUSY:
                    setButtonStatus(true);
                    break;
                default:
                    break;

            }

        };
    };

    public void buttonInit()
    {
        pwoerPCButton = (Button)findViewById(R.id.PowerPCButton);
        minPCButton = (Button)findViewById(R.id.miniPCButton);
        pwoerPCButton.setOnClickListener(this);
        minPCButton.setOnClickListener(this);
        setButtonStatus(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compute);
        windowHide.hide_status(getWindow().getDecorView());
        buttonInit();
    }

    public int PowerPCButtontemp=globalsocket.POWER_OFF;
    public int miniPCButtontemp=globalsocket.POWER_OFF;
    public void sendOpenDoorCmd(int id)
    {
        JSONObject object = new JSONObject();
        try {
            object.put("ctrl_object",globalsocket.COMPUTE);
            if(id == R.id.PowerPCButton) {
                object.put("ctrl_cmd", globalsocket.POWER_ON);
                object.put("ctrl_clientid", 1);
                JsonFunc.sendJsonObject(object);
                Thread.sleep(1000);
                object.put("ctrl_cmd", globalsocket.POWER_OFF);
                object.put("ctrl_clientid", 1);
                globalsocket.needAckFlag = true;
                JsonFunc.sendJsonObject(object,buttonHandler);
            }else if(id == R.id.miniPCButton) {
                object.put("ctrl_cmd", globalsocket.POWER_ON);
                object.put("ctrl_clientid", 2);
                JsonFunc.sendJsonObject(object);
                Thread.sleep(1000);
                object.put("ctrl_cmd", globalsocket.POWER_OFF);
                object.put("ctrl_clientid", 2);
                globalsocket.needAckFlag = true;
                JsonFunc.sendJsonObject(object,buttonHandler);
            }setButtonStatus(false);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        sendOpenDoorCmd(v.getId());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        this.finish();
        return super.onKeyDown(keyCode, event);
    }
}
