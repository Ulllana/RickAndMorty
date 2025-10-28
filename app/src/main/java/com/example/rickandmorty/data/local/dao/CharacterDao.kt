package com.example.rickandmorty.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rickandmorty.data.local.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterEntity)

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity?

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :name || '%'")
    fun searchCharactersByName(name: String): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characters WHERE name LIKE '%' || :name || '%'")
    fun pagingSourceByName(name: String): PagingSource<Int, CharacterEntity>

    @Query("""
        SELECT * FROM characters 
        WHERE (:status IS NULL OR status = :status)
        AND (:species IS NULL OR species = :species)
        AND (:gender IS NULL OR gender = :gender)
    """)
    fun getCharactersFiltered(
        status: String?,
        species: String?,
        gender: String?
    ): Flow<List<CharacterEntity>>

    @Query("""
        SELECT * FROM characters 
        WHERE (:status IS NULL OR status = :status)
        AND (:species IS NULL OR species = :species)
        AND (:gender IS NULL OR gender = :gender)
    """)
    fun pagingSourceFiltered(
        status: String?,
        species: String?,
        gender: String?
    ): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characters ORDER BY id")
    fun pagingSource(): PagingSource<Int, CharacterEntity>

    @Query("SELECT * FROM characters")
    fun getCharacters(): Flow<List<CharacterEntity>>

    @Query("DELETE FROM characters")
    suspend fun clearAllCharacters()

    @Query("SELECT COUNT(*) FROM characters")
    suspend fun getCount(): Int
}