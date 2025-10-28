package com.example.rickandmorty.domain.model

data class FilterOptions(
    val status: String? = null,
    val species: String? = null,
    val gender: String? = null
) {
    fun hasActiveFilters(): Boolean {
        return status != null || species != null || gender != null
    }

    fun clear(): FilterOptions {
        return FilterOptions()
    }
}