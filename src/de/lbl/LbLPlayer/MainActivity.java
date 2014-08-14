package de.lbl.LbLPlayer;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import de.lbl.LbLPlayer.Gui.*;
import de.lbl.LbLPlayer.System.*;
import de.lbl.LbLPlayer.model.*;

public class MainActivity extends Activity implements OnClickListener
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
	public static Context con;

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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		initButton();
		initSongList();
	}

	private void initSongList()
	{
		ListView lv = (ListView) findViewById(R.id.main_ListView);
		ListAdapter adapt = new SongListAdapter(this, Mediathek.mediathek.getCurrentSongIdList());
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
		// TODO: Implement this method
	}

	private void nextSong()
	{
		// TODO: Implement this method
		
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
