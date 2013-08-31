public class item{
	
	/**Index des Items in der Datenbank */
	public int itemIndex = -1;
	
	/**wie oft das Item gestapelt ist */
	public int itemCount = 0;
  
	/** maxmimal Itemanzahl */
	public int itemMaxCount = 100;
	
	/**Ort wo das Item liegt im Lvl(wird nur dort im mom gebraucht!)*/
	public vec v = null;  
	
	/**
	 * erstellst ein neues item
	 * @param index index in der datenbank
	 * @param count anzahl wie oft das item erstellt werden soll
	 */
	public item(int index, int count){
		set(index,count);
	}
	
	/**
	 * 
	 * @param index index des items
	 * @param count anzhal wie oft das item erstellt werden soll
	 */
	public void set(int index, int count){
		this.itemIndex = index;
		if(count>0 && count<=this.itemMaxCount){
			this.itemCount = count;
		}
	}
	
	/**
	 * ein Item entfernen
	 */
	public void del(){
	  if(this.itemCount>0){
	    this.itemCount--;
	  }
	}
	
}