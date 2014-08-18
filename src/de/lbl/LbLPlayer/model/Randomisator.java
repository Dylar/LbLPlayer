package de.lbl.LbLPlayer.model;
import android.util.*;

public class Randomisator
{
	public Randomisator(){
		
	}
	
	public int getNextSong(int id){
		SparseArray<Song> allSongs = Mediathek.mediathek.allSongs;
		
		return id;
	}
	//TODO
	public int getPreviousSong(int id){
		SparseArray<Song> allSongs = Mediathek.mediathek.allSongs;

		return allSongs.get((id-1)%allSongs.size()).id;
	}
	
	private int getRandomSong(){
		return 0;
	}
}
