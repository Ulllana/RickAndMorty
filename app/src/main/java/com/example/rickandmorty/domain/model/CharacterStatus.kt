package com.example.rickandmorty.domain.model

enum class CharacterStatus(val displayName: String) {
    ALIVE("Alive"),
    DEAD("Dead"),
    UNKNOWN("Unknown");

    companion object {
        fun fromString(value: String): CharacterStatus {
            return values().find { it.name.equals(value, ignoreCase = true) } ?: UNKNOWN
        }

        fun getAllDisplayNames(): List<String> {
            return values().map { it.displayName }
        }
    }
}