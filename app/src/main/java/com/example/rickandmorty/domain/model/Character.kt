package com.example.rickandmorty.domain.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) {
    val statusColor: Long
        get() = when (status.lowercase()) {
            "alive" -> 0xFF4CAF50 // Green
            "dead" -> 0xFFF44336 // Red
            else -> 0xFF9E9E9E // Gray
        }

    val genderIcon: String
        get() = when (gender.lowercase()) {
            "male" -> "♂"
            "female" -> "♀"
            "genderless" -> "⚧"
            else -> "?"
        }

    val episodeCount: Int
        get() = episode.size

    val hasSpecialType: Boolean
        get() = type.isNotBlank()

    val formattedSpecies: String
        get() = if (hasSpecialType) {
            "$species ($type)"
        } else {
            species
        }

    val shortInfo: String
        get() = "$species • $status"
}