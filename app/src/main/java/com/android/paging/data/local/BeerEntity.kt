package com.android.paging.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BeerTable")
data class BeerEntity(
    @PrimaryKey
    val id: Int,
    val description: String,
    val first_brewed: String,
    val image_url: String?,
    val name: String,
    val tagline: String
)
