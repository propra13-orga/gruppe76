public class npc_shop{

	private vec pos = null;  //Aktuelle Position
	private inventory inv = null;  //Inventory des Spielers
	
	/**
	 * neuen shop erstellen
	 */
	public npc_shop(){
		this.inv = new inventory(10);
	}
	
	/**
	 * 
	 * @param pos Position des shos
	 */
	public void setPos(vec pos){
		this.pos = pos;
	}
	
	/**
	 * 
	 * @return Position des SHops
	 */
	public vec getPos(){
		return pos;
	}
	
	/**
	 * Item hinzufügen 
	 * @param i
	 */
	public void addItem(item i){ this.inv.addItem(i); }
	
	/**
	 * 
	 * @return Inventar des SHops
	 */
	public inventory getInv(){ return this.inv; }

}