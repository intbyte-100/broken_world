package com.intbyte.bw.desktop;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.intbyte.bw.GameBoot;
import com.intbyte.bw.core.ShadowMappingTest;


public class DesktopLauncher {
    public static void main(String[] arg) {

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(800, 480);



        new Lwjgl3Application(new GameBoot(), config);
    }
}
