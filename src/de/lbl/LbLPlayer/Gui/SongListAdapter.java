package de.lbl.LbLPlayer.Gui;

import android.content.*;
import android.view.*;
import android.widget.*;
import de.lbl.LbLPlayer.model.*;
import java.util.*;
import java.util.zip.*;
import android.text.*;
import de.lbl.LbLPlayer.*;
import android.widget.CompoundButton.*;
import de.lbl.LbLPlayer.System.*;
import android.graphics.*;
import android.content.res.*;

public class SongListAdapter extends BaseAdapter implements OnCheckedChangeListener, OnClickListener
{
	private Context con;
	private Resources res;
	private List<Integer> songs;
	private TextView currentSongView;

	private final int playColor = R.color.red;
	private final int idleColor = R.color.white;
	
	public SongListAdapter(Context con, List<Integer> list){
		super();
		
		this.con = con;
		this.songs = list;
	}

	@Override
	public int getCount()
	{
		return songs.size();
	}

	@Override
	public Integer getItem(int p1)
	{
		return songs.get(p1);
	}

	@Override
	public long getItemId(int p1)
	{
		return p1;
	}

	@Override
	public View getView(int pos, View contentView, ViewGroup parent)
	{
		Song s = Mediathek.mediathek.getSongById(getItem(pos));
		
		ViewHolder vh = null;
		if(contentView == null){
			LayoutInflater inflater = (LayoutInflater) con
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			contentView = inflater.inflate(R.layout.main_row_song, parent, false);
		}
		
		if(res == null)
			res = contentView.getContext().getResources();
		
		vh = (ViewHolder) contentView.getTag();
		
		if(vh == null){
			vh = new ViewHolder();
			vh.check = (CheckBox) contentView.findViewById(R.id.main_row_song_checkbox);
			vh.title = (TextView) contentView.findViewById(R.id.main_row_song_title);
			vh.duration = (TextView) contentView.findViewById(R.id.main_row_song_duration);
			vh.menu = (ImageView) contentView.findViewById(R.id.main_row_song_menu);
			
			vh.title.setOnClickListener(this);
			vh.duration.setOnClickListener(this);
			contentView.setTag(vh);
		}
		
		vh.title.setText(s.title);
		vh.title.setId(s.id);
		
		vh.duration.setText(s.duration);
		vh.duration.setId(s.id);
		
		vh.check.setOnCheckedChangeListener(null);
		
		if(Mediathek.mediathek.isSelectedSong(s.id))
		{
			vh.check.setChecked(true);
		}else{
			vh.check.setChecked(false);
		}
		
		vh.check.setOnCheckedChangeListener(this);
		vh.check.setId(s.id);
		
		//vh.menu.setOnClickListener(this);
		
		if(Mediathek.mediathek.isCurrentSong(s.id)){
			if(currentSongView != null)
				currentSongView.setTextColor(res.getColor(idleColor));
			currentSongView = vh.title;
			vh.title.setTextColor(res.getColor(playColor));
		}
		else
			vh.title.setTextColor(res.getColor(idleColor));
		
		return contentView;
	}
	
	private class ViewHolder{
		public CheckBox check;
		public TextView title;
		public TextView duration;
		public ImageView menu;
	}

	@Override
	public void onCheckedChanged(CompoundButton check, boolean isChecked)
	{
		Mediathek.mediathek.changeSelectedById(check.getId());
	}

	@Override
	public void onClick(View v)
	{
		SystemController sc = SystemController.GetInstance();
		SystemAction sa = sc.getNewAction();
		sa.setAction(SystemController.PLAY_THIS_SONG);
		sa.setSongId(v.getId());
		
		sc.tryAction(sa);
	}
	
}
