package dev.anton.model

import java.sql.Timestamp

data class WhitelistEntry(
    val timestamp: Timestamp = Timestamp(System.currentTimeMillis()),
    val ttl: Int = 30,
) {
    fun isExpired(): Boolean {
        val age = (System.currentTimeMillis() - timestamp.time) / 1000
        return age > ttl
    }
}