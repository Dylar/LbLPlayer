package de.lbl.LbLPlayer.system;

public class SettingAndState
{

	public static int currentTime;
	public static int songDuration;
	
	public enum PlaySequence{
		ALL,SELECTED,SINGLE;
	}
	

	public static int currentSong;
	public static boolean isPlaying = false;

	public static boolean isRandom;
	public static PlaySequence playSeq;
	
	static {
		// TODO loading settings
		playSeq = PlaySequence.ALL;
		currentSong = 0;
	}
	
}
