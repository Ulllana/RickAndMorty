package com.example.rickandmorty.data.mapper

import com.example.rickandmorty.data.local.entity.CharacterEntity
import com.example.rickandmorty.data.remote.dto.CharacterDto
import com.example.rickandmorty.domain.model.Character
import com.example.rickandmorty.domain.model.Location
import com.example.rickandmorty.domain.model.Origin

object CharacterMapper {

    fun dtoToEntity(dto: CharacterDto): CharacterEntity {
        return CharacterEntity(
            id = dto.id,
            name = dto.name,
            status = dto.status,
            species = dto.species,
            type = dto.type,
            gender = dto.gender,
            originName = dto.origin.name,
            originUrl = dto.origin.url,
            locationName = dto.location.name,
            locationUrl = dto.location.url,
            image = dto.image,
            episode = dto.episode,
            url = dto.url,
            created = dto.created
        )
    }

    fun entityToDomain(entity: CharacterEntity): Character {
        return Character(
            id = entity.id,
            name = entity.name,
            status = entity.status,
            species = entity.species,
            type = entity.type,
            gender = entity.gender,
            origin = Origin(
                name = entity.originName,
                url = entity.originUrl
            ),
            location = Location(
                name = entity.locationName,
                url = entity.locationUrl
            ),
            image = entity.image,
            episode = entity.episode,
            url = entity.url,
            created = entity.created
        )
    }

    fun dtoToDomain(dto: CharacterDto): Character {
        return dto.toCharacter()
    }

    fun listDtoToEntity(dtos: List<CharacterDto>): List<CharacterEntity> {
        return dtos.map { dtoToEntity(it) }
    }

    fun listEntityToDomain(entities: List<CharacterEntity>): List<Character> {
        return entities.map { entityToDomain(it) }
    }

    fun listDtoToDomain(dtos: List<CharacterDto>): List<Character> {
        return dtos.map { dtoToDomain(it) }
    }
}