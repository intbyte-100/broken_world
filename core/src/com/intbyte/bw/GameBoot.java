package com.intbyte.bw;

import com.badlogic.gdx.Game;
import com.intbyte.bw.core.Initialization;





public class GameBoot extends Game{
	
	@Override
	public void create(){
		this.setScreen(new Initialization(this));
	}
	@Override
	public void render(){        
	    super.render(); 
	}

	@Override
	public void dispose(){
		super.dispose();
	}
}
