package com.intbyte.bw.engine.world;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.engine.world.Tile;

public class Chunck {
    public int x, z;
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

    public boolean isVisible(PerspectiveCamera camera) {
        return true;
    }

}
