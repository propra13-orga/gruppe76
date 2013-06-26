public class player{

	private int    hp = -1;  //HP des Spielers
	private int hpMax = 100;  //Max-HP des Spielers
	
	private int    mp = -1;  //MP des Spielers
	private int mpMax = 20;  //Max-MP des Spielers
	
	private int    lifes = -1;  //Leben des Spielers
	private int lifesMax = 8;  //Max-Leben des Spielers
	
	private vec pos = null;  //Spieler-Position
	private vec savePos = null;  //Savepoint des Spielers
	
	private inventory inv = null;  //Inventory des Spielers
	
	private int armorWert = -1;  //Rüstungswert
	private int damageWert = -1;  //Waffenschaden
	
	private item weapon = null;  //Waffenitem
	private item armor = null;  //Rüstungsitem
	
	private scriptengine scriptE = null;  //Item-Scipt-Engine
	
	//Konstruktor
	public player(){
	  init();
	}
	
	//init()
	private void init(){
	  this.hp = 100;
	  this.lifes = 3;
	  this.mp = 20;
	  this.armorWert = 5;
	  this.damageWert = 20;
	  //Item-Script-Engine
	  this.scriptE = new scriptengine(this);
	}
	
	//--- Actions ---//
	//Standpunkt sichern
	public void magic()
	{
		if(this.mp-5>=0)
		{this.mp=this.mp-5;
		vec v = null;
		v = this.getPos();
		this.setSavePos(v);}
	}
    //Heilen
	public void heal()
	{
		if(this.mp-1>=0 && this.hp<=90)
		{this.mp=this.mp-1;
		this.hp=this.hp+10;
		}
	}
	
	//Die+Damage
	public void die(){
	  this.hp = this.hp-25;
	  if(this.hp==0)
	  {
	  this.lifes--;
	  this.pos = this.savePos.copy();
	  this.hp = 100;
	  if(this.lifes==0)
	  {System.exit(0);}
	  }
	  }
	
	//--- Set ---//
	
	public void setPos(vec v){ this.pos = v.copy(); }
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
	
	//print()
	public void print(){
	  System.out.print("Pos:"); this.pos.print();
	  System.out.print("SavePos:");this.savePos.print();
	}
	
}