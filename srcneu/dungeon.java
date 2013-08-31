
public class dungeon{

	private static boolean gameRunning;
	
	private static boolean inMenu;
	
	private static boolean mainLoop;
	
	private static String[] Levels;
	
	private static boolean leveledup;
	
	private static int level;
	/**
	 * Das Spiel spielen
	 * @param args
	 */
	public static void main(String[] args){
		
		level = 0;
		
		leveledup = false;
		// 2D Klasse initialisieren
		J2D.createDevice(960, 800, "Dungeon Crawler v0.6.0"); //960x720 ohne Statusleiste unten
		
		//MainMenu init
		mainMenu menu = null;
		menu = new mainMenu();
		
		Levels = new String[3];
		Levels[0] = "lvl/lvl0-test.lvl";
		Levels[1] = "lvl/lvl1-test.lvl";
		Levels[2] = "lvl/lvl2-test.lvl";
		String testlevel;
		
			//Level setzen.
			testlevel = Levels[level];
		
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
					
					//Spielschleife starten
					gameRunning = true;
					long time;
					long fps = 100;
					long waitTime;
					while(level <= 3)
					{
						leveledup = false;
						testlevel = Levels[level];
						game.init(testlevel);
						while(gameRunning && (!leveledup)){
							//Rendert neues Bild
							time = game.render();
							//System.out.println("TIME: " + time);
							waitTime = (1000-(fps*time))/fps;
							//System.out.println("WAIT: " + waitTime);
							try{ if(waitTime>=0){ Thread.sleep(waitTime); }	}
							catch(InterruptedException e){ e.printStackTrace(); }
						}
					}
					
			}
		
		System.exit(0);
	}
	
	
	
	
	
	/**
	 * Erhöhe das Level um 1
	 */
	public static void levelup(){
		// Beendet das Spiel
		//gameRunning = false;
		level++;
		leveledup = true;
	}

	/**
	 * das spiel beenden
	 */
	public static void terminate(){
		gameRunning = false;
	}
	
	
	/**
	 * Aufrufen um das Menü zu beenden
	 */
	public static void terminateMenu(){
		// Beendet das Spiel
		inMenu = false;
	}

}