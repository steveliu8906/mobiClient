package robin.com.anstsmartproject;

import android.app.Activity;
import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class ChooseActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple);	
		
		Drawable wallPaper = WallpaperManager.getInstance( this).getDrawable();
		this.getWindow().setBackgroundDrawable(wallPaper);
			
	}
	
	
}
