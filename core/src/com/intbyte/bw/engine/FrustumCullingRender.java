package com.intbyte.bw.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.engine.entity.Player;
import com.intbyte.bw.engine.block.CustomBlock;
import com.intbyte.bw.engine.input.GameInputProcessor;
import com.intbyte.bw.engine.block.Block;
import com.intbyte.bw.engine.render.Graphic;

import static com.intbyte.bw.engine.GameThread.xDraw;
import static com.intbyte.bw.engine.GameThread.zDraw;

public abstract class FrustumCullingRender {
    protected final CustomBlock[] blocks = Block.getBlocks();
    protected final CustomBlock[] landBlocks = Block.getLandBlocks();
    protected final Player player;
    protected final PerspectiveCamera camera3d;
    protected int xEdge, zEdge, xEdge2, zEdge2;

    public FrustumCullingRender() {
        camera3d = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera3d.far = 150;
        camera3d.near = 1f;
        player = Player.getPlayer();
    }

    protected abstract void draw(int x, int z);

    protected void draw(int x, int xTo, int z, int zTo) {
        for (; x < xTo; x++)
            for (int zz = z; zz > zTo; zz--)
                draw(x, zz);
    }

    public void render() {
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE_MODE);
        xDraw = (float) (player.getPixelX() / 10 - Math.floor(player.getPixelX() / 10)) * 10;
        zDraw = (float) (player.getPixelZ() / 10 - Math.floor(player.getPixelZ() / 10)) * 10;
        camera3d.position.set(0 + xDraw, 49.6f, -18 + zDraw);
        camera3d.lookAt(-0.008f + xDraw, 10, -0.6f * 10.3f + zDraw+8);
        camera3d.update();
        draw(xEdge, xEdge2 + 2, zEdge2 + 1, zEdge - 2);
    }


    public void resize() {
        Vector3 position = new Vector3();
        camera3d.position.set(0, 49.6f, -18);
        camera3d.lookAt(-0.008f, 10, -0.6f * 10.3f+8);;
        camera3d.update();
        xEdge = Math.round(GameInputProcessor.getFastBlock(camera3d, position, Gdx.graphics.getWidth(), 0).x)/10-1;
        zEdge = (int) GameInputProcessor.getFastBlock(camera3d, position, 0, Gdx.graphics.getHeight()).z/10;
        xEdge2 = Math.round(GameInputProcessor.getFastBlock(camera3d, position, 0, 0).x)/10+1;
        zEdge2 = (int) GameInputProcessor.getFastBlock(camera3d, position, 0, 0).z/10+1;
        Graphic.stage.getCamera().viewportHeight = Gdx.graphics.getHeight();
        Graphic.stage.getCamera().viewportWidth = Gdx.graphics.getWidth();
        Graphic.stage.getViewport().setScreenSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public PerspectiveCamera getCamera() {
        return camera3d;
    }
}