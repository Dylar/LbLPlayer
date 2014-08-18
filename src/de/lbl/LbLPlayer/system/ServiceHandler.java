package de.lbl.LbLPlayer.system;

public class ServiceHandler
{
	private PlayerService ps;
	
	public void startService(){
		if(ps == null)
			ps = new PlayerService();
		
	}
}
