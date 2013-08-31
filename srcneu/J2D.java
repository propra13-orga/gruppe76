/**
J2D ist eine Modifikation der Java StdDraw Klasse.
Sie bietet erweiterte Funktionen vom Rendern von 2D Grafiken und Funktionen
zur Benutzerinteraktion.

Ausserdem verwendet sie ein an DirectX angelehntes Render Modell. Dies sorgt fuer eine Gruppierung der
Renderaufrufe und sorgt fuer eine geschlossene Praesentation einer Frame.

	Der Rendervorgang wird mit J2D.beginScene() eingeleitet
	Nun folgt der Renderaufruf fuer alle Objekte die in den Backbuffer gezeichnet werden sollen
	Mit J2D.endScene() wird der Backbuffer deaktiviert und die Szene kann praesentiert werden.
	J2D.present() wechselt den Backbuffer auf den Bildschirm.
	
Die Funktion Image createSprite(file, [int/float] scale) liefert eine 2D Sprite die bereit ist um im 
Renderaufruf verwendet zu werden.

*/

//Java Draw Class

/*
int .createDevice(intWidth,intHeight,strName) 0 gescheitert, 1 erfolgreich

Image .createSprite(file)      Gibt den Handle eines Bildes zurueck
int .drawSprite(Image,dx,dy)     0 gescheitert, 1 erfolgreich, 2 locked
int .drawSprite(image,dx,dy,drotate)   0 gescheitert, 1 erfolgreich, 2 locked
int .drawSprite(image,dx,dy,dsx,dsy)   0 gescheitert, 1 erfolgreich, 2 locked
int .drawSprite(image,dx,dy,dsx,dsy,drotate) 0 gescheitert, 1 erfolgreich, 2 locked
int .drawText(dCenterX, dCenterY, strText)   0 gescheitert, 1 erfolgreich, 2 locked

int .clear()  'Clears Black    0 gescheitert, 1 erfolgreich, 2 locked
int .clear(Color) 'Clears with color   0 gescheitert, 1 erfolgreich, 2 locked

void .beginScene unlocked die szene. aktiviert den backbuffer und deaktiviert present
void .endScene  locked die szene. deaktiviert den backbuffer und aktiviert present
int .present   gibt das bild aus wenn der renderbereich verlassen wurde 0 gescheitert, 1 erfolgreich, 2 locked

int .saveScreenshot(strOutputName)      blabla   .png wird angehaengt  . liefert 1 wenn erfolgreich oder 0 wenn nicht erfolgreich

*/

/*Todo
  Bei Screenshots: ist offscreenimage richtig? vlt eher onscreenimage. pruefen ob das kollidiert
*/

//Importe von StdDraw
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.util.LinkedList;
import java.util.TreeSet;


public final class J2D implements ActionListener, MouseListener, MouseMotionListener, KeyListener{
//{ Predefines
    public static final Color BLACK      = Color.BLACK;
    public static final Color BLUE       = Color.BLUE;
    public static final Color CYAN       = Color.CYAN;
    public static final Color DARK_GRAY  = Color.DARK_GRAY;
    public static final Color GRAY       = Color.GRAY;
    public static final Color GREEN      = Color.GREEN;
    public static final Color LIGHT_GRAY = Color.LIGHT_GRAY;
    public static final Color MAGENTA    = Color.MAGENTA;
    public static final Color ORANGE     = Color.ORANGE;
    public static final Color PINK       = Color.PINK;
    public static final Color RED        = Color.RED;
    public static final Color WHITE      = Color.WHITE;
    public static final Color YELLOW     = Color.YELLOW;
	
	// Structs fuer Bluring
	public static final float[] BLUR3x3LOW = {
        0.05f, 0.05f, 0.05f,    // low-pass filter kernel
        0.05f, 0.6f, 0.05f,
        0.05f, 0.05f, 0.05f
    };
	
	public static final float[] BLUR3x3HIGH = {
        0.1f, 0.1f, 0.1f,    // low-pass filter kernel
        0.1f, 0.2f, 0.1f,
        0.1f, 0.1f, 0.1f
    };
	
//}

//{ Hauptvariablen
  private static boolean lockScene = false; //Locked/Unlocked den Backbuffer
  
  private static int sceneWidth = 0;  //Aufloesung unserer Szene
  private static int sceneHeight = 0;
  private static int screenWidth = 0;  //Fenstergroeﬂe
  private static int screenHeight = 0;
  private static int xmax = 0;    //Skalierung maxX
  private static int xmin = 0;    //Skalierung minX
  private static int ymax = 0;    //Skalierung maxY
  private static int ymin = 0;    //Skalierung minY
  
  private static Font font;
  
  //Input Synchronisation
  private static Object mouseLock = new Object();
  private static Object keyLock = new Object();
  
  //Front und Backbuffer
  private static BufferedImage offscreenImage, onscreenImage;
  private static Graphics2D offscreen, onscreen;
  
  //Fuer eigene Aufrufe
  private static J2D dj = new J2D();
  
  //JFrame
  private static JFrame frame;
  
  //Maus Variablen
  private static boolean mousePressed = false;
  private static boolean mousePressedL = false;  //Linke Maustaste
  private static boolean mousePressedM = false;  //Mausrad klickbar!! kein drehen!!
  private static boolean mousePressedR = false;  //Rechte Maustaste
  private static double mouseX = 0;
  private static double mouseY = 0;
  private static int mouseButton[] = new int[3];

  //Tastatur Variablen
  private static LinkedList<Character> keysTyped = new LinkedList<Character>();
  private static TreeSet<Character> keysDown = new TreeSet<Character>();  //Tasten die aktuell gedrueckt sind
  
  private static LinkedList<Integer> keysTypedInt = new LinkedList<Integer>();
  private static TreeSet<Integer> keysDownInt = new TreeSet<Integer>();  //Tasten die aktuell gedrueckt sind
  
  // not instantiable (StdDraw)
  private J2D() { }
  // static initializer (StdDraw)
  static { init(); }
//}
 
//{ Client testen
  public static void main(String[] args){
    System.out.println("*** J2D - v1.2 ***");
  }
//}

//{ INIT
private static void init(){
  if (frame != null) frame.setVisible(false);
  //Frame initialisieren
  frame = new JFrame();
}

public static int createDevice(int Width,int Height,String sName){
 
 //Backbuffer initialisieren
 screenWidth = Width;
 screenHeight = Height;
 offscreenImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
 onscreenImage  = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
 offscreen = offscreenImage.createGraphics();
 onscreen  = onscreenImage.createGraphics();
 
 //Backbuffer vorbereiten
 setColor();
 setFont();
 clear();

 //Antialiasing aktivieren
 RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
 hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
 offscreen.addRenderingHints(hints);

 //JFrame initialisieren
 ImageIcon icon = new ImageIcon(onscreenImage);
 JLabel draw = new JLabel(icon);

 draw.addMouseListener(dj);
 draw.addMouseMotionListener(dj);

 frame.setContentPane(draw);
 frame.addKeyListener(dj);    

 frame.setResizable(false);
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            // Beendet das Programm wenn JFrame geschlossen wird
 
 //Fenster in der Mitte des Bildschirms anzeigen
 frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - Width) / 2,(Toolkit.getDefaultToolkit().getScreenSize().height - Height) / 2);
 
 frame.setTitle(sName);

 frame.pack();
 frame.requestFocusInWindow();
 frame.setVisible(true);
 return 1;
}
//}

//{ Sprites
public static Image createSprite(String fileName){ //Uebernommen aus der StdDraw "getImage"
  //Oeffnet die Quelldatei als ImageIcon
  ImageIcon icon = new ImageIcon(fileName);   
  
  //Falls dies fehlschlaegt pruefen wir, ob es vielleicht eine InternetRessource ist
  if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
    try {
      URL url = new URL(fileName);
      icon = new ImageIcon(url);
    } catch (Exception e) { /* not a url */ }
  }

  //Oder ob die Datei vielleicht in einer .jar Datei liegt.
  if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
    URL url = J2D.class.getResource(fileName);
    if (url == null) throw new RuntimeException("image " + fileName + " not found");
    icon = new ImageIcon(url);
  }
  
  //Gibt das Icon im Image Format zurueck
  return icon.getImage();
}

public static Image createSprite(String fileName, int scaleMultiply){ //Uebernommen aus der StdDraw "getImage"
  //Oeffnet die Quelldatei als ImageIcon
  ImageIcon icon = new ImageIcon(fileName);   
  
  //Falls dies fehlschlaegt pruefen wir, ob es vielleicht eine InternetRessource ist
  if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
    try {
      URL url = new URL(fileName);
      icon = new ImageIcon(url);
    } catch (Exception e) { /* not a url */ }
  }

  //Oder ob die Datei vielleicht in einer .jar Datei liegt.
  if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
    URL url = J2D.class.getResource(fileName);
    if (url == null) throw new RuntimeException("image " + fileName + " not found");
    icon = new ImageIcon(url);
  }
  
  //Gibt das Icon im Image Format zurueck
  int scaleWidth;
  int scaleHeight;
  scaleWidth = icon.getIconWidth() * scaleMultiply;
  scaleHeight = icon.getIconHeight() * scaleMultiply;
  
  BufferedImage resizedImage = new BufferedImage(scaleWidth, scaleHeight, BufferedImage.TYPE_INT_ARGB);
  Graphics2D g = resizedImage.createGraphics();
  g.drawImage(icon.getImage(), 0, 0, scaleWidth, scaleHeight, null);
  g.dispose();
  g.setComposite(AlphaComposite.Src);
  g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
  g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
  g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
  
  Image retImage = (Image)resizedImage;
  
  return retImage;
}

public static Image createSprite(String fileName, double scaleMultiply){ //Uebernommen aus der StdDraw "getImage"
  //Oeffnet die Quelldatei als ImageIcon
  ImageIcon icon = new ImageIcon(fileName);   
  
  //Falls dies fehlschlaegt pruefen wir, ob es vielleicht eine InternetRessource ist
  if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
    try {
      URL url = new URL(fileName);
      icon = new ImageIcon(url);
    } catch (Exception e) { /* not a url */ }
  }

  //Oder ob die Datei vielleicht in einer .jar Datei liegt.
  if ((icon == null) || (icon.getImageLoadStatus() != MediaTracker.COMPLETE)) {
    URL url = J2D.class.getResource(fileName);
    if (url == null) throw new RuntimeException("image " + fileName + " not found");
    icon = new ImageIcon(url);
  }
  
  //Gibt das Icon im Image Format zurueck
  int scaleWidth;
  int scaleHeight;
  scaleWidth = (int)(icon.getIconWidth() * scaleMultiply);
  scaleHeight = (int)(icon.getIconHeight() * scaleMultiply);
    
  BufferedImage resizedImage = new BufferedImage(scaleWidth, scaleHeight, BufferedImage.TYPE_INT_ARGB);
  Graphics2D g = resizedImage.createGraphics();
  g.drawImage(icon.getImage(), 0, 0, scaleWidth, scaleHeight, null);
  g.dispose();
  g.setComposite(AlphaComposite.Src);
  g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
  g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
  g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
  
  Image retImage = (Image)resizedImage;
  
  return retImage;
}
//}

//{ Text 
    //Uebernommen aus StdDraw
 
 public static void setFont(){ 
   font = new Font("SansSerif", Font.PLAIN, 24); 
 }
 public static void setFont(Font f){ 
   font = f; 
 }
 public static int drawText(double x, double y, String s) {
   if(lockScene == true) return 2;
      offscreen.setFont(font);
      FontMetrics metrics = offscreen.getFontMetrics();
	  if(s == null){ s = " ";}
      int ws = metrics.stringWidth(s);
      int hs = metrics.getDescent();
	  offscreen.drawString(s, (float) x , (float) y );
   return 1;
    }
  
  public static int getTextWidth(String sText){
    offscreen.setFont(font);
    FontMetrics metrics = offscreen.getFontMetrics();
	if(sText == null){ sText = " ";}
	return metrics.stringWidth(sText);
  }
	
  public static int drawTextCenter(double x, double y, String s) {
   if(lockScene == true) return 2;
      offscreen.setFont(font);
      FontMetrics metrics = offscreen.getFontMetrics();
      int ws = metrics.stringWidth(s);
      int hs = metrics.getDescent();
	  offscreen.drawString(s, (float) x - (ws / 2), (float) y - (hs / 2));
   return 1;
    }
 
 public static void setColor() {  //Setzt die Farbe fuer den Backbuffer
      offscreen.setColor(BLACK);
    }
 public static void setColor(Color color) {  //Setzt die Farbe fuer den Backbuffer
      offscreen.setColor(color);
    }
    /**
     * Draw a line from (x0, y0) to (x1, y1).
     * @param x0 the x-coordinate of the starting point
     * @param y0 the y-coordinate of the starting point
     * @param x1 the x-coordinate of the destination point
     * @param y1 the y-coordinate of the destination point
     */
    public static int line(double x0, double y0, double x1, double y1) {
   if(lockScene == true) return 2;  
      offscreen.draw(new Line2D.Double((x0), (y0), (x1), (y1)));
   return 1;
    }
    /**
     * Draw one pixel at (x, y).
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     */
    private static int pixel(double x, double y) {
   if(lockScene == true) return 2;
      offscreen.fillRect((int) Math.round((x)), (int) Math.round((y)), 1, 1);
   return 1;
    }

//}

//{ drawSprite

public static int drawSprite(Image i, double x, double y) {
  if(lockScene == true) return 2;
 int ws = i.getWidth(null);
 int hs = i.getHeight(null);
 if (ws < 0 || hs < 0) return 0;
 
 offscreen.drawImage(i, (int)x, (int)y, null); 
 
 return 1;
}

public static int drawSprite(Image i, double x, double y, int w, int h) {
  if(lockScene == true) return 2;
 int ws = i.getWidth(null);
 int hs = i.getHeight(null);
 if (ws < 0 || hs < 0) return 0;
 
 offscreen.drawImage(i, (int)x, (int)y, w, h, null); 
 
 return 1;
}

public static int drawSprite(Image i, double x, double y, double centerX, double centerY, double degrees) {
  if(lockScene == true) return 2;

 int ws = i.getWidth(null);
 int hs = i.getHeight(null);
 if (ws < 0 || hs < 0) return 0;
 
 offscreen.rotate(Math.toRadians(-degrees), centerX, centerY);

 offscreen.drawImage(i, (int) Math.round(x - ws/2.0), (int) Math.round(y - hs/2.0), null);

 offscreen.rotate(Math.toRadians(+degrees), centerX, centerY);
 return 1;
}


//}

//{ RENDER

  public static int clear(){
 return clear(BLACK);
  }
  public static int clear(Color color) {
    if(lockScene == true) return 2;
 offscreen.setColor(color);
    offscreen.fillRect(0, 0, sceneWidth, sceneHeight);
    return 1;
  }
  
  public static void beginScene(){
    //Unlocked die Szene, damit wir im Backbuffer schreiben koennen. Deaktiviert present()
 lockScene = false;
  }
  
  public static void endScene(){
    //Locked die Szene, deaktiviert den Backbuffer und aktiviert present()
 lockScene = true;
  }
  
  public static int present(){
    //Wenn die Szene nicht freigegeben ist, wird sie auch nicht gezeichnet
    if (lockScene == false) return 2;
	onscreen.drawImage(offscreenImage,0,0,null);
	frame.repaint();
	return 1;
  }
  
  public static int present_blur_low(){
    //Wenn die Szene nicht freigegeben ist, wird sie auch nicht gezeichnet
    if (lockScene == false) return 2;
	
	// Bluringfilter setzen
	BufferedImageOp op = null;
	op = new ConvolveOp(new Kernel(3, 3, BLUR3x3LOW),ConvolveOp.EDGE_NO_OP, null);
	BufferedImage filteredImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
	op.filter(offscreenImage, filteredImage);
 
	onscreen.drawImage(filteredImage,0,0,null);
	frame.repaint();
	return 1;
  }
  
  public static int present_blur_high(){
    //Wenn die Szene nicht freigegeben ist, wird sie auch nicht gezeichnet
    if (lockScene == false) return 2;
	
	// Bluringfilter setzen
	BufferedImageOp op = null;
	op = new ConvolveOp(new Kernel(3, 3, BLUR3x3HIGH),ConvolveOp.EDGE_NO_OP, null);
	BufferedImage filteredImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
	op.filter(offscreenImage, filteredImage);
 
	onscreen.drawImage(filteredImage,0,0,null);
	frame.repaint();
	return 1;
  }
  
  
  
//}

//{ Screenshot
public static int saveScreenshot(String filename){ //Speichert einen Screenshot.
  String filenameEx = filename + ".png";
  File file = new File(filenameEx);
  try {
    ImageIO.write(offscreenImage, "png", file); 
  }
  catch (IOException e) { 
    //e.printStackTrace(); 
 return 0;
  }
  return 1;
}

public void actionPerformed(ActionEvent e) {
//Event aus StdDraw
}
//}

//{ Input Maus
//*************************************************************************

    //Ist die Maustaste gedrueckt?
    public static boolean mousePressed() {
        synchronized (mouseLock) {
            return mousePressed;
        }
    }
	
	public static boolean mousePressedL() {
        synchronized (mouseLock) {
            return mousePressedL;
        }
    }
	
	public static boolean mousePressedM() {
        synchronized (mouseLock) {
            return mousePressedM;
        }
    }
	
	public static boolean mousePressedR() {
        synchronized (mouseLock) {
            return mousePressedR;
        }
    }
 //X-Koordinate der Maus
 //@return double MausPositionX --------------------------- Hier wurde geaendert -----------------------------------
    public static double mouseX(){
     return mouseX;
    }
    //Y-Koordinate der Maus
 //@return double MausPositionY --------------------------- Hier wurd geaendert -----------------------------------
    public static double mouseY(){
     return mouseY;
    }
    //Event
    public void mouseClicked(MouseEvent e) { }
    //Event
    public void mouseEntered(MouseEvent e) { }
    //Event
    public void mouseExited(MouseEvent e) { }
	
	
    // //Event
    // public void mousePressed(MouseEvent e) {
        // synchronized (mouseLock) {
			// mouseButton[e.getButton()-1] = 1;
            // mousePressed = true;
        // }
    // }
	
	//Event
    public void mousePressed(MouseEvent e) {
		
		//System.out.println("CODE: " + e.getButton());
		
        synchronized (mouseLock) {
			mouseButton[e.getButton()-1] = 1;
            mousePressed = true;
			
			if(e.getButton() == 1){ mousePressedL = true; }
			else if(e.getButton() == 2){ mousePressedM = true; }
			else if(e.getButton() == 3){ mousePressedR = true; }
        }
    }
	
	
    //Event
    public void mouseReleased(MouseEvent e) {
        synchronized (mouseLock) {
			mouseButton[e.getButton()-1] = 0;
            mousePressed = false;
			
            mousePressedL = false;
            mousePressedM = false;
            mousePressedR = false;
        }
    }
    //Event
    public void mouseDragged(MouseEvent e)  {
        synchronized (mouseLock) {
			try{
              mouseX = frame.getMousePosition().x;
              mouseY = frame.getMousePosition().y;
			} catch(NullPointerException ne){
			  //Maus auﬂerhalb des Fensters
			}
        }
    }
    //Event
    public void mouseMoved(MouseEvent e) {
        synchronized (mouseLock) {
			try{
              mouseX = frame.getMousePosition().x;
              mouseY = frame.getMousePosition().y;
			} catch(NullPointerException ne){
			  //Maus auﬂerhalb des Fensters
			}
        }
    }
	public static int getMouseButtonState(int index){
		return mouseButton[index];
	}
//}
//{ Input Tastatur
//*************************************************************************
    
	//Buffer of Keys lˆschen
	public static void clearKeyBuffer(){
		synchronized(keyLock){
			keysTyped.clear();
		}
	}
	
 //Wurde eine taste gedrueckt?
 //@return boolean TasteGedrueckt
    public static boolean hasNextKeyTyped() {
        synchronized (keyLock) {
            return !keysTyped.isEmpty();
        }
    }
	public static boolean hasNextKeyTypedInt() {
        synchronized (keyLock) {
            return !keysTypedInt.isEmpty();
        }
    }
    //Welche Taste wurde als letztes gedrueckt
 //@return charTaste
    public static char nextKeyTyped() {
        synchronized (keyLock) {
            return keysTyped.removeLast();
        }
    }
	public static int nextKeyTypedInt() {
        synchronized (keyLock) {
            return keysTypedInt.removeLast();
        }
    }
    //Pruefen ob eine Taste aktuell gedrueckt ist (char Taste)
 //@return booleanGedrueckt
    public static boolean isKeyPressed(char ch) {
        return keysDown.contains(ch);
    }
	public static boolean isKeyPressed(int iKey) {
        return keysDownInt.contains(iKey);
    }
	
    //Event
    public void keyTyped(KeyEvent e) {
        synchronized (keyLock) {
		    KeyEvent e1 = e;
            keysTyped.addFirst(e.getKeyChar());
			keysTypedInt.addFirst((int)e1.getKeyCode());	
        }
    }
    //Event
    public void keyPressed(KeyEvent e) {
	    KeyEvent e1 = e;
        keysDown.add(e.getKeyChar());
		keysDownInt.add((int)e1.getKeyCode());
		//System.out.println(e.getKeyCode());
    }
    //Event
    public void keyReleased(KeyEvent e) {
	    KeyEvent e1 = e;
        keysDown.remove(e.getKeyChar());
		keysDownInt.remove((int)e1.getKeyCode());
    }
//}

}