package com.android.paging.data

import com.android.paging.data.local.BeerEntity
import com.android.paging.data.remote.BeerDto
import com.android.paging.domain.Beer

fun BeerDto.beerEntity(): BeerEntity {
    return BeerEntity(
        id, description, first_brewed, image_url, name, tagline
    )
}

fun BeerEntity.toBeer(): Beer {
    return Beer(
        beerId = id,
        beerDescription = description,
        beerFirstBrewedDate = first_brewed,
        beerImageUrl = image_url,
        beerName = name,
        beerTagline = tagline
    )
}

