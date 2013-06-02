import java.io.*;
import java.util.*;

public class lvl{
  
  public LinkedList<field> rooms = null;  //Einzelnen Räume
  
  public LinkedList<link> links = null;  //Verbindungen der Räume
  
  public vec start = null;  //Start
  public vec  goal = null;  //Ziel
  
  public vec player = null;  //Spieler-Position
  
  //Konstruktor
  public lvl(){
    init();
  }
  
  private void init(){
    //Listen erstellen
	this.rooms = new LinkedList<field>();
	this.links = new LinkedList<link>();
  }
  
  //--- ADD ---//
  
  //addF()
  public void addF(String path){
    field tmp = null;
	//Feld laden
	tmp = new field(15,15);
	tmp.load(path);
	//Feld adden
	this.rooms.add(tmp);
  }
  
  //addC()
  public void addC(vec in, vec out, int move){
    link tmp = null;
	//Link erstellen
	tmp = new link(in,out,move);
	//Link adden
	this.links.add(tmp);
  }
  
  public void addC(String s){
    vec[] v = new vec[2];
	String[] tmp = null;
	String[] data = null;
	link out = null;
	//Teil splitten
	tmp = s.split("#");
	//Parsen
	for(int i=0; i<tmp.length-1; i++){
	  //Daten splitten
	  data = tmp[i].split(",");
	  //Daten verarbeiten
	  v[i] = new vec(Integer.parseInt(data[1]),Integer.parseInt(data[2]),Integer.parseInt(data[0]));
	}
	//Link adden
	out = new link(v[0],v[1],Integer.parseInt(tmp[tmp.length-1]));
	this.links.add(out);
  }
  
  //--- GET ---//
  
  //getPlayer()
  public vec getPlayer(){ return this.player; }
  
  //getStart()
  public vec getStart(){ return this.start; }
  
  //getGoal()
  public vec getGoal(){ return this.goal; }
  
  //getF()
  public field getF(int x){
    //Prüfen
    if(x>=0 && x<this.rooms.size()){ return this.rooms.get(x); }
	else{ return null; }
  }
  
  //getL()
  public link getL(int x){
    //Prüfen
    if(x>=0 && x<this.links.size()){ return this.links.get(x); }
	else{ return null; }
  }
  
  //--- SET ---//
  
  //setPlayer()
  public void setPlayer(int x, int y, int index){
    this.player = new vec(x,y,index);
  }
  
  public void setPlayer(vec v){
    this.player = v;
  }
  
  //setStart()
  public void setStart(int x, int y, int index){
    this.start = new vec(x,y,index);
  }
  
  public void setStart(vec v){
    this.start = v;
  }
  
  //setGoal()
  public void setGoal(int x, int y, int index){
    this.goal = new vec(x,y,index);
  }
  
  public void setGoal(vec v){
    this.goal = v;
  }
  
  //--- MOVE ---//
  
  //move()
  public void move(String s){
    vec tmp = null;
	
	//Übergang holen
	tmp = checkConnection(this.player,s);
	
	//In anderen Raum springen
	if(tmp!=null){
	  this.player = tmp;
	}
	else{
    //Zeichen prüfen
	if(s.equals("w")){
	  //Freiraum prüfen
	  if(checkUP()==true){
	    //Move
		this.player.y--;
		checkmonsteru();
			  }
	}
	else if(s.equals("a")){
	  //Freiraum prüfen
	  if(checkLEFT()==true){
	    //Move
		this.player.x--;
		checkmonsterl();
	  }
	}
	else if(s.equals("s")){
	  //Freiraum prüfen
	  if(checkDOWN()==true){
	    //Move
		this.player.y++;
		checkmonsterd();
		checkziel();
			  }
	}
	else if(s.equals("d")){
	  //Freiraum prüfen
	  if(checkRIGHT()==true){
	    //Move
		this.player.x++;
		checkmonsterr();
		  }
	}
	else{ /*leer*/ }
	}
	
	//Prüfen auf Ziel
	if(this.player.compare(this.goal)){
	  
	  }
	
  }
  
  public void move(char c){ move(String.valueOf(c)); }
  
  private boolean checkUP(){
    if(this.player.y>0){
      //Auf freie Felder prüfen
      if(getF(this.player.z).f[this.player.x][this.player.y-1].equals("f") || getF(this.player.z).f[this.player.x][this.player.y-1].equals("E") || getF(this.player.z).f[this.player.x][this.player.y-1].equals("m")){ return true; }
	  else{ return false; }
	}
	else{ return false; }
  }
  
  private boolean checkDOWN(){
    if(this.player.y<getF(this.player.z).getH()-1){
      //Auf freie Felder prüfen
      if(getF(this.player.z).f[this.player.x][this.player.y+1].equals("f") || getF(this.player.z).f[this.player.x][this.player.y+1].equals("E") || getF(this.player.z).f[this.player.x][this.player.y+1].equals("m") || getF(this.player.z).f[this.player.x][this.player.y+1].equals("z")){ return true; }
	  else{ return false; }
	}
	else{ return false; }
  }
  
  private boolean checkLEFT(){
    if(this.player.x>0){
      //Auf freie Felder prüfen
      if(getF(this.player.z).f[this.player.x-1][this.player.y].equals("f") || getF(this.player.z).f[this.player.x-1][this.player.y].equals("E") || getF(this.player.z).f[this.player.x-1][this.player.y].equals("m")){ return true; }
	  else{ return false; }
	}
	else{ return false; }
  }
  
  private boolean checkRIGHT(){
    if(this.player.x<getF(this.player.z).getW()-1){
      //Auf freie Felder prüfen
    	if(getF(this.player.z).f[this.player.x+1][this.player.y].equals("f") || getF(this.player.z).f[this.player.x+1][this.player.y].equals("E") || getF(this.player.z).f[this.player.x+1][this.player.y].equals("m")){ return true; }
	  else{ return false; }
       
	}
	else{ return false; }
  }
  
  private void checkmonsterr(){
	    if(this.player.x>0){
	    	if(getF(this.player.z).f[this.player.x][this.player.y].equals("m")){ J2D.monster(); }}}
  
  
  private void checkmonsterl(){
	    if(this.player.x<getF(this.player.z).getW()-1){
	    	if(getF(this.player.z).f[this.player.x][this.player.y].equals("m")){ J2D.monster(); }}}
  
  private void checkmonsteru(){
	    if(this.player.x<getF(this.player.z).getW()-1){
	    	if(getF(this.player.z).f[this.player.x][this.player.y].equals("m")){ J2D.monster();
	    	}}}

  private void checkmonsterd(){
	    if(this.player.x<getF(this.player.z).getW()-1){
	    	if(getF(this.player.z).f[this.player.x][this.player.y].equals("m")){ J2D.monster(); }}}

  private void checkziel(){
	    if(this.player.x<getF(this.player.z).getW()-1){
	    	if(getF(this.player.z).f[this.player.x][this.player.y].equals("z")){ J2D.monster();
	    	}}}
  
  
  //checkConnection()
  private vec checkConnection(vec v, int move){
    //Suchen
    for(int i=0; i<this.links.size(); i++){
	  if(getL(i).getIn().compare(v)==true && getL(i).getMove()==move){ return getL(i).getOut(); }
	}
	return null;
  }
  
  private vec checkConnection(vec v, String s){
    int x = -1;
	//Umwandlung
	if(s.equals("w")){ x = 0; }
	else if(s.equals("a")){ x = 1; }
	else if(s.equals("s")){ x = 2; }
	else if(s.equals("d")){ x = 3; }
	//Suchen
	return checkConnection(v,x);
  }
  
  //--- PRINT ---//
  
  //printF()
  public void printF(int x){
    //Prüfen
    if(x>=0 && x<this.rooms.size()){
	  //Ausgabe
	  this.rooms.get(x).print();
	}
  }
  
  public void printF(){
    for(int i=0; i<this.rooms.size(); i++){
	  printF(i);
	  System.out.println();
	}
  }
  
  //printC()
  public void printC(int x){
    //Prüfen
    if(x>=0 && x<this.links.size()){
	  //Ausgabe
	  this.links.get(x).print();
	}
  }
  
  public void printC(){
    for(int i=0; i<this.links.size(); i++){
	  printC(i);
	  System.out.println();
	}
  }
  
  //printStart()
  public void printStart(){ this.start.print(); System.out.println(); }
  
  //printGoal()
  public void printGoal(){ this.goal.print(); System.out.println(); }
  
  //load()
  public void load(String path){
    String in = "";
	String tmp = "";
	String[] lines = null;
	RandomAccessFile f = null;
	try{
      //File öffnen
      f = new RandomAccessFile(path,"r");
	  //Alles einlesen
	  while( (tmp=f.readLine()) != null){
	    in += tmp + ";";
	  }
	  //Splitten
	  lines = in.split(";");
	  //Parsen
	  parse(lines);
	  //Datei schließen
	  f.close();
	}catch(Exception e){ e.printStackTrace(); }
  }
  
  private void parse(String[] lines){
    int mode = -1;
	String[] tmp = null;
    //Zeilen durchgehen
    for(int i=0; i<lines.length; i++){
	  //ENDE prüfen
	  if(lines[i].equals("#")){ mode = -1; }
	  
	  //Modus prüfen
	  if(mode==0){
	    //Maps laden
		addF(lines[i]);
	  }
	  else if(mode==1){
	    tmp = lines[i].split(",");
	    //Start setzen
		setStart(Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]),Integer.parseInt(tmp[0]));
		//Spieler an Startsetzen
		this.player = new vec(Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]),Integer.parseInt(tmp[0]));
	  }
	  else if(mode==2){
	    tmp = lines[i].split(",");
	    //Goal setzen
		setGoal(Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]),Integer.parseInt(tmp[0]));
	  }
	  else if(mode==3){
	    //Connections laden
		addC(lines[i]);
	  }
	  else if(mode==-1){
	    //Tags prüfen
		if(lines[i].equals("maps:")){ mode = 0; }
		else if(lines[i].equals("start:")){ mode = 1; }
		else if(lines[i].equals("goal:")){ mode = 2; }
		else if(lines[i].equals("connections:")){ mode = 3; }
		else if(lines[i].equals("#")){ mode = -1; }
	  }
	}
  }
  
}