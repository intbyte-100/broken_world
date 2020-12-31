package com.intbyte.bw.game;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Rectangle;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.game.gameUI.InventoryLayerUI;
import com.intbyte.bw.game.gameUI.MainLayerUI;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Initialization;
import com.intbyte.bw.gameAPI.environment.Block;
import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.environment.Item;
import com.intbyte.bw.gameAPI.environment.Tools;
import com.intbyte.bw.gameAPI.graphic.ui.GUI;
import com.intbyte.bw.gameAPI.physic.PhysicBlockObject;
import com.intbyte.bw.gameAPI.utils.ID;
import com.intbyte.bw.gameAPI.utils.Resource;


public class Game {
    Player player;


    public void main() {

        CallBack.addCallBack(new Initialization() {
            @Override
            public void main() {

                Rectangle rectangle = new Rectangle();
                rectangle.setSize(20);
                PhysicBlockObject physicBlockEntity = new PhysicBlockObject();
                physicBlockEntity.setShape(rectangle);
                physicBlockEntity.setOffset(10, 10);


                Block.defineLandBlock("grass", "grass.jpg");
                ID.registeredId("block:void", 0);
                Block.defineBlock("grass", "block.obj", "grass.jpg", Block.STONE, 100, 10, physicBlockEntity);
                Block.defineBlock("grass2", "2block.obj", "android.jpg", Block.STONE, 1, 10, physicBlockEntity);

                Model model = Resource.getObjModel("block/block.obj");
                TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, Resource.getSprite("grass.jpg"));
                Material material = model.materials.get(0);
                material.set(textureAttribute1);

                Resource.addSprite(Resource.getIconFromModel(new ModelInstance(model)), "icongrass");
                Resource.addSprite(Resource.getIconFromModel(new ModelInstance(Resource.getObjModel("block/2block.obj"))), "icongrass2");

                Tools.newBlock("test", "icongrass", "grass");
                Tools.newBlock("test1", "icongrass2", "grass2");


                Tools.newPickaxe("pickaxe", "pickaxe.png", 10, 10000, 100, 100f / 6 * 1, (float) 100 / 3);
                Tools.newPickaxe("pickaxe2", "pickaxe.png", 10, 10000, 100, 100f / 6 * 1, (float) 100 / 3);
                player = Player.getPlayer();
                player.getCarriedItem().addItems(Item.newItems("test", 100));
                player.setPosition(100, 25);


                Entity.addFactory(new TestDropFactory());
                Block.setDropID("grass", "test_drop");

                GUI.putLayer("main", new MainLayerUI());
                GUI.setLayer("main", null);

                GUI.putLayer("inventory", new InventoryLayerUI());


            }
        });
    }
}



