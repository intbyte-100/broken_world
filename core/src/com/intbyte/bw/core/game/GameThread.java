package com.intbyte.bw.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.intbyte.bw.GameBoot;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Render;
import com.intbyte.bw.gameAPI.environment.Block;
import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.environment.World;
import com.intbyte.bw.gameAPI.graphic.ui.container.TakenItemsRender;

import static com.intbyte.bw.gameAPI.graphic.Graphic.*;


public class GameThread implements Screen {


    private static boolean isReadyCallBack;
    private final Player player;
    private final PerspectiveCamera camera3d;
    private final GameBoot boot;
    private final GameRenderAdapter gameRenderAdapter;
    private final Block.CustomBlock[] blocks = Block.getBlocks();
    private final Block.CustomBlock[] landBlocks = Block.getLandBlocks();
    public static float xDraw, zDraw;
    private static EntityManager entityManager;

    public GameThread(GameBoot boot) {
        this.boot = boot;
        camera3d = new PerspectiveCamera(67, SCREEN_WIDTH, SCREEN_HEIGHT);
        gameRenderAdapter = new GameRenderAdapter();

        camera3d.far = 150;
        camera3d.near = 1f;
        player = Player.getPlayer();
        World.createVoidWorld(312, 312);


        Gdx.input.setInputProcessor(new InputMultiplexer(TakenItemsRender.listener, STAGE, new GameInputProcessor(camera3d)));

        renderInit();
        gameRenderAdapter.initRender();

        entityManager = new EntityManager();

    }


    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static boolean isReadyCallBack() {
        return isReadyCallBack;
    }

    @Override
    public void render(float p1) {




        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < entityManager.getActive().size; i++) {
                    entityManager.getActive().get(i).renderTick();
                }
            }
        }.start();
        isReadyCallBack = true;

        xDraw = (float) (player.getX() / 10 - Math.floor(player.getX() / 10)) * 10;
        zDraw = (float) (player.getZ() / 10 - Math.floor(player.getZ() / 10)) * 10;
        camera3d.position.set(0+xDraw, 44.6f, -18+zDraw);
        camera3d.lookAt(-0.008f+xDraw, 10, -0.6f * 10.3f+zDraw);

        camera3d.update();


        Gdx.gl.glClearColor(0.52f, 0.8f, 92, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        MODEL_BATCH.begin(camera3d);
        gameRenderAdapter.draw();
        for (Entity i : entityManager.getActive()) {
            i.render();
        }
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
            blocks[id].render(x * 10, 10 * y - 5 + 10, z * 10 - 5);
        else {
            id = World.getLandBlock(x + ((int) player.getXOnBlock()), z + ((int) player.getZOnBlock()));
            landBlocks[id].render(x * 10f, 2 * -5.000f + 10, z * 10f - 5);
        }
    }

    public void draw(int x, int xTo, int z, int zTo, int edgeZ, int edgeZTo) {
        for (; x < xTo; x++)
            for (int zz = z; zz > zTo; zz--)
                draw(x, 0, zz);

        for (; edgeZ > edgeZTo; edgeZ--) {
            draw(7, 0, edgeZ);
            draw(-7, 0, edgeZ);
        }
    }

    public void renderInit() {
        gameRenderAdapter.add("16:9", new Render() {
            @Override
            public void main() {
                draw(-6, 7, 5, -3, 5, -1);
                draw(8, 0, 5);
                draw(8, 0, 4);
                draw(8, 0, 3);
            }
        });

        gameRenderAdapter.add("5:3", new Render() {
            @Override
            public void main() {
                draw(-6, 7, 5, -3, 5, 1);
            }
        });

        gameRenderAdapter.add("4:3", new Render() {
            @Override
            public void main() {
                draw(-5, 7, 5, -3, 0, 0);
            }
        });

        gameRenderAdapter.add("128:75", new Render() {
            @Override
            public void main() {
                draw(-6, 7, 5, -3, 5, 3);
                draw(7, 0, 3);
                draw(7, 0, 2);
            }
        });

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




