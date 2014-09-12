package de.lbl.LbLPlayer.gui;
import android.app.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import de.lbl.LbLPlayer.*;
import de.lbl.LbLPlayer.model.*;
import de.lbl.LbLPlayer.system.*;

public class MusicButtonHandler implements OnClickListener
{
	private Activity act;
	private SystemController sc;
	
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
	
	public MusicButtonHandler(Activity act){
		this.act = act;
		sc = SystemController.GetInstance();
		initButton();
	}
	
	private void initButton()
	{
		playButton = (ImageButton) act.findViewById(R.id.main_button_play);
		playButton.setId(playBtnID);
        playButton.setOnClickListener(this);

		sequenceButton = (ImageButton) act.findViewById(R.id.main_button_sequence);
		sequenceButton.setId(seqBtnID);
        sequenceButton.setOnClickListener(this);

		nextButton = (ImageButton) act.findViewById(R.id.main_button_next);
		nextButton.setId(nextBtnID);
		nextButton.setOnClickListener(this);

		prevButton = (ImageButton) act.findViewById(R.id.main_button_previous);
		prevButton.setId(prevBtnID);
		prevButton.setOnClickListener(this);

		randomButton = (ImageButton) act.findViewById(R.id.main_button_random);
		randomButton.setId(randomBtnID);
		randomButton.setOnClickListener(this);

	}
	

	@Override
	public void onClick(View p1)
	{
		int id = p1.getId();
		switch (id)
		{
			case playBtnID:
				if (SettingAndState.isPlaying)
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
		Mediathek.songH.changeRandom();
		setRandomButtonImage();
	}

	private void changePlaySequence()
	{
		Mediathek.songH.changePlaySequence();
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

	public void setPlayButtonImage()
	{
		playButton.setImageDrawable(act.getResources().getDrawable(SettingAndState.isPlaying ? R.drawable.play: R.drawable.pause));
		//act.showToast(SettingAndState.isPlaying ?"play music": "music stopped");
	}

	public void setRandomButtonImage()
	{
		if (!SettingAndState.isRandom)
		{
			randomButton.setImageDrawable(act.getResources().getDrawable(R.drawable.randomnot));
			//showToast("play not random");
		}
		else
		{
			randomButton.setImageDrawable(act.getResources().getDrawable(R.drawable.random));
			//showToast("play random");
		}
	}

	public void setSequenceButtonImage()
	{
		switch (SettingAndState.playSeq)
		{
			case ALL:
				sequenceButton.setImageDrawable(act.getResources().getDrawable(R.drawable.selectednot));
				//showToast("repeat all");
				break;
			case SINGLE:
				sequenceButton.setImageDrawable(act.getResources().getDrawable(R.drawable.selected));
				//showToast("repeat song");
				break;
			case SELECTED:
				sequenceButton.setImageDrawable(act.getResources().getDrawable(R.drawable.ok));
				//showToast("repeat selected songs");
				break;
		}
	}
	
	
}
