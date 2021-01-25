package com.intbyte.bw.gameAPI.graphic.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.intbyte.bw.gameAPI.utils.Resource;

public class Panel extends Actor {
    Sprite sprite;



    public Panel(String texture) {
        sprite = Resource.getSprite(texture);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.enableBlending();
        batch.draw(sprite,getX(),getY(),getWidth(),getHeight());
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

}
