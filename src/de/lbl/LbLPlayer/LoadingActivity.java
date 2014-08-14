package de.lbl.LbLPlayer;

import android.app.*;
import android.content.*;
import android.os.*;

public class LoadingActivity extends Activity
{
	@Override
	public void onCreate(Bundle state){
		super.onCreate(state);
		setContentView(R.layout.main_activity);
		
		Intent i;
		
		i = new Intent(this, MainActivity.class);
		
		startActivity(i);
		finish();
	}	
}
