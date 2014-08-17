package de.lbl.LbLPlayer;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.SeekBar.*;
import de.lbl.LbLPlayer.Gui.*;
import de.lbl.LbLPlayer.System.*;
import de.lbl.LbLPlayer.model.*;

import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener,OnSeekBarChangeListener
{
//    /** Called when the activity is first created. */
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//	{
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//		SystemController sc = SystemController.GetInstance();
//		SystemAction sa = sc.getNewAction();
//		sa.setAction(SystemAction.START_SERVICE);
//		sc.tryAction(sa);
//		// datenbank laden
//		
//		
//    }
	public static MainActivity con;
	public SystemController sc;
	
	public SongListAdapter adapt;
	
	private SeekBar seekBar = null;
	private TextView durationEnd = null;
	private TextView durationCurrent = null;


	private ImageButton stopButton = null;
	private ImageButton playButton = null;
	private ImageButton nextButton = null;
	private ImageButton prevButton = null;
	private ImageButton randomButton = null;

	private final int playBtnID = 0;
	private final int stopBtnID = 1;
	private final int nextBtnID = 2;
	private final int prevBtnID = 3;
	private final int randomBtnID = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		con = this;
		SystemController.con = this;
		sc = SystemController.GetInstance();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initButton();
		initSongList();
		initSeekBar();
	}

	private void initSeekBar()
	{
		durationEnd = (TextView) findViewById(R.id.main_duration_end);
		seekBar = (SeekBar) findViewById(R.id.main_seekbar);
		seekBar.setMax(100);
		seekBar.setProgress(0);
		seekBar.setOnSeekBarChangeListener(this);

		Runnable runnable = new Runnable() {
			@Override
			public void run()
			{
				while (true)
				{
					//seekBar.post(seekRun);
					try
					{
						Thread.sleep(1000);
					}
					catch (InterruptedException e)
					{}
				}
			}

		};
		new Thread(runnable).start();
	}


	private void updateSeekbar()
	{
//		runOnUiThread(new Runnable(){
//
//				@Override
//				public void run()
//				{
//					if (mp.mp.isPlaying())
//		{
//			if (mp.mp.getDuration() != seekBar.getMax())
//			{
//				seekBar.setMax(mp.mp.getDuration() / 1000);
//				markTitleView();
//			}
//
//			curTime = mp.mp.getCurrentPosition() / 1000;
//			seekBar.setProgress(curTime);
//
//			durationCurrent.setText("" + curTime);
//			durationEnd.setText("" + (seekBar.getMax() - curTime));
//		}
	}
//		}

				public void onProgressChanged(SeekBar p1, int p2, boolean p3)
				{
					if (p3)
					{
						//mp.mp.seekTo(p2);
					}
				}

				public void onStartTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}

				public void onStopTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}


			


		
	
	

	private void initSongList()
	{
		ListView lv = (ListView) findViewById(R.id.main_ListView);
		adapt = new SongListAdapter(this, Mediathek.mediathek.getCurrentSongIdList());
		lv.setAdapter(adapt);
	}

	private void initButton()
	{
		playButton = (ImageButton)findViewById(R.id.main_button_play);
		playButton.setId(playBtnID);
        playButton.setOnClickListener(this);

		stopButton = (ImageButton)findViewById(R.id.main_button_stop);
		stopButton.setId(stopBtnID);
        stopButton.setOnClickListener(this);
		
		nextButton = (ImageButton)findViewById(R.id.main_button_next);
		nextButton.setId(nextBtnID);
		nextButton.setOnClickListener(this);

		prevButton = (ImageButton)findViewById(R.id.main_button_previous);
		prevButton.setId(prevBtnID);
		prevButton.setOnClickListener(this);

		randomButton = (ImageButton)findViewById(R.id.main_button_random);
		randomButton.setId(nextBtnID);
		randomButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		int id = p1.getId();
		switch (id)
		{
			case playBtnID:
				playMusic();
				break;
			case stopBtnID:
				stopMusic();
				break;
			case nextBtnID:
				nextSong();
				break;
			case prevBtnID:
				previousSong();
				break;
			case randomBtnID:
				openRandomSettings();
				break;
		}
	}

	private void openRandomSettings()
	{
		// TODO: Implement this method
	}

	private void previousSong()
	{
		SystemAction sa = sc.getNewAction();
		sa.setAction(SystemController.PLAY_PREVIOUS_SONG);
		sc.tryAction(sa);
	}

	private void nextSong()
	{
		SystemAction sa = sc.getNewAction();
		sa.setAction(SystemController.PLAY_NEXT_SONG);
		sc.tryAction(sa);
	}

	private void playMusic()
	{
		Intent intent = new Intent(getApplicationContext(), 
								   PlayerService.class);
		intent.putExtra(PlayerService.START_PLAY, true);
		startService(intent);
	}

	private void stopMusic()
	{
		Intent intent = new Intent(getApplicationContext(),
								   PlayerService.class);
		stopService(intent);
	}
}
