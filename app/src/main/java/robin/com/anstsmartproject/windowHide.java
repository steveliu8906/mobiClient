package robin.com.anstsmartproject;

import android.view.View;

/**
 * Created by smdt_kfliu on 2018/5/24.
 */

public class windowHide {

    public static void hide_status(View decorView)
    {
			 /*全屏显示 隐藏标题栏 隐藏导航栏 沉浸模式*/
        //View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

    }
}
