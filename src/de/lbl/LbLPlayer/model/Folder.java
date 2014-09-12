package de.lbl.LbLPlayer.model;

import java.io.*;
import java.util.*;

public class Folder extends Entry
{
	public List<Entry> entrys;
	
	public Folder(File f){
		super(f.getName());
		entrys = new ArrayList<>();
	}
	
	public void sortEntrys(){
		boolean songBefor = false;
		for(int i = 1; i < entrys.size(); i++){
			Entry e = entrys.get(i);
			if(e instanceof Folder && songBefor){
				songBefor = false;
				entrys.remove(e);
				entrys.add(1,e);
			}
			else
				songBefor = true;
		}
	}
}
