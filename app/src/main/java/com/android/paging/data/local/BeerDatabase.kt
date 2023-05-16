package com.android.paging.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BeerEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BeerDatabase : RoomDatabase() {
    abstract fun getBeerDao(): BeerDao
}