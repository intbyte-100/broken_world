package com.intbyte.bw.jsondb


fun main() {
    var config = Config("/home/intbyte/IdeaProjects/broken_world/core/json","/dev/null")
    var builder = JavaBuilder()
    var linker = Linker(config,builder)
    linker.link()
}
