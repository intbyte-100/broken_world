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

    public static void defineBlock(String id, String model, String texture, int type, int level, int maxHealth, PhysicBlockObject physicEntity) {
        int integerId = ID.registeredId("block:" + id);
        CustomBlock block = new CustomBlock(maxHealth, type,integerId);
        block.setPhysicEntity(physicEntity);
        block.setModelInstance(getModelInstance("block/"+model, texture));
        block.setLevel(level);
        blocks[integerId] = block;
        block.setId(id);
        block.updateIcon();
        Gdx.app.log("BLOCK", "defined block " + id);
    }


    public static CustomBlock defineBlock(BlockWrapper wrapper){
        int id = ID.registeredId("block:"+wrapper.getId());
        System.out.println(wrapper);
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
    public static void defineBlock(String id, int integerId, String texture, int type, int level, int maxHealth) {
        ID.registeredId("block:" + id, integerId);
        CustomBlock block = new CustomBlock(maxHealth, type,integerId);
        block.setLevel(level);
        block.setId(id);
        block.setModelInstance(getModelInstance("block/block.obj", texture));
        block.setLevel(level);
        blocks[integerId] = block;
        block.updateIcon();
        Gdx.app.log("BLOCK", "defined block " + id);
    }

    public static void defineBlock(String id, Model model, String texture, int type, int level, int maxHealth) {
        int integerId = ID.registeredId("block:" + id);
        CustomBlock block = new CustomBlock(maxHealth, type,integerId);
        block.setId(id);
        TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, new Texture(Gdx.files.internal("textures/" + texture)));
        Material material = model.materials.get(0);
        material.set(textureAttribute1);
        ModelInstance instance = new ModelInstance(model);
        instance.transform.setToTranslation(0, 0, 0);

        block.setModelInstance(instance);
        blocks[integerId] = block;
        block.updateIcon();
        Gdx.app.log("BLOCK", "defined block " + id);
    }

    public static void setDropID(int blockID, int dropID) {
        blocks[blockID].setDropID(dropID);
    }

    public static void setDropID(String blockID, String dropID) {
        setDropID(ID.get("block:" + blockID),ID.get("entity:" + dropID));
    }

}
