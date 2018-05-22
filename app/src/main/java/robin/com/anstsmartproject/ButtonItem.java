package robin.com.anstsmartproject;

import android.widget.Button;

public class ButtonItem {
	private int Rid;
	private int id;
	private int cmd;
	private Button button;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getRid() {
		return Rid;
	}
	
	public void setRid(int Rid) {
		this.Rid = Rid;
	}
	
	public int getCmd() {
		return cmd;
	}
	
	public void setCmd(int cmd) {
		this.cmd= cmd;
	}
	
	public Button getButton() {
		return button;
	}
	
	public void setButton(Button button) {
		this.button= button;
	}
}
