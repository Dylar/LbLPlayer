package de.lbl.LbLPlayer.gui;

import android.content.*;
import android.content.res.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.CompoundButton.*;
import de.lbl.LbLPlayer.*;
import de.lbl.LbLPlayer.model.*;
import java.util.*;

import android.view.View.OnClickListener;

public class PlaylistAdapter extends BaseAdapter implements OnCheckedChangeListener, OnClickListener
{
	private Context con;
	private Resources res;
	private List<Playlist> playlists;
	private List<Song> currentSongs;

	boolean inPlaylist = false;
	
	private String TAG;

	public PlaylistAdapter(Context con)
	{
		super();
		this.con = con;
		this.res = con.getResources();
		this.playlists = Mediathek.playlistH.getAllPlaylists();
	}

	@Override
	public int getCount()
	{
		if (inPlaylist)
			return currentSongs.size();
		return playlists.size();
	}

	@Override
	public Entry getItem(int p1)
	{
		if(inPlaylist)
			return currentSongs.get(p1);
		return playlists.get(p1);
	}

	@Override
	public long getItemId(int p1)
	{
		return p1;
	}

	@Override
	public View getView(int pos, View contentView, ViewGroup parent)
	{
		Entry e = getItem(pos);

		ViewHolder vh = null;
		if (contentView == null)
		{
			LayoutInflater inflater = (LayoutInflater) con
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			contentView = inflater.inflate(R.layout.main_row_song, parent, false);

			vh = new ViewHolder();
			vh.check = (CheckBox) contentView.findViewById(R.id.main_row_song_checkbox);
			vh.title = (TextView) contentView.findViewById(R.id.main_row_song_title);
			vh.menu = (ImageView) contentView.findViewById(R.id.main_row_song_menu);

			vh.title.setOnClickListener(this);

			contentView.setTag(vh);
		}

		if (vh == null)
			vh = (ViewHolder) contentView.getTag();

		vh.title.setId(pos);
		vh.title.setTag(vh);

		vh.check.setOnCheckedChangeListener(null);
		vh.check.setChecked(Mediathek.playlistH.isSelectedEntry(e));
		vh.check.setOnCheckedChangeListener(this);
		vh.check.setId(pos);

		return contentView;
	}

	private class ViewHolder
	{
		public CheckBox check;
		public TextView title;
		public ImageView menu;
	}

	@Override
	public void onCheckedChanged(CompoundButton check, boolean isChecked)
	{
		Mediathek.playlistH.changeSelectedEntry(getItem(check.getId()));
	}

	@Override
	public void onClick(View v)
	{
		Entry e = getItem(v.getId());
		if (e instanceof Song)
		{
			CheckBox box = v instanceof CheckBox ? (CheckBox) v: ((ViewHolder)v.getTag()).check;
			box.setChecked(!box.isChecked());}
		else
		{
			if(inPlaylist)
				closePlaylist();
			else
				openPlaylist((Playlist)e);
		}
//		SystemController sc = SystemController.GetInstance();
//		SystemAction sa = sc.getNewAction(SystemController.PLAY_THIS_SONG);
//		sa.getData().putInt(SystemAction.ENTRY_ID, v.getId());
//		sc.tryAction(sa);
	}

	private void openPlaylist(Playlist pl)
	{
		Mediathek.playlistH.setCurrentPlaylist(pl);
		inPlaylist = true;
		refresh();
	}
	
	private void closePlaylist(){
		Mediathek.playlistH.setCurrentPlaylist(null);
		inPlaylist = false;
		refresh();
	}

	public void refresh()
	{
		//entrys = Mediathek.playlistH.getPlaylistSongList();
		notifyDataSetChanged();
	}
}
