package com.intbyte.bw.engine.item;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.engine.input.InteractionOfItems;
import com.intbyte.bw.engine.render.GlobalEnvironment;
import com.intbyte.bw.engine.render.Graphic;
import com.intbyte.bw.engine.utils.ID;

public abstract class Item {
    public static final int PICKAXE = 0, AXE = 1, SWARD = 2, RESOURCE = 3, BLOCK = 4, HELMET = 5, ARMOR = 6, LEGGINGS = 6;
    private static final ItemFactory[] items = new ItemFactory[1200];
    private static final InteractionOfItems interaction = InteractionOfItems.getInstance();


    private double weight;

    private int stackSize;
    private Sprite icon;
    private ModelInstance modelInstance;
    ItemData itemData;
    protected int id;
    private int type;



    public static void addItemFactory(String id, ItemFactory item) {
        items[item.getId()] = item;
    }

    public static void setSettableItem(int itemID, int blockID) {
        interaction.addSettableItem(itemID, blockID);
    }

    public static void setSettableItem(String itemID, String blockID) {
        setSettableItem(ID.get("item:" + itemID), ID.get("block:" + blockID));
    }

    public static ItemFactory[] getItemFactories() {
        return items;
    }

    public static Item newItem(int id) {
        return items[id].create();
    }

    public static Item newItem(String id) {
        return newItem(ID.get("item:" + id));
    }

    public static Array<Item> newItems(int id, int count) {
        Array<Item> itemsArray = new Array<>();
        for (int i = 0; i < count; i++)
            itemsArray.add(items[id].create());
        return itemsArray;

    }

    public static Array<Item> newItems(String id, int count) {
        return newItems(ID.get("item:" + id), count);

    }

    public final int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStackSize() {
        return stackSize;
    }

    public void setStackSize(int stackSize) {
        this.stackSize = stackSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void render(Vector3 vector, Vector3 axis, float radiant) {
        modelInstance.transform.setToTranslation(vector);
        modelInstance.transform.setToRotationRad(axis, radiant);
        Graphic.getModelBatch().render(modelInstance, GlobalEnvironment.getEnvironment(false));
    }

    public void drawIcon(float x, float y, float width, float height) {
        Graphic.batch.draw(getIcon(), x, y, width, height);
    }

    public abstract byte[] getBytes();

    public abstract void readBytes(byte[] bytes);

    public ItemData getItemData() {
        return itemData;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Sprite getIcon() {
        return icon;
    }

    public void setIcon(Sprite icon) {
        this.icon = icon;
    }
}
