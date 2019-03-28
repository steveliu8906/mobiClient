package robin.com.anstsmartproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by smdt_kfliu on 2018/5/24.
 */

public class doorActivity extends Activity  implements View.OnClickListener {

    Button outsideDoorButton,insideDoorButton;

    public void setButtonStatus(boolean flag){

        insideDoorButton.setEnabled(flag);
        outsideDoorButton.setEnabled(flag);
        outsideDoorButton.setBackgroundColor(Color.GREEN);
        insideDoorButton.setBackgroundColor(Color.GREEN);

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
                object.put("ctrl_cmd", globalsocket.POWER_ON);
                object.put("ctrl_clientid", 4);
                JsonFunc.sendJsonObject(object);
                Thread.sleep(1000);
                JSONObject openobject = new JSONObject();
                openobject.put("ctrl_object",globalsocket.COMPUTE);
                openobject.put("ctrl_cmd", globalsocket.POWER_ON);
                openobject.put("ctrl_clientid", 3);
                JsonFunc.sendJsonObject(openobject);
                Thread.sleep(2000);
                object.put("ctrl_cmd", globalsocket.POWER_OFF);
                openobject.put("ctrl_cmd", globalsocket.POWER_OFF);
                JsonFunc.sendJsonObject(object);
                JsonFunc.sendJsonObject(openobject);

            }else if(id == R.id.insideDoorButton) {
                insideDoortemp = (insideDoortemp == globalsocket.POWER_OFF?globalsocket.POWER_ON:globalsocket.POWER_OFF);
                //insideDoortemp =  (insideDoortemp == globalsocket.POWER_OFF?globalsocket.POWER_ON:globalsocket.POWER_OFF);
                object.put("ctrl_cmd", insideDoortemp);
                object.put("ctrl_clientid", 1);
                // object.put("ctrl_cmd", insideDoortemp=(insideDoortemp == globalsocket.POWER_OFF)?globalsocket.POWER_ON:globalsocket.POWER_OFF);
                //object.put("ctrl_clientid", 4);
            }
            setButtonStatus(false);
            globalsocket.needAckFlag = true;
            JsonFunc.sendJsonObject(object,buttonHandler);
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
