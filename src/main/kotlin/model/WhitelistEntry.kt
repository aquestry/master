package dev.anton.model

import java.sql.Timestamp
import java.util.*

data class WhitelistEntry(
    val transferID: UUID = UUID.randomUUID(),
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis()),
    val ttl: Int = 30,
) {
    fun isExpired(): Boolean {
        val age = (System.currentTimeMillis() - timestamp.time) / 1000
        return age > ttl
    }
}