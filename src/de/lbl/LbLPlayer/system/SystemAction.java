package de.lbl.LbLPlayer.system;
import android.os.*;

public class SystemAction
{
	public static final String SONG_ID = "Song id";
	public static final String MUSIC_CURRENT_DURATION = "music current";
	
	public int action;
	private Bundle data;

	public static String MUSIC_POSITION = "music position";
	
	
	public void setData(Bundle data)
	{
		this.data = data;
	}
	
	public Bundle getData(){
		return data;
	}
}
