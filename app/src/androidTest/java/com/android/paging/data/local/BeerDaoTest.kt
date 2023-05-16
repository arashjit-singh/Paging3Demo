package com.android.paging.data.local

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BeerDaoTest {

    lateinit var db: BeerDatabase
    lateinit var beerDao: BeerDao

    val beerList = listOf(
        BeerEntity(
            id = 1,
            description = "",
            first_brewed = "",
            image_url = "",
            name = "",
            tagline = ""
        )
    )

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            appContext,
            BeerDatabase::class.java
        ).build()
        beerDao = db.getBeerDao()
    }

    @Test
    fun test_insert_beer_in_db() = runTest {
        beerDao.upsertBeer(beerList)
        val beerFromDb = beerDao.getAllBeers()
        Assert.assertEquals(beerFromDb.size, beerList.size)
    }

    @Test
    fun test_db_clear() = runTest {
        beerDao.upsertBeer(beerList)
        beerDao.clearTable()
        val beerFromDb = beerDao.getAllBeers()
        Assert.assertEquals(beerFromDb.size, 0)
    }
}