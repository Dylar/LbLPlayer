package de.lbl.LbLPlayer;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.ContextMenu.*;
import android.view.View.*;
import android.widget.*;
import android.widget.SeekBar.*;
import de.lbl.LbLPlayer.gui.*;
import de.lbl.LbLPlayer.system.*;

import android.view.View.OnClickListener;
import de.lbl.LbLPlayer.model.*;

public class MainActivity extends Activity implements OnClickListener,OnSeekBarChangeListener
{
//	final String PLAY_RANDOM = "Play Random";
//	final String PLAY_STRAIGHT = "Play straight";
//	final String PLAY_SELECTED = "Play just selected";
//	final String PLAY_ALL = "Play all";
	
	public SystemController sc;

	public SongListAdapter adapt;

	private SeekBar seekBar = null;
	private TextView durationEnd = null;
	private TextView durationCurrent = null;

	private PopupMenu popup;
	
	private ImageButton sequenceButton = null;
	private ImageButton playButton = null;
	private ImageButton nextButton = null;
	private ImageButton prevButton = null;
	private ImageButton randomButton = null;

	private final int playBtnID = 0;
	private final int seqBtnID = 1;
	private final int nextBtnID = 2;
	private final int prevBtnID = 3;
	private final int randomBtnID = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		sc = SystemController.GetInstance();
		sc.startSystem(this);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initButton();
		initSongList();
		//initSeekBar();
	}

	private void initSeekBar()
	{
		durationEnd = (TextView) findViewById(R.id.main_duration_end);
		durationCurrent = (TextView) findViewById(R.id.main_duration_current);
		seekBar = (SeekBar) findViewById(R.id.main_seekbar);
		seekBar.setMax(100);
		seekBar.setProgress(SettingAndState.currentTime);
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
		adapt = new SongListAdapter(this);
		lv.setAdapter(adapt);
	}

	private void initButton()
	{
		playButton = (ImageButton)findViewById(R.id.main_button_play);
		playButton.setId(playBtnID);
        playButton.setOnClickListener(this);

		sequenceButton = (ImageButton)findViewById(R.id.main_button_sequence);
		sequenceButton.setId(seqBtnID);
        sequenceButton.setOnClickListener(this);

		nextButton = (ImageButton)findViewById(R.id.main_button_next);
		nextButton.setId(nextBtnID);
		nextButton.setOnClickListener(this);

		prevButton = (ImageButton)findViewById(R.id.main_button_previous);
		prevButton.setId(prevBtnID);
		prevButton.setOnClickListener(this);

		randomButton = (ImageButton)findViewById(R.id.main_button_random);
		randomButton.setId(randomBtnID);
		randomButton.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		// TODO: Implement this method
		switch(featureId){
			case R.id.close:
				sc.stopSystem();
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		int id = p1.getId();
		switch (id)
		{
			case playBtnID:
				if(SettingAndState.isPlaying)
					stopMusic();
				else
					playMusic();
				break;
			case seqBtnID:
				changePlaySequence();
				//stopMusic();
				break;
			case nextBtnID:
				nextSong();
				break;
			case prevBtnID:
				previousSong();
				break;
			case randomBtnID:
				changeRandom();
				//openRandomSettings();
				break;
		}
	}

	private void changeRandom()
	{
		Mediathek.mediathek.changeRandom();
		setRandomButtonImage();
	}

	private void changePlaySequence()
	{
		Mediathek.mediathek.changePlaySequence();
		setSequenceButtonImage();
	}

	private void previousSong()
	{
		SystemAction sa = sc.getNewAction(SystemController.PLAY_PREVIOUS_SONG);
		sc.tryAction(sa);
	}

	private void nextSong()
	{
		SystemAction sa = sc.getNewAction(SystemController.PLAY_NEXT_SONG);
		sc.tryAction(sa);
	}

	private void playMusic()
	{
		SystemAction sa = sc.getNewAction(SystemController.PLAY_MUSIC);
		sc.tryAction(sa);
	}

	private void stopMusic()
	{
		SystemAction sa = sc.getNewAction(SystemController.STOP_MUSIC);
		sc.tryAction(sa);
		
	}
	
	public void setPlayButtonImage(){
		playButton.setImageDrawable(getResources().getDrawable(SettingAndState.isPlaying? R.drawable.play:R.drawable.pause));
		showToast(SettingAndState.isPlaying?"play music":"music stopped");
	}
	
	public void setRandomButtonImage(){
		if(!SettingAndState.isRandom){
			randomButton.setImageDrawable(getResources().getDrawable(R.drawable.randomnot));
			showToast("play not random");
		}
		else {
			randomButton.setImageDrawable(getResources().getDrawable(R.drawable.random));
			showToast("play random");
		}
	}
	
	public void setSequenceButtonImage(){
		switch(SettingAndState.playSeq){
			case ALL:
				sequenceButton.setImageDrawable(getResources().getDrawable(R.drawable.selectednot));
				showToast("repeat all");
				break;
			case SINGLE:
				sequenceButton.setImageDrawable(getResources().getDrawable(R.drawable.selected));
				showToast("repeat song");
				break;
			case SELECTED:
				sequenceButton.setImageDrawable(getResources().getDrawable(R.drawable.ok));
				showToast("repeat selected songs");
				break;
		}
	}

	private void showToast(String s)
	{
		Toast.makeText(this, s,Toast.LENGTH_SHORT).show();
	}
	
	public void refreshSongList(){
		adapt.notifyDataSetChanged();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		SystemAction sa = sc.getNewAction(SystemController.ON_DESTROY);
		sc.tryAction(sa);
	}
}
