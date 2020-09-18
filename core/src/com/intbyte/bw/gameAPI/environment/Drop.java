package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.intbyte.bw.core.game.GameThread;
import com.intbyte.bw.gameAPI.graphic.Graphic;
import com.intbyte.bw.gameAPI.utils.Resource;

public class Drop extends Entity {
    protected ModelInstance modelInstance = Resource.createModalInstance("block/block.obj");

    @Override
    public void tick() {
        translate(0.005,0.005);
    }

    @Override
    public void render() {
        modelInstance.transform.setToTranslation((float) (getX() - player.getX()  + GameThread.xDraw), 0+1f, (float) (getZ() - player.getZ() + GameThread.zDraw));
        modelInstance.transform.scale(0.2f,0.2f,0.2f);
        Graphic.MODEL_BATCH.render(modelInstance, Graphic.ENVIRONMENT);
    }
}
