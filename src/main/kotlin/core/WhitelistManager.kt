package dev.anton.core

import dev.anton.model.WhitelistEntry
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.timer

object WhitelistManager {

    private val sessions = ConcurrentHashMap<UUID, WhitelistEntry>()

    init {
        timer(name = "whitelist-cleaner", daemon = true, period = 5000) {
            cleanupExpired()
        }
    }

    fun add(uuid: UUID, entry: WhitelistEntry) {
        sessions[uuid] = entry
    }

    fun cleanupExpired() {
        sessions.entries.removeIf { (_, entry) -> entry.isExpired() }
    }
}