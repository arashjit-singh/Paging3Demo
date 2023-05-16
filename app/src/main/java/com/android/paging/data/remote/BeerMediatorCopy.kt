//package com.android.paging.data.remote
//
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.LoadType
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import androidx.room.withTransaction
//import com.android.paging.data.local.BeerDatabase
//import com.android.paging.data.local.BeerEntity
//import com.android.paging.data.beerEntity
//import retrofit2.HttpException
//import java.io.IOException
//import javax.inject.Inject
//
//@OptIn(ExperimentalPagingApi::class)
//class BeerMediatorCopy @Inject constructor(
//    private val api: BeerApi,
//    private val beerDatabase: BeerDatabase
//) : RemoteMediator<Int, BeerEntity>() {
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, BeerEntity>
//    ): MediatorResult {
//
//
//        val loadKey = when (loadType) {
//            //For REFRESH, pass null to load the first page.
//            LoadType.REFRESH -> null
//            // In this example, you never need to prepend, since REFRESH
//            // will always load the first page in the list. Immediately
//            // return, reporting end of pagination.
//            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
//
//            LoadType.APPEND -> {
//                val lastItem = state.lastItemOrNull()
//
//                // You must explicitly check if the last item is null when
//                // appending, since passing null to networkService is only
//                // valid for initial load. If lastItem is null it means no
//                // items were loaded after the initial REFRESH and there are
//                // no more items to load.
//
//                if (lastItem == null) {
//                    return MediatorResult.Success(endOfPaginationReached = true)
//                }
//
//                (lastItem.id / state.config.pageSize) + 1
//            }
//        }
//
//        // Suspending network load via Retrofit. This doesn't need to be
//        // wrapped in a withContext(Dispatcher.IO) { ... } block since
//        // Retrofit's Coroutine CallAdapter dispatches on a worker
//        // thread.
//
//
//        return try {
//
//            val response =
//                loadKey?.let {
//                    api.getBeers(
//                        page = it,
//                        perPage = state.config.pageSize
//                    )
//                }
//
//
//            response?.let {
//
//                beerDatabase.withTransaction {
//                    // clear all tables in the database if refresh
//                    if (loadType == LoadType.REFRESH) {
//                        beerDatabase.getBeerDao().clearTable()
//                    }
//
//                    val beerEntity = response!!.map {
//                        it.beerEntity()
//                    }
//
//                    beerDatabase.getBeerDao().upsertBeer(beerEntity)
//                }
//
//                return MediatorResult.Success(
//                    endOfPaginationReached = response!!.isEmpty()
//                )
//            }
//
//
//        } catch (e: IOException) {
//            MediatorResult.Error(e)
//        } catch (e: HttpException) {
//            MediatorResult.Error(e)
//        }
//    }
//
//}