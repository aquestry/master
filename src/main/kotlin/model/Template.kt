package dev.anton.model

data class Template(
    val name: String,
    val dockerImage: String,
    val fallback: Boolean,
    val minInstances: Int,
    val maxInstances: Int,
    val capacity: Int,
    val maxCapacity: Int,
    val ramMb: Int = 2048
)