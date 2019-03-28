package robin.com.anstsmartproject;


import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class globalsocket {
	public static Socket s_socket=null;	
	public static OutputStream ops=null;
	public static InputStream ips=null;
	
	public static final int POWER_ON = 0xD1;
	public static final int POWER_OFF = 0xD0;

	public final String CMD_CTRL_LED = "cmd_ctrl_led";

	public final static int USER_MANAGER = 0x51;
	public final static int DOOR = 0x52;
	public final static int COMPUTE = 0x53;
	public final static int LIGHTCENTER = 0x54;
	public  static final String JsonHeader = "lkfjson";


	public static final int MSGNOBUSY = 0xE0;

	public static boolean socket_init_flag = false;

	public static boolean needAckFlag = false;

	public static List<OrderItem> orderList = new LinkedList<OrderItem>();	
	private static final int LIGHT_TYPE= 0xAA00;//
	public  static final int WATCH_TV_TYPE= 0xAA01;
	public  static final int SOCKET_SERVER_PORT= 10134;
	public  static final String ServerIP = "vipgz1.idcfengye.com";

	public  static final String LocalServerIP = "192.168.1.112";
	public  static final int LOCAL_SOCKET_SERVER_PORT= 8752;

	
}
