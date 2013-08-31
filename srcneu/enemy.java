import java.util.*;




/**
 * Klasse für Gegner 
 *
 */
public class enemy{
	
	/**
	 * 
	 * Verschiedene Gegnertypen
	 */
	enum typen{
		STANDARD,FEUER,EIS,ENDGEGNER;
		
		/**
		 * Parst den gegner typ aus der level datei
		 * @param i
		 * @return einen gegenertyp (1->standard),(2->feuer),(3->eis),(4->endgegner)
		 */
		public static typen parseTypen(int i){
			switch(i){
			case 1:
				return(STANDARD);
			case 2:
				return(FEUER);
			case 3:
				return(EIS);
			case 4:
				return(ENDGEGNER);
			}
			return(STANDARD);
		}
	}
	
	
	private typen typ = typen.STANDARD;  //Typ des Gegners (Feuer, Eis, Gift, Endboss ...)
	/**
	 * Gegner HP
	 */
	public int    hp = -1;  
	
	/**Maximale HP*/
	private int hpMax = 100; 
	
	/**Gegner Mana maximal*/
	private int mpMax = 20;  
	
	/**Leben des Gegners*/
	public int    lifes = -1; 
	
	/**Maximale Leben des Gegners*/
	private int lifesMax = 1; 
	
	/**Rüstung des Gegners*/
	public int armorWert = -1;  
	
	/** Schaden des Gegners*/
	public int damageWert = -1; 
	
	/** Raum des Gegners */
	private int room = -1;  
	
	/**Weg des Gegners*/
	private LinkedList<vec> way = null;
	
	/**Position des Gegners*/
	private vec pos = null;
	
	/**Zufallsgenerator*/
	private Random Zufall = new Random();
	
	/**
	 * Erstellt einen neuen Gegner des Angegeben Typs
	 * @param typ
	 */
	public enemy(typen typ){
		init();
		this.typ = typ;
	}
	
	/**
	 * Erstellt einen neuen Gegner des Typs Standard
	 */
	public enemy(){
	  init();
	  this.typ = typen.STANDARD;
	}
	
	/**
	 * initialisiert einen neuen Gegner
	 */
	private void init(){
	  this.way = new LinkedList<vec>();
	  this.hp = this.hpMax;
	  this.lifes = this.lifesMax;
	  this.armorWert = 0;
	  this.damageWert = 10;
	  if(this.typ.equals(typen.ENDGEGNER)){
		  this.armorWert = 1;
	  }
	}
	
	/**
	 * zieht ein leben ab
	 */
	public void die(){
	  this.lifes--;
	}
	
	/**
	 * Bewegt den Gegner
	 */
	public void move(){
	  vec v = null;
	
	  if(Zufall.nextFloat()>0.9f)
	  {
		  v = getNextPos();
		  this.pos = v;
	  }
	}
	
	/** fügt einen wegpunkt hinzu */
	public void addWayPoint(vec v){ this.way.add(v); }
	
	
	/**
	 * 
	 * @return position des Gegners
	 */
	public vec getPos(){ return this.pos; }
	/**
	 * 
	 * @return Raum in dem der Gegner ist
	 */
	public int getRoom(){ return this.room; }
	/**
	 * 
	 * @return den Typ des Gegners
	 */
	public typen getTyp(){ return this.typ; }
	
	/**
	 * 
	 * @return Weg des Gegners
	 */
	public LinkedList<vec> getWay(){ return this.way; }
	
	/** nächste Position des Gegners */
	public vec getNextPos(){
	  for(int i=0; i<this.way.size(); i++){
	    if(this.way.get(i).compare(this.pos) && i<this.way.size()-1)
	    {
	    	return this.way.get(i+1); 
	    }
		else if(this.way.get(i).compare(this.pos) && i>=this.way.size()-1)
		{
			return this.way.get(0);
		}
	  }
	  return null;
	}
	
	//--- SET ---//
	/**
	 * 
	 * @param v Position des Gegners
	 */
	public void setPos(vec v){ this.pos = v; }
	/**
	 * 
	 * @param x Raum in dem der Gegner sein soll
	 */
	public void setRoom(int x){ this.room = x; }
	/**
	 * 
	 * @param x Typ des Gegners
	 */
	public void setTyp(typen x){ this.typ = x; }
	
	/**
	 * erstellt einen neuen Pfad
	 */
	public void createPath(){
	  LinkedList<vec> out = new LinkedList<vec>();
	  vec v = null;
	  vec v2 = null;
	  //Start holen
	  v = this.way.get(0);
	  for(int i=0; i<this.way.size()+1; i++){
	    if(i==0){ continue; }
		//Nächsten Punkt holen
		if(i==this.way.size()){ v2 = this.way.get(0); }
	    else{ v2 = this.way.get(i); }
		//v.print();
		//v2.print();
		//System.out.println("#" + i);
		//Prüfen was übereinstimmt X,Y
		if(v.getX()==v2.getX()){
		  //Y iterieren
		  if(v.getY()<v2.getY()){
		    //++
			for(int j=v.getY(); j<v2.getY(); j++){
				out.add(new vec(v.getX(),j,v.getZ()));
			}
		  }
		  else{
		    //--
			for(int j=v2.getY(); j<v.getY(); j++){
			    //System.out.println("y--:" + j + "#" + ((v.getY()-j)+1));
				out.add(new vec(v.getX(),((v.getY()-j)+1),v.getZ()));
			}
		  }
		}
		else if(v.getY()==v2.getY()){
		  //X iterieren
		  if(v.getX()<v2.getX()){
		    //++
			for(int j=v.getX(); j<v2.getX(); j++){
				out.add(new vec(j,v.getY(),v.getZ()));
			}
		  }
		  else{
		    //--
			for(int j=v2.getX(); j<v.getX(); j++){
			    //System.out.println("x--:" + j + "#" + ((v.getX()-j)+1));
				out.add(new vec(((v.getX()-j)+1),v.getY(),v.getZ()));
			}
		  }
		}
		v = v2;
	  }
	  this.way = out;
	}
	
	/**
	 * Zum Debuggen, gibt den Weg des Gegners aus
	 */
	public void printWay(){
	  for(int i=0; i<this.way.size(); i++){
	    this.way.get(i).print();
	  }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}