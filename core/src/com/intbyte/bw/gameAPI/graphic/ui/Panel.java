package com.intbyte.bw.gameAPI.graphic.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.intbyte.bw.gameAPI.utils.Resource;

public class Panel extends Group {
    Sprite sprite;



    public Panel(String texture) {
        sprite = Resource.getSprite(texture);
        addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.enableBlending();
        batch.draw(sprite,getX(),getY(),getWidth(),getHeight());
        super.draw(batch, parentAlpha);
    }


    public static Sprite drawPanel(int width, int height, int padding, float r, float g, float b, float a) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, a);
        pixmap.fillRectangle(0,0,width,height);
        pixmap.setColor(r*0.9f, g*0.9f, b*0.9f, a*0.5f);
        pixmap.fillRectangle(0,height-padding,width,padding);
        pixmap.fillRectangle(width-padding,0,padding,height);
        return new Sprite(new Texture(pixmap));
    }

    public static Sprite getDrawRoundedPanel(int width, int height, int radius, float r, float g, float b, float a) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(r, g, b, a);
        pixmap.fillRectangle(0, radius, pixmap.getWidth(), pixmap.getHeight()-2*radius);
        pixmap.fillRectangle(radius, 0, pixmap.getWidth() - 2*radius, pixmap.getHeight());
        pixmap.fillCircle(radius, radius, radius);
        pixmap.fillCircle(radius, pixmap.getHeight()-radius, radius);
        pixmap.fillCircle(pixmap.getWidth()-radius, radius, radius);
        pixmap.fillCircle(pixmap.getWidth()-radius, pixmap.getHeight()-radius, radius);
        return new Sprite(new Texture(pixmap));
    }

}
