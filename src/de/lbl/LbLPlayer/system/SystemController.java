package de.lbl.LbLPlayer.system;

import android.os.*;
import android.util.*;
import de.lbl.LbLPlayer.*;
import de.lbl.LbLPlayer.model.*;
import de.lbl.LbLPlayer.system.*;
import java.util.*;

public class SystemController
{
	private String TAG = "Mediathek";
	
	private static SystemController sc = new SystemController();
	public static MainActivity act;
	private PlayerCommunicationHandler pch;

	private Queue<SystemAction> actionPool;
	
	public static final int PLAY_THIS_SONG = 0;
	public final static int START_SERVICE = 1;
	public static final int PLAY_NEXT_SONG = 2;
	public static final int PLAY_PREVIOUS_SONG = 3;
	public static final int PLAY_MUSIC = 4;
	public static final int STOP_MUSIC = 5;
	public static final int ON_DESTROY = 6;

	
	public static final int CHANGED_SEEKBAR = 7;
	public static final int UPDATE_SEEKBAR = 8;

	public static final int START_SEEKBAR = 9;
	
	 
	
	private SystemController()
	{
	}

	public void stopSystem()
	{
		pch.stopService(PlayerCommunicationService.class);
		//act.finish();
	}

	public void startSystem(MainActivity act)
	{
		actionPool = new LinkedList<>(); 
		this.act = act;
		pch = new PlayerCommunicationHandler(act);
		pch.startService(PlayerCommunicationService.class);
	}
	
	public void restartSystem(){
		pch.startService(PlayerCommunicationService.class);
	}

	public void tryAction(SystemAction sa)
	{
		switch(sa.action){
//			case START_SERVICE:
//				startService();
//				break;
			case PLAY_THIS_SONG:
				playThisSong(sa);
				break;
			case STOP_MUSIC:
				stopMusic();
				break;
			case PLAY_MUSIC:
				playMusic();
				break;
			case PLAY_NEXT_SONG:
				playNextSong();
				break;
			case PLAY_PREVIOUS_SONG:
				playPreviousSong();
				break;
			case ON_DESTROY:
				onDestroy();
				break;
			case START_SEEKBAR:
				startSeekbar(sa);
				break;
			case CHANGED_SEEKBAR:
				changedSeekbar(sa);
				break;
				
		}
		addToPool(sa);
	}

	private void changedSeekbar(SystemAction sa)
	{
		if(SettingAndState.isPlaying)
		pch.sendMessageToService(sa.action,sa.getData());
		
	}

	private void startSeekbar(SystemAction sa)
	{
		int dur = sa.getData().getInt(SystemAction.MUSIC_CURRENT_DURATION);
		//int cur = sa.getData().getInt(SystemAction.MUSIC_CURRENT_POSITION);
		//int seconds = (cur / 1000) % 60 ;
		//int minutes = ((cur / (1000*60)) % 60);
		//String dur = "" + minutes + ":" + seconds;
		act.startSeekbar(0,dur);
	}

	private void onDestroy()
	{
		pch.onDestroy();
	}

	private void playNextSong()
	{
		Mediathek.songH.setNextSong();
		playMusic();
	}
	
	private void playPreviousSong()
	{
		Mediathek.songH.setPreviousSong();
		playMusic();
	}
	private void playThisSong(SystemAction sa)
	{
		Log.wtf(TAG, "id song " + sa.getData().getInt(SystemAction.SONG_ID));
		Mediathek.songH.setCurrentSong(sa.getData().getInt(SystemAction.SONG_ID));
		playMusic();
	}
	
	private void playMusic(){
		
		pch.sendMessageToService(SystemController.PLAY_MUSIC, null);
		act.refreshList();
	}
	
	private void stopMusic(){
		pch.sendMessageToService(SystemController.STOP_MUSIC, null);
		act.refreshList();
		
	}

//	private void stargtService()
//	{
//		new AsyncTask<Void, Void, Void>(){
//
//			@Override
//			protected Void doInBackground(Void[] p1)
//			{
//				if(ps == null)
//					ps = new PlayerService();
//				
//				Intent i = new Intent(con, PlayerService.class);
//				con.startService(i);
//				
//				return null;
//			}
//
//			@Override
//			public void onPostExecute(Void v){
//				
//			}
//		}.execute();
//	}

	public SystemAction getNewAction(int action)
	{
		SystemAction sa = null;
		if(actionPool.isEmpty())
			sa = new SystemAction();
		else sa = actionPool.poll();
		sa.action = action;
		Bundle b = sa.getData();
		if(b == null)
		{
			b = new Bundle();
			sa.setData(b);
		}
		else sa.getData().clear();
		return sa;
	}

	public static SystemController GetInstance()
	{
		return sc;
	}
	
	public void addToPool(SystemAction sa){
		actionPool.add(sa);
	}
}
