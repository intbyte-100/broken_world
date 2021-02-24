package com.intbyte.bw.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.game.gameUI.InventoryLayerUI;
import com.intbyte.bw.game.gameUI.MainLayerUI;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Initialization;
import com.intbyte.bw.gameAPI.environment.*;
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

                Sprite sprite = Panel.drawPanel(400,400,8,0.1f,0.1f,0.1f,0.5f);


                Resource.addSprite(sprite,"slot");
                Rectangle rectangle = new Rectangle();

                rectangle.setSize(20);
                PhysicBlockObject physicBlockEntity = new PhysicBlockObject();
                physicBlockEntity.setShape(rectangle);
                physicBlockEntity.setOffset(10, 10);


                Resource.putBlockObject("10x10",physicBlockEntity);
                Block.defineLandBlock("grass", "grass.jpg");
                ID.registeredId("block:void", 0);

                Block.defineBlock("grass", "_stone_3.g3db", "null", Block.STONE, 100, 10, physicBlockEntity);
                Block.defineBlock("grass2", "_bush_2.g3db", "null", Block.STONE, 1, 10, physicBlockEntity);
                Block.getBlock(ID.get("block:grass")).setScale(0.1f);
                Block.getBlock(ID.get("block:grass")).setPosition(0,-5,0);
                Block.getBlock(ID.get("block:grass2")).setScale(0.1f);
                Block.getBlock(ID.get("block:grass2")).setPosition(2,2.5f,2);
                Block.getBlock(ID.get("block:grass2")).updateIcon();
                Block.getBlock(ID.get("block:grass2")).setPosition(0,-5,0);

                Block.getBlock(ID.get("block:grass")).setScale(0.1f);
                Block.getBlock(ID.get("block:grass")).setPosition(2,2.5f,2);
                Block.getBlock(ID.get("block:grass")).updateIcon();
                Block.getBlock(ID.get("block:grass")).setPosition(0,-5,0);
                Tools.newBlock("test", "icon:grass", "grass",1);
                Tools.newBlock("test1", "icon:grass2", "grass2",1);
                Tools.newPickaxe("pickaxe", "pickaxe.png", 10, 10000, 100, 100f / 12 * 1, (float) 100 / 3,2);
                Tools.newPickaxe("pickaxe2", "pickaxe.png", 10, 10000, 100, 100f / 12 * 1, (float) 100 / 3,2);

                player = Player.getPlayer();
                player.getCarriedItem().addItems(Item.newItems("test1", 100));



                Entity.addFactory(new DropFactory("grass","test_drop",new DropData("test",1,10)));
                Block.setDropID("grass", "test_drop");
                Entity.addFactory(new DropFactory("grass2","test_drop2",new DropData("test1",1,10)));
                Block.setDropID("grass2", "test_drop2");

                GUI.putLayer("main", new MainLayerUI());


                GUI.putLayer("inventory", new InventoryLayerUI());
                GUI.setLayer("main", null);

                int id = ID.get("block:grass2");

                player.setPosition(100, 100);
                player.takeDrop(new Container(64).addItems(
                        Item.newItems("test",64)));

            }
        });
    }
}



