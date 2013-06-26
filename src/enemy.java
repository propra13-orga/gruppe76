import java.util.*;

public class enemy{
	
	private int typ = -1;  //Typ des Gegners (Feuer, Eis, Gift, Endboss ...)
	
	private int    hp = -1;  //HP des Gegners
	private int hpMax = 100;  //Max-HP des Gegners
	
	private int    mp = -1;  //MP des Gegners
	private int mpMax = 20;  //Max-MP des Gegners
	
	private int    lifes = -1;  //Leben des Gegners
	private int lifesMax = 1;  //Max-Leben des Gegners
	
	private int armorWert = -1;  //Rüstungswert
	private int damageWert = -1;  //Waffenschaden
	
	private int room = -1;  //Raum des Gegners
	
	private LinkedList<vec> way = null;  //Weg des Gegners
	
	private vec pos = null;  //Aktuelle Position
	
	//Konstruktor
	public enemy(){
	  init();
	}
	
	//init()
	private void init(){
	  this.way = new LinkedList<vec>();
	  this.hp = this.hpMax;
	  this.mp = this.mpMax;
	  this.lifes = this.lifesMax;
	  this.armorWert = 0;
	  this.damageWert = 10;
	}
	
	public void disappear()
	{vec v = null;
	v = this.getPos();
	v.setY(v.getY()+10);
	}
	
	//move()
	public void move(){
	  vec v = null;
	  v = getNextPos();
	  this.pos = v;
	}
	
	public void addWayPoint(vec v){ this.way.add(v); }
	
	//--- GET ---//
	
	public vec getPos(){ return this.pos; }
	public int getRoom(){ return this.room; }
	public int getTyp(){ return this.typ; }
	
	public LinkedList<vec> getWay(){ return this.way; }
	
	//getNextPos()
	public vec getNextPos(){
	  for(int i=0; i<this.way.size(); i++){
	    if(this.way.get(i).compare(this.pos) && i<this.way.size()-1){ return this.way.get(i+1); }
		else if(this.way.get(i).compare(this.pos) && i>=this.way.size()-1){ return this.way.get(0); }
	  }
	  return null;
	}
	
	//--- SET ---//
	
	public void setPos(vec v){ this.pos = v; }
	public void setRoom(int x){ this.room = x; }
	public void setTyp(int x){ this.typ = x; }
	
	//createPath()
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
	
	//printWay()
	public void printWay(){
	  for(int i=0; i<this.way.size(); i++){
	    this.way.get(i).print();
	  }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}