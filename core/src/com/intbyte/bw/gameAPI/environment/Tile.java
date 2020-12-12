package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.math.Vector3;

public class Tile {
    static Block.CustomBlock[] blocks = Block.getBlocks();
    Vector3 position = new Vector3();
    int blockID;
    BlockExtraData data;
    public void setPosition(Vector3 position) {
        this.position.set(position);
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x,y, z);
    }

    public void setBlockID(int blockID) {
        this.blockID = blockID;
    }

    public void setData(BlockExtraData data) {
        this.data = data;
    }

    public void render(){
        blocks[blockID].render(position.x, position.y,position.z);
    }
}
