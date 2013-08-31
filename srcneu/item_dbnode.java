public class item_dbnode{

	private String name;
	private boolean use;
	private boolean equip;
	private boolean carry;
	private int cost;
	private String effect;
	private String text;

	/**
	 * 
	 * @param name Name des Items
	 * @param use kann man das Item bneutzen?
	 * @param equip kann man das item ausrüsten?
	 * @param carry kann man das item tragen
	 * @param cost kosten des items
	 * @param effect Auswrikungen des Items
	 * @param text Text der das item beschriebt
	 */
	public item_dbnode(String name, boolean use, boolean equip, boolean carry, int cost, String effect, String text){
		this.name = name;
		this.use = use;
		this.equip = equip;
		this.carry = carry;
		this.cost = cost;
		this.effect = effect;
		this.text = text;
	}
	
	/**
	 * 
	 * @return Name des Items
	 */
	public String getName(){ return name; }
	
	/**
	 * 
	 * @return kann man das item benutzen
	 */
	public boolean getUse(){ return use; }
	
	/**
	 * 
	 * @return kann man das item ausrüsten
	 */
	public boolean getEquip(){ return equip; }
	
	/**
	 * 
	 * @return kann man das item tragen
	 */
	public boolean getCarry(){ return carry; }
	
	/**
	 * 
	 * @return Kosten des Items
	 */
	public int getCost(){ return cost; }
	
	/**
	 * 
	 * @return Auswirkung des Items
	 */
	public String getEffect(){ return effect; }
	
	/**
	 * 
	 * @return Beschreibung des Items
	 */
	public String getText(){ return text; }
	
	/**
	 * Zum Debuggen
	 */
	public void print(){
		System.out.println("name: " + this.name);
		System.out.println("use: " + this.use);
		System.out.println("equip: " + this.equip);
		System.out.println("carry: " + this.carry);
		System.out.println("cost: " + this.cost);
		System.out.println("effect: " + this.effect);
		System.out.println("text: " + this.text);
	}
}