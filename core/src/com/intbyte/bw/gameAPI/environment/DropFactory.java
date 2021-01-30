package com.intbyte.bw.gameAPI.environment;

import com.intbyte.bw.gameAPI.physic.Physic;
import com.intbyte.bw.gameAPI.utils.ID;

public class DropFactory extends EntityFactory {

    protected int id = 0;
    protected DropData dropData;
    static {
        Physic.defineDynamicRectangleBody("drop_body",0.1f,0.1f);
    }
    public DropFactory(String blockId, String dropId, DropData dropData) {
        super(dropId,"drop_body");
        this.dropData = dropData;
        this.id = ID.get("block:"+blockId);

    }

    @Override
    protected Entity createEntity() {
        return new Drop(id,dropData);
    }
}
