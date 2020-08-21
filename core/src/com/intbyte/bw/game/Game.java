package com.intbyte.bw.game;

import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.game.gameUI.GameGUI;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Initialization;
import com.intbyte.bw.gameAPI.callbacks.TouchOnBlock;
import com.intbyte.bw.gameAPI.environment.World;
import com.intbyte.bw.gameAPI.utils.ID;


public class Game{
	Player player;
	GameGUI gameGUI;
	public void main(){
		CallBack.addCallBack(new TouchOnBlock() {
			@Override
			public void main(int x, int z) {
				if (player.getCarriedItem().getId() == ID.get("item:test"))
					World.setBlock(x, z, ID.get("block:grass"));
				if (player.getCarriedItem().getId() == ID.get("item:test2"))
					World.setBlock(x, z, ID.get("block:grass2"));
			}
		});
		CallBack.addCallBack(new Initialization(){
				@Override
				public void main(){
					player = Player.getPlayer();
					for(int i = 0; i < 100; i+=5)
						World.setBlock(100,i,1);
					player.setTranslateToBlock(100,25);
					gameGUI = new GameGUI();
					gameGUI.main();
				}
			});
	}
}
