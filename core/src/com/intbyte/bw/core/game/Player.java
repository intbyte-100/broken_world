package com.intbyte.bw.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.environment.EntityFactory;
import com.intbyte.bw.gameAPI.graphic.Graphic;
import com.intbyte.bw.gameAPI.environment.Container;
import com.intbyte.bw.gameAPI.utils.Resource;


public class Player extends Entity {
    static Player player;
    float coolDown;

    private ModelInstance modelInstance;

    protected Player() {
        for(int i = 0; i < 100; i++)
            inventory.add(new Container(64));
        Gdx.app.log("PLAYER", "player is initialized");
        modelInstance = Resource.createModelInstance("block/2block.obj");
        setCarrying(100);
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

    public Array<Container> getInventory() {
        return inventory;
    }

    @Override
    public void tick() {
        setItemsWeight(getSummaryWeight());
        if(getItemsWeight()>getCarrying()){
            float dWeight = (float) ((getItemsWeight()-getCarrying())*0.1);
            getSpeed().set(getMaxSpeed().x/dWeight,0,getMaxSpeed().z/dWeight);
        } else
            getSpeed().set(getMaxSpeed());
    }

    @Override
    public boolean takeDrop(Container container) {
        for(Container i: inventory) {
            if(container.getItems().isEmpty())
                break;
            i.addItems(container.getItems());
        }
        if(container.getItems().notEmpty()){
            Container container1 = new Container(64);
            container1.addItems(container.getItems());
            inventory.add(container1);
        }
        return true;
    }
}

