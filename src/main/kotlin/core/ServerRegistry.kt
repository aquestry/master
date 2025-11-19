package dev.anton.core

import dev.anton.model.Server

object ServerRegistry {
    private val servers = mutableSetOf<Server>()

    fun add(server: Server) {
        servers.add(server)
    }

    fun findServerByID(id: String): Server? {
        return servers.firstOrNull { it.id == id }
    }

    fun all(): Set<Server> = servers
}