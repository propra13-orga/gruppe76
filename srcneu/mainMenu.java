import java.awt.Font;
import java.awt.Image;

/**
 * Klasse für das Hauptmenü
 *
 */
public class mainMenu{

	// Container fuer die Tile Bilder
	private Image[] tileset = null;
	
	/**
	 * Initialisieren
	 */
	public void init(){
		
		//Graphiken laden
		tileset = new Image[3];
		
		//Menu
		tileset[0] = J2D.createSprite("tiles/mainMenuBack.png");
		tileset[1] = J2D.createSprite("tiles/mainMenuTitle.png");
		tileset[2] = J2D.createSprite("tiles/mainMenuButtonBlank.png");

	}
	
	/**
	 * zeigt ein Menü an bis der User etwas ausgewählt hat
	 */
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
		
		//Zeichen prüfen
		if(String.valueOf(c).equals("i")){
			//leer
		}
		
		//Mouse-Bewegung prüfen
		if(J2D.mousePressedL()==true){
		  //System.out.println("X: " + J2D.mouseX());
		  //System.out.println("Y: " + J2D.mouseY());
		  //Button "Start" prüfen
		  if(checkButton(382,582,282,362,J2D.mouseX(),J2D.mouseY())){
		    dungeon.terminateMenu();
		  }
		  else if(checkButton(382,582,382,462,J2D.mouseX(),J2D.mouseY())){
		    System.exit(0);
		  }
		}
		
		//--- Menu rendern ---//
		
		//Hintergrund malen
		J2D.drawSprite(tileset[0],0,0);
		
		//Title malen
		J2D.drawSprite(tileset[1],200,50);
		
		//Button malen
		J2D.drawSprite(tileset[2],380,260);
		J2D.setFont(new Font("SansSerif", Font.BOLD, 20));
		J2D.drawText(415,305,"START GAME");
		
		//Button malen
		J2D.drawSprite(tileset[2],380,360);
		J2D.setFont(new Font("SansSerif", Font.BOLD, 20));
		J2D.drawText(390,405,"EXIT TO DESKTOP");
		
		// Praesentieren
		J2D.endScene();
		J2D.present_blur_low();
		
	}
	
	//--- Buttons ---//
	
	private boolean checkButton(int xRangeMin, int xRangeMax, int yRangeMin, int yRangeMax, double xx, double yy){
		int x = (int)xx;
		int y = (int)yy;
		if(x>= xRangeMin && x<= xRangeMax && y>=yRangeMin && y<=yRangeMax){
		  return true;
		}
		return false;
	}
	
}