package com.intbyte.bw.gameAPI.graphic;
import com.intbyte.bw.gameAPI.graphic.Graphic;

public interface TypedValue{
	float PIXEL = 1,
	HHPIXEL = Graphic.SCREEN_HEIGHT / 100,
	HWPIXEL = Graphic.SCREEN_WIDTH / 100,
	APIXEL = Graphic.SCREEN_HEIGHT / 480;
}


