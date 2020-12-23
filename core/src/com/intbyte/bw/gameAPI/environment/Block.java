package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.intbyte.bw.gameAPI.physic.PhysicBlockEntity;
import com.intbyte.bw.gameAPI.utils.ID;
import com.intbyte.bw.gameAPI.utils.Resource;

import java.util.HashMap;

import static com.intbyte.bw.gameAPI.graphic.Graphic.ENVIRONMENT;
import static com.intbyte.bw.gameAPI.graphic.Graphic.MODEL_BATCH;

public class Block {

    public static final int STONE = 0, WOOD = 1;
    static CustomBlock[] blocks = new CustomBlock[1200];
    static CustomBlock[] landBlocks = new CustomBlock[1200];

    public static CustomBlock[] getBlocks() {
        return blocks;
    }

    public static CustomBlock[] getLandBlocks() {
        return landBlocks;
    }

    private static ModelInstance getModelInstance(String pathToModel, String pathToTexture) {
        Model model = Resource.getObjModel(pathToModel);
        TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, Resource.getSprite(pathToTexture));
        Material material = model.materials.get(0);
        material.set(textureAttribute1);
        ModelInstance instance = new ModelInstance(model);
        return instance;
    }

    public static void defineLandBlock(String id, String texture) {
        int integerId = ID.registeredId("land_block:" + id);
        CustomBlock block = new CustomBlock(-1, -1, integerId);


        block.modelInstance = getModelInstance("block/landblock.obj", texture);
        landBlocks[integerId] = block;
        Gdx.app.log("BLOCK", "defined block " + id);
    }

    public static void defineBlock(String id, String model, String texture, int type, int level, int maxHealth, PhysicBlockEntity physicEntity) {
        int integerId = ID.registeredId("block:" + id);
        CustomBlock block = new CustomBlock(maxHealth, type,integerId);
        block.setPhysicEntity(physicEntity);
        block.modelInstance = getModelInstance("block/"+model, texture);
        block.level = level;
        blocks[integerId] = block;
        Gdx.app.log("BLOCK", "defined block " + id);
    }

    public static void defineBlock(String id, int integerId, String texture, int type, int level, int maxHealth) {
        ID.registeredId("block:" + id, integerId);
        CustomBlock block = new CustomBlock(maxHealth, type,integerId);
        block.level = level;
        block.modelInstance = getModelInstance("block/block.obj", texture);
        block.level = level;
        blocks[integerId] = block;
        Gdx.app.log("BLOCK", "defined block " + id);
    }

    public static void defineBlock(String id, Model model, String texture, int type, int level, int maxHealth) {
        int integerId = ID.registeredId("block:" + id);
        CustomBlock block = new CustomBlock(maxHealth, type,integerId);
        TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, new Texture(Gdx.files.internal("textures/" + texture)));
        Material material = model.materials.get(0);
        material.set(textureAttribute1);
        ModelInstance instance = new ModelInstance(model);
        instance.transform.setToTranslation(0, 0, 0);

        block.modelInstance = instance;
        blocks[integerId] = block;
        Gdx.app.log("BLOCK", "defined block " + id);
    }

    public static void setDropID(int blockID, int dropID) {
        blocks[blockID].setDropID(dropID);
    }

    public static void setDropID(String blockID, String dropID) {
        blocks[ID.get("block:" + blockID)].setDropID(ID.get("entity:" + dropID));
    }

    public static class CustomBlock {
        public final int MAX_HEATH, TYPE, ID;
        protected int level;
        HashMap<Integer, BlockExtraData> blockData;
        private int dropID;
        private ModelInstance modelInstance;
        private PhysicBlockEntity physicEntity;

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
                    if (health < 0) health = 0;
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

        void setPhysicEntity(PhysicBlockEntity physicEntity) {
            this.physicEntity = physicEntity;
        }

        public PhysicBlockEntity getPhysicEntity() {
            return physicEntity;
        }

        public int getDropID() {
            return dropID;
        }

        public int getID() {
            return ID;
        }

        protected void setDropID(int dropID) {
            this.dropID = dropID;
        }

        public int getLevel() {
            return level;
        }

        public void render(float x, float y, float z) {
            modelInstance.transform.setToTranslation(x, y, z);
            MODEL_BATCH.render(modelInstance, ENVIRONMENT);
        }
    }
}
