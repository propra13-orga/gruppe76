import java.awt.*;

public class gameplay{

	// Container fuer die Tile Bilder
	private Image[] tileset = null;
	
	// Das Lvl
	private lvl curlvl = null;
	
	// init()
	public void init(String file){
		// Laedt den Startlevel
		curlvl = new lvl();
		curlvl.load(file);
		
		//Graphiken laden
		tileset = new Image[24];
		//Boden
		tileset[0] = J2D.createSprite("img/floor.png");
		//Wände
		tileset[1] = J2D.createSprite("img/wall-h.png");
		tileset[2] = J2D.createSprite("img/wall-v.png");
		//Wand-Ecken
		tileset[3] = J2D.createSprite("img/wall corner no.png");
		tileset[4] = J2D.createSprite("img/wall corner so.png");
		tileset[5] = J2D.createSprite("img/wall corner sw.png");
		tileset[6] = J2D.createSprite("img/wall corner nw.png");
		//Türen
		tileset[7] = J2D.createSprite("img/door open.png");
		tileset[8] = J2D.createSprite("img/door open cave.png");
		tileset[9] = J2D.createSprite("img/door closed.png");
		//Wände-Kreuzungen
		tileset[10] = J2D.createSprite("img/wall cross.png");
		tileset[11] = J2D.createSprite("img/wall half cross up.png");
		tileset[12] = J2D.createSprite("img/wall half cross down.png");
		tileset[13] = J2D.createSprite("img/wall half cross left.png");
		tileset[14] = J2D.createSprite("img/wall half cross right.png");
		//Wände-Enden
		tileset[15] = J2D.createSprite("img/wall up end.png");
		tileset[16] = J2D.createSprite("img/wall down end.png");
		tileset[17] = J2D.createSprite("img/wall left end.png");
		tileset[18] = J2D.createSprite("img/wall right end.png");
		//Sonstiges
		tileset[19] = J2D.createSprite("img/start.png");
		tileset[20] = J2D.createSprite("img/goal.png");
		tileset[21] = J2D.createSprite("img/door.png");
		tileset[22] = J2D.createSprite("img/player.png");
		//Gegner
		tileset[23] = J2D.createSprite("img/octopus.png");
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
		field f = curlvl.getF(curlvl.player.getZ());
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
				s = f.f[x][y];
				//Prüfen und zeichnen
				
				//Boden malen
				if(s.equals("f")){ J2D.drawSprite(tileset[0], x*64, y*48); }
				
				//Ziel malen
				if(s.equals("z")){ J2D.drawSprite(tileset[20], x*64, y*48); }
				
				//Monster malen
				if(s.equals("m")){ J2D.drawSprite(tileset[23], x*64, y*48);}
				
				//Wände prüfen
				if(s.equals("w")){
				  //Umgebung checken
				  tmp = checkAround(x,y,f.f);
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
		if(curlvl.player.z==curlvl.start.z){ J2D.drawSprite(tileset[19], curlvl.start.getX()*64, curlvl.start.getY()*48); }
		
		//Ziel eintragen(drübermalen)
	
		
		//Player eintragen(drübermalen)
		J2D.drawSprite(tileset[22], curlvl.player.getX()*64, curlvl.player.getY()*48);
		
		// Praesentieren
		J2D.endScene();
		J2D.present_blur_low();
		
	}
	
	//Wände auf Umgebung prüfen
	private int checkAround(int x, int y, String[][] data){
	  //1=Links; 2=Rechts; 4=Oben; 8=Unten;
	  int out = 0;
	  
	  //Links prüfen. Wenn wir ein Feld ausserhalb des SPielfeldes betrachten wird eine Exception geworfen, aber das ist dann ja sowieso egal, weil dort kein Verbindungsfeld liegen kann...
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