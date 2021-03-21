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

                Sprite sprite = Panel.drawPanel(400,400,8,0.1f,0.1f,0.1f,0.5f);
                Resource.addSprite(sprite,"slot");

                Block.defineLandBlock("grass", "grass.jpg");



                player = Player.getPlayer();
                player.getCarriedItem().addItems(Item.newItems("block_grass2", 100));



                Entity.addFactory(new DropFactory("grass","test_drop",new DropData("block_grass",1,1)));
                Block.setDropID("grass", "test_drop");
                Entity.addFactory(new DropFactory("grass2","test_drop2",new DropData("block_grass2",1,1)));
                Block.setDropID("grass2", "test_drop2");

                GUI.putLayer("main", new MainLayerUI());
                GUI.putLayer("inventory", new InventoryLayerUI());
                GUI.setLayer("main", null);



                player.setPosition(100, 100);
                player.takeDrop(new Container(64).addItems(
                        Item.newItems("block_grass",64)));
                player.takeDrop(new Container(64).addItems(
                        Item.newItems("pickaxe",4)));
                player.takeDrop(new Container(64).addItems(
                        Item.newItems("block_mashroom",4)));

            }
        });
    }
}



