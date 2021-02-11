package com.intbyte.bw.gameAPI.ui.button;

import com.badlogic.gdx.math.Circle;

public class RoundButton implements ButtonShape{

    private Circle circle = new Circle();
    @Override
    public boolean hit(Button button, float x, float y) {
        circle.radius = button.getHeight()/2;
        circle.setPosition(button.getHeight()/2,button.getHeight()/2);

        return circle.contains(x,y);
    }
}
