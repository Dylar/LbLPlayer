package de.lbl.LbLPlayer.model;
import java.util.*;

public class Randomisator
{
	public Randomisator(){
		
	}
	
	public int getNextSong(int id){
		List<Integer> songs = Mediathek.songH.currentList;
		id = (int) (songs.size() * Math.random());
		return id;
	}
	//TODO
	
	private int getRandomSong(){
		return 0;
	}
}
