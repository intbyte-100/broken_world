package com.intbyte.bw.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.environment.EntityFactory;
import com.intbyte.bw.gameAPI.graphic.Graphic;
import com.intbyte.bw.gameAPI.graphic.ui.container.Container;
import com.intbyte.bw.gameAPI.physic.Physic;
import com.intbyte.bw.gameAPI.utils.Resource;


public class Player extends Entity {
    static Player player;
    float coolDown;
    private Container[] inventory;
    private ModelInstance modelInstance;

    protected Player() {
        inventory = new Container[36];
        for (int i = 0; i < 36; i++) {
            inventory[i] = new Container(64);
        }
        Gdx.app.log("PLAYER", "player is initialized");
        modelInstance = Resource.createModalInstance("block/2block.obj");
    }

    public static Player getPlayer() {
        if (player == null)
            player = (Player) new PlayerFactory().create();
        return player;
    }

    @Override
    public ModelInstance getEntityModel() {
        return modelInstance;
    }

    @Override
    public void renderTick() {
        super.renderTick();
        if (0 < coolDown) {
            coolDown -= (float) 100 / 6 * Gdx.graphics.getRawDeltaTime();
        } else
            coolDown = 0;
    }

    @Override
    public void render() {
        modelInstance.transform.setToTranslation(0 + GameThread.xDraw, 0, 0 + GameThread.zDraw);
        modelInstance.transform.rotateRad(Vector3.Y, rotate);
        Graphic.MODEL_BATCH.render(modelInstance, Graphic.ENVIRONMENT);
    }

    private static class PlayerFactory extends EntityFactory {

        public PlayerFactory() {
            super("player", 0);
        }

        @Override
        protected Entity createEntity() {
            return new Player();
        }
    }
}

