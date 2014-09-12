package de.lbl.LbLPlayer.model;

import android.net.*;
import java.io.*;

public abstract class Entry
{
	public String title;
	public Uri uri;
	public int id = 42;
	private File file;
	
	public Entry(String title){
		setTitle(title);
	}
	
	public void setTitle(String name){
		title = name;
	}

}
