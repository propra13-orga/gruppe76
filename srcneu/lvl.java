import java.io.RandomAccessFile;
import java.util.LinkedList;

public class lvl{
  
  private LinkedList<field> rooms = null;  //Einzelnen Räume
  
  private LinkedList<link> links = null;  //Verbindungen der Räume
  
  private LinkedList<enemy> enemys = null;  //Alle Gegner, die das Lvl hat
  
  private LinkedList<vec> savepoints = null;  //Savepoints des Lvl
  
  /**
   * Items die im Level rumliegen
   */
  public LinkedList<item> items = null; 
  
  private vec start = null;  //Start
  private vec  goal = null;  //Ziel
  
  private player player = null;  //Spieler
  
  private npc_shop shop = null; // Shop
  
  public item_db itemdb = null;  //Item-Datenbank
  
  /**
   * Konstruktor
   */
  public lvl(){
    init();
  }
  
  /**
   * Initialisieren
   */
  private void init(){
    //Listen erstellen
	this.rooms = new LinkedList<field>();
	this.links = new LinkedList<link>();
	this.enemys = new LinkedList<enemy>();
	this.savepoints = new LinkedList<vec>();
	this.items = new LinkedList<item>();
	//Player stellen
	this.player = new player();
	// Item-DB laden
	this.itemdb = new item_db();
	this.itemdb.loadDB("items.txt");
	// Shop erstellen
	this.shop = new npc_shop();
	
	item i = null;
	i = new item(0,1);
	this.shop.addItem(i);
	i = new item(1,1);
	this.shop.addItem(i);
	this.shop.setPos( new vec(64*1,48*3,2));	// Plaziert in Raum 2, dem Startraum
  }
  
  
  /**
   * Feld hinzufügen
   * @param path Pfad zu der Datei die das Feld enthält Feld
   */
  public void addF(String path){
    field tmp = null;
	//Feld laden
	tmp = new field(15,15);
	tmp.load(path);
	//Feld adden
	this.rooms.add(tmp);
  }
  
  /**
   * Verbindung zwischen zwei feldern hinzufügen
   * @param in eingang
   * @param out ausgang
   * @param move bewegunsrichtung
   */
  public void addC(vec in, vec out, int move){
    link tmp = null;
	//Link erstellen
	tmp = new link(in,out,move);
	//Link adden
	this.links.add(tmp);
  }
  
  /**
   * Verbindung hinzufügen
   * @param s String aus der Feld-Datei für die Verbindung
   */
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
  
  /** 
   * Gegner hinzufügne
   * @param s String aus der Feld-Datei für einen Gegner
   */
  public void addE(String s){
	String[] tmp = null;
	String[] data = null;
	enemy out = new enemy();
	//Teil splitten
	tmp = s.split("#");
	//Room, Typ adden
	out.setRoom(Integer.parseInt(tmp[0]));
	out.setTyp(enemy.typen.parseTypen(Integer.parseInt(tmp[1])));
	//Parsen
	for(int i=2; i<tmp.length; i++){
	  //Daten splitten
	  data = tmp[i].split(",");
	  //Daten verarbeiten
	  out.addWayPoint(new vec(Integer.parseInt(data[0]),Integer.parseInt(data[1]),out.getRoom()));
	  if(i==2){ out.setPos(out.getWay().get(0)); }
	}
	//Path erstellen
	out.createPath();
	//out.printWay();
	//Enemy adden
	this.enemys.add(out);
  }
  
  /**
   * Savepoint hiznufügen
   * @param s String aus der Feld-Datei
   */
  public void addSP(String s){
    vec v = null;
	String[] tmp = null;
	//Teil splitten
	tmp = s.split(",");
	//Room,X-Coord,Y-Coord
	//Daten verarbeiten
	v = new vec(Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]),Integer.parseInt(tmp[0]));
	//Savepoints adden
	this.savepoints.add(v);
  }
  
  /**
   * Item zum Spieler hinzufügen
   * @param s String aus der Feld-Datei
   */
  public void addPSI(String s){
	String[] tmp = null;
	//Teil splitten
	tmp = s.split("#");
	//Items erstellen
	item i = null;
	i = new item(Integer.parseInt(tmp[0]),Integer.parseInt(tmp[1]));
	//Items adden
	this.player.addItem(i);
  }
  
  /**
   * Item hinzufügen
   * @param s String aus der Feld-datei
   */
  public void addItems(String s){
	String[] tmp = null;
	//Teil splitten
	tmp = s.split("#");
	//Items erstellen
	item i = null;
	i = new item(Integer.parseInt(tmp[0]),1);
	vec v0 = new vec(Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]),Integer.parseInt(tmp[3]));
	i.v = v0;
	//Items adden
	this.items.add(i);
  }
  
  
  //getPlayer()
  public player getPlayer(){ return this.player; }
  
  //getShop()
  public npc_shop getShop(){ return this.shop; }
  
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
  
  /**
   * 
   * @return Savepoints
   */
  public LinkedList<vec> getS(){ return this.savepoints; }
  
  /**
   * 
   * @return Gegner
   */
  public LinkedList<enemy> getE(){ return this.enemys; }
  
  
  //setPlayer()
  public void setPlayer(int x, int y, int index){
    this.player.setPos(new vec(x,y,index));
  }
  
  public void setPlayer(vec v){
    this.player.setPos(v);
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
  
  /**
   * Spieler bewegen, auf wände, übergänge, itesm prüfen
   * @param s "w" "a" "s" oder "d"
   */
  public void move(String s){
    vec tmp = null;
	
	//Übergang holen
	tmp = checkConnection(this.player.getPos(),s);
	
	//In anderen Raum springen
	if(tmp!=null){
	  //Richtung prüfen
	  if(this.player.getPos().getX()==0){ this.player.see = 1; }
	  else if(this.player.getPos().getY()==0){ this.player.see = 0; }
	  else if(this.player.getPos().getX()==14){ this.player.see = 3; }
	  else if(this.player.getPos().getY()==14){ this.player.see = 2; }
	  //Neue Position setzen
	  this.player.setPos(tmp);
	  return;
	}
	else{
    //Zeichen prüfen
	if(s.equals("w")){
	  //Freiraum prüfen
	  if(checkUP()==true){
	    //Move
		vec v = null;
		v = this.player.getPos();
		v.setY(v.getY()-1);
		this.player.setPos(v);
		this.player.see = 0;
	  }
	}
	else if(s.equals("a")){
	  //Freiraum prüfen
	  if(checkLEFT()==true){
	    //Move
		vec v = null;
		v = this.player.getPos();
		v.setX(v.getX()-1);
		this.player.setPos(v);
		this.player.see = 1;
	  }
	}
	else if(s.equals("s")){
	  //Freiraum prüfen
	  if(checkDOWN()==true){
	    //Move
		vec v = null;
		v = this.player.getPos();
		v.setY(v.getY()+1);
		this.player.setPos(v);
		this.player.see = 2;
	  }
	}
	else if(s.equals("d")){
	  //Freiraum prüfen
	  if(checkRIGHT()==true){
	    //Move
		vec v = null;
		v = this.player.getPos();
		v.setX(v.getX()+1);
		this.player.setPos(v);
		this.player.see = 3;
	  }
	}
	else{ /*leer*/ }
	}
	
	//Prüfen auf Items
	for(int i=0; i<this.items.size(); i++){
	  if(this.player.getPos().compare(this.items.get(i).v)){
		//Item benutzen
		item_dbnode node = this.itemdb.getItem(this.items.get(i).itemIndex);
		this.player.scriptE.executescript(node.getEffect());
		//System.out.println("Item gefunden!");
		this.items.remove(i);
		break;
	  }
	}
	
	//Prüfen auf Savepoint
	for(int i=0; i<this.savepoints.size(); i++){
	  if(this.player.getPos().compare(this.savepoints.get(i))){
		this.player.setSavePos(this.player.getPos());
		//System.out.println("Savepoint erreicht!");
		break;
	  }
	}
	
	//Prüfen auf Gegner
	for(int i=0; i<this.enemys.size(); i++){
	  if(this.player.getPos().compare(this.enemys.get(i).getPos())){
	    this.player.fight(this.enemys.get(i));
	  }
	  if(this.player.getPos().distance(this.enemys.get(i).getPos()) < 3.0f ){
		  this.player.magic(this.enemys.get(i));
		//Toten Enemy löschen
		if(this.enemys.get(i).lifes<=0){ this.enemys.remove(i); }
		//GAME OVER prüfen
		if(this.player.getLifes()<=0){
		  System.out.println("\n!!! GAME OVER !!!");
		  dungeon.terminate();
		}
		//System.out.println("Sie wurden besiegt!");
		break;
	  }
	}
	
	//Prüfen auf Ziel
	if(this.player.getPos().compare(this.goal)){
	  System.out.println("\n!!! ZIEL ERREICHT !!!");
	  dungeon.levelup();
	}
	
  }
  
  /**
   * Spieler bewegen, auf wände, übergänge, itesm prüfen
   * @param c 'w' 'a' 's' oder 'd'
   */
  public void move(char c){ move(String.valueOf(c)); }
  
  boolean checkUP(){
    if(this.player.getPos().getY()>0){
      //Auf freie Felder prüfen
      if(getF(this.player.getPos().getZ()).getF()[this.player.getPos().getX()][this.player.getPos().getY()-1].equals("f") || getF(this.player.getPos().getZ()).getF()[this.player.getPos().getX()][this.player.getPos().getY()-1].equals("E")){ return true; }
	  else{ return false; }
	}
	else{ return false; }
  }
  
  boolean checkDOWN(){
    if(this.player.getPos().getY()<getF(this.player.getPos().getZ()).getH()-1){
      //Auf freie Felder prüfen
      if(getF(this.player.getPos().getZ()).getF()[this.player.getPos().getX()][this.player.getPos().getY()+1].equals("f") || getF(this.player.getPos().getZ()).getF()[this.player.getPos().getX()][this.player.getPos().getY()+1].equals("E")){ return true; }
	  else{ return false; }
	}
	else{ return false; }
  }
  
  boolean checkLEFT(){
    if(this.player.getPos().getX()>0){
      //Auf freie Felder prüfen
      if(getF(this.player.getPos().getZ()).getF()[this.player.getPos().getX()-1][this.player.getPos().getY()].equals("f") || getF(this.player.getPos().getZ()).getF()[this.player.getPos().getX()-1][this.player.getPos().getY()].equals("E")){ return true; }
	  else{ return false; }
	}
	else{ return false; }
  }
  
  boolean checkRIGHT(){
    if(this.player.getPos().getX()<getF(this.player.getPos().getZ()).getW()-1){
      //Auf freie Felder prüfen
      if(getF(this.player.getPos().getZ()).getF()[this.player.getPos().getX()+1][this.player.getPos().getY()].equals("f") || getF(this.player.getPos().getZ()).getF()[this.player.getPos().getX()+1][this.player.getPos().getY()].equals("E")){ return true; }
	  else{ return false; }
	}
	else{ return false; }
  }
  
  private vec checkConnection(vec v, int move){
    vec v0 = null;
	vec v1 = null;
	link l = null;
    //Suchen
    for(int i=0; i<this.links.size(); i++){
	  l = null;
	  l = getL(i);
	  v0 = null;
	  v0 = l.getIn();
	  if(v0.compare(v)==true && l.getMove()==move){
	    v1 = l.getOut();
		return v1.copy();
	  }
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
  void printF(int x){
    //Prüfen
    if(x>=0 && x<this.rooms.size()){
	  //Ausgabe
	  this.rooms.get(x).print();
	}
  }
  
  void printF(){
    for(int i=0; i<this.rooms.size(); i++){
	  printF(i);
	  System.out.println();
	}
  }
  
  //printC()
  void printC(int x){
    //Prüfen
    if(x>=0 && x<this.links.size()){
	  //Ausgabe
	  this.links.get(x).print();
	}
  }
  
  void printC(){
    for(int i=0; i<this.links.size(); i++){
	  printC(i);
	  System.out.println();
	}
  }
  
  //printStart()
  void printStart(){ this.start.print(); System.out.println(); }
  
  //printGoal()
  void printGoal(){ this.goal.print(); System.out.println(); }
  
  /**
   * Level laden
   * @param path Pfadangabe zu Lvl Datei
   */
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
		this.player.setPos(new vec(Integer.parseInt(tmp[1]),Integer.parseInt(tmp[2]),Integer.parseInt(tmp[0])));
		this.player.setSavePos(this.player.getPos());
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
	  else if(mode==4){
	    //Enemys laden
		addE(lines[i]);
	  }
	  else if(mode==5){
	    //Savepoints laden
		addSP(lines[i]);
	  }
	  else if(mode==6){
	    //PlayerStartItems laden
		addPSI(lines[i]);
	  }
	  else if(mode==7){
	    //Items(die rumliegen) laden
		addItems(lines[i]);
	  }
	  else if(mode==-1){
	    //Tags prüfen
		if(lines[i].equals("maps:")){ mode = 0; }
		else if(lines[i].equals("start:")){ mode = 1; }
		else if(lines[i].equals("goal:")){ mode = 2; }
		else if(lines[i].equals("connections:")){ mode = 3; }
		else if(lines[i].equals("enemys:")){ mode = 4; }
		else if(lines[i].equals("savepoints:")){ mode = 5; }
		else if(lines[i].equals("PlayerStartItems:")){ mode = 6; }
		else if(lines[i].equals("items:")){ mode = 7; }
		else if(lines[i].equals("#")){ mode = -1; }
	  }
	}
  }
  
}