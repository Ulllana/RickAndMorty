package com.example.rickandmorty.domain.model

data class Location(
    val name: String,
    val url: String
) {
    val isKnown: Boolean
        get() = name.lowercase() != "unknown" && name.isNotBlank()

    val locationId: Int?
        get() = try {
            url.split("/").last().toIntOrNull()
        } catch (e: Exception) {
            null
        }
}