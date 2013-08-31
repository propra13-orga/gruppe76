/**
 * 
 * Klasse für Verbindung
 *
 */
public class link{
  
  private vec  in = null;  
  private vec out = null;  
  /**
   * 0=W=Oben; 1=A=Links; 2=S=Unten; 3=D=Rechts;
   */
  private int move = -1;
  
  /**
   * Konstruktor
   * @param in Eingang
   * @param out Ausgang
   * @param x Bewegunsrichtung um die verbindung zu benutzen 0=W=Oben; 1=A=Links; 2=S=Unten; 3=D=Rechts; 
   */
  public link(vec in, vec out, int x){
    this.in = in;
	this.out = out;
	this.move = x;
  }
  
  /**
   * 
   * @param inX Eingang x wert
   * @param inY Eingang y wert
   * @param inIndex Eingang z wert
   * @param outX Ausgang x wert
   * @param outY Ausgang y wert
   * @param outIndex Ausgang z wert
   */
  public link(int inX, int inY, int inIndex, int outX, int outY, int outIndex){
    vec in = null;
    vec out = null;
	in = new vec(inX,inY,inIndex);
	out = new vec(outX,outY,outIndex);
    this.in = in;
	this.out = out;
  }
  
  
  /** 
   * 
   * @return eingang
   */
  public vec getIn(){ return this.in; }
  
  /**
   * 
   * @return ausgang
   */
  public vec getOut(){ return this.out; }
  
  /**
   * 
   * @return Bewegungsrichtung um den eingan zu benutzen
   */
  public int getMove(){ return this.move; }
  
  /**
   * 
   * @param v Position des Eingangs
   */
  public void setIn(vec v){ this.in = v; }
  
  /**
   * 
   * @param x Position des Eingangs
   */
  public void setInX(int x){ this.in.setX(x); }
  
  /**
   * 
   * @param y Position des Eingangs
   */
  public void setInY(int y){ this.in.setY(y); }
  
  /**
   * 
   * @param z Raum des Eingangs
   */
  public void setInZ(int z){ this.in.setZ(z); }
  
  /**
   * 
   * @param v Position des Ausgangs
   */
  public void setOut(vec v){ this.out = v; }
  
  /**
   * 
   * @param x wert des Ausgangs
   */
  public void setOutX(int x){ this.out.setX(x); }
  
  /**
   * 
   * @param y y wert des ausgangs
   */
  public void setOutY(int y){ this.out.setY(y); }
  
  /**
   * 
   * @param z Raum des ausgangs
   */
  public void setOutZ(int z){ this.out.setZ(z); }
  
  
  /**
   * zum debuggen
   */
  public void print(){
    this.in.print();
	System.out.print(" --> ");
	if(this.move==0){ System.out.print("Oben"); }
	else if(this.move==1){ System.out.print("Links"); }
	else if(this.move==2){ System.out.print("Unten"); }
	else if(this.move==3){ System.out.print("Rechts"); }
	System.out.print(" --> ");
	this.out.print();
  }
  
}