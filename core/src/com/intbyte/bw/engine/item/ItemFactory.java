package com.intbyte.bw.engine.item;

import com.intbyte.bw.engine.item.Item;
import com.intbyte.bw.engine.utils.ID;

public abstract class ItemFactory {

    public int type, stacksize = 64;
    private int id;



    public ItemFactory(String strId, int type) {
        this.id = ID.registeredId("item:" + strId);
        this.type = type;
    }

    abstract public Item createItem();

    public final Item create() {
        Item item = createItem();
        item.setType(type);
        item.setStackSize(stacksize);
        item.setId(id);
        return item;
    }

    public int getType() {
        return type;
    }

    public int getStacksize() {
        return stacksize;
    }

    public int getId() {
        return id;
    }
}
