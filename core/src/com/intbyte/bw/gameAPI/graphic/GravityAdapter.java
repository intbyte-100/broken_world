package com.intbyte.bw.gameAPI.graphic;
import java.util.HashSet;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.Arrays;
import static com.intbyte.bw.gameAPI.graphic.GravityAttribute.*;
import static com.intbyte.bw.gameAPI.graphic.Graphic.*;


public class GravityAdapter{

	protected HashSet<Integer> gravity = new HashSet<Integer>();
	protected float x, y, height, width;
	protected Actor actor;

	public GravityAdapter(Actor actor){
		this.actor = actor;
	}

	public final void setActor(Actor actor){
		this.actor = actor;
	}

	public final void setGravity(Integer... gravity){
		this.gravity.addAll(Arrays.asList(gravity));
	}


	public final void setWidth(float typeValue, float width){
		this.width = width * typeValue;
	}

	public final void setHeight(float typeValue, float height){
		this.height = height * typeValue;
	}

	public final void loadHeightAndWidthFromActor(){
		this.height = actor.getHeight();
		this.width = actor.getWidth();
	}

	public final void margin(float valueType, float x, float y){
		this.x += valueType * x;
		this.y += valueType * y;
	}

	public final Actor apply(){
		if(gravity.contains(CENTER)){
			actor.setX(SCREEN_WIDTH / 2 - width / 2);
			actor.setY(SCREEN_HEIGHT / 2 - height / 2);
		}
		if(gravity.contains(BOTTOM))
			actor.setY(0);
		if(gravity.contains(TOP))
			actor.setY(SCREEN_HEIGHT - height);
		if(gravity.contains(LEFT))
			actor.setX(0);
		if(gravity.contains(RIGHT))
			actor.setX(SCREEN_WIDTH - width);
		actor.setX(actor.getX() + x);
		actor.setY(actor.getY() + y);
		return actor;
	}

}
