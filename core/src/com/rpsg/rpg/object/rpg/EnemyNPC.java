package com.rpsg.rpg.object.rpg;

import java.awt.Point;
import java.util.List;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.BattleParam;

public class EnemyNPC extends RandomWalkNPC {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EnemyNPC(String id, String path, Integer width, Integer height) {
		super(id, path, width, height);
		// TODO Auto-generated constructor stub
		this.drawShadow = true;
		maxLength = 1;
		battleParam = new BattleParam();
		battleParam.enemy = 1;
	}

	public int defaultWalkSpeed = 2;
	public int accelerate1WalkSpeed = 4;
	public int accelerate2WalkSpeed = 6;
	public int accelerate1Length = 5;
	public int accelerate2Length = 3;
	public BattleParam battleParam;
	
	@Override
	public void act(float f){
		if(walked){
			List<Hero> heros = RPG.ctrl.hero.currentHeros;
			int npcDistance = 0;
			int mode = 0;
			Boolean isBattled = false;
			for(int i = 0;i<heros.size();i++){
				Hero _Hero = heros.get(i);
				
				int HeroX,HeroY;
				
				HeroX = _Hero.mapx;
				HeroY = _Hero.mapy;
				
				int dx = Math.abs(mapx-HeroX);
				int dy = Math.abs(mapy-HeroY);
				npcDistance = Math.max(dx,dy);
				
				if(((npcDistance ==1 && dx != dy) && // near checked
				(currentImageNo==1 || currentImageNo==4 || currentImageNo==7 || currentImageNo ==10) && // static Image NO. checked
				getCurrentFace() == getFaceByPoint(HeroX,HeroY)) //Check if the face is to Hero 
					|| npcDistance == 0){
							
//					RPG.ctrl.battle.start(battleParam);
//					remove();
//					this.StopRandomWalking();
//					isBattled = true;
				}
			}

			if(!isBattled){
				if(npcDistance <= accelerate2Length){
					mode = 2;
				}else if(npcDistance <= accelerate1Length){
					mode = 1;
				}

				switch(mode){
					case 1:
						walkSpeed = accelerate1WalkSpeed;
						point = new Point(-1,-1);
						speed = 10;
						break;
					case 2:
						walkSpeed = accelerate2WalkSpeed;
						point = new Point(-1,-1);
						speed = 0;
						if(npcDistance == 0){
							step = 0;
						}
						break;
					case 0:
					default: 
						speed = 60;
						walkSpeed=defaultWalkSpeed;
						break;
				}
			}
		}
		super.act(f);
	}
	
}