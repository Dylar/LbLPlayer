package de.lbl.LbLPlayer.gui;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import de.lbl.LbLPlayer.*;

public class PlaylistFragment extends Fragment
{
	private View contentView;
	private PlaylistAdapter playAdapt;
	private ListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		contentView = inflater.inflate(R.layout.fragment_folder, container, false);
		initPlaylistList();
		setHasOptionsMenu(true);
		return contentView;
	}

	private void initPlaylistList(){
		lv = (ListView) contentView.findViewById(R.id.folder_list);
		playAdapt = new PlaylistAdapter(contentView.getContext());
		lv.setAdapter(playAdapt);
	}

	public void refresh(){
		playAdapt.notifyDataSetChanged();
	}
}
