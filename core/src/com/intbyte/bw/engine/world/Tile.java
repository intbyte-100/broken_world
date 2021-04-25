package com.intbyte.bw.engine.world;


import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

import com.intbyte.bw.engine.block.BlockExtraData;
import com.intbyte.bw.engine.block.CustomBlock;
import com.intbyte.bw.engine.physic.Physic;
import com.intbyte.bw.engine.physic.PhysicData;
import com.intbyte.bw.engine.block.Block;


public class Tile {
    static CustomBlock[] blocks = Block.getBlocks();

    private final Vector3 position = new Vector3();
    private int blockID;
    private int angle = 0;
    private BlockExtraData data;
    private Body body;



    public Tile(int blockID){
        setBlockID(blockID);
    }
    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
        this.body.setTransform(x, z, angle);
    }

    public void setBlockID(int blockID) {
        if (this.blockID != 0)
            Physic.getBodyFactory(Block.getBlocks()[this.blockID].getPhysicEntity().getBodyID()).addBody(body);
        this.blockID = blockID;
        if (blockID != 0)
            body = Block.getBlocks()[blockID].getPhysicEntity().getBody();
        PhysicData data = (PhysicData)  body.getUserData();
        data.setType(data.BLOCK);
        data.setObject(this);

    }

    public void render(float x, float y, float z) {
        blocks[blockID].render(x, y + 5, z);
    }

    public BlockExtraData getData() {
        if (!(data != null && data.getID() == blockID)) data = getBlock().newData();
        return data;
    }

    public void setData(BlockExtraData data) {
        this.data = data;
    }

    public CustomBlock getBlock() {
        return blocks[blockID];
    }

    public int getID() {
        return blockID;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position.set(position);
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }


}
