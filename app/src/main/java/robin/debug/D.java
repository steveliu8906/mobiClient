package robin.debug;

import android.provider.Settings;
import android.util.Log;

/**
 * Created by smdt_kfliu on 2018/5/21.
 */

public class D {
    public static final  boolean debugFlag=true;
    public static void Debug(String str)
    {
        if(debugFlag){
            Log.d("kfliu_Smart",":"+str);
        }
    }
}
