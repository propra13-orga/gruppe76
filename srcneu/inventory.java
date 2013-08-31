import java.util.*;

/**
 * 
 * Inventar Klasse
 *
 */
public class inventory{
	
	/** Items */
	public LinkedList<item> l = null;
	
	/** Maximale Anzahl der verschiedenen Items im Inventar.(Taschenplätze) */
	public int itemsMaxCount = -1;  
	
	/** Konstruktor */
	public inventory(){
	  init();
	}
	/** Konstruktor
	 * 
	 * @param maxCount Größe des Inventar (Taschenplätze)
	 */
	public inventory(int maxCount){
	  this.itemsMaxCount = maxCount;
	  init();
	}
	
	/**
	 * Initialisieren
	 */
	private void init(){
	  this.l = new LinkedList<item>();
	}
	
	/**
	 * auf leere Items prüfen und leere Items entfernen
	 */
	public void check(){
	  for(int i=0; i<this.l.size(); i++){
	    if(this.l.get(i).itemCount<=0){ this.l.remove(i); i=0; }
	  }
	}
	
	
	/**
	 * gibt das x element aus dem inventar
	 * @param x  nummer des items
	 * @return das x. item aus dem inventar
	 */
	public item get(int x){
	  if(x>=0 && x<this.l.size()){
	    return this.l.get(x);
	  }
	  return null;
	}
	
	/**
	 * 
	 * @return Liste der Items
	 */
	public LinkedList<item> get(){ return this.l; }
	
	/**
	 * 
	 * @param list Liste der Items
	 */
	public void set(LinkedList<item> list){ this.l = list; }
	
	public void addItem(item i){
		if(this.l.size()<this.itemsMaxCount){
			this.l.add(i);
		}
	}
	
}