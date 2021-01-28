package com.intbyte.bw.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Render;
import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.environment.World;
import com.intbyte.bw.gameAPI.graphic.ui.GUI;
import com.intbyte.bw.gameAPI.graphic.ui.container.TakenItemsRender;
import com.intbyte.bw.gameAPI.physic.Physic;

import static com.intbyte.bw.gameAPI.graphic.Graphic.*;


public class GameThread implements Screen {


    public static float xDraw, zDraw;
    public static int visible;
    private static boolean isReadyCallBack;
    private static EntityManager entityManager;
    private final FrustumCullingRender render;
    private boolean isInventory;


    public GameThread() {
        World.createVoidWorld(12, 12);


        render = new ChuncksRender();
        Gdx.input.setInputProcessor(new InputMultiplexer(TakenItemsRender.listener, STAGE, new GameInputProcessor(render.camera3d)));


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
        Physic.update();
        World.update();

                for (int i = 0; i < entityManager.getActive().size; i++)
                    entityManager.getActive().get(i).renderTick();

        isReadyCallBack = true;

        Gdx.gl.glClearColor(0.52f, 0.8f, 92, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        MODEL_BATCH.begin(render.getCamera());
        render.render();
        for (Entity i : entityManager.getActive()) {
            i.render();
        }
        BATCH.begin();
        CallBack.executeRenderCallBacks();
        BATCH.end();
        MODEL_BATCH.end();
        STAGE.act();
        STAGE.draw();
        BATCH.begin();
        Render.callBacks.get(0).main();
        BATCH.end();
        isReadyCallBack = false;
        visible = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)&&!isInventory){
            GUI.setLayer("inventory",null);
            isInventory = true;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            GUI.setLayer("main", null);
            isInventory = false;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void resume() {

    }

    @Override
    public void resize(int p1, int p2) {
        render.camera3d.viewportHeight = p2;
        render.camera3d.viewportWidth = p1;
        render.resize();
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




