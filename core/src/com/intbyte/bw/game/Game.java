package com.intbyte.bw.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.game.gameUI.InventoryLayerUI;
import com.intbyte.bw.game.gameUI.MainLayerUI;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Initialization;
import com.intbyte.bw.gameAPI.environment.*;
import com.intbyte.bw.gameAPI.environment.json_wrapper.GeneratedJsonData;
import com.intbyte.bw.gameAPI.ui.GUI;
import com.intbyte.bw.gameAPI.ui.Panel;
import com.intbyte.bw.gameAPI.physic.PhysicBlockObject;
import com.intbyte.bw.gameAPI.utils.ID;
import com.intbyte.bw.gameAPI.utils.Resource;


public class Game {
    Player player;


    public void main() {

        CallBack.addCallBack(new Initialization() {
            @Override
            public void main() {
                ID.registeredId("block:void", 0);
                GeneratedJsonData.init();

                Sprite sprite = Panel.drawPanel(400,400,6,0.1f,0.1f,0.1f,0.6f-0.1f);
                Resource.addSprite(sprite,"slot");

                Block.defineLandBlock("grass", "grass.jpg");



                player = Player.getPlayer();



                Entity.addFactory(new DropFactory("grass","test_drop",new DropData("block_grass",1,1)));
                Block.setDropID("grass", "test_drop");
                Entity.addFactory(new DropFactory("grass2","test_drop2",new DropData("block_grass2",1,1)));
                Block.setDropID("grass2", "test_drop2");

                GUI.putLayer("main", new MainLayerUI());
                GUI.putLayer("inventory", new InventoryLayerUI());
                GUI.setLayer("main", null);



                player.setPosition(100, 100);
                Container container = new Container(64);
                if(World.getConfig().isCreative())
                    for (int i = 1; i < Item.getItemFactories().length; i++) {
                        ItemFactory factory;
                        if((factory = Item.getItemFactories()[i]) == null) break;
                        container.setMaxCountItems(factory.getStacksize());
                        container.clear();
                        player.takeDrop(container.addItems(Item.newItems(factory.getId(),factory.getStacksize())));
                    }
            }
        });
    }
}



