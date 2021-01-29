package com.intbyte.bw.gameAPI.graphic.ui.container;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Render;
import com.intbyte.bw.gameAPI.graphic.Graphic;

import static com.intbyte.bw.gameAPI.graphic.TypedValue.APIXEL;

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
            if (isRendering) {
                float setY = screenY - Graphic.getScreenHeight();
                instance.setX(screenX - instance.getWidth() / 2);
                instance.setY(setY * -1 - instance.getHeight() / 2);
            }
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            if (takenItems.isTaken && isRendering) {
                float setY = screenY - Graphic.getScreenHeight();
                instance.setX(screenX - instance.getWidth() / 2);
                instance.setY(setY * -1 - instance.getHeight() / 2);
            }
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            if (takenItems.isTaken && isRendering) {
                float setY = screenY - Graphic.getScreenHeight();
                instance.setX(screenX - instance.getWidth() / 2);
                instance.setY(setY * -1 - instance.getHeight() / 2);
            }
            return false;
        }
    };

    private float xDraw = (APIXEL * 60) * 0.7f;
    private BitmapFont font;


    private TakenItemsRender() {
        setSize(APIXEL * 60 * 0.8f, APIXEL * 60 * 0.8f);
        font = new BitmapFont();
        font.getData().setScale((18 / (18 / (APIXEL * 60 * 0.2f))) / 10);
        CallBack.addCallBack(new Render() {
            @Override
            public void main() {
                if (takenItems.isTaken && isRendering) {
                    takenItems.getItems().get(0).drawIcon(getX(), getY(), getWidth(), getHeight());
                    String value = String.valueOf(takenItems.getCountItems());
                    font.draw(Graphic.BATCH, value, getX() + xDraw - (font.getXHeight() * value.length() - font.getXHeight()), getY() + font.getLineHeight() * 0.6f);
                }
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
