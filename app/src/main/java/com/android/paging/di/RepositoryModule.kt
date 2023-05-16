package com.android.paging.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.android.paging.data.local.BeerDatabase
import com.android.paging.data.local.BeerEntity
import com.android.paging.data.remote.BeerApi
import com.android.paging.data.remote.BeerMediator
import com.android.paging.utilities.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providePager(api: BeerApi, beerDb: BeerDatabase): Pager<Int, BeerEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE
            ),
            remoteMediator = BeerMediator(
                api = api,
                beerDatabase = beerDb
            ),
            pagingSourceFactory = {
                beerDb.getBeerDao().getPagingSource()
            }
        )
    }

}