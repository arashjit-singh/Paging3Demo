package com.android.paging.ui

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.android.paging.data.local.BeerEntity
import com.android.paging.data.toBeer
import com.android.paging.domain.Beer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(pager: Pager<Int, BeerEntity>) : ViewModel() {

    val beerPagingFlow: Flow<PagingData<Beer>> = pager.flow.map { beerEntity ->
        beerEntity.map {
            it.toBeer()
        }
    }

}