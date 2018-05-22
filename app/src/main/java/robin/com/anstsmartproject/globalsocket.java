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
	
	private final int LED_ON = 0xD1;
	private final int LED_OFF = 0xD0;
	private final String CMD_CTRL_LED = "cmd_ctrl_led";
	public static boolean socket_init_flag = false;
	public static List<OrderItem> orderList = new LinkedList<OrderItem>();	
	private static final int LIGHT_TYPE= 0xAA00;//
	public  static final int WATCH_TV_TYPE= 0xAA01;
	public  static final int SOCKET_SERVER_PORT= 35568; 
	public  static final String ServerIP = "free.ngrok.cc";

	
}
