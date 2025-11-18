package dev.anton.event.events

import dev.anton.event.Listener
import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.network.packet.server.common.TransferPacket

class AsyncPlayerConfiguration : Listener<AsyncPlayerConfigurationEvent> {

    private val instance: InstanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer()

    override fun invoke(event: AsyncPlayerConfigurationEvent) {
        event.spawningInstance = instance
        event.player.playerConnection.storeCookie("master:session", "tests".toByteArray())
        event.player.sendPacket(TransferPacket("127.0.0.1", 25566))
    }
}