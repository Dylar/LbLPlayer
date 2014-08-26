package de.lbl.LbLPlayer.model;
import android.net.*;
import android.os.*;
import android.util.*;
import de.lbl.LbLPlayer.system.*;
import java.io.*;
import java.util.*;

public class Mediathek
{
	public static Mediathek mediathek;
	protected SparseArray<Song> allSongs;
	protected List<Integer> currentList;
	protected SparseArray<Integer> selectedSongs;
	protected List<Integer> songHistory;
	protected Queue<Integer> songQueue;
	
	private Randomisator random;
	
	private int selectedPointer;

	private String TAG = "mediathek";
	
	private Mediathek(){
		allSongs = new SparseArray<Song>();
		selectedSongs = new SparseArray<Integer>();
		currentList = new ArrayList<Integer>();
		songHistory = new ArrayList<Integer>();
		songQueue = new LinkedList<Integer>();
		//dummySongs();
		updateDir();
		
		random = new Randomisator();
	}

	public static void CreateMediathek()
	{
		mediathek = new Mediathek();
	}

	public void changePlaySequence()
	{
		switch(SettingAndState.playSeq){
			case ALL:
				SettingAndState.playSeq = SettingAndState.PlaySequence.SELECTED;
				break;
			case SELECTED:
				SettingAndState.playSeq = SettingAndState.PlaySequence.SINGLE;
				break;
			case SINGLE:
				SettingAndState.playSeq = SettingAndState.PlaySequence.ALL;
				break;
		}
	}

	public void changeRandom()
	{
		SettingAndState.isRandom = SettingAndState.isRandom ? false : true;
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
		return SettingAndState.currentSong == id;
	}

	public void setCurrentSong(int songId)
	{
		SettingAndState.currentSong = songId;
	}

	public Uri getCurrentSongUri()
	{
		Log.wtf(TAG, Boolean.toString(allSongs.get(SettingAndState.currentSong) == null));
		return allSongs.get(SettingAndState.currentSong).uri;
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
		if(SettingAndState.isRandom){
			setCurrentSong(random.getNextSong(SettingAndState.currentSong));
		}
		else
			chooseNextSong();
	}
	
	private void chooseNextSong(){
		Integer i = null;
		switch(SettingAndState.playSeq)
			{
				case SELECTED:
					if(selectedSongs.size() != 0)
					{
						while(i == null){
							setCurrentSong((SettingAndState.currentSong + 1)%allSongs.size());
							i = selectedSongs.get((SettingAndState.currentSong));
						}
						//setCurrentSong(i);
						break;
					}
				case ALL:
					while(i == null)
					{
						setCurrentSong((SettingAndState.currentSong + 1)%allSongs.size());
						i = currentList.get((SettingAndState.currentSong));
					}
					//setCurrentSong(i);
					break;
				case SINGLE:
					break;
			}	
	}
	
	public void setPreviousSong(){
//		if(SettingAndState.playRandom)
//			setCurrentSong(random.getPreviousSong(SettingAndState.currentSong));
//		else
//			chooseNextSong(-1);
//			
		//TODO history + pointer
	}

	public Song getSongById(Integer id)
	{
		return allSongs.get(id);
	}
	
//	public void dummySongs(){
//		for(int i = 0;i < 20;i++){
//			allSongs.put(i,new Song(i));
//			currentList.add(i);
//		}
//	}
	
	public List<Integer> getCurrentSongList(){
//		List<Integer> list = new ArrayList<Integer>();
//		for(int i = 0;i < currentList.size();i++){
//			list.add(currentList.get(allSongs.keyAt(i)));
//		}
		return currentList;
	}
	
	private List<Song> sortList(List<Song> list){
		return list;
	}
	
	public void updateDir()
	{
		File file;

		String root_sd = Environment.getExternalStorageDirectory().toString();
		file = new File(root_sd + "/media");       
		File[] list = file.listFiles();
	
int dirAmount = 0;
		for (int i=0; i < list.length; i++)
		{
			if (!list[i].isDirectory())
			{
				Song t = new Song(list[i],i-dirAmount);
				
				currentList.add(i-dirAmount);
				allSongs.put(i-dirAmount,t);
			}
			else
			    dirAmount++;
		}
		
		Log.wtf(TAG, "" + allSongs.size());
	}
}
