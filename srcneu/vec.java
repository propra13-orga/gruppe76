public class vec{
  
  private int x = -1;  //X-Coord
  private int y = -1;  //Y-Coord
  private int z = -1;  //Z-Coord
  
  //Konstruktor
  public vec(int x, int y, int z){
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public vec(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  //--- GET ---//
  
  /** gibt X zurück */
  public int getX(){ return this.x; }
  
  /** gibt Y zurück */
  public int getY(){ return this.y; }
  
  /** gibt Z zurück */
  public int getZ(){ return this.z; }
  
  //--- SET ---//
  
  //setX()
  public void setX(int x){ this.x = x; }
  
  //setY()
  public void setY(int y){ this.y = y; }
  
  //setZ()
  public void setZ(int z){ this.z = z; }
  
  //copy()
  public vec copy(){
    vec out = null;
	out = new vec(getX(),getY(),getZ());
	return out;
  }
  
  /** compare() */
  public boolean compare(vec v){
    if(v.getX()==getX() && v.getY()==getY() && v.getZ()==getZ()){ return true; }
	else{ return false; }
  }
  
  /**
   * Entfernung zwischen zwei Punkten 
   */
  public float distance(vec v){
	  if(this.z != v.z){
		  return(Float.MAX_VALUE);
	  }
	  float x = (v.x-this.x);
	  float y = (v.y-this.y);
	  x *= x;
	  y *= y;
	  return(x+y);
  }
  
  
  //print()
  public void print(){
    System.out.println(getX() + "," + getY() + "," + getZ());
  }
  
}