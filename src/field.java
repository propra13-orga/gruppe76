import java.io.*;
import java.util.*;

public class field{
  
  private int sizeX = -1;  //Breite des Feldes
  private int sizeY = -1;  //Höhe des Feldes
  
  private String[][] f = null;  //Spielfeld
  
  //Konstruktor
  public field(int x, int y){
    this.sizeX = x;
    this.sizeY = y;
	rebuild();
  }
  
  //--- GET ---//
  
  //getW()
  public int getW(){ return this.sizeX; }
  
  //getH()
  public int getH(){ return this.sizeY; }
  
  //getF()
  public String[][] getF(){ return this.f; }
  
  //search()
  public vec search(String s){
    vec out = null;
	//Ziel suchen
	for(int y = 0; y < f[0].length; y++){
		for(int x = 0; x < f.length; x++){
			if(f[x][y].equals(s)){
				out = new vec(x,y);
				return out;
			}
		}
	}
	return null;
  }
  
  //--- SET ---//
  
  //setW()
  public void setW(int x){
    this.sizeX = x;
	rebuild();
  }
  
  //setH()
  public void setH(int y){
    this.sizeY = y;
	rebuild();
  }
  
  //rebuild() Feld erneuern
  private void rebuild(){
    String[][] tmp = null;
	tmp = new String[this.sizeX][this.sizeY];
	this.f = tmp;
  }
  
  //load()
  public void load(String path){
    String in = "";
	String tmp = "";
	RandomAccessFile f = null;
	try{
      //File öffnen
      f = new RandomAccessFile(path,"r");
	  //Alles einlesen
	  while( (tmp=f.readLine()) != null){ in += tmp + ";"; }
	  //Feld füllen
	  fill(in);
	  //Datei schließen
	  f.close();
	}catch(Exception e){ e.printStackTrace(); }
  }
  
  //fill()
  private void fill(String in){
    String[] lines = null;
    String[] tmp = null;
	//Zeilen splitten
	lines = in.split(";");
	//Feld füllen
    for(int y=0; y<lines.length; y++){
	  //Werte splitten
	  tmp = lines[y].split(",");
	  for(int x=0; x<tmp.length; x++){
	    this.f[x][y] = tmp[x];
	  }
	}
  }
  
  //print()
  public void print(String[][] f){
    //Ausgabe
	for(int y=0; y<f[0].length; y++){
	  for(int x=0; x<f.length; x++){
	    System.out.print(f[x][y]);
	  }
	  System.out.println();
	}
  }
  
  public void print(){ print(this.f); }
  
}