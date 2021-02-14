package com.intbyte.bw.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.intbyte.bw.GameBoot;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = (int) (920);
        config.height = (int) (720);
        config.samples = 16;


        new LwjglApplication(new GameBoot(), config);
    }
}
