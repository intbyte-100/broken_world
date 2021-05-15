package com.intbyte.bw.engine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

import com.intbyte.bw.engine.entity.EntityManager;
import com.intbyte.bw.engine.entity.Player;
import com.intbyte.bw.engine.envinroment.Time;
import com.intbyte.bw.engine.input.GameInputProcessor;
import com.intbyte.bw.engine.callbacks.CallBack;
import com.intbyte.bw.engine.callbacks.Render;
import com.intbyte.bw.engine.entity.Entity;
import com.intbyte.bw.engine.render.Graphic;
import com.intbyte.bw.engine.utils.Debug;
import com.intbyte.bw.engine.world.LocalChunkHandler;
import com.intbyte.bw.engine.world.World;
import com.intbyte.bw.engine.world.WorldConfig;
import com.intbyte.bw.engine.render.GlobalEnvironment;
import com.intbyte.bw.engine.ui.GUI;
import com.intbyte.bw.engine.ui.container.TakenItemsRender;
import com.intbyte.bw.engine.physic.Physic;


public class GameThread implements Screen {


    public static float xDraw, zDraw;
    public static int visible;
    private static boolean isReadyCallBack;
    private static EntityManager entityManager;
    private final FrustumCullingRender render;
    private final Engine engine;


    public GameThread() {
        World.createVoidWorld(12, 12, new WorldConfig(true, true));
        World.handler = new LocalChunkHandler(Player.getPlayer());

        render = new ChuncksRender();
        Gdx.input.setInputProcessor(new InputMultiplexer(TakenItemsRender.listener, Graphic.stage, new GameInputProcessor(render.getCamera())));


        entityManager = new EntityManager();
        engine = new Engine();
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static boolean isReadyCallBack() {
        return isReadyCallBack;
    }


    @Override
    public void render(final float delta) {
        Debug.update();
        Time.update();
        GlobalEnvironment.update();
        Physic.update();
        World.update();



        for (int i = 0; i < entityManager.getActive().size; i++)
            entityManager.getActive().get(i).renderTick();

        isReadyCallBack = true;


        render.render();
        for (Entity i : entityManager.getActive()) {
            i.render();
        }
        engine.update(delta);

        Graphic.batch.begin();
        CallBack.executeRenderCallBacks();
        Graphic.batch.end();
        Graphic.getModelBatch().end();
        Graphic.stage.act();
        Graphic.stage.draw();
        Graphic.batch.begin();
        Render.callBacks.get(0).main();
        Graphic.batch.end();
        isReadyCallBack = false;
        visible = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            GUI.setLayer(GUI.isOpen("main") ? "inventory" : "main", null);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.log("APPLICATION", "application finished");
            System.exit(0);
        }
    }


    @Override
    public void resume() {

    }

    @Override
    public void resize(int p1, int p2) {
        render.getCamera().viewportHeight = p2;
        render.getCamera().viewportWidth = p1;
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




