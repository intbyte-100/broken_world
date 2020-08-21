package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.gameAPI.utils.ID;

import static com.intbyte.bw.gameAPI.graphic.Graphic.*;

public abstract class Item {
    protected final int id;
    protected Texture icon;
    protected ModelInstance modelInstance;
    public final int STACK_SIZE;

    private static Item[] items = new Item[12000];

    public static void addItem(String id, Item item){
        ID.registredId("item:"+id,item.getId());
        items[item.getId()] = item;
    }

    public Item(int id,int stackSize) {
        STACK_SIZE = stackSize;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void render(Vector3 vector, Vector3 axis, float radiant) {
        modelInstance.transform.setToTranslation(vector);
        modelInstance.transform.setToRotationRad(axis, radiant);
        MODEL_BATCH.render(modelInstance, ENVIRONMENT);
    }

    public void drawIcon(float x, float y, float width, float height) {
        BATCH.draw(icon, x, y, width, height);
    }

    public abstract byte[] getBytes();

    public abstract void readBytes(byte[] bytes);


    public static Item[] getItems() {
        return items;
    }
}
