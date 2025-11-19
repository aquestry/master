package dev.anton.event.events

import dev.anton.core.ServerRegistry
import dev.anton.core.WhitelistManager
import dev.anton.event.Listener
import dev.anton.util.HmacUtil
import net.minestom.server.MinecraftServer
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.instance.InstanceContainer
import net.minestom.server.network.packet.server.common.TransferPacket

class AsyncPlayerConfiguration : Listener<AsyncPlayerConfigurationEvent> {

    private val instance: InstanceContainer = MinecraftServer.getInstanceManager().createInstanceContainer()

    override fun invoke(event: AsyncPlayerConfigurationEvent) {
        event.spawningInstance = instance
        WhitelistManager.add(event.player.uuid)
        val now = System.currentTimeMillis()/ 1000
        val exp = now + 30

        val target  = ServerRegistry.all().toList()[0]
        val targetAddress = target.ip + ":" + target.port

        val payload = "uuid=${event.player.uuid};target=$targetAddress;exp=$exp"
        val token = HmacUtil.create(payload)
        event.player.playerConnection.storeCookie("master:session", token.toByteArray())
        event.player.sendPacket(TransferPacket(target.ip, target.port))
    }
}