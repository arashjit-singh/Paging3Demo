package com.android.paging.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.paging.data.beerEntity
import com.android.paging.data.local.BeerDatabase
import com.android.paging.data.local.BeerEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BeerMediator(
    private val beerApi: BeerApi, private val beerDatabase: BeerDatabase
) : RemoteMediator<Int, BeerEntity>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, BeerEntity>
    ): MediatorResult {

        val loadKey = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                if (lastItem == null) return MediatorResult.Success(endOfPaginationReached = true)
                else {
                    (lastItem.id / state.config.pageSize) + 1
                }
            }
        }

        return try {

            val beers = beerApi.getBeers(
                page = loadKey, perPage = state.config.pageSize
            )

            beerDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    beerDatabase.getBeerDao().clearTable()
                }

                val beerEntity = beers.map { beerDto ->
                    beerDto.beerEntity()
                }

                beerDatabase.getBeerDao().upsertBeer(beerEntity)
            }

            val endOfPaginationReached = beers.isEmpty() || beers.size < state.config.pageSize

            return MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )

        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}