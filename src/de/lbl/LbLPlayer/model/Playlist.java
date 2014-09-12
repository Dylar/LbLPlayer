package de.lbl.LbLPlayer.model;

import java.util.*;

public class Playlist extends Entry
{
	public List<Song> songs;
	
	public Playlist(String name){
		super(name);
		songs = new ArrayList<>();
	}

}
