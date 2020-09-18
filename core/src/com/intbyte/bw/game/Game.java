package com.intbyte.bw.game;

import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.game.gameUI.MainLayerUI;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Initialization;
import com.intbyte.bw.gameAPI.environment.Block;
import com.intbyte.bw.gameAPI.environment.Drop;
import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.environment.Item;
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
                Block.defineBlock("grass", "grass.jpg", Block.STONE, 10);
                Block.defineBlock("grass2", "android.jpg", Block.STONE, 10);

                Item.addItem("test", new Item1());
                player = Player.getPlayer();
                player.getCarriedItem().addItems(Item.newItems("test", 10));
                player.setTranslateToBlock(100, 25);

                Drop drop = new Drop();
                drop.setZOnBlock(25);
                drop.setXOnBlock(100);
                Entity.spawn(drop);

                GUI.putLayer("main", MainLayerUI.getInstance());
                GUI.openLayer("main", null);
                Item.setSettableItem("test", "grass");
            }
        });
    }
}

class Item1 extends Item {


    public Item1() {
        super(1, 64);
        icon = Resource.getTexture("grass.jpg");
        strength = 5;
    }


    @Override
    public int getType() {
        return Item.PICKAXE;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    @Override
    public void readBytes(byte[] bytes) {

    }

    @Override
    public Item1 create() {
        return new Item1();
    }
}