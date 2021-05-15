package com.intbyte.bw.engine.utils.gdx

import com.badlogic.gdx.utils.Array

inline fun<T> Array<T>.each(main: (it: T) -> Unit){
    for (i in 0 until size) main(get(i))
}