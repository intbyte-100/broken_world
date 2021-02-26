package com.intbyte.bw.jsondb

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.JsonValue

class JavaBuilder : AbstractBuilder {

    private val map = mutableMapOf<String, Array<JsonValue>>()

    override fun build(out: String, jsonValues: Array<JsonValue>) {
        sort(jsonValues)
        map.keys.forEach {
            when (it) {
                "block" -> println(it)
            }
        }
    }

    private fun sort(array: Array<JsonValue>) {
        array.forEach {
            val str = it.get("type")?.asString()?.split(":")?.get(0)
            if (map[str] == null && str != null) map[str] = Array()
            map[str]?.add(it)
        }
    }
}