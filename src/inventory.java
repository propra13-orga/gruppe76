import java.util.*;

public class inventory{
	
	public LinkedList<item> l = null;  //Items
	
	public int itemsMaxCount = -1;  //Maximale Anzahl der verschiedenen Items im Inventar.(Taschenplätze)
	
	//Konstruktor
	public inventory(){
	  init();
	}
	
	public inventory(int maxCount){
	  this.itemsMaxCount = maxCount;
	  init();
	}
	
	//init()
	private void init(){
	  this.l = new LinkedList<item>();
	}
	
	//check() Prüfen auf "leere" Items
	public void check(){
	  for(int i=0; i<this.l.size(); i++){
	    if(this.l.get(i).itemCount<=0){ this.l.remove(i); i=0; }
	  }
	}
	
	//--- Get ---//
	
	public item get(int x){
	  if(x>=0 && x<this.l.size()){
	    return this.l.get(x);
	  }
	  return null;
	}
	
	public LinkedList<item> get(){ return this.l; }
	
	public void set(LinkedList<item> list){ this.l = list; }
	
	public void addItem(item i){
		if(this.l.size()<this.itemsMaxCount){
			this.l.add(i);
		}
	}
	
}