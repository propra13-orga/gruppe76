import java.io.*;
import java.util.*;

public class field{
  
  private int sizeX = -1;  //Breite des Feldes
  private int sizeY = -1;  //H�he des Feldes
  
  private String[][] f = null;  //Spielfeld
  
  /**
   * Konstruktor
   * @param x Breite
   * @param y H�he
   */
  public field(int x, int y){
    this.sizeX = x;
    this.sizeY = y;
	rebuild();
  }
  
  
  /**
   * 
   * @return Breite
   */
  public int getW(){ return this.sizeX; }
  
  /**
   * 
   * @return H�he
   */
  public int getH(){ return this.sizeY; }
  
  /**
   * 
   * @return String repr�sentation (so wie in lvl datei)
   */
  public String[][] getF(){ return this.f; }
  
  /**
   * 
   * @param s suchstring
   * @return erste position an der der string gefunden wurde
   */
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
  
  /**
   * Breite setzen
   * @param x Breite
   */
  public void setW(int x){
    this.sizeX = x;
	rebuild();
  }
  
  /**
   * H�he setzen
   * @param y H�he
   */
  public void setH(int y){
    this.sizeY = y;
	rebuild();
  }
  
  /**
   * Feld erneuern
   */
  private void rebuild(){
    String[][] tmp = null;
	tmp = new String[this.sizeX][this.sizeY];
	this.f = tmp;
  }
  
  /**
   * Feld laden
   * @param path Pfad zur Datei mit dem Raum
   */
  public void load(String path){
    String in = "";
	String tmp = "";
	RandomAccessFile f = null;
	try{
      //File �ffnen
      f = new RandomAccessFile(path,"r");
	  //Alles einlesen
	  while( (tmp=f.readLine()) != null){ in += tmp + ";"; }
	  //Feld f�llen
	  fill(in);
	  //Datei schlie�en
	  f.close();
	}catch(Exception e){ e.printStackTrace(); }
  }
  
  /**
   * Feld f�llen
   * @param in Inhalt der Datei
   */
  private void fill(String in){
    String[] lines = null;
    String[] tmp = null;
	//Zeilen splitten
	lines = in.split(";");
	//Feld f�llen
    for(int y=0; y<lines.length; y++){
	  //Werte splitten
	  tmp = lines[y].split(",");
	  for(int x=0; x<tmp.length; x++){
	    this.f[x][y] = tmp[x];
	  }
	}
  }
  
  /**
   * Feld als Strng ausgeben
   * @param f 
   */
  public void print(String[][] f){
    //Ausgabe
	for(int y=0; y<f[0].length; y++){
	  for(int x=0; x<f.length; x++){
	    System.out.print(f[x][y]);
	  }
	  System.out.println();
	}
  }
  
  /**
   * Dieses Feld auf der console ausgeben
   */
  public void print(){ print(this.f); }
  
}