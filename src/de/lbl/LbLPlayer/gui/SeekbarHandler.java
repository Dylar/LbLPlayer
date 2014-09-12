package de.lbl.LbLPlayer.gui;

import android.app.*;
import android.os.*;
import android.util.*;
import android.widget.*;
import android.widget.SeekBar.*;
import de.lbl.LbLPlayer.*;
import de.lbl.LbLPlayer.system.*;

public class SeekbarHandler implements OnSeekBarChangeListener
{
	private Activity act;
	private SystemController sc;
	
	private SeekBar seekBar = null;
	private TextView durationEnd = null;
	private TextView durationCurrent = null;
	private SeekbarTicker seekbarTicker;
	
	public SeekbarHandler(Activity act){
		this.act = act;
		sc = SystemController.GetInstance();
		initSeekBar();
	}
	
	private void initSeekBar()
	{
		durationEnd = (TextView) act.findViewById(R.id.main_duration_end);
		durationCurrent = (TextView) act.findViewById(R.id.main_duration_current);
		seekBar = (SeekBar) act.findViewById(R.id.main_seekbar);
		//seekBar.setMax(100);
		//seekBar.setProgress(SettingAndState.currentTime);
		seekBar.setOnSeekBarChangeListener(this);
	}

	public int cur;

	public void startSeekbar(int cur, int dur)
	{
		//Log.wtf("start seek dur","" + cur);
		//Log.wtf("start seek value","" + value);

		if (seekbarTicker != null && seekbarTicker.getStatus().equals(AsyncTask.Status.RUNNING))
			seekbarTicker.cancel(true);

		seekbarTicker = new SeekbarTicker();

		int sec = (dur / 1000) % 60 ;
		int min = ((dur / (1000 * 60)) % 60);

		durationEnd.setText("" + min + ":" + ((sec < 10) ?"0" + sec: sec));
		seekBar.setMax(dur);
		seekBar.setProgress(cur);

		seekbarTicker.execute();
	}

	public void updateSeekbar(int cur)
	{
		Log.wtf("update seek", "" + cur);   
		seekBar.setProgress(cur);
		int sec = (cur / 1000) % 60 ;
		int min = ((cur / (1000 * 60)) % 60);

		durationCurrent.setText("" + min + ":" + ((sec < 10) ?"0" + sec: sec));		
	}

	public void onProgressChanged(SeekBar p1, int p2, boolean p3)
	{
		cur = p2;
	}

	public void onStartTrackingTouch(SeekBar p1)
	{

	}

	public void onStopTrackingTouch(SeekBar p1)
	{
		SystemAction sa = sc.getNewAction(SystemController.CHANGED_SEEKBAR);
		Bundle b = new Bundle();
		b.putInt(SystemAction.MUSIC_POSITION, cur);
		sa.setData(b);
		sc.tryAction(sa);
	}
	
	
	private class SeekbarTicker extends AsyncTask<Void,Void,Void>
	{
		private Runnable update;

		public SeekbarTicker()
		{

			update = new Updater();
		}

		@Override
		protected Void doInBackground(Void[] p1)
		{
			while (SettingAndState.isPlaying && !isCancelled())
			{
				try
				{
					Thread.sleep(1000);
				}
				catch (InterruptedException e)
				{}
				seekBar.post(update);

			}
			return null;
		}

		private class Updater implements Runnable
		{
			@Override
			public void run()
			{
				cur += 1000;
				updateSeekbar(cur);
			}
		}
	}
}
