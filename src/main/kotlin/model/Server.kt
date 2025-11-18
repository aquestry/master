package dev.anton.model

import java.util.UUID

data class Server(
    val id: UUID = UUID.randomUUID(),
    val template: Template,
    val ip: String,
    val port: Int,
    var status: Enums.ServerStatus = Enums.ServerStatus.STOPPED
)