package de.lbl.LbLPlayer.model;
import java.util.*;

public class PlaylistHandler
{
	
	private Playlist currentPlaylist;
	private List<Playlist> allLists;
	private List<Entry> selectedEntrys;

	public PlaylistHandler()
	{
		selectedEntrys = new ArrayList<Entry>();
		allLists = new ArrayList<Playlist>();
		//dummySongs();
		loadPlaylists();
		//updateDir();
	}

	private void loadPlaylists()
	{
		// TODO: Implement this method
	}
	
	public void setCurrentPlaylist(Playlist pl)
	{
		currentPlaylist = pl;
	}

	public void changeSelectedEntry(Entry item)
	{
		if(!(selectedEntrys.remove(item)))
			selectedEntrys.add(item);
	}

	public boolean isSelectedEntry(Entry e)
	{
		return selectedEntrys.contains(e);
	}


	public List<Song> getPlaylistSongList()
	{
		return currentPlaylist.songs;
	}
	
	public List<Playlist> getAllPlaylists()
	{
		return allLists;
	}
}
