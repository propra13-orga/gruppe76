import java.util.*;

public class inventory{
	
	public LinkedList<item> l = null;  //Items
	
	//Konstruktor
	public inventory(){
	  init();
	}
	
	//init()
	private void init(){
	  this.l = new LinkedList<item>();
	}
	
	//--- Get ---//
	
	public item get(int x){
	  if(x>=0 && x<this.l.size()){
	    return this.l.get(x);
	  }
	  return null;
	}
	
}