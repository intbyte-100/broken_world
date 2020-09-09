package com.intbyte.bw.core.game;

import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.graphic.ui.container.Container;
import com.intbyte.bw.gameAPI.utils.ID;


public class Player extends Entity{


    static Player player;
	private Player(){
		super();
		carriedItem = new Container(64);
		id = ID.registeredId("entity:player", 0);
	}
	public static Player getPlayer(){
		if(player == null)
			player = new Player();
		return player;
	}
	@Override
	public void render(){

	}}
