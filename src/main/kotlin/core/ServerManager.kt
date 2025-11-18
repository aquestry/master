package dev.anton.core

object ServerManager {
    val dockerSocket = if (java.io.File("/.dockerenv").exists())
        "/var/run/docker.sock"
    else
        "tcp://localhost:2375"

    fun createServer()
}