package com.intbyte.bw.gameAPI.graphic;

public interface TypedValue{
	float PIXEL = 1,
	HHPIXEL = Graphic.getScreenHeight() / 100f,
	HWPIXEL = Graphic.getScreenWidth() / 100f,
	APIXEL = Graphic.getScreenHeight() / 480f;
}


