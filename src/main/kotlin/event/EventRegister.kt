package dev.anton.event

import net.minestom.server.MinecraftServer
import net.minestom.server.event.Event
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import kotlin.reflect.KClass
import dev.anton.event.events.AsyncPlayerConfiguration
import dev.anton.event.events.PlayerSpawn
import net.minestom.server.event.player.PlayerSpawnEvent

internal object EventRegister {
    fun init() {
        register(AsyncPlayerConfigurationEvent::class, AsyncPlayerConfiguration())
        register(PlayerSpawnEvent::class, PlayerSpawn())
    }

    fun <T : Event> register(eventClass: KClass<T>, listener: Listener<T>) {
        MinecraftServer.getGlobalEventHandler().addListener(eventClass.java) { listener(it) }
    }
}

fun interface Listener<T : Event> {
    operator fun invoke(event: T)
}