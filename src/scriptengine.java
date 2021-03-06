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

	private player p = null;

	public scriptengine(player p){
		this.p = p;
	}
	
	public void executescript(String command){
	    String[] data = command.split(";");
		//System.out.println("Leng: " + data.length);
		for(int i=0; i<data.length; i++){
		String[] s = data[i].split(" ");
		if(s[0].equals("player")){
			if(s[1].equals("curhp")){
				if(s[2].equals("increase")){
					this.p.setHP(this.p.getHP() + Integer.parseInt(s[3]));
					if(this.p.getHP() > this.p.getHPMax()){
						this.p.setHP(this.p.getHPMax());
					}
				}
				else if(s[2].equals("decrease")){
					this.p.setHP(this.p.getHP() - Integer.parseInt(s[3]));
					if(this.p.getHP() < 0){
						this.p.die();
					}
				}
			}
			else if(s[1].equals("curmp")){
				if(s[2].equals("increase")){
					this.p.setMP(this.p.getMP() + Integer.parseInt(s[3]));
					if(this.p.getMP() > this.p.getMPMax()){
						this.p.setMP(this.p.getMPMax());
					}
				}
				else if(s[2].equals("decrease")){
					this.p.setHP(this.p.getHP() - Integer.parseInt(s[3]));
					if(this.p.getHP() < 0){
						this.p.die();
					}
				}
			}
			else if(s[1].equals("maxhp")){
				if(s[2].equals("increase")){
					this.p.setHPMax(this.p.getHPMax() + Integer.parseInt(s[3]));
				}
				else if(s[2].equals("decrease")){
					this.p.setHPMax(this.p.getHPMax() - Integer.parseInt(s[3]));
					if(this.p.getHPMax() < 1){
						this.p.setHPMax(1);
					}
				}
			}
			else if(s[1].equals("maxmp")){
				if(s[2].equals("increase")){
					this.p.setMPMax(this.p.getMPMax() + Integer.parseInt(s[3]));
				}
				else if(s[2].equals("decrease")){
					this.p.setMPMax(this.p.getMPMax() - Integer.parseInt(s[3]));
					if(this.p.getMPMax() < 1){
						this.p.setMPMax(1);
					}
				}
			}
			else if(s[1].equals("money")){
				if(s[2].equals("increase")){
					this.p.money = this.p.money + Integer.parseInt(s[3]);
				}
				else if(s[2].equals("decrease")){
					this.p.money = this.p.money - Integer.parseInt(s[3]);
					if(this.p.money>0){ this.p.money = 0; }
				}
			}
			else if(s[1].equals("set")){
				if(s[2].equals("damage")){
					this.p.setDamageWert(Integer.parseInt(s[3]));
				}
				else if(s[2].equals("armor")){
					this.p.setArmorWert(Integer.parseInt(s[3]));
				}
			}
			else if(s[1].equals("equip")){
				if(s[2].equals("weapon")){
					if(s[3].equals("sword")){
						//p.weapon = "sword" // schwert muss als grafik angezeigt werden
					}
				}
			}
		}
		else{
			return;
		}
		}
	}

}