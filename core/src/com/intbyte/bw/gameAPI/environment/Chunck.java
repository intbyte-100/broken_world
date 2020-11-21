package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.utils.Array;

class Chunck {
    int x, z;
    private Array<Tile> tileArray = new Array<>();

    public void setTile(Tile tile) {
        tileArray.add(tile);
    }

    public Array<Tile> getTiles() {
        return tileArray;
    }

    public void setPosition(int x, int z) {
        this.x = x;
        this.z = z;
    }
}
