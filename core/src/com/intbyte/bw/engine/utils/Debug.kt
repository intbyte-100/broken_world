package com.intbyte.bw.engine.utils

import com.badlogic.ashley.utils.ImmutableArray
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Array
import com.intbyte.bw.engine.world.World

private class Message(val index: Int, var value: Any)

object Debug {

    private val debugMessagesArray = Array<String>()
    private val values = HashMap<String, Message>()

    @JvmStatic
    val debugMessages = ImmutableArray<String>(debugMessagesArray)

    @JvmStatic
    fun valueInfo(name: String, value: Any) {
        var message = values[name]
        if (message == null) {
            message = Message(values.keys.size, value)
            values[name] = message
        } else {
            message.value = value
        }

    }

    @JvmStatic
    fun message(message: String) {
        debugMessagesArray.add(message)
    }

    @JvmStatic
    fun update() {
        if(!World.getConfig().isDebug) return

        debugMessagesArray.clear()
        for (i in 0 until values.size)
            values.forEach {
                if (it.value.index == i)
                    debugMessagesArray.add("${it.key} = ${it.value.value};")
            }
        valueInfo("fps", Gdx.graphics.framesPerSecond)
    }
}