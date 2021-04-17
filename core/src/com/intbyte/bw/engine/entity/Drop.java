package com.intbyte.bw.engine.entity;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.intbyte.bw.engine.graphic.Graphic;
import com.intbyte.bw.engine.item.Container;
import com.intbyte.bw.engine.physic.PhysicData;
import com.intbyte.bw.engine.block.Block;

public class Drop extends Entity {

    protected ModelInstance modelInstance;
    protected DropData dropData;


    public Drop(int blockID, DropData dropData) {
        this.rotate = (float) Math.random() * 360;
        this.modelInstance = Block.getBlock(blockID).getModelInstance();
        this.dropData = dropData;
    }

    public Drop(ModelInstance modelInstance, DropData dropData){
        this.rotate = (float) Math.random() * 360;
        this.modelInstance = modelInstance;
        this.dropData = dropData;
    }

    @Override
    protected void spawn() {
        PhysicData data = (PhysicData) getBody().getUserData();
        data.setObject(this);
        data.setType(data.DROP);
    }

    @Override
    public ModelInstance getEntityModel() {
        return modelInstance;
    }

    @Override
    public void render() {
        calculateModelPositionAndRotation(0, 1f, 0);
        float scaleX = modelInstance.transform.getScaleX(),
              scaleY = modelInstance.transform.getScaleY(),
              scaleZ = modelInstance.transform.getScaleZ();
        float scale = Block.getBlock(bodyID).getScale()*0.3f;
        modelInstance.transform.scale(scale,scale,scale);
        Graphic.getModelBatch().render(modelInstance, Graphic.ENVIRONMENT);
        modelInstance.transform.scale(scaleX,scaleY,scaleZ);
    }



    public Container getDrop() {
        return dropData.get();
    }
}
