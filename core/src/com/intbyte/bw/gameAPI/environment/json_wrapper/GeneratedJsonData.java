package com.intbyte.bw.gameAPI.environment.json_wrapper;

import com.intbyte.bw.gameAPI.environment.*;
import com.intbyte.bw.gameAPI.physic.PhysicBlockObject;
import com.badlogic.gdx.math.Rectangle;
import com.intbyte.bw.gameAPI.utils.Resource;

//do not delete, this file was automatically generated by json data generator from json files
public class GeneratedJsonData{
    public static void init(){
        BlockWrapper blockWrapper = new BlockWrapper();
        Block.CustomBlock block;
        PhysicBlockObject object = new PhysicBlockObject();
        object.setShape(new Rectangle(0,0,20,20));
        object.setOffset(10, 10);
        Resource.putBlockObject("10x10",object);
        
        blockWrapper.reset();
        blockWrapper.setId("mashroom");
		blockWrapper.setModel("_mashroom_2.g3db");
		blockWrapper.setTexture("Ekfs_bush_map.png");
		blockWrapper.setHealth(10);
		blockWrapper.setBody(object);
		blockWrapper.setIconScale(0.1f);
		blockWrapper.getIconRender().set(0f,-5f,0f);
		blockWrapper.setType(Block.WOOD);
		blockWrapper.setLevel(20);
		block = Block.defineBlock(blockWrapper);
		block.setScale(0.1f);
		block.setPosition(0f,-5f,0f);
        
        blockWrapper.reset();
        blockWrapper.setId("grass");
		blockWrapper.setModel("_stone_3.g3db");
		blockWrapper.setTexture("Ekfs_bush_map.png");
		blockWrapper.setHealth(10);
		blockWrapper.setBody(object);
		blockWrapper.setIconScale(0.1f);
		blockWrapper.getIconRender().set(2f,2.5f,2f);
		blockWrapper.setType(Block.STONE);
		blockWrapper.setLevel(20);
		block = Block.defineBlock(blockWrapper);
		block.setScale(0.1f);
		block.setPosition(0f,-5f,0f);
        
        blockWrapper.reset();
        blockWrapper.setId("spruce");
		blockWrapper.setModel("spruce_1.obj");
		blockWrapper.setTexture("Lp_trees_texture_atlas.png");
		blockWrapper.setHealth(20);
		blockWrapper.setBody(object);
		blockWrapper.setIconScale(1f);
		blockWrapper.getIconRender().set(0f,0f,0f);
		blockWrapper.setType(Block.WOOD);
		blockWrapper.setLevel(2);
		block = Block.defineBlock(blockWrapper);
		block.setScale(4f);
		block.setPosition(0f,-5f,0f);
        
        blockWrapper.reset();
        blockWrapper.setId("grass2");
		blockWrapper.setModel("_bush_2.g3db");
		blockWrapper.setTexture("Ekfs_bush_map.png");
		blockWrapper.setHealth(10);
		blockWrapper.setBody(object);
		blockWrapper.setIconScale(0.1f);
		blockWrapper.getIconRender().set(0f,-5f,0f);
		blockWrapper.setType(Block.WOOD);
		blockWrapper.setLevel(20);
		block = Block.defineBlock(blockWrapper);
		block.setScale(0.1f);
		block.setPosition(0f,-5f,0f);


		Tools.newPickaxe("pickaxe","pickaxe.png",20,10,10,100f / 12 * 1,10,1);
		Tools.newBlock("block_mashroom","icon:mashroom","mashroom",0);
		Tools.newBlock("block_grass","icon:grass","grass",0);
		Tools.newBlock("block_spruce","icon:spruce","spruce",0);
		Tools.newBlock("block_grass2","icon:grass2","grass2",0);
    }
}