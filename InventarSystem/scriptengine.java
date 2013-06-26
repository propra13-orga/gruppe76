/*
player
	curhp
	maxhp
	equip
	set
		armor
		damage
*/

public class scriptengine{

	private player pc;

	public scriptengine(player pc){
		this.pc = pc;
	}
	
	public boolean executescript(String command){
		String[] s = command.split(" ");
		if(s[0].equals("player")){
			if(s[1].equals("curhp"){
				if(s[2].equals("increase"){
					p.hp = p.rhp + Integer.parseInt(s[3]);
					if(p.hp > p.hpMax){
						p.hp = p.hpMax;
					}
				}
				else if(s[2].equals("decrease"){
					p.hp = p.hp - Integer.parseInt(s[3]);
					if(p.hp < 0){
						//p.die();
					}
				}
			}
			else if(s[1].equals("maxhp"){
				if(s[2].equals("increase"){
					p.hpMax = p.hpMax + Integer.parseInt(s[3]);
				}
				else if(s[2].equals("decrease"){
					p.hpMax = p.hpMax - Integer.parseInt(s[3]);
					if(p.hpMax < 1){
						p.hpMax = 1;
					}
				}
			}
			else if(s.[1].equals("set"){
				if(s[2].equals("armor"){
					p.armorWert = Integer.parseInt(s[3]);
				}
				else if(s[2].equals("weapon"){
					if(s[3].equals("sword"){
						//p.weapon = "sword" // schwert muss als grafik angezeigt werden
					}
				}
				else if(s[2].equals("damage"){
					p.damageWert = Integer.parseInt(s[3]);
				}
			}
		}
		else{
			return false;
		}
	}

}