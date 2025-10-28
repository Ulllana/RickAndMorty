package com.example.rickandmorty.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmorty.domain.model.Character
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters")
data class CharacterDto(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("origin")
    val origin: OriginDto,
    @SerializedName("location")
    val location: LocationDto,
    @SerializedName("image")
    val image: String,
    @SerializedName("episode")
    val episode: List<String>,
    @SerializedName("url")
    val url: String,
    @SerializedName("created")
    val created: String
) {
    fun toCharacter(): Character {
        return Character(
            id = id,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            origin = origin.toOrigin(),
            location = location.toLocation(),
            image = image,
            episode = episode,
            url = url,
            created = created
        )
    }
}

data class OriginDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) {
    fun toOrigin(): com.example.rickandmorty.domain.model.Origin {
        return com.example.rickandmorty.domain.model.Origin(
            name = name,
            url = url
        )
    }
}

data class LocationDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
) {
    fun toLocation(): com.example.rickandmorty.domain.model.Location {
        return com.example.rickandmorty.domain.model.Location(
            name = name,
            url = url
        )
    }
}

data class CharactersResponse(
    @SerializedName("info")
    val info: InfoDto,
    @SerializedName("results")
    val results: List<CharacterDto>
)

data class InfoDto(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: String?
)