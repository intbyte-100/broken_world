package com.intbyte.bw.desktop;


import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.intbyte.bw.GameBoot;


public class DesktopLauncher {
    public static void main(String[] arg) {

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 720);
        config.setBackBufferConfig(8,8,8,8,16,16,4);

        GameBoot gameBoot = new GameBoot();
        new Lwjgl3Application(gameBoot, config);
    }
}
