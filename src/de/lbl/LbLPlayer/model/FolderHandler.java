package de.lbl.LbLPlayer.model;

import android.os.*;
import de.lbl.LbLPlayer.model.*;
import java.io.*;
import java.util.*;

public class FolderHandler
{
	private Folder rootFolder;
	private Folder currentFolder;
	private List<Entry> selectedEntrys;

	public FolderHandler()
	{
		selectedEntrys = new ArrayList<Entry>();
		//dummySongs();
		createFolderandSongs();
		//updateDir();
	}

	public boolean isRootFolder()
	{
		// TODO: Implement this method
		return currentFolder.equals(rootFolder);
	}

	public void changeSelectedEntryById(Entry e)
	{
		if (isSelectedEntry(e))
		{
			selectedEntrys.remove(e);
		}
		else
			selectedEntrys.add(e);
	}

	public boolean isSelectedEntry(Entry e)
	{
		return selectedEntrys.contains(e);
	}

	public List<Entry> getFolderEntryList()
	{
		return currentFolder.entrys;
	}

	public void setCurrentFolder(Folder f)
	{
		if (f != null)
			currentFolder = f;
	}

	private void createFolderandSongs()
	{
		//TODO check ob man es schon drin war
		String root_sd = Environment.getExternalStorageDirectory().toString();
		File file = new File(root_sd + "/media");    

		rootFolder = currentFolder = createEntry(file, null);
	}

	private int id = 0;
	private Folder createEntry(File f, Folder up)
	{
		Folder fol = new Folder(f);
		File[] list = f.listFiles();
		if (up != null)
			fol.entrys.add(up);
		for (File fi : list)
		{
			Entry e = null;
			if (fi.isDirectory())
			{
				fol.entrys.add(createEntry(fi, fol));
			}
			else
			{
				e = new Song(fi, id);
				fol.entrys.add(e);
				Mediathek.songH.currentList.add(id);
				Mediathek.songH.allSongs.put(id, (Song)e);
				id++;
			}
		}
		fol.sortEntrys();
		return fol;
	}

}
