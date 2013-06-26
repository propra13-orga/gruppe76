import java.awt.*;
import java.awt.image.*;

public class dungeon{

	private static boolean gameRunning;
	
	public static void main(String[] args){
	
		// 2D Klasse initialisieren
		J2D.createDevice(960, 800, "Dungeon Crawler v0.6.0"); //960x720 ohne Statusleiste unten
	
		// Testlevel zum Debuggen. Wird spaeter ueber das Hauptmenue gesteuert
		String testlevel = null;
		if(args.length == 0){
			// testlevel = "roomss/r2.room";
			testlevel = "lvl/lvl0-test.lvl";
		}
		else{
			testlevel = args[0];
		}
	
		// Kommt später in die Hauptmenue Klasse
		gameplay game = new gameplay();
		game.init(testlevel);
		
		// Spielschleife starten
		gameRunning = true;
		while(gameRunning){
			//Rendert neues Bild
			game.render();
			
			// Thread verzoegern
			try { 
				Thread.sleep(10);
			}
			catch(InterruptedException e) {
			}
			
		}
		
	}
	
	public static void terminate(){
		// Beendet das Spiel
		gameRunning = false;
	}

}