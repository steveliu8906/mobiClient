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

public class doorActivity extends Activity  implements View.OnClickListener {

    Button outsideDoorButton,insideDoorButton;

    public void setButtonStatus(boolean flag){

        insideDoorButton.setEnabled(flag);
        outsideDoorButton.setEnabled(flag);

        if(flag){
            outsideDoorButton.setBackgroundColor(Color.GREEN);
            insideDoorButton.setBackgroundColor(Color.GREEN);
        }else{
            outsideDoorButton.setBackgroundColor(Color.RED);
            insideDoorButton.setBackgroundColor(Color.RED);
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
        outsideDoorButton = (Button)findViewById(R.id.outsideDoorButton);
        insideDoorButton = (Button)findViewById(R.id.insideDoorButton);
        outsideDoorButton.setOnClickListener(this);
        insideDoorButton.setOnClickListener(this);
        setButtonStatus(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple);
        windowHide.hide_status(getWindow().getDecorView());
        buttonInit();
    }

    public int outsideDoortemp=globalsocket.POWER_OFF;
    public int insideDoortemp=globalsocket.POWER_OFF;
    public void sendOpenDoorCmd(int id)
    {
        JSONObject object = new JSONObject();
        try {
            object.put("ctrl_object",globalsocket.COMPUTE);

            if(id == R.id.outsideDoorButton) {
                outsideDoortemp = (outsideDoortemp == globalsocket.POWER_OFF?globalsocket.POWER_ON:globalsocket.POWER_OFF);
                object.put("ctrl_cmd", outsideDoortemp);
                object.put("ctrl_clientid", 3);
            }else if(id == R.id.insideDoorButton) {
                insideDoortemp = (insideDoortemp == globalsocket.POWER_OFF?globalsocket.POWER_ON:globalsocket.POWER_OFF);
                //insideDoortemp =  (insideDoortemp == globalsocket.POWER_OFF?globalsocket.POWER_ON:globalsocket.POWER_OFF);
                object.put("ctrl_cmd", insideDoortemp);
                // object.put("ctrl_cmd", insideDoortemp=(insideDoortemp == globalsocket.POWER_OFF)?globalsocket.POWER_ON:globalsocket.POWER_OFF);
                object.put("ctrl_clientid", 4);
            }
            setButtonStatus(false);
            globalsocket.needAckFlag = true;
            JsonFunc.sendJsonObject(object,buttonHandler);
        } catch (JSONException e) {
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
