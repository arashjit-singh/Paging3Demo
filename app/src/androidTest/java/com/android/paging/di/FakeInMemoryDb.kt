package com.android.paging.di

import android.content.Context
import androidx.room.Room
import com.android.paging.data.local.BeerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FakeInMemoryDb {

    @Provides
    @Singleton
    fun provideInMemoryDb(@ApplicationContext context: Context): BeerDatabase {
        return Room.inMemoryDatabaseBuilder(context, BeerDatabase::class.java).build()
    }
}