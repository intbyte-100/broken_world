package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.math.Vector3;

public class Tile {
    static Block.CustomBlock[] blocks = Block.getBlocks();
    Vector3 position = new Vector3();
    int blockID;
    BlockExtraData data;

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }

    public void setBlockID(int blockID) {
        this.blockID = blockID;
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

    public Block.CustomBlock getBlock() {
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
}
