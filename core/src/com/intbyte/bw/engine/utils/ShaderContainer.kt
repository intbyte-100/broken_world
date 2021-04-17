package com.intbyte.bw.engine.utils

class ShaderContainer{
    val frag: String
    val vert: String
    constructor(shader: String){
        frag = Resource.getFile("shaders/${shader}_frag.glsl").readString()
        vert = Resource.getFile("shaders/${shader}_vert.glsl").readString()
    }
}