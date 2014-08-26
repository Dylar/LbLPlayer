package de.lbl.LbLPlayer.system;

import android.os.*;
import android.util.*;
import de.lbl.LbLPlayer.*;
import de.lbl.LbLPlayer.model.*;
import de.lbl.LbLPlayer.system.*;
import java.util.*;

public class SystemController
{
	private static SystemController sc = new SystemController();
	public static MainActivity act;
	private PlayerCommunicationHandler pch;

	private Queue<SystemAction> actionPool;
	
	public static final int PLAY_THIS_SONG = 0;
	public final static int STAhRT_SERVICE = 1;
	public static final int PLAY_NEXT_SONG = 2;
	public static final int PLAY_PREVIOUS_SONG = 3;
	public static final int PLAY_MUSIC = 4;
	public static final int STOP_MUSIC = 5;
	public static final int ON_DESTROY = 6;

	private String TAG = "Mediathek";
	
	 
	
	private SystemController()
	{
	}

	public void stopSystem()
	{
		pch.stopService(PlayerCommunicationService.class);
		act.finish();
	}

	public void startSystem(MainActivity act)
	{
		actionPool = new LinkedList<>();
		Mediathek.CreateMediathek(); 
		this.act = act;
		pch = new PlayerCommunicationHandler(act);
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
		}
		addToPool(sa);
	}

	private void onDestroy()
	{
		pch.onDestroy();
	}

	private void playNextSong()
	{
		Mediathek.mediathek.setNextSong();
		playMusic();
	}
	
	private void playPreviousSong()
	{
		Mediathek.mediathek.setPreviousSong();
		playMusic();
	}
	private void playThisSong(SystemAction sa)
	{
		Log.wtf(TAG, "id song " + sa.getData().getInt(SystemAction.SONG_ID));
		Mediathek.mediathek.setCurrentSong(sa.getData().getInt(SystemAction.SONG_ID));
		playMusic();
	}
	
	private void playMusic(){
		
		pch.sendMessageToService(SystemController.PLAY_MUSIC, null);
		act.refreshSongList();
		act.setPlayButtonImage();
	}
	
	private void stopMusic(){
		pch.sendMessageToService(SystemController.STOP_MUSIC, null);
		act.refreshSongList();
		act.setPlayButtonImage();
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
