package de.lbl.LbLPlayer.System;

import android.content.*;
import android.os.*;
import de.lbl.LbLPlayer.*;
import de.lbl.LbLPlayer.model.*;
import java.util.*;

public class SystemController
{
	private static SystemController sc = new SystemController();
	public static Context con;
	
	
	private Queue<SystemAction> actionPool;
	
	private ServiceHandler sh;
	private PlayerService ps;

	public static final int PLAY_THIS_SONG = 0;
	public final static int START_SERVICE = 1;
	public static final int PLAY_NEXT_SONG = 2;
	public static final int PLAY_PREVIOUS_SONG = 3;

	 
	
	private SystemController()
	{
		actionPool = new LinkedList<>();
	}

	public void tryAction(SystemAction sa)
	{
		switch(sa.getAction()){
			case START_SERVICE:
				startService();
				break;
			case PLAY_THIS_SONG:
				playThisSong(sa);
				break;
			case PLAY_NEXT_SONG:
				playNextSong();
				break;
		}
		addToPool(sa);
	}

	private void playNextSong()
	{
		//Mediathek.mediathek.setNext
	}

	private void playThisSong(SystemAction sa)
	{
		Mediathek.mediathek.setCurrentSong(sa.getSongId());
		playMusic();
		//MainActivity.con.adapt.notifyDataSetChanged();
	}
	
	private void playMusic(){
		Intent intent = new Intent(con.getApplicationContext(), 
								   PlayerService.class);
		intent.putExtra(PlayerService.START_PLAY, true);
		con.startService(intent);
	}

	private void startService()
	{
		new AsyncTask<Void, Void, Void>(){

			@Override
			protected Void doInBackground(Void[] p1)
			{
				if(ps == null)
					ps = new PlayerService();
				
				Intent i = new Intent(con, PlayerService.class);
				con.startService(i);
				
				return null;
			}

			@Override
			public void onPostExecute(Void v){
				
			}
		}.execute();
	}

	public SystemAction getNewAction()
	{
		if(actionPool.isEmpty())
			return new SystemAction();
		return actionPool.poll();
	}

	public static SystemController GetInstance()
	{
		return sc;
	}
	
	public void addToPool(SystemAction sa){
		actionPool.add(sa);
	}
}
