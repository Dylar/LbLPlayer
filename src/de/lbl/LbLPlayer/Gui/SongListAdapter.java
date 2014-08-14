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

public class SongListAdapter extends BaseAdapter implements OnCheckedChangeListener, OnClickListener
{
	private Context con;
	private List<Integer> songs;
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
		// TODO: Implement this method
		return p1;
	}

	@Override
	public View getView(int pos, View contentView, ViewGroup parent)
	{
		// TODO: Implement this method
		Song s = Mediathek.mediathek.getSongById(getItem(pos));
		ViewHolder vh = null;
		if(contentView == null){
			LayoutInflater inflater = (LayoutInflater) con
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			contentView = inflater.inflate(R.layout.main_row_song, parent, false);
		}
		
		vh = (ViewHolder) contentView.getTag();
		if(vh == null){
			vh = new ViewHolder();
			vh.check = (CheckBox) contentView.findViewById(R.id.main_row_song_checkbox);
			vh.title = (TextView) contentView.findViewById(R.id.main_row_song_title);
			vh.duration = (TextView) contentView.findViewById(R.id.main_row_song_duration);
			contentView.setTag(vh);
		}
		
		vh.check.setOnCheckedChangeListener(this);
		vh.check.setId(s.id);
		vh.title.setOnClickListener(this);
		vh.title.setText(s.title);
		vh.title.setId(s.id);
		vh.duration.setText(s.duration);
		vh.duration.setOnClickListener(this);
		vh.duration.setId(s.id);
		
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
//		SystemController sc = SystemController.GetInstance();
//		SystemAction sa = sc.getNewAction();
//		sa.setAction(SystemController.PLAY_MUSIC);
//		sa.setSongId(v.getId());
//		
//		sc.tryAction(sa);
		v.setBackgroundColor(R.color.fuchsia);
	}
	
}
