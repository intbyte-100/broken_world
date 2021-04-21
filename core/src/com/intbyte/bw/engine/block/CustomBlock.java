package com.intbyte.bw.engine.block;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.engine.graphic.Graphic;
import com.intbyte.bw.engine.physic.PhysicBlockObject;
import com.intbyte.bw.engine.utils.Resource;

import java.util.HashMap;

public class CustomBlock {
    public final int MAX_HEATH, TYPE, ID;
    private int level;
    private String id;
    private HashMap<Integer, BlockExtraData> blockData;
    private int dropID;
    private int bodyID;
    private ModelInstance modelInstance;
    private PhysicBlockObject physicEntity;
    private float scale = 1;
    private boolean isLand = false;
    Vector3 position = new Vector3(0,0,0);
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
        render(getModelInstance(),x,y,z);
    }


    public void render(ModelInstance modelInstance,float x, float y, float z){
        modelInstance.transform.setToTranslation(x+position.x, y+position.y, z+position.z);
        float scaleX = modelInstance.transform.getScaleX(),
                scaleY = modelInstance.transform.getScaleY(),
                scaleZ = modelInstance.transform.getScaleZ();
        modelInstance.transform.scale(getScale()/10, getScale()/10, getScale()/10);

        Graphic.getModelBatch().render(modelInstance, Graphic.getEnvironment(isLand()));
        modelInstance.transform.scale(scaleX,scaleY,scaleZ);
    }


    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
    }

    public void setPosition(Vector3 position) {
        this.position.set(position);
    }

    public void updateIcon(){
        Resource.addSprite(Resource.getIconFromModel(getModelInstance(),position.x,position.y,position.z, getScale()), "icon:"+ getId());
    }
    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBodyID(int bodyID) {
        this.bodyID = bodyID;
    }

    public void setModelInstance(ModelInstance modelInstance) {
        this.modelInstance = modelInstance;
    }

    public boolean isLand() {
        return isLand;
    }

    public void setLand(boolean land) {
        isLand = land;
    }

    public HashMap<Integer, BlockExtraData> getBlockData() {
        return blockData;
    }

    public void setBlockData(HashMap<Integer, BlockExtraData> blockData) {
        this.blockData = blockData;
    }
}
