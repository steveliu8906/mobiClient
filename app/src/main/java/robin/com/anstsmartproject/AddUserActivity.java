package robin.com.anstsmartproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static robin.com.anstsmartproject.R.id.button;

/**
 * Created by smdt_kfliu on 2018/5/24.
 */

public class AddUserActivity extends Activity implements View.OnClickListener {

    public Button addButton,quitButton;
    public EditText macEditText;
    public CheckBox rootCheckBox;

    public void idInit()
    {
        addButton = (Button)findViewById(R.id.button);
        quitButton = (Button)findViewById(R.id.button2);
        macEditText = (EditText)findViewById(R.id.editText);
        rootCheckBox = (CheckBox)findViewById(R.id.checkBox);
        addButton.setOnClickListener(this);
        quitButton.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermanager);
        windowHide.hide_status(getWindow().getDecorView());
        idInit();
    }


    public void addUserInfo()
    {
        JSONObject object = new JSONObject();
        try {
            object.put("ctrl_object",globalsocket.USER_MANAGER);
            object.put("AddUserName",macEditText.getText());
            JsonFunc.sendJsonObject(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                addUserInfo();
                break;
            case R.id.button2:
                finish();
                break;
        }
    }
}
