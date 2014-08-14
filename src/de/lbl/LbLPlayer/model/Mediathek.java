package de.lbl.LbLPlayer.model;
import android.util.*;
import java.util.*;
import android.net.*;

public class Mediathek
{
	public static Mediathek mediathek = new Mediathek();
	private SparseArray<Song> allSongs;
	private SparseArray<Integer> selectedSongs;
	private int currentSong;
	
	private Mediathek(){
		allSongs = new SparseArray<Song>();
		selectedSongs = new SparseArray<Integer>();
		dummySongs();
	}

	public void setCurrentSong(int songId)
	{
		currentSong = songId;
	}

	public Uri getcurrentSongUri()
	{
		return allSongs.get(currentSong).uri;
	}

	public void changeSelectedById(int id)
	{
		if(selectedSongs.get(id) == null)
		{
			selectedSongs.put(id, id);
		}
		else
		{
			selectedSongs.remove(id);
		}
	}

	public Song getSongById(Integer id)
	{
		return allSongs.get(id);
	}
	
	public void dummySongs(){
		for(int i = 0;i < 20;i++){
			allSongs.put(i,new Song(i));
		}
	}
	
	public List<Song> getCurrentSongList(){
		List<Song> list = new ArrayList<Song>();
		for(int i = 0;i < allSongs.size();i++){
			list.add(allSongs.get(allSongs.keyAt(i)));
		}
		return sortList(list);
	}
	
	public List<Integer> getCurrentSongIdList(){
		List<Integer> list = new ArrayList<Integer>();
		for(int i = 0;i < allSongs.size();i++){
			list.add(allSongs.get(allSongs.keyAt(i)).id);
		}
		return list;
	}
	
	private List<Song> sortList(List<Song> list){
		return list;
	}
}
