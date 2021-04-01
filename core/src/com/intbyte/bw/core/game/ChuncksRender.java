package com.intbyte.bw.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.ModelCache;
import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.gameAPI.environment.Chunck;
import com.intbyte.bw.gameAPI.environment.Tile;
import com.intbyte.bw.gameAPI.environment.World;
import com.intbyte.bw.gameAPI.graphic.Graphic;

import java.util.Iterator;

import static com.intbyte.bw.core.game.GameThread.xDraw;
import static com.intbyte.bw.core.game.GameThread.zDraw;


public class ChuncksRender extends FrustumCullingRender {

    private ModelCache modelCache = new ModelCache();
    @Override
    protected void draw(int x, int z) {
        if (!camera3d.frustum.boundsInFrustum(x * 10f, 0, z * 10f - 5, 5, 0, 5)) return;
        int id = World.getLandBlock(x + ((int) player.getX()), z + ((int) player.getZ()));
        landBlocks[id].render(x * 10f, 0, z * 10f - 5);
        GameThread.visible++;
    }

    protected void draw2(int x, int z) {

        if (!camera3d.frustum.boundsInFrustum(x * 10f, 0, z * 10f, 40, 0, 40)) return;
        Chunck chunck = World.world[World.fixedIndex(World.playerX + x / 2)][World.fixedIndex(World.playerZ + z / 2)];
        for (Iterator<Tile> iterator = chunck.getTiles().iterator(); iterator.hasNext(); ) {
            Tile tile = iterator.next();
            if (tile.getID() == 0) {
                iterator.remove();
                continue;
            }
            tile.render((float) (tile.getPosition().x - player.getPixelX() + GameThread.xDraw), 0, (float) (tile.getPosition().z - player.getPixelZ() + GameThread.zDraw));
            GameThread.visible++;
        }

    }

    protected void draw2(int x, int xTo, int z, int zTo) {

        for (; x < xTo+4; x += 2)
            for (int zz = z; zz > zTo-4; zz -= 2)
                draw2(x, zz);

    }

    private void render(int x, int xTo, int z, int zTo){
        x-=20;
        z+=20;
        draw2(x, xTo, z, zTo);
        for (; x < xTo; x++)
            for (int zz = z; zz > zTo; zz--)
                draw(x, zz);
    }
    @Override
    protected void draw(int x, int xTo, int z, int zTo) {
        xDraw = (float) (player.getPixelX() / 10 - Math.floor(player.getPixelX() / 10)) * 10;
        zDraw = (float) (player.getPixelZ() / 10 - Math.floor(player.getPixelZ() / 10)) * 10;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


        Graphic.setShadowMod(true);
        Graphic.getShadowLight().begin(Vector3.Zero,camera3d.direction);
            Graphic.getShadowLight().getCamera().translate(xDraw,0,zDraw);
            Graphic.getShadowLight().getCamera().update();
            Graphic.getModelBatch().begin(Graphic.getShadowLight().getCamera());
                draw2(x, xTo, z, zTo);
            Graphic.getModelBatch().end();
        Graphic.getShadowLight().end();
        Graphic.setShadowMod(false);
        Graphic.getModelBatch().begin(camera3d);
        render(x, xTo, z, zTo);
    }
}
