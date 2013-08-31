public class player{
	/** Name des Spielers */
	public String name = "Player";  //
	
	/** Geld des Spielers */
	public int money = -1;        //
	/** Maximal-Geld des Spielers */
	public int moneyMax = 10000;  //
	/** HP des Spielers */
	private int    hp = -1;  //
	/** Max-HP des Spielers */
	private int hpMax = 100;  //
	/** MP des Spielers*/
	private int    mp = -1;  //
	/** Max-MP des Spielers */
	private int mpMax = 50;  //
	/** Leben des Spielers */
	private int    lifes = -1;  
	/** Max-Leben des Spielers */
	private int lifesMax = 8;  //
	/** Spieler-Position */
	private vec pos = null;  //
	/** Savepoint des Spielers */
	private vec savePos = null;  //
	/** Inventory des Spielers */
	private inventory inv = null;  //
	/** Rüstungswert*/
	private int armorWert = -1;  //
	/** Waffenschaden */
	private int damageWert = -1;  //
	/** Waffenitem */
	private item weapon = null;  //
	/** Rüstungsitem */
	private item armor = null;  //
	
	/** Item Sricpt Engine */
	public scriptengine scriptE = null;  
	
	/** blickrichtung des spielers */
	public int see = 3;  //Blickrichtung 0=W=Oben; 1=A=Links; 2=S=Unten; 3=D=Rechts
	
	/**
	 * Konstruktor
	 */
	public player(){
	  init();
	}
	
	/**
	 * initialisieren
	 */
	private void init(){
	  this.hp = 100;
	  this.lifes = 3;
	  this.mp = 50;
	  this.armorWert = 5;
	  this.damageWert = 20;
	  this.money = 1000;
	  //Item-Script-Engine
	  this.scriptE = new scriptengine(this);
	  //Inventar
	  this.inv = new inventory(10);
	}
	
	/**
	 * ein leben verlieren
	 */
	public void die(){
	  this.pos = this.savePos.copy();
	  this.lifes--;
	  this.hp = this.hpMax;
	}
	
	/**
	 * Bekämpft einen Gegner
	 * @param e Gegner der bekämpft werden soll
	 */
	public void fight(enemy e){
	  //Player-HP berechnen
		
	  this.hp = this.hp - Math.max(0, (e.damageWert - this.armorWert));
	  //Enemy-HP berechnen
	  e.hp = e.hp - Math.max(0, (this.damageWert - e.armorWert));
	  System.out.println("P: " + this.hp + " E: " + e.hp);
	  //Prüfen ob tot
	  if(this.hp<=0){
	    this.hp = 0;
		this.die();
	  }
	  if(e.hp<=0){
	    e.hp = 0;
	    e.die();
	  }
	}
	
	
	/**
	 * Bekämpft einen Gegner mit Magie
	 * @param e Gegner der Bekämpft werden soll
	 */
	public void magic(enemy e) {
		if(this.mp >= 10 ){
			this.mp -= 10;
			e.hp = e.hp -5;
		}
		if(e.hp<=0){
		    e.hp = 0;
		    e.die();
		}
	}
	
	
	
	//--- add ---//
	
	public void addItem(item i){ this.inv.addItem(i); }
	
	//--- Set ---//
	
	public void setPos(vec v){
		this.pos = v.copy();
	}
	
	public void setSavePos(vec v){ this.savePos = v.copy(); }
	
	public void setHP(int x){ this.hp = x; }
	public void setHPMax(int x){ this.hpMax = x; }
	public void setMP(int x){ this.mp = x; }
	public void setMPMax(int x){ this.mpMax = x; }
	public void setLifes(int x){ this.lifes = x; }
	public void setLifesMax(int x){ this.lifesMax = x; }
	
	public void setInv(inventory inv){ this.inv = inv; }
	
	public void setArmorWert(int x){ this.armorWert = x; }
	public void setDamageWert(int x){ this.damageWert = x; }
	public void setWeapon(item x){ this.weapon = x; }
	public void setArmor(item x){ this.armor = x; }
	
	public void setScriptE(scriptengine sc){ this.scriptE = sc; }
	
	//--- Get ---//
	
	public vec getPos(){ return this.pos; }
	
	public vec getSavePos(){ return this.pos; }
	
	public int getHP(){ return this.hp; }
	public int getHPMax(){ return this.hpMax; }
	public int getMP(){ return this.mp; }
	public int getMPMax(){ return this.mpMax; }
	public int getLifes(){ return this.lifes; }
	public int getLifesMax(){ return this.lifesMax; }
	
	public inventory getInv(){ return this.inv; }
	
	public int getArmorWert(){ return this.armorWert; }
	public int getDamageWert(){ return this.damageWert; }
	public item getWeapon(){ return this.weapon; }
	public item getArmor(){ return this.armor; }
	
	/**
	 * zum Debuggen
	 */
	public void print(){
	  System.out.print("Pos:"); this.pos.print();
	  System.out.print("SavePos:");this.savePos.print();
	}

	/**
	 * regeneriert 1 MP
	 */
	public void addMP() {
		if(this.mp < this.mpMax){
			this.mp += 1;
		}
	}

	
	
}