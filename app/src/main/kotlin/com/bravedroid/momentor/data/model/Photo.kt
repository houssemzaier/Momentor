package com.bravedroid.momentor.data.model

data class Photo(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val authorId: String,
    val likes: Int,
    val createdAt: Long
)
