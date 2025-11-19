package dev.anton.model

data class Server(
    val id: String,
    val template: Template,
    val ip: String,
    val port: Int,
    var status: Enums.ServerStatus = Enums.ServerStatus.STOPPED
)