package com.example.codetest.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "beer")
data class BeerEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val description: String?,
    val imageUrl: String?,
    val name: String?,
    val tagline: String?,
)