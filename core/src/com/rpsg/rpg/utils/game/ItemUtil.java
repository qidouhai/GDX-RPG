package com.rpsg.rpg.utils.game;


import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.base.items.SpellCard;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.utils.display.AlertUtil;

public class ItemUtil {

	public static void useEquip(Hero hero,Equipment equip){
		if(hero.equips.get(equip.equipType)!=null){
			Equipment tmp=hero.equips.get(equip.equipType);
			RPG.global.getItems("equipment").add(tmp);
			replace(hero, equip, false);
		}
		hero.equips.put(equip.equipType, equip);
		replace(hero, equip, true);
		RPG.global.getItems("equipment").remove(equip);
	}
	public static boolean throwItem(String type,Item item){
		return throwItem(type, item, 1);
	}
	public static boolean throwItem(String type,Item item,int count){
		if(item.count==0){
			if(!RPG.global.getItems(type).remove(item)){
				AlertUtil.add("非法操作。", AlertUtil.Red);
				return false;
			}
		}else{
			if(item.count>=count){
				item.count-=count;
				if(item.count<=0){
					RPG.global.getItems(type).remove(item);
					return false;
				}
			}else{
				RPG.global.getItems(type).remove(item);
				return false;
			}
		}
		return true;
	}
	
	public static void takeOffEquip(Hero hero,String equipType){
		if(hero!=null && equipType!=null && hero.equips.get(equipType)!=null){
			Equipment tmp=hero.equips.get(equipType);
			replace(hero, tmp, false);
			RPG.global.getItems("equipment").add(tmp);
			hero.equips.put(equipType, null);
		}
	}
	
	private static void replace(Hero hero,Equipment equip,boolean add){
		r("maxhp",hero,equip,add);
		r("maxmp",hero,equip,add);
		r("attack",hero,equip,add);
		r("magicAttack",hero,equip,add);
		r("defense",hero,equip,add);
		r("magicDefense",hero,equip,add);
		r("speed",hero,equip,add);
		r("hit",hero,equip,add);
	}
	
	private static void r(String key,Hero hero,Equipment equip,boolean add){
		hero.prop.put(key, add?hero.prop.get(key)+equip.prop.get(key):hero.prop.get(key)-equip.prop.get(key));
	}
	
	private static boolean include=false;
	public static void addItem(Item item){
		String type=item.getClass().getSuperclass().getSimpleName().toLowerCase();
		if(!(item instanceof Equipment || item instanceof SpellCard)){
			include=false;
			for (Item i : RPG.global.getItems(type)) {
				if(i.getClass().equals(item.getClass())){
					include=true;
					i.count++;
				}
			}

			if(!include)
				RPG.global.getItems(type).add(item);
		}else
			RPG.global.getItems(type).add(item);
	}
}
