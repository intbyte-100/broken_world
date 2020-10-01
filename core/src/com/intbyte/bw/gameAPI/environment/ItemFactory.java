package com.intbyte.bw.gameAPI.environment;

import com.intbyte.bw.gameAPI.utils.ID;

public abstract class ItemFactory {

    protected int type, stackSize;
    private int id;

    public ItemFactory(String strId, int id, int type) {
        this.id = ID.registeredId("item:" + strId, id);
        this.type = type;
    }

    public ItemFactory(String strId, int type) {
        this.id = ID.registeredId("item:" + strId);
        this.type = type;
    }

    abstract public Item createItem();

    public final Item create() {
        Item item = createItem();
        item.setType(type);
        item.setSTACK_SIZE(stackSize);
        item.setId(id);
        return item;
    }

    public int getType() {
        return type;
    }

    public int getStackSize() {
        return stackSize;
    }

    public int getId() {
        return id;
    }
}
