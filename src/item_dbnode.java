public class item_dbnode{

	private String name;
	private boolean use;
	private boolean equip;
	private boolean carry;
	private int cost;
	private String effect;
	private String text;

	public item_dbnode(String name, boolean use, boolean equip, boolean carry, int cost, String effect, String text){
		this.name = name;
		this.use = use;
		this.equip = equip;
		this.carry = carry;
		this.cost = cost;
		this.effect = effect;
		this.text = text;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean getUse(){
		return use;
	}
	
	public boolean getEquip(){
		return equip;
	}
	
	public boolean getCarry(){
		return carry;
	}
	
	public int getCost(){
		return cost;
	}
	
	public String getEffect(){
		return effect;
	}
	
	public String getText(){
		return text;
	}
}