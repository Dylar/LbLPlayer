package de.lbl.LbLPlayer.system;

import android.content.*;
import android.os.*;

public class PlayerCommunicationHandler extends AbsCommunicationServiceHandler
{
	
	SystemController sc;

	public PlayerCommunicationHandler(Context con){
		super(con);
		sc = SystemController.GetInstance();
	}
	
	@Override
	public void handleMessage(Message msg)
	{
		SystemAction sa = sc.getNewAction(msg.what);
		sa.setData(msg.getData());
		sc.tryAction(sa);
	}
}
