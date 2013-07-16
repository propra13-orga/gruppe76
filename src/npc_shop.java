public class npc_shop{

	private vec pos = null;  //Aktuelle Position
	private inventory inv = null;  //Inventory des Spielers
	
	public npc_shop(){
		this.inv = new inventory(10);
	}
	
	public void setPos(vec pos){
		this.pos = pos;
	}
	public vec getPos(){
		return pos;
	}
	
	public void addItem(item i){ this.inv.addItem(i); }
	
	public inventory getInv(){ return this.inv; }

}