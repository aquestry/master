package dev.anton

import dev.anton.core.ServerManager.createServer
import dev.anton.event.EventRegister
import dev.anton.model.Template
import net.minestom.server.Auth
import net.minestom.server.MinecraftServer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger("Main")!!

fun main() {
    val server = MinecraftServer.init(Auth.Online())
    EventRegister.init()
    loadhardcodedstufffortesting()
    server.start("0.0.0.0", 25565)
}

fun loadhardcodedstufffortesting() {
    val lobbyTemplate = Template("lobby", "paper-wrapper", true, 1, 1, 15, 20)
    //TemplateRegistry.add(lobbyTemplate)
    //val template = TemplateRegistry.findTemplateByName("lobby")
    createServer(lobbyTemplate)
}