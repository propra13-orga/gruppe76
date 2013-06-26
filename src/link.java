public class link{
  
  private vec  in = null;  //Eingang
  private vec out = null;  //Ausgang
  
  private int move = -1;  //0=W=Oben; 1=A=Links; 2=S=Unten; 3=D=Rechts;
  
  //Konstruktor
  public link(vec in, vec out, int x){
    this.in = in;
	this.out = out;
	this.move = x;
  }
  
  public link(int inX, int inY, int inIndex, int outX, int outY, int outIndex){
    vec in = null;
    vec out = null;
	in = new vec(inX,inY,inIndex);
	out = new vec(outX,outY,outIndex);
    this.in = in;
	this.out = out;
  }
  
  //--- GET ---//
  
  //getIn()
  public vec getIn(){ return this.in; }
  
  //getOut()
  public vec getOut(){ return this.out; }
  
  //getMove()
  public int getMove(){ return this.move; }
  
  //--- SET ---//
  
  //setIn()
  public void setIn(vec v){ this.in = v; }
  
  //setInX()
  public void setInX(int x){ this.in.setX(x); }
  
  //setInY()
  public void setInY(int y){ this.in.setY(y); }
  
  //setInZ()
  public void setInZ(int z){ this.in.setZ(z); }
  
  //setOut()
  public void setOut(vec v){ this.out = v; }
  
  //setOutX()
  public void setOutX(int x){ this.out.setX(x); }
  
  //setOutY()
  public void setOutY(int y){ this.out.setY(y); }
  
  //setOutZ()
  public void setOutZ(int z){ this.out.setZ(z); }
  
  //--- PRINT ---//
  
  //print()
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