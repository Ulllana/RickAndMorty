package com.example.rickandmorty.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.rickandmorty.data.local.converter.ListConverter

@Entity(tableName = "characters")
@TypeConverters(ListConverter::class)
data class CharacterEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val originName: String,
    val originUrl: String,
    val locationName: String,
    val locationUrl: String,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String,
    val lastUpdated: Long = System.currentTimeMillis()
)