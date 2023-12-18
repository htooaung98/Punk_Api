package com.example.codetest.data.entities

import com.google.gson.annotations.SerializedName

class BeerVO (
    @SerializedName("description") val description: String,
    @SerializedName("id") val id: Int,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("tagline") val tagline: String,
)

fun List<BeerVO>.toBeerEntityList(): List<BeerEntity> {
    return map {
        BeerEntity(
            description = it.description,
            id = it.id,
            imageUrl = it.imageUrl,
            name = it.name,
            tagline = it.tagline
        )
    }
}
