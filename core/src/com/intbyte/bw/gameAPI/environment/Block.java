package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.gameAPI.environment.json_wrapper.BlockWrapper;
import com.intbyte.bw.gameAPI.graphic.Graphic;
import com.intbyte.bw.gameAPI.physic.PhysicBlockObject;
import com.intbyte.bw.gameAPI.utils.ID;
import com.intbyte.bw.gameAPI.utils.Resource;

import java.util.HashMap;

import static com.intbyte.bw.gameAPI.graphic.Graphic.ENVIRONMENT;


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
        block.id = id;

        block.modelInstance = getModelInstance("block/landblock.obj", texture);
        landBlocks[integerId] = block;
        Gdx.app.log("BLOCK", "defined block " + id);
    }

    public static void defineBlock(String id, String model, String texture, int type, int level, int maxHealth, PhysicBlockObject physicEntity) {
        int integerId = ID.registeredId("block:" + id);
        CustomBlock block = new CustomBlock(maxHealth, type,integerId);
        block.setPhysicEntity(physicEntity);
        block.modelInstance = getModelInstance("block/"+model, texture);
        block.level = level;
        blocks[integerId] = block;
        block.id = id;
        block.updateIcon();
        Gdx.app.log("BLOCK", "defined block " + id);
    }


    public static CustomBlock defineBlock(BlockWrapper wrapper){
        int id = ID.registeredId("block:"+wrapper.getId());
        CustomBlock block = new CustomBlock(wrapper.getHealth(),wrapper.getType(),id);
        block.id = wrapper.getId();
        block.level = wrapper.getLevel();
        block.modelInstance = getModelInstance(wrapper.getModel(),wrapper.getTexture());
        block.scale = wrapper.getIconScale();
        block.setPosition(wrapper.getIconRender());
        block.updateIcon();
        block.scale = wrapper.getScale();
        block.setPosition(wrapper.getRender());
        blocks[id] = block;
        block.updateIcon();
        return block;
    }
    public static void defineBlock(String id, int integerId, String texture, int type, int level, int maxHealth) {
        ID.registeredId("block:" + id, integerId);
        CustomBlock block = new CustomBlock(maxHealth, type,integerId);
        block.level = level;
        block.id = id;
        block.modelInstance = getModelInstance("block/block.obj", texture);
        block.level = level;
        blocks[integerId] = block;
        block.updateIcon();
        Gdx.app.log("BLOCK", "defined block " + id);
    }

    public static void defineBlock(String id, Model model, String texture, int type, int level, int maxHealth) {
        int integerId = ID.registeredId("block:" + id);
        CustomBlock block = new CustomBlock(maxHealth, type,integerId);
        block.id = id;
        TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, new Texture(Gdx.files.internal("textures/" + texture)));
        Material material = model.materials.get(0);
        material.set(textureAttribute1);
        ModelInstance instance = new ModelInstance(model);
        instance.transform.setToTranslation(0, 0, 0);

        block.modelInstance = instance;
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

    public static class CustomBlock {
        public final int MAX_HEATH, TYPE, ID;
        protected int level;
        public String id;
        HashMap<Integer, BlockExtraData> blockData;
        private int dropID, bodyID;
        private ModelInstance modelInstance;
        private PhysicBlockObject physicEntity;
        private float scale = 1;
        private Vector3 position = new Vector3(0,0,0);
        public ModelInstance getModelInstance() {
            return modelInstance;
        }

        public CustomBlock(int maxHealth, int type, int id) {
            MAX_HEATH = maxHealth;
            TYPE = type;
            blockData = new HashMap<>();
            this.ID = id;
        }


        public BlockExtraData newData() {

            return new BlockExtraData() {
                int health = MAX_HEATH;

                @Override
                public int getHealth() {
                    return health;
                }

                @Override
                public void setHealth(int health) {

                    this.health = health;
                    if (this.health < 0) this.health = 0;
                }

                @Override
                public int getID() {
                    return ID;
                }

                @Override
                public byte[] getBytes() {
                    return new byte[0];
                }

                @Override
                public void readData(byte[] data) {

                }
            };
        }

        void setPhysicEntity(PhysicBlockObject physicEntity) {
            this.physicEntity = physicEntity;
        }

        public PhysicBlockObject getPhysicEntity() {
            return physicEntity;
        }

        public int getDropID() {
            return dropID;
        }

        public int getBodyID() {
            return bodyID;
        }


        protected void setDropID(int dropID) {
            this.dropID = dropID;
        }

        public int getLevel() {
            return level;
        }

        public void render(float x, float y, float z) {
            render(modelInstance,x,y,z);
        }

        public void render(ModelInstance modelInstance,float x, float y, float z){
            modelInstance.transform.setToTranslation(x+position.x, y+position.y, z+position.z);
            float scaleX = modelInstance.transform.getScaleX(),
                    scaleY = modelInstance.transform.getScaleY(),
                    scaleZ = modelInstance.transform.getScaleZ();
            modelInstance.transform.scale(scale, scale, scale);
            Graphic.getModelBatch().render(modelInstance, Graphic.ENVIRONMENT);
            modelInstance.transform.scale(scaleX,scaleY,scaleZ);
        }


        public void setPosition(float x, float y, float z) {
            position.set(x, y, z);
        }

        public void setPosition(Vector3 position) {
            this.position.set(position);
        }

        public void updateIcon(){
            Resource.addSprite(Resource.getIconFromModel(modelInstance,position.x,position.y,position.z,scale), "icon:"+id);
        }
        public void setScale(float scale) {
            this.scale = scale;
        }

        public float getScale() {
            return scale;
        }
    }
}
