package de.lbl.LbLPlayer.system;


import android.content.Context;

import android.content.Intent;



public class MyIntentReceiver extends android.content.BroadcastReceiver {
	
    public void onReceive(Context ctx, Intent intent) {
		
        if (intent.getAction().equals(android.media.AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
			
            Intent myIntent = new Intent(ctx, PlayerCommunicationService.class);
			
            ctx.stopService(myIntent);
			
        }
		
    }
}
