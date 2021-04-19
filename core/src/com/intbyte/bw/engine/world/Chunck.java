package com.intbyte.bw.engine.world;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.engine.world.Tile;

public class Chunck {
    public int x, z;
    private final Array<Tile> tileArray = new Array<>();

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

    public void render(float x, float y, float z){
        for (int i = 0; i < tileArray.size; i++) {
            Tile tile = tileArray.get(i);
            tile.render(tile.getPosition().x,0,tile.getPosition().z);
        }
    }
}
