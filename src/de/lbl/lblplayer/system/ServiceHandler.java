package de.lbl.LbLPlayer.System;

public class ServiceHandler
{
	private PlayerService ps;
	
	public void startService(){
		if(ps == null)
			ps = new PlayerService();
		
	}
}
