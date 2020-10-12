package com.intbyte.bw.game;

import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
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
                Block.defineBlock("grass", "grass.jpg", Block.STONE,100, 10);
                Block.defineBlock("grass2", "android.jpg", Block.STONE,1, 10);

                Model model = Resource.getObjModel("block/block.obj");
                TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, Resource.getTexture("grass.jpg"));
                Material material = model.materials.get(0);
                material.set(textureAttribute1);

                Resource.addTexture(Resource.getIconFromModel(new ModelInstance(model)),"icongrass");
                Tools.newBlock("test","icongrass","grass");
                Tools.newPickaxe("pickaxe","pickaxe.png",10,10,10,100f/2,(float) 100/3);
                player = Player.getPlayer();
                player.getCarriedItem().addItems(Item.newItems("test", 100));
                player.setTranslateToBlock(100, 25);


                Entity.addFactory(new TestDropFactory());

                Block.setDropID("grass","test_drop");

                GUI.putLayer("main", MainLayerUI.getInstance());
                GUI.openLayer("main", null);

            }
        });
    }
}



