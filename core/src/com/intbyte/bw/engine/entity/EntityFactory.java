package com.intbyte.bw.engine.entity;

import com.intbyte.bw.engine.entity.Entity;
import com.intbyte.bw.engine.physic.Physic;
import com.intbyte.bw.engine.utils.ID;

public abstract class EntityFactory {
    private int id;
    int bodyID;

    public EntityFactory(String strId, int id){
        this(strId,"defaultEntity",id);
    }
    public EntityFactory(String strId){
        this(strId,"defaultEntity");
    }
    public EntityFactory(String strId, String bodyID, int id){
        this.id = ID.registeredId("entity:"+strId, id);
        this.bodyID = ID.get("2dBody:"+bodyID);
    }

    public EntityFactory(String strId, String bodyID){
        this.id = ID.registeredId("entity:"+strId);
        this.bodyID = ID.get("2dBody:"+bodyID);
    }

    public final int getId() {
        return id;
    }

    protected abstract Entity createEntity();

    public final Entity create(){
        Entity entity = createEntity();
        entity.body = Physic.allocateBody(bodyID);
        entity.bodyID = bodyID;
        entity.setId(id);
        entity.spawn();
        return entity;
    }
}
