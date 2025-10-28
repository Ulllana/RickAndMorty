package com.example.rickandmorty.util

import androidx.paging.ItemSnapshotList

fun <T : Any> ItemSnapshotList<T>.toList(): List<T> {
    val list = mutableListOf<T>()
    for (i in 0 until this.size) {
        this[i]?.let { list.add(it) }
    }
    return list
}