package de.lbl.LbLPlayer.model;
import android.media.*;
import android.net.*;
import de.lbl.LbLPlayer.*;
import java.io.*;

public class Song
{
	public String title;
	public Uri uri;
	public int id;
	private File file;
	public String duration = "0:42";



    public Song(File f, int id){
		file = f;
		uri = Uri.fromFile(f);
		this.id = id;
		setTitle(f.getName());
		//  setInfo(f);
	}
	
	public Song(int id){
		this.id = id;
		uri = Uri.parse("android.resource://" + MainActivity.con.getPackageName() + "/" + R.raw.rain);
		title = "Testi McTest - Bass Test";
		
	}

	public void setTitle(String name)
	{
		char[] ca1 = name.toCharArray();
		char[] ca2 = new char[ca1.length-4];
		for(int i = 0;i < ca2.length;i++){
			ca2[i] = ca1[i];
		}
		title = String.valueOf(ca2);
	} 

	public void setInfo(File f){
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		String filePath = f.getAbsolutePath();
		mmr.setDataSource(filePath);

		String albumName =
			mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		title=albumName;
	}
}
