package robin.com.anstsmartproject;

/**
 * Created by smdt_kfliu on 2018/5/22.
 */

public class ItemManager {

    static public final String itemName[]= {
            "电视",
            "门锁",
            "电脑主机",
            "照明灯",
            "用户管理"};
    static public final int bitMapId[]  = {
            R.drawable.tv,
            R.drawable.door,
            R.drawable.tv,
            R.drawable.door,
            R.drawable.usermanager
    };

    static public void itemInit()
    {
        int i = -1;
        for(i =0 ;i<bitMapId.length;i++){
            OrderItem myItem = new OrderItem();
            myItem.setName(itemName[i]);
            myItem.setPicture(bitMapId[i]);
            globalsocket.orderList.add(myItem);
        }
    }


}
