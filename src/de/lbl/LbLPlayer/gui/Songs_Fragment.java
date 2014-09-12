package de.lbl.LbLPlayer.gui;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import de.lbl.LbLPlayer.*;

public class Songs_Fragment extends Fragment
{
	private View contentView;
	private SongListAdapter songAdapt;
	private ListView lv;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		contentView = inflater.inflate(R.layout.fragment_songs, container, false);
		initSongList();
		return contentView;
	}
	
	private void initSongList(){
		lv = (ListView) contentView.findViewById(R.id.song_list);
		songAdapt = new SongListAdapter(contentView.getContext());
		lv.setAdapter(songAdapt);
	}
	
	public void refresh(){
		songAdapt.notifyDataSetChanged();
	}
}
