package de.lbl.LbLPlayer.model;
import android.*;
import android.media.*;
import android.net.*;
import android.util.*;
import de.lbl.LbLPlayer.system.*;
import java.io.*;

public class Song
{
	public String title = "muh";
	public Uri uri;
	public int id = 42;
	private File file;
	public String duration = "0:42";

	private String TAG;

    public Song(File f, int id)
	{
		//Log.wtf(TAG, "" + id);
		file = f;
		uri = Uri.fromFile(f);
		this.id = id;
		setTitle(f.getName());
		initMediaDuration();
		//  setInfo(f);
	}

//	public Song(int id){
//		this.id = id;
//		uri = Uri.parse("android.resource://" + SystemController.act.getPackageName() + "/" + R.raw.rain);
//		title = "Testi McTest - Bass Test";
//		
//	}

	public void setTitle(String name)
	{
		char[] ca1 = name.toCharArray();
		char[] ca2 = new char[ca1.length - 4];
		for (int i = 0;i < ca2.length;i++)
		{
			ca2[i] = ca1[i];
		}
		title = String.valueOf(ca2);
	} 

	public void setInfo(File f)
	{
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		String filePath = f.getAbsolutePath();
		mmr.setDataSource(filePath);

		String albumName =
			mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		title = albumName;
	}

	protected void initMediaDuration()
	{ 
        if (uri == null)
		{
            throw new IllegalArgumentException("Uri may not be null.");
        } 

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int duration = 0;
        try
		{ 
            retriever.setDataSource(SystemController.act, uri);
            String dur = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            if (dur != null)
			{
				int milliseconds = Integer.parseInt(dur);
				int seconds = (int) (milliseconds / 1000) % 60 ;
				int minutes = (int) ((milliseconds / (1000*60)) % 60);
				int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
				dur = "" + minutes + ":" + seconds;
            } 
            this.duration = dur;
        }
		catch (Exception ex)
		{}
		finally
		{ 
            retriever.release();
        } 
}
	}
