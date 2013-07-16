import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class gameplay{

	// Container fuer die Tile Bilder
	private Image[] tileset = null;
	private Image[] playerPics = null;
	
	// GUI Elemente
	private Image bar_info = null;
	private Image bar_scroll = null;
	private Image bar_hp = null;
	private Image bar_mp = null;
	private Image bar_money = null;
	
	// Das Lvl
	private lvl curlvl = null;
	
	private boolean inv = false;  //Inventar ist offen
	private boolean invSub = false;  //Inventar Sub-Menu ist offen
	
	private boolean shop = false; // Shop offen
	private boolean shopSub = false; // Shop Sub-Menu ist offen
	
	private boolean mouseR = false;  //Rechte Mousetaste wurde gedrückt!
	private boolean mouseL = false;  //Rechte Mousetaste wurde gedrückt!
	
	private vec invSubXY = null;  //Coords für Inventar Sub-Menu
	
	private boolean moveD = false;  //Player geht nach Rechts
	private int moveDCount = 0;  //Zähler für Pic-Array
	private boolean moveW = false;  //Player geht nach Oben
	private int moveWCount = 0;  //Zähler für Pic-Array
	private boolean moveA = false;  //Player geht nach Links
	private int moveACount = 0;  //Zähler für Pic-Array
	private boolean moveS = false;  //Player geht nach Unten
	private int moveSCount = 0;  //Zähler für Pic-Array
	
	// init()
	public void init(String file){
	
		// GUI laden
		bar_info = J2D.createSprite("/gui/infobar.png");
		bar_scroll = J2D.createSprite("/gui/bar.png");
		bar_hp = J2D.createSprite("/gui/hp.png");
		bar_mp = J2D.createSprite("/gui/mp.png");
		bar_money = J2D.createSprite("/gui/money.png");
		
		// Laedt den Startlevel
		curlvl = new lvl();
		curlvl.load(file);
		
		//Graphiken laden
		tileset = new Image[29];
		//Boden
		tileset[0] = J2D.createSprite("/tiles/floor.png");
		//Wände
		tileset[1] = J2D.createSprite("/tiles/wall-h.png");
		tileset[2] = J2D.createSprite("/tiles/wall-v.png");
		//Wand-Ecken
		tileset[3] = J2D.createSprite("/tiles/wall corner no.png");
		tileset[4] = J2D.createSprite("/tiles/wall corner so.png");
		tileset[5] = J2D.createSprite("/tiles/wall corner sw.png");
		tileset[6] = J2D.createSprite("/tiles/wall corner nw.png");
		//Türen
		tileset[7] = J2D.createSprite("/tiles/door open.png");
		tileset[8] = J2D.createSprite("/tiles/door open cave.png");
		tileset[9] = J2D.createSprite("/tiles/door closed.png");
		//Wände-Kreuzungen
		tileset[10] = J2D.createSprite("/tiles/wall cross.png");
		tileset[11] = J2D.createSprite("/tiles/wall half cross up.png");
		tileset[12] = J2D.createSprite("/tiles/wall half cross down.png");
		tileset[13] = J2D.createSprite("/tiles/wall half cross left.png");
		tileset[14] = J2D.createSprite("/tiles/wall half cross right.png");
		//Wände-Enden
		tileset[15] = J2D.createSprite("/tiles/wall up end.png");
		tileset[16] = J2D.createSprite("/tiles/wall down end.png");
		tileset[17] = J2D.createSprite("/tiles/wall left end.png");
		tileset[18] = J2D.createSprite("/tiles/wall right end.png");
		//Sonstiges
		tileset[19] = J2D.createSprite("/tiles/start.png");
		tileset[20] = J2D.createSprite("/tiles/goal.png");
		tileset[21] = J2D.createSprite("/tiles/floor.png");  //J2D.createSprite("/tiles/door.png");
		tileset[22] = J2D.createSprite("/tiles/player-alt.png");
		tileset[23] = J2D.createSprite("/tiles/savepoint.png");
		//Enemys
		tileset[24] = J2D.createSprite("/tiles/enemy.png");
		//Inventar
		tileset[25] = J2D.createSprite("/tiles/player inventory.png");
		tileset[26] = J2D.createSprite("/tiles/shop inventory.png");
		tileset[27] = J2D.createSprite("/tiles/player inventory submenu.png");
		tileset[28] = J2D.createSprite("/tiles/shop inventory submenu.png");
		
		//Player Pics laden
		playerPics = new Image[40];
		playerPics[0] = J2D.createSprite("/tiles/player/player steht W.png");
		playerPics[1] = J2D.createSprite("/tiles/player/player steht A.png");
		playerPics[2] = J2D.createSprite("/tiles/player/player steht S.png");
		playerPics[3] = J2D.createSprite("/tiles/player/player steht D.png");
		//Move nach Rechts
		playerPics[4] = J2D.createSprite("/tiles/player/move/move D0.png");
		playerPics[5] = J2D.createSprite("/tiles/player/move/move D1.png");
		playerPics[6] = J2D.createSprite("/tiles/player/move/move D2.png");
		playerPics[7] = J2D.createSprite("/tiles/player/move/move D3.png");
		playerPics[8] = J2D.createSprite("/tiles/player/move/move D4.png");
		playerPics[9] = J2D.createSprite("/tiles/player/move/move D5.png");
		playerPics[10] = J2D.createSprite("/tiles/player/move/move D6.png");
		playerPics[11] = J2D.createSprite("/tiles/player/move/move D7.png");
		playerPics[12] = J2D.createSprite("/tiles/player/move/move D8.png");
		//Move nach Oben
		playerPics[13] = J2D.createSprite("/tiles/player/move/move W0.png");
		playerPics[14] = J2D.createSprite("/tiles/player/move/move W1.png");
		playerPics[15] = J2D.createSprite("/tiles/player/move/move W2.png");
		playerPics[16] = J2D.createSprite("/tiles/player/move/move W3.png");
		playerPics[17] = J2D.createSprite("/tiles/player/move/move W4.png");
		playerPics[18] = J2D.createSprite("/tiles/player/move/move W5.png");
		playerPics[19] = J2D.createSprite("/tiles/player/move/move W6.png");
		playerPics[20] = J2D.createSprite("/tiles/player/move/move W7.png");
		playerPics[21] = J2D.createSprite("/tiles/player/move/move W8.png");
		//Move nach Links
		playerPics[22] = J2D.createSprite("/tiles/player/move/move A0.png");
		playerPics[23] = J2D.createSprite("/tiles/player/move/move A1.png");
		playerPics[24] = J2D.createSprite("/tiles/player/move/move A2.png");
		playerPics[25] = J2D.createSprite("/tiles/player/move/move A3.png");
		playerPics[26] = J2D.createSprite("/tiles/player/move/move A4.png");
		playerPics[27] = J2D.createSprite("/tiles/player/move/move A5.png");
		playerPics[28] = J2D.createSprite("/tiles/player/move/move A6.png");
		playerPics[29] = J2D.createSprite("/tiles/player/move/move A7.png");
		playerPics[30] = J2D.createSprite("/tiles/player/move/move A8.png");
		//Move nach Unten
		playerPics[31] = J2D.createSprite("/tiles/player/move/move S0.png");
		playerPics[32] = J2D.createSprite("/tiles/player/move/move S1.png");
		playerPics[33] = J2D.createSprite("/tiles/player/move/move S2.png");
		playerPics[34] = J2D.createSprite("/tiles/player/move/move S3.png");
		playerPics[35] = J2D.createSprite("/tiles/player/move/move S4.png");
		playerPics[36] = J2D.createSprite("/tiles/player/move/move S5.png");
		playerPics[37] = J2D.createSprite("/tiles/player/move/move S6.png");
		playerPics[38] = J2D.createSprite("/tiles/player/move/move S7.png");
		playerPics[39] = J2D.createSprite("/tiles/player/move/move S8.png");
		
		
	}
	
	public long render(){
		long startTime = System.currentTimeMillis();
		long endTime = -1;
		
		// Backbuffer vorbereiten
		J2D.beginScene();
		J2D.clear();
		
		//Userinput bearbeiten
		char c = ' ';
		if(this.moveD==true || this.moveW==true || this.moveA==true || this.moveS==true){
		  J2D.clearKeyBuffer();
		}
		try{
		  c = J2D.nextKeyTyped();
		  // System.out.println(c);
		}catch(Exception e){ /*System.out.print(".");*/ }
		
		// //Userinput bearbeiten
		// char c = ' ';
		// if(this.moveD==false || this.moveW==false){
		// try{
		  // c = J2D.nextKeyTyped();
		  // // System.out.println(c);
		// }catch(Exception e){ /*System.out.print(".");*/ }
		// }
		
		//Zeichen prüfen
		if(String.valueOf(c).equals("i") && this.shop == false ){
			if(this.inv == true){
				this.inv = false;
				this.invSub = false;
			}
			else{
				this.inv = true;
				//this.invSub = false;
			}
		}
		else if(String.valueOf(c).equals("k") && this.inv == false){
			//Prüfen, ob Spieler ind er Nähe des NPC ist
			int epsilon = 0;
			if( this.curlvl.getPlayer().getPos().getZ() == this.curlvl.getShop().getPos().getZ() ){ epsilon+=1; }
			if( Math.abs( this.curlvl.getPlayer().getPos().getX()*64 - this.curlvl.getShop().getPos().getX()) < 128 ){ epsilon+=2; }
			if( Math.abs( this.curlvl.getPlayer().getPos().getY()*48 - this.curlvl.getShop().getPos().getY()) < 96 ){ epsilon+=4; }
			
			if(epsilon == 7){
			
				if(this.shop == true){
					this.shop = false;
					this.shopSub = false;
				}
				else{
					this.shop = true;
					//this.invSub = false;
				}
			}
		}
		else if(String.valueOf(c).equals("d") && this.moveD==false && this.curlvl.checkRIGHT()==true && this.inv == false && this.shop == false){
		//else if(J2D.isKeyPressed('d')==true && this.moveD==false && this.curlvl.checkRIGHT()==true){
		  this.moveD = true;
		  this.moveDCount = 0;
		}
		else if(String.valueOf(c).equals("w") && this.moveW==false && this.curlvl.checkUP()==true && this.inv == false && this.shop == false){
		  this.moveW = true;
		  this.moveWCount = 0;
		}
		else if(String.valueOf(c).equals("a") && this.moveA==false && this.curlvl.checkLEFT()==true && this.inv == false && this.shop == false){
		  this.moveA = true;
		  this.moveACount = 0;
		}
		else if(String.valueOf(c).equals("s") && this.moveS==false && this.curlvl.checkDOWN()==true && this.inv == false && this.shop == false){
		  this.moveS = true;
		  this.moveSCount = 0;
		}
		
		
		//Mouse-Bewegung prüfen
		if(J2D.mousePressedR()==true){
		  //System.out.println("X: " + J2D.mouseX());
		  //System.out.println("Y: " + J2D.mouseY());
		  this.mouseR = true;
		}
		else if(J2D.mousePressedL()==true){
		  //System.out.println("X: " + J2D.mouseX());
		  //System.out.println("Y: " + J2D.mouseY());
		  this.mouseL = true;
		}
		else{
			this.mouseL = false;
			this.mouseR = false;
		}
		
		//Lvl aktualisieren, wenn Shop und Inventar geschlossen sind
		if(this.inv == false && this.shop == false){
			curlvl.move(c);
		}
		
		//--- Level rendern ---//
		
		//Aktuellen Raum holen
		field f = null;
		f = curlvl.getF(curlvl.getPlayer().getPos().getZ());
		String s = "";
		
		//Boden komplett malen
		for(int y = 0; y < f.getH(); y++){
			for(int x = 0; x < f.getW(); x++){
			  J2D.drawSprite(tileset[0], x*64, y*48);
			}
		}
		
		int tmp = -1;
		
		//Aktuellen Raum rendern
		for(int y = 0; y < f.getH(); y++){
			for(int x = 0; x < f.getW(); x++){
				//Feld holen
				s = f.getF()[x][y];
				//Prüfen und zeichnen
				
				//Wände prüfen
				if(s.equals("w")){
				  //Umgebung checken
				  tmp = checkAround(x,y,f.getF());
				  tmp = checkTable(tmp);
				  //Wand malen
				  J2D.drawSprite(tileset[tmp], x*64, y*48);
				}
				
				//Eingänge malen
				if(s.equals("E")){	J2D.drawSprite(tileset[21], x*64, y*48);	}
			}
		}
		
		// //Ecken eintragen(drübermalen)
		// //Rechts oben
		// if(f.f[f.getW()-1][0].equals("w")){ J2D.drawSprite(tileset[3], (f.getW()-1)*64, 0*48); }
		// //Rechts unten
		// if(f.f[f.getW()-1][f.getH()-1].equals("w")){ J2D.drawSprite(tileset[4], (f.getW()-1)*64, (f.getH()-1)*48); }
		// //Links unten
		// if(f.f[0][f.getH()-1].equals("w")){ J2D.drawSprite(tileset[5], 0*64, (f.getH()-1)*48); }
		// //Links oben
		// if(f.f[0][0].equals("w")){ J2D.drawSprite(tileset[6], 0*64, 0*48); }
		
		//Start eintragen(drübermalen)
		if(curlvl.getPlayer().getPos().getZ()==curlvl.getStart().getZ()){ J2D.drawSprite(tileset[19], curlvl.getStart().getX()*64, curlvl.getStart().getY()*48); }
		
		//Ziel eintragen(drübermalen)
		if(curlvl.getPlayer().getPos().getZ()==curlvl.getGoal().getZ()){ J2D.drawSprite(tileset[20], curlvl.getGoal().getX()*64, curlvl.getGoal().getY()*48); }
		
		//Savepoint eintragen(drübermalen)
		vec v = null;
		for(int i=0; i<curlvl.getS().size(); i++){
			v = curlvl.getS().get(i);
			if(curlvl.getPlayer().getPos().getZ()==v.getZ()){
				J2D.drawSprite(tileset[23], v.getX()*64, v.getY()*48);
				break;
			}
		}
		
		//Items eintragen(drübermalen)
		for(int i=0; i<curlvl.items.size(); i++){
			v = curlvl.items.get(i).v;
			if(curlvl.getPlayer().getPos().getZ()==v.getZ()){
				J2D.drawSprite(bar_money, v.getX()*64, v.getY()*48);
				break;
			}
		}
		
		//Enemys eintragen(drübermalen)
		enemy e = null;
		for(int i=0; i<curlvl.getE().size(); i++){
			e = curlvl.getE().get(i);
			if(curlvl.getPlayer().getPos().getZ()==e.getRoom()){
				J2D.drawSprite(tileset[24], e.getPos().getX()*64, e.getPos().getY()*48);
				e.move();
				break;
			}
		}
		
		// NPC malen
		J2D.drawSprite(playerPics[2], curlvl.getShop().getPos().getX(), curlvl.getShop().getPos().getY() );
		
		//Bewegung malen
		if(this.moveD==true){
		  J2D.drawSprite(playerPics[this.moveDCount+4], (curlvl.getPlayer().getPos().getX()-1)*64+(7*this.moveDCount), curlvl.getPlayer().getPos().getY()*48);
		  this.moveDCount++;
		  if(this.moveDCount==9){
		    this.moveDCount = 0;
			this.moveD = false;
			
		  }
		}
		else if(this.moveW==true){
		  J2D.drawSprite(playerPics[this.moveWCount+13], curlvl.getPlayer().getPos().getX()*64, (curlvl.getPlayer().getPos().getY()+1)*48-(5*this.moveWCount));
		  this.moveWCount++;
		  if(this.moveWCount==9){
		    this.moveWCount = 0;
			this.moveW = false;
		  }
		}
		else if(this.moveA==true){
		  J2D.drawSprite(playerPics[this.moveACount+22], (curlvl.getPlayer().getPos().getX()+1)*64-(7*this.moveACount), curlvl.getPlayer().getPos().getY()*48);
		  this.moveACount++;
		  if(this.moveACount==9){
		    this.moveACount = 0;
			this.moveA = false;
		  }
		}
		else if(this.moveS==true){
		  J2D.drawSprite(playerPics[this.moveSCount+31], curlvl.getPlayer().getPos().getX()*64, (curlvl.getPlayer().getPos().getY()-1)*48+(5*this.moveSCount));
		  this.moveSCount++;
		  if(this.moveSCount==9){
		    this.moveSCount = 0;
			this.moveS = false;
		  }
		}
		else{
		  //Player eintragen(drübermalen)
		  J2D.drawSprite(playerPics[curlvl.getPlayer().see], curlvl.getPlayer().getPos().getX()*64, curlvl.getPlayer().getPos().getY()*48);
		}
		
		//Inventar zeichnen
		if(inv==true){
			drawInv();
			//Aktionen per Maus prüfen
			if(this.mouseR==true){
			  actInv();
			}
			//Sub-Menu zeichnen
			if(this.invSub==true){
			  drawInvSub(this.invSubXY);
			}
			//Sub-Menu Action ausführen
			if(this.invSub==true && this.mouseR==false){
			  actInvSub(this.invSubXY);
			}
		}
		//Shop zeichnen
		else if(shop==true){
			drawShop();
			//Aktionen per Maus prüfen
			if(this.mouseR==true){
			  actShop();
			}
			//Sub-Menu zeichnen
			if(this.shopSub==true){
			  drawShopSub(this.invSubXY);
			}
			//Sub-Menu Action ausführen
			if(this.shopSub==true && this.mouseR==false){
			  actShopSub(this.invSubXY);
			}
		}
		
		// GUI rendern
		drawGuiBar();
		
		// Praesentieren
		J2D.endScene();
		J2D.present_blur_low();
		
		endTime = System.currentTimeMillis() - startTime;
		return endTime;
	}
	
	//--- Action ---//
	
	//actInv()
	private void actInv(){
	  int x = (int)J2D.mouseX();
	  int y = (int)J2D.mouseY();
	  int itemStartY = 180;
	  int itemAddY = 40;
	  int itemStartX = 160;
	  int itemEndX = 820;
	  int itemIndex = -1;
	  //Itemindex berechnen
	  for(int i=0; i<10; i++){
		if(x>=itemStartX && x<=itemEndX && y>=(itemStartY + (itemAddY*i)) && y<=(itemStartY + (itemAddY*(i+1)))){
		  //System.out.println("Item: " + i);
		  itemIndex = i;
		  //Prüfen ob überhaupt genug Items in der Liste stehen.
		  if((curlvl.getPlayer().getInv().get().size() - (itemIndex+1))>=0){
			//Sub-Menu speichern/Coords dafür
			this.invSub = true;
			this.invSubXY = new vec(x,y,itemIndex);
		  }
		}
	  }
	}
	
	//actInvSub()
	private void actInvSub(vec v){
	  //System.out.println("X: " + J2D.mouseX() + " Y: " + J2D.mouseY());
	  //System.out.println("vecX: " + v.getX() + " vecY: " + v.getY());
	  
	  //System.out.println("diffX: " + (J2D.mouseX()-v.getX()) + " diffY: " + (J2D.mouseY()-v.getY()));
	  
	  //Auswahl prüfen
	  if(checkButton(v.getX()+14,v.getX()+142,v.getY()+31,v.getY()+69,J2D.mouseX(),J2D.mouseY()) && this.mouseL==true){
	    //System.out.println("USE: " + v.getZ());
		int itemCount = v.getZ();
		player p = curlvl.getPlayer();
		item i = p.getInv().get(itemCount);
		item_dbnode node = curlvl.itemdb.getItem(i.itemIndex);
		//Prüfen ob überhaupt benutzbar
		if(node.getUse()){
			p.scriptE.executescript(node.getEffect());
			i.del();
			//Inventory updaten
			p.getInv().check();
			//Sub-Menu schließen
			this.invSub = false;
			this.invSubXY = null;
		}
	  }
	  else if(checkButton(v.getX()+14,v.getX()+142,v.getY()+75,v.getY()+119,J2D.mouseX(),J2D.mouseY()) && this.mouseL==true){
	    //System.out.println("EQUIP: " + v.getZ());
		int itemCount = v.getZ();
		player p = curlvl.getPlayer();
		item i = p.getInv().get(itemCount);
		item_dbnode node = curlvl.itemdb.getItem(i.itemIndex);
		//Prüfen ob überhaupt benutzbar
		if(node.getEquip()){
			p.scriptE.executescript(node.getEffect());
			//Prüfen ob Waffe oder Rüstung
			if(node.getEffect().contains("equip")){
				p.setWeapon(i);
			}
			else if(node.getEffect().contains("armor")){
				p.setArmor(i);
			}
			//Inventory updaten
			p.getInv().check();
			//Sub-Menu schließen
			this.invSub = false;
			this.invSubXY = null;
		}
	  }
	  else if(checkButton(v.getX()+14,v.getX()+142,v.getY()+125,v.getY()+161,J2D.mouseX(),J2D.mouseY()) && this.mouseL==true){
	    System.out.println("INFO: " + v.getZ());
	  }
	  else if(this.mouseL==true || this.mouseR==true){
	    //Sub-Menu schließen
		this.invSub = false;
		this.invSubXY = null;
	  }
	  else{
	    
	  }
	}
	
	private void actShop(){
	  int x = (int)J2D.mouseX();
	  int y = (int)J2D.mouseY();
	  int itemStartY = 180;
	  int itemAddY = 40;
	  int itemStartX = 160;
	  int itemEndX = 820;
	  int itemIndex = -1;
	  //Itemindex berechnen
	  for(int i=0; i<10; i++){
		if(x>=itemStartX && x<=itemEndX && y>=(itemStartY + (itemAddY*i)) && y<=(itemStartY + (itemAddY*(i+1)))){
		  //System.out.println("Item: " + i);
		  itemIndex = i;
		  
		  this.shopSub = true;
		  this.invSubXY = new vec(x,y,itemIndex);

		}
	  }
	}
	
	//actShopSub()
	private void actShopSub(vec v){
	  
	  //Auswahl prüfen
	  if(checkButton(v.getX()+14,v.getX()+142,v.getY()+31,v.getY()+69,J2D.mouseX(),J2D.mouseY()) && this.mouseL==true){
	    
		int iIndex = v.getZ();
	    npc_shop n = curlvl.getShop();
	    item i = n.getInv().get(iIndex);
	    item_dbnode node = curlvl.itemdb.getItem(i.itemIndex);
		int cost = node.getCost();
		
		if(curlvl.getPlayer().getInv().l.size()!=curlvl.getPlayer().getInv().itemsMaxCount && curlvl.getPlayer().money >= cost){
			
			
			inventory inv = curlvl.getPlayer().getInv();
			//inv.addItem(i);
			
			boolean b = false;
			
			for(item j: inv.l){
				if(j.itemIndex == i.itemIndex){
					j.itemCount++;
					b = true;
					break;
				}
			}
			if(b == false){
				inv.addItem(i);
			}
			
			this.shopSub = false;
			this.invSubXY = null;
			
			curlvl.getPlayer().money -= cost;
			
		}
		else{
			this.shopSub = false;
			this.invSubXY = null;
		}
		
	  }
	  else if(this.mouseL==true || this.mouseR==true){
	    //Sub-Menu schließen
		this.shopSub = false;
		this.invSubXY = null;
	  }
	  else{
	    
	  }
	}
	
	//--- Draw ---//
	
	//drawInvSub()
	private void drawInvSub(int x, int y, int itemIndex){
	  //Hintergrund zeichnen
	  J2D.drawSprite(tileset[27], x, y);
	}
	
	private void drawInvSub(vec v){
	  drawInvSub(v.getX(),v.getY(),v.getZ());
	}
	
	//drawInv()
	private void drawInv(){
	  LinkedList<item> l = null;
	  l = curlvl.getPlayer().getInv().get();
	  
	  //Hintergrund zeichnen
	  J2D.drawSprite(tileset[25], 130, 110);
	  
	  ////Ausgabe
	  // item_dbnode tmp = null;
	  // for(int i=0; i<l.size(); i++){
	    // tmp = curlvl.itemdb.getItem(l.get(i).itemIndex);
		// tmp.print();
		// System.out.println("count:" + l.get(i).itemCount + "\n");
	  // }
	  
	  //Items zeichnen
	  item_dbnode tmp = null;
	  for(int i=0; i<l.size(); i++){
	    tmp = curlvl.itemdb.getItem(l.get(i).itemIndex);
		//tmp.print();
		//System.out.println("count:" + l.get(i).itemCount + "\n");
		
		//Prüfen ob Item angelegt ist (Waffe/Rüstung)
		if(l.get(i)==curlvl.getPlayer().getWeapon() || l.get(i)==curlvl.getPlayer().getArmor()){
		  J2D.setFont(new Font("SansSerif", Font.BOLD, 18));
		}
		else{
		  J2D.setFont(new Font("SansSerif", Font.PLAIN, 18));
		}
		
		//Namen zeichen
		J2D.drawText(160,180 + (i*40),"\"" + tmp.getName() + "\"" + "          " + l.get(i).itemCount + "/" + l.get(i).itemMaxCount);
		J2D.setFont(new Font("SansSerif", Font.PLAIN, 16));
		//Beschreibung zeichnen
		J2D.drawText(165,195 + (i*40),tmp.getText());
	  }
	  
	}
	
	
	//drawShopSub()
	private void drawShopSub(int x, int y, int itemIndex){
	  //Hintergrund zeichnen
	  J2D.drawSprite(tileset[28], x, y);
	}
	
	private void drawShopSub(vec v){
	  drawShopSub(v.getX(),v.getY(),v.getZ());
	}
	
	//drawInv()
	private void drawShop(){
	  LinkedList<item> l = null;
	  l = curlvl.getShop().getInv().get();
	  
	  //Hintergrund zeichnen
	  J2D.drawSprite(tileset[26], 130, 110);
	    
	  //Items zeichnen
	  item_dbnode tmp = null;
	  for(int i=0; i<l.size(); i++){
	    tmp = curlvl.itemdb.getItem(l.get(i).itemIndex);
		//tmp.print();
		//System.out.println("count:" + l.get(i).itemCount + "\n");
		
		//Prüfen ob Item angelegt ist (Waffe/Rüstung)
		/*if(l.get(i)==curlvl.getPlayer().getWeapon() || l.get(i)==curlvl.getPlayer().getArmor()){
		  J2D.setFont(new Font("SansSerif", Font.BOLD, 18));
		}
		else{
		  J2D.setFont(new Font("SansSerif", Font.PLAIN, 18));
		}
		*/
		//Namen zeichen
		
		
		
		J2D.setFont(new Font("SansSerif", Font.PLAIN, 16));
		J2D.drawText(160,180 + (i*40),"\"" + tmp.getName() + "\"" + "          ");
		
		//  Wenn der Spieler nicht genug Geld hat werden die Kosten rot gemalt
		if(tmp.getCost() > curlvl.getPlayer().money){
			J2D.setColor(J2D.RED);
		}
		J2D.drawText(400,180 + (i*40),"Kosten: " + tmp.getCost());
		
		J2D.setColor(J2D.BLACK);
		J2D.setFont(new Font("SansSerif", Font.PLAIN, 16));
		//Beschreibung zeichnen
		J2D.drawText(165,195 + (i*40),tmp.getText());
		
		
	  }
	  
	}
	
	//--- Statusbar ---//
	
	//drawGUIBar()
	private void drawGuiBar(){
	    // GUI rendern
		J2D.drawSprite(bar_info, 0, 721);
		
		//HP-Leiste
		J2D.drawSprite(bar_scroll, 20, 739);
		// fülle des HP Balken berechnen
		float hpval = (180.0f / (float)curlvl.getPlayer().getHPMax()) * (float)curlvl.getPlayer().getHP();
		J2D.drawSprite(bar_hp, 30, 743, (int)hpval, 10);
		
		//MP-Leiste
		J2D.drawSprite(bar_scroll, 20, 765);
		// fülle des MP Balken berechnen
		float mpval = (180.0f / (float)curlvl.getPlayer().getMPMax()) * (float)curlvl.getPlayer().getMP();
		J2D.drawSprite(bar_mp, 30, 769, (int)mpval, 10);
		
		//Beschreibung der Leisten
		J2D.setFont(new Font("SansSerif", Font.BOLD, 16));
		J2D.drawText(230,754,"HP");
		J2D.drawText(230,781,"MP");
		
		//Geld anzeigen
		J2D.setFont(new Font("SansSerif", Font.BOLD, 16));
		J2D.drawSprite(bar_money, 280, 739);
		J2D.drawText(300,754,String.valueOf(curlvl.getPlayer().money));
		
		//Spielername anzeigen
		J2D.setFont(new Font("SansSerif", Font.BOLD, 16));
		J2D.drawText(360,754,"Name: " + curlvl.getPlayer().name);
		
		//Leben anzeigen
		J2D.setFont(new Font("SansSerif", Font.BOLD, 16));
		J2D.drawText(280,781,"Lifes: " + curlvl.getPlayer().getLifes());
		
	}
	
	//--- Sonstiges ---//
	
	//Rechteckigen Bereich prüfen
	private boolean checkButton(int xRangeMin, int xRangeMax, int yRangeMin, int yRangeMax, double xx, double yy){
		int x = (int)xx;
		int y = (int)yy;
		if(x>= xRangeMin && x<= xRangeMax && y>=yRangeMin && y<=yRangeMax){
		  return true;
		}
		return false;
	}
	
	private boolean checkButton(int xRangeMin, int xRangeMax, int yRangeMin, int yRangeMax, int x, int y){
		if(x>= xRangeMin && x<= xRangeMax && y>=yRangeMin && y<=yRangeMax){
		  return true;
		}
		return false;
	}
	
	//Wände auf Umgebung prüfen
	private int checkAround(int x, int y, String[][] data){
	  //1=Links; 2=Rechts; 4=Oben; 8=Unten;
	  int out = 0;
	  
	  //Links prüfen. Wenn wir ein Feld ausserhalb des Spielfeldes betrachten wird eine Exception geworfen, aber das ist dann ja sowieso egal, weil dort kein Verbindungsfeld liegen kann...
	  try{
		if(data[x-1][y].equals("w")){ out+=1; }
	  }
	  catch(Exception e){
	  }
	  //Rechts prüfen
	  try{
		if(data[x+1][y].equals("w")){ out+=2; }
	  }
	  catch(Exception e){
	  }
	  
	  
	  //Oben prüfen
	  try{
		if(data[x][y-1].equals("w")){ out+=4; }
	  }
	  catch(Exception e){
	  }
	  //Unten prüfen
	  try{
	    if(data[x][y+1].equals("w")){ out+=8; }
	  }
	  catch(Exception e){
	  }

	  return out;
	}
	
	//Tile[] Nummer ermitteln
	private int checkTable(int x){
	  if(x==0){
	    //Alleine
		return 18;
	  }
	  else if(x==1){
	    //Rechts Ende
		return 18;
	  }
	  else if(x==2){
	    //Links Ende
		return 17;
	  }
	  else if(x==4){
	    //Unteres Ende
		return 16;
	  }
	  else if(x==8){
	    //Oberes Ende
		return 15;
	  }
	  else if(x==3){
	    //Horizontales Verbindungsstück
		return 1;
	  }
	  else if(x==5){
	    //Ecke Rechts Unten
		return 4;
	  }
	  else if(x==9){
	    //Ecke Rechts Oben
		return 3;
	  }
	  else if(x==6){
	    //Ecke Links Unten
		return 5;
	  }
	  else if(x==10){
	    //Ecke Links Oben
		return 6;
	  }
	  else if(x==12){
	    //Vertikales Verbindungsstück
		return 2;
	  }
	  else if(x==7){
	    //Halbe Kreuzung nach Oben
		return 11;
	  }
	  else if(x==11){
	    //Halbe Kreuzung nach Unten
		return 12;
	  }
	  else if(x==13){
	    //Halbe Kreuzung nach Links
		return 13;
	  }
	  else if(x==14){
	    //Halbe Kreuzung nach Rechts
		return 14;
	  }
	  else if(x==15){
	    //Ganze Kreuzung
		return 10;
	  }
	  else{ return -1; }
	}
	
}