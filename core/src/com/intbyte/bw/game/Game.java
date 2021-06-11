package com.intbyte.bw.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.intbyte.bw.engine.block.Block;
import com.intbyte.bw.engine.block.CustomBlock;
import com.intbyte.bw.engine.callbacks.CallBack;
import com.intbyte.bw.engine.callbacks.Initialization;
import com.intbyte.bw.engine.entity.DropData;
import com.intbyte.bw.engine.entity.DropFactory;
import com.intbyte.bw.engine.entity.Entity;
import com.intbyte.bw.engine.entity.Player;
import com.intbyte.bw.engine.item.Container;
import com.intbyte.bw.engine.item.Item;
import com.intbyte.bw.engine.item.ItemFactory;
import com.intbyte.bw.engine.render.PointLight;
import com.intbyte.bw.engine.ui.GUI;
import com.intbyte.bw.engine.ui.Panel;
import com.intbyte.bw.engine.utils.ID;
import com.intbyte.bw.engine.utils.Resource;
import com.intbyte.bw.engine.world.World;
import com.intbyte.bw.game.gameUI.InventoryLayerUI;
import com.intbyte.bw.game.gameUI.MainLayerUI;


public class Game {
    Player player;


    public void main() {

        CallBack.addCallBack(new Initialization() {
            @Override
            public void main() {
                ID.registeredId("block:void", 0);
                GeneratedJsonData.init();

                Sprite sprite = Panel.drawPanel(400, 400, 6, 0.1f, 0.1f, 0.1f, 0.6f - 0.1f);
                Resource.addSprite(sprite, "slot");

                Block.defineLandBlock("grass", "grass.jpg");


                player = Player.getPlayer();


                Entity.addFactory(new DropFactory("stone", "test_drop", new DropData("block_stone", 1, 1)));
                Block.setDropID("stone", "test_drop");
                Entity.addFactory(new DropFactory("grass2", "test_drop2", new DropData("block_grass2", 1, 1)));
                Block.setDropID("grass2", "test_drop2");

                GUI.putLayer("main", new MainLayerUI());
                GUI.putLayer("inventory", new InventoryLayerUI());
                GUI.setLayer("main", null);

                CustomBlock block = Block.getBlock(ID.get("block:light"));
                block.setGlowing(true);
                PointLight pointLight = new PointLight();
                pointLight.getLandPointLight().set(Color.YELLOW,0, 30, 0, 1000);
                pointLight.getPointLight().set(Color.YELLOW,0, 20, 0, 500);
                block.setPointLight(pointLight);

                player.setPosition(10000, 100);
                Container container = new Container(64);
                if (World.getConfig().isCreative())
                    for (int i = 1; i < Item.getItemFactories().length; i++) {
                        ItemFactory factory;
                        if ((factory = Item.getItemFactories()[i]) == null) break;
                        container.setMaxCountItems(factory.getStacksize());
                        container.clear();
                        player.takeDrop(container.addItems(Item.newItems(factory.getId(), factory.getStacksize())));
                    }
            }
        });
    }
}



