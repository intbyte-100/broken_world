package com.intbyte.bw.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.intbyte.bw.GameBoot;
import com.intbyte.bw.core.ShadowMappingTest;


public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.height = 620;
        config.width = 1210;
        config.samples = 4;

        config.useGL30 = true;

        new LwjglApplication(new GameBoot(), config);
    }
}
