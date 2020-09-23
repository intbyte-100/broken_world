package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.intbyte.bw.gameAPI.graphic.Graphic;
import com.intbyte.bw.gameAPI.utils.Resource;

public class Drop extends Entity {

    protected ModelInstance modelInstance = Resource.createModalInstance("block/block.obj");


    public Drop() {
        rotate = (float) Math.random() * 360;
    }

    @Override
    public ModelInstance getEntityModel() {
        return modelInstance;
    }

    @Override
    public void render() {
        calculateModelPositionAndRotation(0, 1f, 0);
        modelInstance.transform.scale(0.2f, 0.2f, 0.2f);
        Graphic.MODEL_BATCH.render(modelInstance, Graphic.ENVIRONMENT);
    }
}
