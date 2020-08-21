package com.intbyte.bw.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.intbyte.bw.GameBoot;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.environment.Block;
import com.intbyte.bw.gameAPI.environment.World;
import com.intbyte.bw.gameAPI.graphic.ui.container.TakenItemsRender;
import com.intbyte.bw.gameAPI.utils.ID;

import static com.intbyte.bw.gameAPI.graphic.Graphic.*;


public class GameThread implements Screen {


    private static boolean isReadyCallBack;
    private final Player player;
    private final PerspectiveCamera camera3d;
    private final GameBoot boot;
    private final Block.CustomBlock[] blocks = Block.getBlocks();
    private final Block.CustomBlock[] landBlocks = Block.getLandBlocks();
    public float xDraw, zDraw;

    public GameThread(GameBoot boot) {
        this.boot = boot;
        World.createVoidWorld(312, 312);
        camera3d = new PerspectiveCamera(67, SCREEN_WIDTH, SCREEN_HEIGHT);


        camera3d.position.set(0, 40.6f, -18);
        camera3d.lookAt(-0.008f, 10, -0.6f * 10);

        camera3d.far = 150;
        camera3d.near = 1f;


        player = Player.getPlayer();


        Block.defineLandBlock("grass", "grass.jpg");
        ID.registredId("block:void", 0);
        Block.defineBlock("grass", "grass.jpg");
        Block.defineBlock("grass2", "android.jpg");

        Gdx.input.setInputProcessor(new InputMultiplexer(TakenItemsRender.listener, STAGE, new GameInputProcessor(camera3d)));
    }

    public static boolean isReadyCallBack() {
        return isReadyCallBack;
    }

    @Override
    public void render(float p1) {
        isReadyCallBack = true;

        camera3d.update();

        xDraw = (float) (player.getX() / 10 - Math.floor(player.getX() / 10)) * 10;
        zDraw = (float) (player.getZ() / 10 - Math.floor(player.getZ() / 10)) * 10;

        Gdx.gl.glClearColor(0.52f, 0.8f, 92, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        MODEL_BATCH.begin(camera3d);
        for (int x = -6; x < 7; x++)
            for (int z = 5; z > -3; z--)
                draw(x, 0, z);

        for (int x = 7; x < 8; x++)
            for (int z = 5; z > -1; z--)
                draw(x, 0, z);


        for (int x = -7; x < -6; x++)
            for (int z = 5; z > -1; z--)
                draw(x, 0, z);
        draw(8, 0, 5);
        draw(8, 0, 4);
        MODEL_BATCH.end();

        STAGE.act();
        STAGE.draw();
        BATCH.begin();
        CallBack.executeRenderCallBacks();
        BATCH.end();
        isReadyCallBack = false;
    }

    public void draw(int x, int y, int z) {
        int id = World.getBlock(x + ((int) player.getXOnBlock()), z + ((int) player.getZOnBlock()));

        if (id > 0)
            blocks[id].render(x * 10 - xDraw, 10 * y - 5 + 10, z * 10 - 5 - zDraw);
        else {
            id = World.getLandBlock(x + ((int) player.getXOnBlock()), z + ((int) player.getZOnBlock()));
            landBlocks[id].render(x * 10f - xDraw, 2 * -5.000f + 10, z * 10f - 5 - zDraw);
        }

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int p1, int p2) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}




