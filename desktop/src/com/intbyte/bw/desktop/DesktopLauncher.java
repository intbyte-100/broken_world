package com.intbyte.bw.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.intbyte.bw.GameBoot;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.fullscreen = true;
        config.width = 1920;
        config.height = 1080;
        config.samples = 16;
        new LwjglApplication(new GameBoot(), config);
    }
}
