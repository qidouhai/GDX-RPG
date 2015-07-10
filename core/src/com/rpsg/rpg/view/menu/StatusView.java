package com.rpsg.rpg.view.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.gdxQuery.GdxQueryRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.EmptyAssociation;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.Resistance;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.ui.CheckBox;
import com.rpsg.rpg.system.ui.CheckBox.CheckBoxStyle;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.display.RadarUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.TimeUtil;
import com.rpsg.rpg.view.GameViews;

public class StatusView extends DefaultIView {
	Group group,inner;
	public void init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),MenuView.stage.getBatch());
		group=(Group) $.add(new Group()).setHeight(1750).getItem();
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"menu_fg_shadow.png").disableTouch()).appendTo(group).setPosition(50, y(30)).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(.3f),Actions.moveTo(50, y(180),0.3f,Interpolation.pow4Out)));
		inner=(Group) $.add(new Group()).setHeight(1750).appendTo(group).getItem();
		$.add(new ScrollPane(group)).setSize(GameUtil.screen_width-190, GameUtil.screen_height).setX(190).appendTo(stage);
//		stage.setDebugAll(true);
		generate();
	}
	
	private void generate() {
		inner.clear();
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"m_right.png")).appendTo(inner).setPosition(570, y(150)).onClick(new Runnable() {public void run() {
			next();
		}}).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"m_right.png")).setScaleX(-1).appendTo(inner).setPosition(36, y(150)).onClick(new Runnable() {public void run() {
			prev();
		}}).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);
		$.add(new Label(parent.current.name,parent.current.name.length()>7?50:60).align(306, y(90))).appendTo(inner).addAction(Actions.fadeIn(0.2f)).setColor(1,1,1,0);;
		$.add(new Label(parent.current.jname,30).align(326, y(155)).setPad(-8)).setColor(1,1,1,0f).appendTo(inner).addAction(Actions.color(new Color(1,1,1,0.3f),0.2f));
		
		Group group=(Group) $.add(new Group()).setX(70).addAction(Actions.parallel(Actions.moveTo(70,y(320),0.5f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"info.png")).appendTo(group);
		$.add(new Label(parent.current.prop.get("level")+"",60).align(40, 90)).appendTo(group);
		$.add(new Label(parent.current.tag+"",20).align(60, 28)).appendTo(group).setColor(1,1,1,0.3f);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(327,27).setPosition(142, 75).appendTo(group);
		$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,27).setPosition(142, 75).appendTo(group).setColor(Color.valueOf("c33737")).addAction(Actions.delay(0.3f,Actions.sizeTo(((float)parent.current.prop.get("exp")/(float)parent.current.prop.get("maxexp"))*327, 27,0.4f,Interpolation.pow4Out)));
		$.add(new Label(parent.current.prop.get("exp")+"/"+parent.current.prop.get("maxexp"),20).align(300, 97).setPad(-5)).appendTo(group).setColor(Color.valueOf("3bb740"));
		$.add(new Label(parent.current.prop.get("level")+1+"",20).align(485, 88)).appendTo(group);
		$.add(new Label(parent.current.lead?"无信息":parent.current.association.name+"（等级"+parent.current.association.level+"）",20).align(320, 65)).appendTo(group);
		$.add(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"more_soc_info.png"), Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"more_soc_info_p.png"))).appendTo(group).setX(142);
		
		Group group2=(Group) $.add(new Group()).setX(70).addAction(Actions.parallel(Actions.moveTo(70,y(420),0.7f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"info2.png")).appendTo(group2);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(245,24).setPosition(120, 48).appendTo(group2);
		$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(123, 51).appendTo(group2).setColor(Color.valueOf("c33737")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("hp")/(float)parent.current.prop.get("maxhp"))*239, 18,0.6f,Interpolation.pow4Out)));
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(245,24).setPosition(120, 8).appendTo(group2);
		$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(123, 11).appendTo(group2).setColor(Color.valueOf("3762c3")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("mp")/(float)parent.current.prop.get("maxmp"))*239, 18,0.6f,Interpolation.pow4Out)));
		$.add(new Label(parent.current.prop.get("hp")+"/"+parent.current.prop.get("maxhp"),22).align(438, 68).setPad(-8)).appendTo(group2);
		$.add(new Label(parent.current.prop.get("mp")+"/"+parent.current.prop.get("maxmp"),22).align(438, 28).setPad(-8)).appendTo(group2);
		
		Group group3=(Group) $.add(new Group()).setX(70).addAction(Actions.parallel(Actions.moveTo(70,y(558),0.8f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"info3.png")).appendTo(group3);
		$.add(new Label(parent.current.prop.get("attack")+"",22).align(170, 103).setPad(-8)).appendTo(group3);
		$.add(new Label(parent.current.prop.get("defense")+"",22).align(435, 103).setPad(-8)).appendTo(group3);
		$.add(new Label(parent.current.prop.get("magicAttack")+"",22).align(170, 65).setPad(-8)).appendTo(group3);
		$.add(new Label(parent.current.prop.get("magicDefense")+"",22).align(435, 65).setPad(-8)).appendTo(group3);
		$.add(new Label(parent.current.prop.get("speed")+"",22).align(170, 27).setPad(-8)).appendTo(group3);
		$.add(new Label(parent.current.prop.get("hit")+"",22).align(435, 27).setPad(-8)).appendTo(group3);
		
		Group group4=(Group) $.add(new Group()).setX(70).addAction(Actions.parallel(Actions.moveTo(70,y(1032),1f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"equipbox.png")).appendTo(group4);
		int yoff=0;
		for(String key:parent.current.equips.keySet()){
			Equipment equip=parent.current.equips.get(key);
			yoff+=90;
			if(equip!=null) {
				$.add(Res.get(equip.icon)).appendTo(group4).setPosition(12, yoff-84).setColor(Color.RED).setSize(73,70);
				$.add(new Label(equip.illustration,16).setPos(100, yoff-55).setWidth(385)).appendTo(group4);
			}
			$.add(new Label(equip==null?"无装备":equip.name,30).setPos(equip!=null?90:240, yoff-(equip==null?37:18))).appendTo(group4);
		}
		
		Group group5=(Group) $.add(new Group()).setX(70).addAction(Actions.parallel(Actions.moveTo(70,y(1442),1f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
		$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"p.png")).appendTo(group5);
		int count=-1;
		for(String key:parent.current.resistance.keySet())
			$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+parent.current.resistance.get(key).name()+".png")).appendTo(group5).setPosition(38+(174*(++count%3)), 247-(count>=3 && count <6?115:count>=6?228:0));
		CheckBoxStyle cstyle=new CheckBoxStyle();
		cstyle.checkboxOff=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"help.png");
		cstyle.checkboxOn=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"help_p.png");// help button press
		final Image phelp=(Image) $.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"p_help.png")).appendTo(group5).setColor(1,1,1,0).getItem();//resistance help button
		$.add(new CheckBox("", cstyle, 1)).appendTo(group5).setPosition(392,342).run(new GdxQueryRunnable() {public void run(final GdxQuery self) {self.onClick(new Runnable() {public void run() {
			phelp.addAction(self.isChecked()?Actions.fadeIn(0.3f):Actions.fadeOut(0.3f));
		}});}});
		
		if(parent.current.lead){
			Group group6=(Group) $.add(new Group()).setX(70).addAction(Actions.parallel(Actions.moveTo(70,y(1702),1f,Interpolation.pow4Out),Actions.fadeIn(0.4f))).setColor(1, 1, 1,0).appendTo(inner).getItem();
			$.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"person.png")).appendTo(group6);
			$.add(Res.get(Setting.UI_BASE_IMG)).setSize(350,24).setPosition(87, 159).appendTo(group6);
			$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(90, 162).appendTo(group6).setColor(Color.valueOf("717171")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("courage")/100f)*344, 18,0.6f,Interpolation.pow4Out)));
			$.add(Res.get(Setting.UI_BASE_IMG)).setSize(350,24).setPosition(87, 121).appendTo(group6);
			$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(90, 124).appendTo(group6).setColor(Color.valueOf("717171")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("express")/100f)*344, 18,0.6f,Interpolation.pow4Out)));
			$.add(Res.get(Setting.UI_BASE_IMG)).setSize(350,24).setPosition(87, 83).appendTo(group6);
			$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(90, 87).appendTo(group6).setColor(Color.valueOf("717171")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("respect")/100f)*344, 18,0.6f,Interpolation.pow4Out)));
			$.add(Res.get(Setting.UI_BASE_IMG)).setSize(350,24).setPosition(87, 45).appendTo(group6);
			$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(90, 48).appendTo(group6).setColor(Color.valueOf("717171")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("perseverance")/100f)*344, 18,0.6f,Interpolation.pow4Out)));
			$.add(Res.get(Setting.UI_BASE_IMG)).setSize(350,24).setPosition(87, 7).appendTo(group6);
			$.add(Res.get(Setting.UI_BASE_PRO)).setSize(0,18).setPosition(90, 10).appendTo(group6).setColor(Color.valueOf("717171")).addAction(Actions.delay(0.4f,Actions.sizeTo(((float)parent.current.prop.get("knowledge")/100f)*344, 18,0.6f,Interpolation.pow4Out)));
			$.add(new Label(parent.current.prop.get("courage")+"",22).align(479, 179).setPad(-8)).appendTo(group6);
			$.add(new Label(parent.current.prop.get("express")+"",22).align(479, 141).setPad(-8)).appendTo(group6);
			$.add(new Label(parent.current.prop.get("respect")+"",22).align(479, 103).setPad(-8)).appendTo(group6);
			$.add(new Label(parent.current.prop.get("perseverance")+"",22).align(479, 65).setPad(-8)).appendTo(group6);
			$.add(new Label(parent.current.prop.get("knowledge")+"",22).align(479, 27).setPad(-8)).appendTo(group6);
			final Image pe_help=(Image) $.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_STATUS+"person_help.png")).appendTo(group6).setColor(1,1,1,0).getItem();//resistance help button
			$.add(new CheckBox("", cstyle, 1)).appendTo(group6).setPosition(392,190).run(new GdxQueryRunnable() {public void run(final GdxQuery self) {self.onClick(new Runnable() {public void run() {
				pe_help.addAction(self.isChecked()?Actions.fadeIn(0.3f):Actions.fadeOut(0.3f));
			}});}});
		}
	}
	

	public int y(int y){
		return (int) (group.getHeight()-y);
	}
	
	private void prev(){
		if(parent.getHeros().indexOf(parent.current)!=0){
			parent.click(parent.getHeros().get(parent.getHeros().indexOf(parent.current)-1));
			generate();
		}
	}
	
	private void next(){
		if(parent.getHeros().indexOf(parent.current)!=parent.getHeros().size()-1){
			parent.click(parent.getHeros().get(parent.getHeros().indexOf(parent.current)+1));
			generate();
		}
	}
	
	public void draw(SpriteBatch batch) {
		stage.draw();
	}

	public void logic() {
		stage.act();
	}

	public void onkeyDown(int keyCode) {
		if (Keys.ESCAPE == keyCode || keyCode == Keys.X) {
			this.disposed = true;
		} else
			stage.keyDown(keyCode);
		if(Keys.LEFT == keyCode)
			prev();
		if(Keys.RIGHT == keyCode)
			next();
	}

	public void dispose() {
		stage.dispose();
	}
}
