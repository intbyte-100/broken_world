package com.intbyte.bw.gameAPI.graphic.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.gameAPI.utils.MathUtil;

public class Joystick extends Actor {

    float x, y, k, translateX, translateY;
    private final Sprite circle;
    private final Sprite cursor;
    private final float radius;
    private boolean isTouched;
    private final float cursorRadius;
    private float cursorX, cursorY;
    private Player player;


    public Joystick(Sprite circle, Sprite cursor, float radius) {
        this.circle = circle;
        this.cursor = cursor;
        this.radius = radius;
        cursorRadius = radius / 4;
        super.setHeight(radius * 2);
        super.setWidth(radius * 2);
        addListener(new JoystickInputListener(this));
        player = Player.getPlayer();
    }

    void touchDown() {
        isTouched = true;
    }

    void touchUp() {
        isTouched = false;
        resetCursor();
        changePositionCursor(0, 0);
        player.getBody().setLinearVelocity(0,0);
    }

    private void resetCursor() {
        cursorX = 0;
        cursorY = 0;
    }


    public void changePositionCursor(float x, float y) {
        float dx = x - radius,
                dy = y - radius,
                length = (float) Math.sqrt(dx * dx + dy * dy);
        if (length < radius) {
            cursorX = dx;
            cursorY = dy;
        } else {
            k = radius / length;
            cursorX = dx * k;
            cursorY = dy * k;
        }
        if (isTouched&&length>radius*0.1f)
            player.setRotate((float) MathUtil.getAngle(cursorX,cursorY,80,0,0,0));


        translateX = cursorX / radius;
        translateY = cursorY / radius;
        player.getBody().setLinearVelocity(-translateX * Gdx.graphics.getDeltaTime()*60, Gdx.graphics.getDeltaTime()*60 * translateY);

    }

    public void moveBy(float valueTyped, float x, float y) {
        super.moveBy(x * valueTyped, y * valueTyped);
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        Actor actor = super.hit(x, y, touchable);
        if (actor == null) return null;
        float dx = x - radius,
                dy = y - radius;
        return (dx * dx + dy * dy <= radius * radius) ? this : null;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(circle, getX(), getY(), getWidth(), getHeight());
        if (isTouched) {
            changePositionCursor(this.x, this.y);
            batch.draw(cursor, getX() + radius - cursorRadius + cursorX, getY() + radius - cursorRadius + cursorY, cursorRadius * 2, cursorRadius * 2);
        } else
            batch.draw(cursor, getX() + radius - cursorRadius, getY() + radius - cursorRadius, cursorRadius * 2, cursorRadius * 2);

    }


    @Override
    public void setHeight(float height) {
    }

    @Override
    public void setWidth(float width) {
    }
}


class JoystickInputListener extends InputListener {

    private Joystick joystick;

    public JoystickInputListener(Joystick joystick) {
        this.joystick = joystick;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        joystick.touchDown();
        joystick.x = x;
        joystick.y = y;
        return true;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        joystick.touchUp();
    }

    @Override
    public void touchDragged(InputEvent event, float x, float y, int pointer) {
        joystick.x = x;
        joystick.y = y;
    }

}








