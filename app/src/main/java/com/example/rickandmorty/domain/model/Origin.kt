package com.example.rickandmorty.domain.model


data class Origin(
    val name: String,
    val url: String
) {
    val isKnown: Boolean
        get() = name.lowercase() != "unknown" && name.isNotBlank()

    val originId: Int?
        get() = try {
            url.split("/").last().toIntOrNull()
        } catch (e: Exception) {
            null
        }
}