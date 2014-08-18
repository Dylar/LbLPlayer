package de.lbl.LbLPlayer.model;
import android.net.*;
import android.util.*;
import de.lbl.LbLPlayer.model.*;
import java.util.*;
import java.security.*;

public class Mediathek
{
	public static Mediathek mediathek = new Mediathek();
	protected SparseArray<Song> allSongs;
	protected SparseArray<Integer> currentList;
	protected SparseArray<Integer> selectedSongs;
	protected List<Integer> songHistory;
	protected Queue<Integer> songQueue;
	
	protected int currentSong;
	
	private Randomisator random;
	
	public boolean playRandom;
	public boolean playSelected;
	
	private Mediathek(){
		allSongs = new SparseArray<Song>();
		selectedSongs = new SparseArray<Integer>();
		currentList = new SparseArray<Integer>();
		songHistory = new ArrayList<Integer>();
		songQueue = new LinkedList<Integer>();
		dummySongs();
		
		random = new Randomisator();
	}

	public boolean isSelectedSong(int id)
	{
		for(int i = 0 ;i < selectedSongs.size();i++)
			if(selectedSongs.keyAt(i) == id)
				return true;
		return false;
	}

	public boolean isCurrentSong(int id)
	{
		return currentSong == id;
	}

	public void setCurrentSong(int songId)
	{
		currentSong = songId;
	}

	public Uri getCurrentSongUri()
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
	
	public void setNextSong(){
		if(playRandom)
			setCurrentSong(random.getNextSong(currentSong));
		else
		{
			if(playSelected)
			{
				setCurrentSong(selectedSongs.get((selectedSongs.indexOfKey(currentSong)+1)%selectedSongs.size()));
			}
			else
				setCurrentSong(currentList.get((currentSong+1)%currentList.size()));
		}
	}
	
	public void setPreviousSong(){
		if(playRandom)
			setCurrentSong(random.getPreviousSong(currentSong));
		else{
			if(playSelected)
			{
				setCurrentSong(selectedSongs.get((selectedSongs.indexOfKey(currentSong)-1)%selectedSongs.size()));
			}
			else
				setCurrentSong(currentList.get((currentSong-1)%currentList.size()));
			
		}
	}

	public Song getSongById(Integer id)
	{
		return allSongs.get(id);
	}
	
	public void dummySongs(){
		for(int i = 0;i < 20;i++){
			allSongs.put(i,new Song(i));
			currentList.put(i,i);
		}
	}
	
	public List<Integer> getCurrentSongList(){
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
