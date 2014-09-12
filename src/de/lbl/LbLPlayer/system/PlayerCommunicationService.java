package de.lbl.LbLPlayer.system;

import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import de.lbl.LbLPlayer.*;
import de.lbl.LbLPlayer.model.*;

public class PlayerCommunicationService extends AbsCommunicationService implements MediaPlayer.OnCompletionListener
{

	private final static String TAG = "PlayerService";
	

	private MediaPlayer mediaPlayer = null;
	
	private static int classID = 579; // just a number

	public static String START_PLAY = "START_PLAY";

	
	private void play() {
		if (!SettingAndState.isPlaying) {			
			SettingAndState.isPlaying = true;
			

//			Intent intent = new Intent(this, MainActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
//							Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//			PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
//
//			Notification notification = new Notification.Builder(getApplicationContext())
//	         	.setContentTitle("LbLPlayer")
//	         	.setContentText("Now Playing: \"Rain\"")
//	         	.setSmallIcon(R.drawable.ic_launcher)
//	         	.setContentIntent(pi)
//	         	.build();
//
			mediaPlayer = MediaPlayer.create(this, Mediathek.songH.getCurrentSongUri());
			//mediaPlayer.setLooping(true);
			mediaPlayer.setOnCompletionListener(this);
			
			Bundle data = new Bundle();
			//data.putInt(SystemAction.MUSIC_CURRENT_POSITION, mediaPlayer.getCurrentPosition());
			data.putInt(SystemAction.MUSIC_CURRENT_DURATION, mediaPlayer.getDuration());
			sendMessageToUI(SystemController.START_SEEKBAR,data);
			
			mediaPlayer.start();
			
		
			//startForeground(classID, notification);
		}
		else{
			stop();
			play();
		}
	}

	private void stop() {
		if (SettingAndState.isPlaying) {
			SettingAndState.isPlaying = false;
			if (mediaPlayer != null) {
				mediaPlayer.release();
				mediaPlayer = null;
			}
			//stopForeground(true);
		}
	}
	

	@Override
	public void handleMessage(Message msg)
	{
		switch (msg.what)
		{
			case SystemController.PLAY_MUSIC:
				play();
				break;
			case SystemController.STOP_MUSIC:
				stop();
				break;
			case SystemController.CHANGED_SEEKBAR:
				changePosition(msg.getData());
				break;
		}
	}

	private void changePosition(Bundle data)
	{
		mediaPlayer.seekTo(data.getInt(SystemAction.MUSIC_POSITION));
	}

	@Override
	public void onCompletion(MediaPlayer p1)
	{
		stop();
		Log.wtf(TAG, "song complete");
		
		sendMessageToUI(SystemController.PLAY_NEXT_SONG, null);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stop();
		//sendMessageToUI(SystemController.FINISH
	}	
	
	
	
}
