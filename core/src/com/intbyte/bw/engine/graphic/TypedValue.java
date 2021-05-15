package com.intbyte.bw.engine.graphic;

import com.badlogic.gdx.Gdx;

public interface TypedValue{
	float PIXEL = 1;
    float HHPIXEL = Gdx.graphics.getHeight() / 100f;
    float HWPIXEL = Gdx.graphics.getWidth() / 100f;
    float APIXEL = Gdx.graphics.getHeight() / 480f;
}


