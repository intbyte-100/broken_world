package com.intbyte.bw.game;

import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.game.gameUI.MainLayerUI;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Initialization;
import com.intbyte.bw.gameAPI.environment.*;
import com.intbyte.bw.gameAPI.graphic.ui.GUI;
import com.intbyte.bw.gameAPI.utils.ID;
import com.intbyte.bw.gameAPI.utils.Resource;


public class Game {
    Player player;


    public void main() {

        CallBack.addCallBack(new Initialization() {
            @Override
            public void main() {

                Block.defineLandBlock("grass", "grass.jpg");
                ID.registeredId("block:void", 0);
                Block.defineBlock("grass", "grass.jpg", Block.STONE,50, 10);
                Block.defineBlock("grass2", "android.jpg", Block.STONE,1, 10);

                Item.addItemFactory("test", new GrassBlockFactory());
                Item.addItemFactory("pickaxe", Tools.newPickaxe("pickaxe","pickaxe.png",100,2,10));
                player = Player.getPlayer();
                player.getCarriedItem().addItems(Item.newItems("test", 100));
                player.setTranslateToBlock(100, 25);


                Entity.addFactory(new TestDropFactory());

                Block.setDropID("grass","test_drop");

                GUI.putLayer("main", MainLayerUI.getInstance());
                GUI.openLayer("main", null);
                Item.setSettableItem("test", "grass");
            }
        });
    }
}

class Item1 extends Item {


    public Item1() {
        itemData = new ItemData(200,1,1);
        icon = Resource.getTexture("grass.jpg");
    }


    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    @Override
    public void readBytes(byte[] bytes) {

    }
}


class GrassBlockFactory extends ItemFactory{

    public GrassBlockFactory() {
        super("test",Item.BLOCK);
    }

    @Override
    public Item createItem() {
        return new Item1();
    }
}