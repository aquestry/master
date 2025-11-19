package dev.anton.event.events

import dev.anton.event.Listener
import net.minestom.server.event.player.PlayerSpawnEvent

class PlayerSpawn : Listener<PlayerSpawnEvent> {
    override fun invoke(event: PlayerSpawnEvent) {
        event.player.playerConnection.disconnect()
    }
}