package com.intbyte.bw.gameAPI.callbacks;
import com.badlogic.gdx.utils.Array;
import java.util.Iterator;
import java.util.ArrayList;

public abstract class Render
{
	static ArrayList<Render> callBacks = new ArrayList<>();
	abstract public void main();
}
