package com.intbyte.bw.gameAPI.graphic.ui.container;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Render;
import com.intbyte.bw.gameAPI.graphic.Graphic;

public class TakenItemsRender extends Actor {
    private static final TakenItems takenItems = TakenItems.getInstance();
    private static TakenItemsRender instance;
    private static boolean isRendering;
    public static final InputProcessor listener = new InputAdapter() {

        {
            initInstance();
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            //if you add a check for true isTaken, then a rendering bug occurs
            if(isRendering) {
                float setY = screenY - Graphic.SCREEN_HEIGHT;
                instance.setX(screenX - instance.getWidth() / 2);
                instance.setY(setY * -1 - instance.getHeight() / 2);
            }
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            if (takenItems.isTaken&&isRendering) {
                float setY = screenY - Graphic.SCREEN_HEIGHT;
                instance.setX(screenX - instance.getWidth() / 2);
                instance.setY(setY * -1 - instance.getHeight() / 2);
            }
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            if (takenItems.isTaken&&isRendering) {
                float setY = screenY - Graphic.SCREEN_HEIGHT;
                instance.setX(screenX - instance.getWidth() / 2);
                instance.setY(setY * -1 - instance.getHeight() / 2);
            }
            return false;
        }
    };

    private TakenItemsRender() {
        setSize(Graphic.SCREEN_HEIGHT / (Graphic.SCREEN_HEIGHT / 50), Graphic.SCREEN_HEIGHT / (Graphic.SCREEN_HEIGHT / 50));
        CallBack.addCallBack(new Render() {
            @Override
            public void main() {
                if (takenItems.isTaken&&isRendering)
                    takenItems.items.get(0).drawIcon(getX(), getY(), getWidth(), getHeight());
            }
        });
    }

    public static void setRendering(boolean isRendering) {
        TakenItemsRender.isRendering = isRendering;
    }

    public static void initInstance() {
        if (instance == null)
            instance = new TakenItemsRender();
    }
}
