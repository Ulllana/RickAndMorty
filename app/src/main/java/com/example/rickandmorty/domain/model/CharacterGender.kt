package com.example.rickandmorty.domain.model

enum class CharacterGender(val displayName: String) {
    MALE("Male"),
    FEMALE("Female"),
    GENDERLESS("Genderless"),
    UNKNOWN("Unknown");

    companion object {
        fun fromString(value: String): CharacterGender {
            return values().find { it.name.equals(value, ignoreCase = true) } ?: UNKNOWN
        }

        fun getAllDisplayNames(): List<String> {
            return values().map { it.displayName }
        }
    }
}