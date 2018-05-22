package robin.com.anstsmartproject;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonFunc {
	public String jsonString;
	public int ERR = -1;
	public JsonFunc(String str){
		this.jsonString = str;
	}
	
	public int getIntByKey(String key)
	{
		JSONObject object;
		try {
			object = new JSONObject(jsonString);
			return  object.getInt(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ERR;
	}
	
	public String getStringByKey(String key)
	{
		JSONObject object;
		try {
			object = new JSONObject(jsonString);
			return  object.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
