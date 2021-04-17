package com.intbyte.bw.engine.entity;

import com.intbyte.bw.engine.entity.Drop;
import com.intbyte.bw.engine.entity.DropData;
import com.intbyte.bw.engine.entity.Entity;
import com.intbyte.bw.engine.entity.EntityFactory;
import com.intbyte.bw.engine.physic.Physic;
import com.intbyte.bw.engine.utils.ID;

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
