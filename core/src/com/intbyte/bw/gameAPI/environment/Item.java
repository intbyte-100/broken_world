package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.gameAPI.utils.ID;

import static com.intbyte.bw.gameAPI.graphic.Graphic.*;

public abstract class Item {
    private static Item[] items = new Item[12000];
    public final int STACK_SIZE;
    protected final int id;
    protected Texture icon;
    protected ModelInstance modelInstance;

    public Item(int id, int stackSize) {
        STACK_SIZE = stackSize;
        this.id = id;
    }

    public static void addItem(String id, Item item) {
        ID.registredId("item:" + id, item.getId());
        items[item.getId()] = item;
    }

    public static Item[] getItems() {
        return items;
    }

    public static Item newItem(int id) {
        return items[id].create();
    }

    public static Item newItem(String id) {
        return newItem(ID.get("item:" + id));
    }

    public static Item[] newItems(int id, int count) {
        Array<Item> itemsArray = new Array<>();
        for (int i = 0; i < count; i++)
            itemsArray.add(items[id].create());
        return itemsArray.toArray(Item.class);

    }

    public static Item[] newItems(String id, int count) {
        return newItems(ID.get("item:" + id), count);

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

    public abstract Item create();
}
