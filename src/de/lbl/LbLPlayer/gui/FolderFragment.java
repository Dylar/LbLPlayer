package de.lbl.LbLPlayer.gui;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import de.lbl.LbLPlayer.*;

public class FolderFragment extends Fragment
{
	private View contentView;
	private FolderListAdapter folderAdapt;
	private ListView lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		contentView = inflater.inflate(R.layout.fragment_folder, container, false);
		initFolderList();
		setHasOptionsMenu(true);
		return contentView;
	}

	private void initFolderList(){
		lv = (ListView) contentView.findViewById(R.id.folder_list);
		folderAdapt = new FolderListAdapter(contentView.getContext());
		lv.setAdapter(folderAdapt);
	}

	public void refresh(){
		folderAdapt.notifyDataSetChanged();
	}
}
