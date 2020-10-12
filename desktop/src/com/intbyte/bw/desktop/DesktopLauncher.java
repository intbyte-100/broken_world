package com.intbyte.bw.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.intbyte.bw.GameBoot;

import java.time.LocalTime;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        config.samples = 16;
        new LwjglApplication(new GameBoot(), config);
    }
}
