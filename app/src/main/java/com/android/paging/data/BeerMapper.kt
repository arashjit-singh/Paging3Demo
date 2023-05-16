package com.android.paging.data

import com.android.paging.data.local.BeerEntity
import com.android.paging.data.remote.BeerDto

fun BeerDto.toBeerEntiity(): BeerEntity {
    return BeerEntity(
        id, description, first_brewed, image_url, name, tagline
    )
}