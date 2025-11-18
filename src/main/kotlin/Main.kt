package dev.anton

import dev.anton.model.Template
import net.minestom.server.Auth
import net.minestom.server.MinecraftServer

fun main() {
    val server = MinecraftServer.init(Auth.Online())
    loadhardcodedstufffortesting()
    server.start("0.0.0.0", 25565)
}

fun loadhardcodedstufffortesting() {
    val lobbyTemplate = Template("lobby", "paper-wrapper", true, 1, 1, 15, 20)

    //TemplateRegistry.add(lobbyTemplate)
    //val template = TemplateRegistry.findTemplateByName("lobby")

}