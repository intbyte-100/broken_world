package com.intbyte.bw.gameAPI.graphic;

public interface TypedValue{
	float PIXEL = 1,
	HHPIXEL = Graphic.getScreenHeight() / 100,
	HWPIXEL = Graphic.getScreenWidth() / 100,
	APIXEL = Graphic.getScreenHeight() / 480;
}


