package de.lbl.LbLPlayer.system;
import android.os.*;

public class SystemAction
{
	public static final String SONG_ID = "Song id";
	
	public int action;
	private Bundle data;

	
	public void setData(Bundle data)
	{
		this.data = data;
	}
	
	public Bundle getData(){
		return data;
	}
}
