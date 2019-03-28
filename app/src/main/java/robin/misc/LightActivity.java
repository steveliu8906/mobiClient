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

public class  LightActivity extends Activity  implements View.OnClickListener {

    Button lightCenterOpenButton,lightCenterCloseButton;
    Handler buttonHandler = new Handler(){
        public void handleMessage(Message msg) {

            switch (msg.what){
                case globalsocket.MSGNOBUSY:
                    break;
                default:
                    break;

            }

        };
    };

    public void buttonInit()
    {
        lightCenterOpenButton = (Button) findViewById(R.id.button_open_light);
        lightCenterCloseButton = (Button) findViewById(R.id.button_colse_light);
        lightCenterOpenButton.setOnClickListener(this);
        lightCenterCloseButton.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light);
        windowHide.hide_status(getWindow().getDecorView());
        buttonInit();
    }

    public int temp=globalsocket.POWER_ON;

    public void sendOpenLight(int id)
    {
        JSONObject object = new JSONObject();
        switch (id){
            case R.id.button_colse_light:
                temp= globalsocket.POWER_OFF;
                break;
            case R.id.button_open_light:
                temp= globalsocket.POWER_ON;
                break;
            default:
                break;
        }
        try {
            object.put("ctrl_object",globalsocket.LIGHTCENTER);
            object.put("ctrl_cmd", temp);
            //setButtonStatus(false);
            globalsocket.needAckFlag = true;
            JsonFunc.sendJsonObject(object,buttonHandler);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        sendOpenLight(v.getId());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        this.finish();
        return super.onKeyDown(keyCode, event);
    }
}
