package com.intbyte.bw.engine.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.engine.GameThread;
import com.intbyte.bw.engine.render.GlobalEnvironment;
import com.intbyte.bw.engine.graphic.Graphic;
import com.intbyte.bw.engine.item.Container;
import com.intbyte.bw.engine.utils.Resource;


public class Player extends Entity {
    static Player player;
    float coolDown;

    private ModelInstance modelInstance;

    protected Player() {
        final float padding = Gdx.graphics.getHeight() * 0.06f;
        float width = (Gdx.graphics.getWidth() - padding * 2 - padding) / 2;
        float height = Gdx.graphics.getHeight() - padding * 2;
        final float slotSize = (width - padding * 4) / 3f * 0.85f;
        int containerCount = (int) Math.round((height-padding)/slotSize)*5-3;

        for(int i = 0; i < containerCount; i++)
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
            coolDown -= (float) 100 / 6 * Gdx.graphics.getDeltaTime();
        } else
            coolDown = 0;
    }

    @Override
    public void render() {
        modelInstance.transform.setToTranslation(0 + GameThread.xDraw, 0, 0 + GameThread.zDraw);
        modelInstance.transform.rotateRad(Vector3.Y, rotate);
        Graphic.getModelBatch().render(modelInstance, GlobalEnvironment.getEnvironment(false));
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
            float dWeight = (float) ((getItemsWeight()-getCarrying()));
            getSpeed().set(getMaxSpeed().x-getMaxSpeed().x/30*dWeight,0,getMaxSpeed().z-getMaxSpeed().z/30*dWeight);
            if(getSpeed().x<0) getSpeed().set(0,0,0);
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

    public float getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(float coolDown) {
        this.coolDown = coolDown;
    }
}

