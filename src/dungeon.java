import java.awt.*;
import java.awt.image.*;

public class dungeon{

	private static boolean gameRunning;
	
	private static boolean inMenu;
	
	private static boolean mainLoop;
	
	public static void main(String[] args){
	
		// 2D Klasse initialisieren
		J2D.createDevice(960, 800, "Dungeon Crawler v0.6.0"); //960x720 ohne Statusleiste unten
		
		//MainMenu init
		mainMenu menu = null;
		menu = new mainMenu();
		
		// Testlevel zum Debuggen. Wird spaeter ueber das Hauptmenue gesteuert
		String testlevel = null;
		if(args.length == 0){
			// testlevel = "rooms/r2.room";
			testlevel = "lvl/lvl0-test.lvl";
		}
		else{
			testlevel = args[0];
		}
		
		gameplay game = null;
		
		mainLoop = true;
		//Programm läuft
		while(mainLoop){
		
		//Menu starten
		menu.init();
		inMenu = true;
		while(inMenu){
			//Menu anzeigen
			menu.render();
			
			try{
				Thread.sleep(10);
			}
			catch(Exception e){ e.printStackTrace(); }
		}
		
		game = new gameplay();
		game.init(testlevel);
		// Spielschleife starten
		gameRunning = true;
		long time;
		long fps = 100;
		long waitTime;
		while(gameRunning){
			//Rendert neues Bild
			time = game.render();
			//System.out.println("TIME: " + time);
			waitTime = (1000-(fps*time))/fps;
			//System.out.println("WAIT: " + waitTime);
			try{ if(waitTime>=0){ Thread.sleep(waitTime); }	}
			catch(InterruptedException e){ e.printStackTrace(); }
		}
		
		}
		
		System.exit(0);
	}
	
	public static void terminate(){
		// Beendet das Spiel
		gameRunning = false;
	}
	
	public static void terminateMenu(){
		// Beendet das Spiel
		inMenu = false;
	}

}