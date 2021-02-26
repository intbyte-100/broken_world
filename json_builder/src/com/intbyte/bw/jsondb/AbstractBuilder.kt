package com.intbyte.bw.jsondb

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.JsonValue

interface AbstractBuilder {
    fun build(out: String, jsonValues: Array<JsonValue>)
}