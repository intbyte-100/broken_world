package com.intbyte.bw.core.game;

import com.intbyte.bw.gameAPI.environment.Chunck;
import com.intbyte.bw.gameAPI.environment.Tile;
import com.intbyte.bw.gameAPI.environment.World;

public class ChuncksRender extends FrustumCullingRender{
    @Override
    protected void draw(int x, int z) {
        if (!camera3d.frustum.boundsInFrustum(x * 10f, 0, z * 10f - 5, 5, 0, 5)) return;
        Chunck chunck = World.getChunck((float) (x+Player.player.getXOnBlock()),(float) (z+ player.getZOnBlock()));
        int id = World.getLandBlock(x + ((int) player.getXOnBlock()), z + ((int) player.getZOnBlock()));
        landBlocks[id].render(x * 10f, 0, z * 10f - 5);
        GameThread.visible++;
        for (Tile tile : chunck.getTiles()) {
            tile.render(x*10,0,z*10);
            GameThread.visible++;
        }
    }
}
