package com.intbyte.bw.gameAPI.callbacks;

import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.core.game.GameInputProcessor;
import com.intbyte.bw.core.game.GameThread;


public class CallBack {


    public static void addCallBack(Initialization callback) {
        Initialization.callBacks.add(callback);
    }

    public static void addCallBack(Render callback) {
        Render.callBacks.add(callback);
    }

    public static void addCallBack(Touch callback) {
        Touch.callBacks.add(callback);
    }

    public static void addCallBack(TouchOnBlock callback) {
        TouchOnBlock.callBacks.add(callback);
    }

    public static void executeInitializationCallBacks() {
        Initialization.iterator = Initialization.callBacks.iterator();
        if (com.intbyte.bw.core.Initialization.isReadyCallBack())
            while (Initialization.iterator.hasNext()) {
                Initialization.iterator.next().main();
                Initialization.iterator.remove();
            }
    }

    public static void executeRenderCallBacks() {
        if (GameThread.isReadyCallBack())
            for (Render i : Render.callBacks)
                i.main();
    }

    public static void executeTouchCallBacks(Vector3 position) {
        if (GameInputProcessor.isReadyCallBack())
            for (Touch i : Touch.callBacks)
                i.main(position);
    }

    public static void executeTouchOnBlockCallBack(int x, int z) {
        if (GameInputProcessor.isReadyCallBack())
            for (TouchOnBlock i : TouchOnBlock.callBacks)
                i.main(x, z);

    }
}
