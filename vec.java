public class vec{
  
  public int x = -1;  //X-Coord
  public int y = -1;  //Y-Coord
  public int z = -1;  //Z-Coord
  
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
  
  //getX()
  public int getX(){ return this.x; }
  
  //getY()
  public int getY(){ return this.y; }
  
  //getZ()
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
    vec out = new vec(this.x,this.y,this.z);
	return out;
  }
  
  //compare()
  public boolean compare(vec v){
    if(v.x==this.x && v.y==this.y && v.z==this.z){ return true; }
	else{ return false; }
  }
  
  //print()
  public void print(){
    System.out.print(this.x + "," + this.y + "," + this.z);
  }
  
}