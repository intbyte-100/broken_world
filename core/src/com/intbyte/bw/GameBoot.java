package com.intbyte.bw;

import com.badlogic.gdx.Game;
import com.intbyte.bw.engine.Initialization;
import com.intbyte.bw.engine.utils.Resource;


public class GameBoot extends Game{
	long init = 0;
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
		Resource.dispose();
		System.exit(0);
	}
}
