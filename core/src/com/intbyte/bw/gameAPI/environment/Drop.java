package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.intbyte.bw.gameAPI.graphic.Graphic;

public class Drop extends Entity {

    protected ModelInstance modelInstance;




    public Drop(int id) {
        this.rotate = (float) Math.random() * 360;
        this.modelInstance = Block.getBlock(id).getModelInstance();
    }

    public Drop(ModelInstance modelInstance){
        this.rotate = (float) Math.random() * 360;
        this.modelInstance = modelInstance;
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
        modelInstance.transform.scale(0.2f, 0.2f, 0.2f);
        Graphic.MODEL_BATCH.render(modelInstance, Graphic.ENVIRONMENT);
        modelInstance.transform.scale(scaleX,scaleY,scaleZ);
    }

    public Container take() {
        return null;
    }
}
