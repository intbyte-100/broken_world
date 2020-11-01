package com.intbyte.bw.gameAPI.graphic.ui.button;

import com.badlogic.gdx.math.Polygon;

public class TriangleButton implements ButtonShape{
    private Polygon polygon;
    public TriangleButton(){
        polygon = new Polygon();
    }
    @Override
    public boolean hit(Button button, float x, float y) {
        polygon.setVertices(new float[]{0,0,button.getWidth()/2,button.getHeight(),button.getWidth(),0});
        polygon.setRotation(button.getRotation());
        return polygon.contains(x, y);
    }
}
