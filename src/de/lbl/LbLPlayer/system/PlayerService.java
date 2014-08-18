package de.lbl.LbLPlayer.system;

import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import de.lbl.LbLPlayer.*;
import de.lbl.LbLPlayer.model.*;

public class PlayerService extends Service
{

	private MediaPlayer mediaPlayer = null;
	private boolean      isPlaying = false;

	private static int classID = 579; // just a number

	public static String START_PLAY = "START_PLAY";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent.getBooleanExtra(START_PLAY, false)) {
			play();	
		}
		return Service.START_STICKY;	
	}

	private void play() {
		if (!isPlaying) {			
			isPlaying = true;

			Intent intent = new Intent(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
							Intent.FLAG_ACTIVITY_SINGLE_TOP);

			PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

			Notification notification = new Notification.Builder(getApplicationContext())
	         	.setContentTitle("LbLPlayer")
	         	.setContentText("Now Playing: \"Rain\"")
	         	.setSmallIcon(R.drawable.ic_launcher)
	         	.setContentIntent(pi)
	         	.build();

			mediaPlayer = MediaPlayer.create(this, Mediathek.mediathek.getCurrentSongUri());
			mediaPlayer.setLooping(true);
			mediaPlayer.start();

			startForeground(classID, notification);
		}
		else{
			stop();
			play();
		}
	}

	@Override
	public void onDestroy() {
		stop();
	}	

	private void stop() {
		if (isPlaying) {
			isPlaying = false;
			if (mediaPlayer != null) {
				mediaPlayer.release();
				mediaPlayer = null;
			}
			stopForeground(true);
		}
	}
}
