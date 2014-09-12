package de.lbl.LbLPlayer.model;
import android.net.*;
import android.os.*;
import android.util.*;
import de.lbl.LbLPlayer.system.*;
import java.io.*;
import java.util.*;

public class Mediathek
{
	//public static Mediathek mediathek;
	public static SongHandler songH = new SongHandler();
	public static FolderHandler folderH = new FolderHandler();
	public static PlaylistHandler playlistH = new PlaylistHandler();
	
	//private String TAG = "mediathek";

//	private Mediathek()
//	{
//		
//	}
	
//	public static void CreateMediathek()
//	{
//		mediathek = new Mediathek();
//	}

	private List<Song> sortList(List<Song> list)
	{
		return list;
	}

	
}
