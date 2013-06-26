import java.io.*;
import java.util.*;
import java.util.TreeMap;

public class item_db{

	// Einträge inder Datenbank
	private HashMap<Integer, item_dbnode> itemList = null;
	
	public item_db(){
		itemList = new HashMap<Integer, item_dbnode>(25);
		// Datenbank laden
		loadDB("items.txt");
		
		System.out.println(itemList.size() + " Items in Datenbank geladen.");
		System.out.println(getItem(0).getName());
		System.out.println(getItem(1).getName());
		System.out.println(getItem(2).getName());
	}
	
	private void loadDB(String path){
		BufferedReader in = null;
		try{
			// datenbankdatei öffnen
			in = new BufferedReader(new FileReader(path));
			
			String line = null;
			int headercheck = 0;
			
			// Header überprüfen
			while( (line = in.readLine()) != null ){
				if( line.equals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" ) ) headercheck++;
				// Datei parsen wenn es eine Itembeschreibung ist
				if( line.equals("<itemlist>") ) headercheck++;
				
				if(headercheck == 2)break;
			}
			if(headercheck != 2) throw new Exception("Dateiformat ungueltig.");
			
			// Datei zeilenweise parsen bis Ende der Datei erreicht ist
			while( (line = in.readLine()) != null ){
				line = line.trim();
				// Wenn Endtag erreicht ist war das Erstellen erfolgreich.
				if( line.equals("</itemlist>") == true) return;
				
				//Solange das Ende nicht erreicht ist lesen und erstellen wir Einträge für die Datenbank
				if( line.trim().equals("<item>") == true ){
					int attributCount = 0;
					String name = "";
					boolean use = false;
					boolean equip = false;
					boolean carry = false;
					int cost = 0;
					String effect = "";
					String text = "";
					
					while( (line = in.readLine()) != null ){
						line = line.trim();
						if( line.equals("</item>") == true ){
							if( attributCount != 7) throw new Exception("Ungueltige Parameter fuer Item.");
							break;
						}
						if( line.length() < 4) continue;						// leere Zeilen überspringen
						if( line.substring(0,4).equals("<!--") ) continue;		// auskommentierte Zeilen überspringen
						
						// attribute lesen
						if( line.substring(0,6).equals("<name>") ){
							int posEnd = line.indexOf("<", 6);
							if( posEnd == -1) throw new Exception("Ungueltiges Dateiformat.");
							name = line.substring(6, posEnd);
							attributCount++;
							if( line.substring(posEnd).equals("</name>") == false ) throw new Exception("Ungueltiges Dateiformat.");
						}
						
						if( line.substring(0,5).equals("<use>") ){
							int posEnd = line.indexOf("<", 5);
							if( posEnd == -1) throw new Exception("Ungueltiges Dateiformat.");
							use = (line.substring(5, posEnd).equals("true")) ? true : false;
							attributCount++;
							if( line.substring(posEnd).equals("</use>") == false ) throw new Exception("Ungueltiges Dateiformat.");
						}
						
						if( line.substring(0,7).equals("<equip>") ){
							int posEnd = line.indexOf("<", 7);
							if( posEnd == -1) throw new Exception("Ungueltiges Dateiformat.");
							equip = (line.substring(7, posEnd).equals("true")) ? true : false;
							attributCount++;
							if( line.substring(posEnd).equals("</equip>") == false ) throw new Exception("Ungueltiges Dateiformat.");
						}
						
						if( line.substring(0,7).equals("<carry>") ){
							int posEnd = line.indexOf("<", 7);
							if( posEnd == -1) throw new Exception("Ungueltiges Dateiformat.");
							carry = (line.substring(7, posEnd).equals("true")) ? true : false;
							attributCount++;
							if( line.substring(posEnd).equals("</carry>") == false ) throw new Exception("Ungueltiges Dateiformat.");
						}
						
						if( line.substring(0,6).equals("<cost>") ){
							int posEnd = line.indexOf("<", 6);
							if( posEnd == -1) throw new Exception("Ungueltiges Dateiformat.");
							cost = Integer.parseInt(line.substring(6, posEnd));
							attributCount++;
							if( line.substring(posEnd).equals("</cost>") == false ) throw new Exception("Ungueltiges Dateiformat.");
						}
						
						if( line.substring(0,8).equals("<effect>") ){
							int posEnd = line.indexOf("<", 8);
							if( posEnd == -1) throw new Exception("Ungueltiges Dateiformat.");
							effect = line.substring(8, posEnd);
							attributCount++;
							if( line.substring(posEnd).equals("</effect>") == false ) throw new Exception("Ungueltiges Dateiformat.");
						}
						
						if( line.substring(0,6).equals("<text>") ){
							int posEnd = line.indexOf("<", 6);
							if( posEnd == -1) throw new Exception("Ungueltiges Dateiformat.");
							text = line.substring(6, posEnd);
							attributCount++;
							if( line.substring(posEnd).equals("</text>") == false ) throw new Exception("Ungueltiges Dateiformat.");
						}
					}
					if(line == null) break;
					
					// Neues Item zur datenbank hinzufügen
					item_dbnode tmp = new item_dbnode(name, use, equip, carry, cost, effect, text);
					itemList.put(itemList.size(), tmp);
					
					System.out.println("Debug>> Erstelle Item:" + name + "|" + use + "|" + equip + "|" + carry + "|" + cost + "|" + effect + "|" +text + "|");
				}
				
			}
			
			throw new Exception("Dateiende beim Parsen erreicht.");
		}
		catch(Exception e){
			System.out.println("Datenbank kann nicht erstellt werden.\n>>" + e.getMessage());
			System.exit(-1);
		}
		finally{
			try{
				in.close();
			}
			catch(Exception e){
				System.out.println("Dateioperation fehlgeschlagen.\n>>" + e.getMessage());
				System.exit(-1);
			}
		}
	}

	public item_dbnode getItem(int index){
		return itemList.get(index);
	}

}