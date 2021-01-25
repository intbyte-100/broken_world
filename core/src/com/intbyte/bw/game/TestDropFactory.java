package com.intbyte.bw.game;

import com.intbyte.bw.gameAPI.environment.Drop;
import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.environment.EntityFactory;
import com.intbyte.bw.gameAPI.physic.Physic;
import com.intbyte.bw.gameAPI.utils.ID;

public class TestDropFactory extends EntityFactory {

    int id = 0;
    static {
        Physic.defineDynamicRectangleBody("drop_body",0.1f,0.1f);
    }
    public TestDropFactory(String blockId, String dropId) {
        super(dropId,"drop_body");
        id = ID.get("block:"+blockId);

    }

    @Override
    protected Entity createEntity() {
        return new Drop(id);
    }
}
