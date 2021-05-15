package com.intbyte.bw.engine.block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.intbyte.bw.engine.utils.json_wrapper.BlockWrapper;
import com.intbyte.bw.engine.physic.PhysicBlockObject;
import com.intbyte.bw.engine.utils.ID;
import com.intbyte.bw.engine.utils.Resource;


public class Block {

    public static final int STONE = 0, WOOD = 1;
    static CustomBlock[] blocks = new CustomBlock[1200];
    static CustomBlock[] landBlocks = new CustomBlock[1200];

    public static CustomBlock[] getBlocks() {
        return blocks;
    }

    public static CustomBlock getBlock(int id){
        return blocks[id];
    }
    public static CustomBlock[] getLandBlocks() {
        return landBlocks;
    }





    private static ModelInstance getModelInstance(String pathToModel, String pathToTexture) {

        ModelInstance instance = Resource.createModelInstance(pathToModel);
        if(pathToTexture.equals("null")) return instance;
        TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, Resource.getSprite(pathToTexture));
        Material material = instance.materials.get(0);
        material.clear();
        material.set(textureAttribute1);

        return instance;
    }


    public static void defineLandBlock(String id, String texture) {
        int integerId = ID.registeredId("land_block:" + id);
        CustomBlock block = new CustomBlock(-1, -1, integerId);
        block.setId(id);

        block.setModelInstance(getModelInstance("block/landblock.obj", texture));
        block.setLand(true);
        landBlocks[integerId] = block;
        Gdx.app.log("BLOCK", "defined block " + id);
    }

    public static CustomBlock defineBlock(BlockWrapper wrapper){
        int id = ID.registeredId("block:"+wrapper.getId());
        CustomBlock block = new CustomBlock(wrapper.getHealth(),wrapper.getType(),id);
        block.setId(wrapper.getId());
        block.setLevel(wrapper.getLevel());
        block.setModelInstance(getModelInstance("block/"+wrapper.getModel(),wrapper.getTexture()));
        block.setScale(wrapper.getIconScale());
        block.setPosition(wrapper.getIconRender());
        block.updateIcon();
        block.setPhysicEntity(wrapper.getBody());
        block.setScale(wrapper.getScale());
        block.setPosition(wrapper.getRender());
        blocks[id] = block;
        return block;
    }

    public static void setDropID(int blockID, int dropID) {
        blocks[blockID].setDropID(dropID);
    }

    public static void setDropID(String blockID, String dropID) {
        setDropID(ID.get("block:" + blockID),ID.get("entity:" + dropID));
    }

}
