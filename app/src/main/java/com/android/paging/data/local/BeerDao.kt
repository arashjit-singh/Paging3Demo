package com.android.paging.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BeerDao {

    //method to insert beer in db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBeer(beerList: List<BeerEntity>)

    @Query("DELETE from beerTable")
    suspend fun clearTable()

    @Query("SELECT * from BeerTable")
    suspend fun getAllBeers(): List<BeerEntity>

    @Query("SELECT * from BeerTable")
    fun getPagingSource(): PagingSource<Int, BeerEntity>

}