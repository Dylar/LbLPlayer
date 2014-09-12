package de.lbl.LbLPlayer.gui;

import android.content.*;
import android.content.res.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.widget.CompoundButton.*;
import de.lbl.LbLPlayer.*;
import de.lbl.LbLPlayer.model.*;
import de.lbl.LbLPlayer.system.*;
import java.util.*;

import android.view.View.OnClickListener;

public class FolderListAdapter extends BaseAdapter implements OnCheckedChangeListener, OnClickListener
{
	private Context con;
	private Resources res;
	private List<Entry> entrys;

	private String TAG;

	private int folderColor = R.color.red;
	private int songColor = R.color.blue;

	public FolderListAdapter(Context con)
	{
		super();
		this.con = con;
		this.res = con.getResources();
		this.entrys = Mediathek.folderH.getFolderEntryList();
	}

	@Override
	public int getCount()
	{
		return entrys.size();
	}

	@Override
	public Entry getItem(int p1)
	{
		return entrys.get(p1);
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


		if (e instanceof Song)
			contentView.setBackgroundColor(res.getColor( songColor));
		else 
			contentView.setBackgroundColor(res.getColor(folderColor));
		
			
		if (pos == 0 && !Mediathek.folderH.isRootFolder())
			vh.title.setText("[...]");
		else
			vh.title.setText(e.title);
			
		vh.title.setId(pos);
		vh.title.setTag(vh);

		vh.check.setOnCheckedChangeListener(null);
		vh.check.setChecked(Mediathek.folderH.isSelectedEntry(e));
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
		Mediathek.folderH.changeSelectedEntryById(getItem(check.getId()));
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
			openFolder((Folder)e);
		}
//		SystemController sc = SystemController.GetInstance();
//		SystemAction sa = sc.getNewAction(SystemController.PLAY_THIS_SONG);
//		sa.getData().putInt(SystemAction.ENTRY_ID, v.getId());
//		sc.tryAction(sa);
	}

	private void openFolder(Folder f)
	{
		Mediathek.folderH.setCurrentFolder(f);
		refresh();
	}

	public void refresh()
	{
		entrys = Mediathek.folderH.getFolderEntryList();
		notifyDataSetChanged();
	}
}
