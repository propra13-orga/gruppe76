import java.awt.*;
import java.awt.image.*;

public class gameplay{

	// Container fuer die Tile Bilder
	private Image[] tileset = null;
	
	// GUI Elemente
	private Image bar_info = null;
	private Image bar_scroll = null;
	private Image bar_hp = null;
	private Image bar_mp = null;
	
	// Das Lvl
	private lvl curlvl = null;
	
	// init()
	public void init(String file){
	
		// GUI laden
		bar_info = J2D.createSprite("gui/infobar.png");
		bar_scroll = J2D.createSprite("gui/bar.png");
		bar_hp = J2D.createSprite("gui/hp.png");
		bar_mp = J2D.createSprite("gui/mp.png");
		
		
		// Laedt den Startlevel
		curlvl = new lvl();
		curlvl.load(file);
		
		//Graphiken laden
		tileset = new Image[27];
		//Boden
		tileset[0] = J2D.createSprite("tiles/floor.png");
		//Wände
		tileset[1] = J2D.createSprite("tiles/wall-h.png");
		tileset[2] = J2D.createSprite("tiles/wall-v.png");
		//Wand-Ecken
		tileset[3] = J2D.createSprite("tiles/wall corner no.png");
		tileset[4] = J2D.createSprite("tiles/wall corner so.png");
		tileset[5] = J2D.createSprite("tiles/wall corner sw.png");
		tileset[6] = J2D.createSprite("tiles/wall corner nw.png");
		//Türen
		tileset[7] = J2D.createSprite("tiles/door open.png");
		tileset[8] = J2D.createSprite("tiles/door open cave.png");
		tileset[9] = J2D.createSprite("tiles/door closed.png");
		//Wände-Kreuzungen
		tileset[10] = J2D.createSprite("tiles/wall cross.png");
		tileset[11] = J2D.createSprite("tiles/wall half cross up.png");
		tileset[12] = J2D.createSprite("tiles/wall half cross down.png");
		tileset[13] = J2D.createSprite("tiles/wall half cross left.png");
		tileset[14] = J2D.createSprite("tiles/wall half cross right.png");
		//Wände-Enden
		tileset[15] = J2D.createSprite("tiles/wall up end.png");
		tileset[16] = J2D.createSprite("tiles/wall down end.png");
		tileset[17] = J2D.createSprite("tiles/wall left end.png");
		tileset[18] = J2D.createSprite("tiles/wall right end.png");
		//Sonstiges
		tileset[19] = J2D.createSprite("tiles/start.png");
		tileset[20] = J2D.createSprite("tiles/goal.png");
		tileset[21] = J2D.createSprite("tiles/door.png");
		tileset[22] = J2D.createSprite("tiles/player.png");
		tileset[23] = J2D.createSprite("tiles/savepoint.png");
		//Enemys
		tileset[24] = J2D.createSprite("tiles/trex.png");
		//Story-NPC
		tileset[25] = J2D.createSprite("tiles/stickman.png");
		//Wiese
		tileset[26] = J2D.createSprite("tiles/wiese.png");
	}
	
	public void render(){
		
		// Backbuffer vorbereiten
		J2D.beginScene();
		J2D.clear();
		
		//Userinput bearbeiten
		char c = ' ';
		try{
		  c = J2D.nextKeyTyped();
		  // System.out.println(c);
		}catch(Exception e){ /*System.out.print(".");*/ }
		
		//Lvl aktualisieren
		curlvl.move(c);
		
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
				//NPC malen
				if(s.equals("S")){	J2D.drawSprite(tileset[25], x*64, y*48);	}
                //Haus malen
				if(s.equals("g")){	J2D.drawSprite(tileset[26], x*64, y*48);	}
			
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
		
		//Player eintragen(drübermalen)
		J2D.drawSprite(tileset[22], curlvl.getPlayer().getPos().getX()*64, curlvl.getPlayer().getPos().getY()*48);
		
		// GUI rendern
		drawGuiBar();
		// Praesentieren
		J2D.endScene();
		J2D.present_blur_low();
		
		
	}
	
	//--- Draw ---//
	
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
		
		}
	
	public static void drawStory()
	{
		J2D.drawSprite(J2D.createSprite("tiles/sprechblase.png"), 440, -20);
		J2D.endScene();
		}
	//--- Sonstiges ---//
	
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