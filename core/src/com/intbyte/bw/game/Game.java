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
                Block.defineBlock("grass", "block.obj", "grass.jpg", Block.STONE, 100, 10, physicBlockEntity);
                Block.defineBlock("grass2", "2block.obj", "android.jpg", Block.STONE, 1, 10, physicBlockEntity);


                Tools.newBlock("test", "icon:grass", "grass",1);
                Tools.newBlock("test1", "icon:grass2", "grass2",1);
                Tools.newPickaxe("pickaxe", "pickaxe.png", 10, 10000, 100, 100f / 12 * 1, (float) 100 / 3,2);
                Tools.newPickaxe("pickaxe2", "pickaxe.png", 10, 10000, 100, 100f / 12 * 1, (float) 100 / 3,2);

                player = Player.getPlayer();
                player.getCarriedItem().addItems(Item.newItems("test", 100));



                Entity.addFactory(new DropFactory("grass","test_drop",new DropData("test",1,10)));
                Block.setDropID("grass", "test_drop");
                Entity.addFactory(new DropFactory("grass2","test_drop2",new DropData("test1",1,10)));
                Block.setDropID("grass2", "test_drop2");

                GUI.putLayer("main", new MainLayerUI());


                GUI.putLayer("inventory", new InventoryLayerUI());
                GUI.setLayer("inventory", null);

                int id = ID.get("block:grass2");

                player.setPosition(100, 100);
                for(int i = 0; i < 100; i++)
                    World.setBlock(i, 100,id);

            }
        });
    }
}



