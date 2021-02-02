package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.gameAPI.utils.ExtraData;
import com.intbyte.bw.gameAPI.utils.ID;


public class DropData implements ExtraData {

    protected int itemID;
    protected int minCount;
    protected int maxCount;

    public DropData(String itemID, int minCount, int maxCount){
        this.itemID = ID.get("item:"+itemID);
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    @Override
    public void readData(byte[] data) {

    }

    public Container get(){
        Array<Item> items = Item.newItems(itemID, MathUtils.random(minCount,maxCount));
        Container container = new Container(items.get(0).stackSize);
        container.addItems(items);
        return container;
    }
}
