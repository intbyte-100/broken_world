package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.gameAPI.utils.ID;

import java.util.HashMap;

import static com.intbyte.bw.core.game.InteractionOfItems.settableItemsHashMap;
import static com.intbyte.bw.gameAPI.graphic.Graphic.*;

public abstract class Item {
    public static final int PICKAXE = 0, AXE = 1, SWARD = 2, RESOURCE = 3, BLOCK = 4;
    private static final Item[] items = new Item[12000];


    static {
        com.intbyte.bw.core.game.InteractionOfItems.init();
    }

    public final int STACK_SIZE;
    protected final int id;
    protected boolean takenable;
    protected Texture icon;
    protected ModelInstance modelInstance;
    protected int strength;
    protected ItemData itemData;

    public Item(int id, int stackSize) {
        STACK_SIZE = stackSize;
        this.id = id;
    }

    public static void addItem(String id, Item item) {
        ID.registeredId("item:" + id, item.getId());
        items[item.getId()] = item;
    }

    public static void setSettableItem(int itemID, int blockID) {
        settableItemsHashMap.put(itemID, blockID);
    }

    public static void setSettableItem(String itemID, String blockID) {
        setSettableItem(ID.get("item:" + itemID), ID.get("block:" + blockID));
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

    public abstract int getType();

    public int getLevel() {
        return 0;
    }

    public int getMaxStrength() {
        return 0;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        if (strength > -1 && strength < +getMaxStrength())
            this.strength = strength;
    }

    public void decrementStrength() {
        if (strength > 0) strength--;
    }

    public boolean isTakenable() {
        return takenable;
    }

    public int getDamage() {
        return 1;
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

    public ItemData getItemData() {
        return itemData;
    }
}
