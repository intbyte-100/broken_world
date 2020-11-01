package com.intbyte.bw.core.game;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.gameAPI.environment.Block;
import com.intbyte.bw.gameAPI.environment.World;
import com.intbyte.bw.gameAPI.graphic.Graphic;

import static com.intbyte.bw.core.game.GameThread.xDraw;
import static com.intbyte.bw.core.game.GameThread.zDraw;
import static com.intbyte.bw.gameAPI.graphic.Graphic.*;

public class FrustumCullingRender {
    protected final Block.CustomBlock[] blocks = Block.getBlocks();
    protected final Block.CustomBlock[] landBlocks = Block.getLandBlocks();
    protected final Player player;
    protected final PerspectiveCamera camera3d;
    protected int xEdge, zEdge, xEdge2, zEdge2;

    public FrustumCullingRender() {
        camera3d = new PerspectiveCamera(67, getScreenWidth(), getScreenHeight());
        camera3d.far = 150;
        camera3d.near = 1f;
        player = Player.getPlayer();
    }

    protected void draw(int x, int z) {
        int id = World.getBlock(x + ((int) player.getXOnBlock()), z + ((int) player.getZOnBlock()));
        if (!camera3d.frustum.boundsInFrustum(x * 10f, 0, z * 10f - 5, 5, 0, 5)) return;
        GameThread.visible++;
        if (id > 0) {
            blocks[id].render(x * 10, 5, z * 10 - 5);
            GameThread.visible++;
        } else {
            id = World.getLandBlock(x + ((int) player.getXOnBlock()), z + ((int) player.getZOnBlock()));
            landBlocks[id].render(x * 10f, 0, z * 10f - 5);
        }
    }

    protected void draw(int x, int xTo, int z, int zTo) {
        for (; x < xTo; x++)
            for (int zz = z; zz > zTo; zz--)
                draw(x, zz);
    }

    public void render() {
        xDraw = (float) (player.getX() / 10 - Math.floor(player.getX() / 10)) * 10;
        zDraw = (float) (player.getZ() / 10 - Math.floor(player.getZ() / 10)) * 10;
        camera3d.position.set(0 + xDraw, 44.6f, -18 + zDraw);
        camera3d.lookAt(-0.008f + xDraw, 10, -0.6f * 10.3f + zDraw);
        camera3d.update();
        draw(xEdge, xEdge2 + 2, zEdge2 + 1, zEdge - 2);
    }


    public void resize() {
        Vector3 position = new Vector3();
        Graphic.resize();
        camera3d.position.set(0 + xDraw, 44.6f, -18 + zDraw);
        camera3d.lookAt(-0.008f + xDraw, 10, -0.6f * 10.3f + zDraw);
        camera3d.update();
        xEdge = Math.round(GameInputProcessor.getFastBlock(camera3d, position, getScreenWidth(), 0).x)/10-1;
        zEdge = (int) GameInputProcessor.getFastBlock(camera3d, position, 0, getScreenHeight()).z/10;
        xEdge2 = Math.round(GameInputProcessor.getFastBlock(camera3d, position, 0, 0).x)/10+1;
        zEdge2 = (int) GameInputProcessor.getFastBlock(camera3d, position, 0, 0).z/10+1;
        STAGE.getCamera().viewportHeight = getScreenHeight();
        STAGE.getCamera().viewportWidth = getScreenWidth();
        STAGE.getViewport().setScreenSize(getScreenWidth(),getScreenHeight());
    }

    public PerspectiveCamera getCamera() {
        return camera3d;
    }
}
