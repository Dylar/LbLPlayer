package de.lbl.LbLPlayer.System;

import android.content.*;
import android.os.*;
import java.util.*;
import de.lbl.LbLPlayer.model.*;

public class SystemController
{
	private static SystemController sc = new SystemController();
	public static Context con;
	
	
	private Queue<SystemAction> actionPool;
	
	private ServiceHandler sh;
	private PlayerService ps;

	public static final int PLAY_MUSIC = 0;
	public final static int START_SERVICE = 1;
	
	
	private SystemController()
	{
		actionPool = new LinkedList<>();
	}

	public void tryAction(SystemAction sa)
	{
		switch(sa.getAction()){
			case START_SERVICE:
				startService(sa);
				break;
			case PLAY_MUSIC:
				playMusic(sa);
				break;
		}
		addToPool(sa);
	}

	private void playMusic(SystemAction sa)
	{
		Mediathek.mediathek.setCurrentSong(sa.getSongId());
	}

	private void startService(SystemAction sa)
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
