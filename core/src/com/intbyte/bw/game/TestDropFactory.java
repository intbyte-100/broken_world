package com.intbyte.bw.game;

import com.intbyte.bw.gameAPI.environment.Drop;
import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.environment.EntityFactory;

public class TestDropFactory extends EntityFactory {


    public TestDropFactory() {
        super("test_drop");
    }

    @Override
    protected Entity createEntity() {
        return new Drop();
    }
}
