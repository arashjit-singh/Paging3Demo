package com.android.paging.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.paging.adapters.BeerListAdapter
import com.android.paging.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val viewModel by viewModels<MainViewModel>()
    lateinit var beerAdapter: BeerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setAdapter()
        setObservers()
    }

    fun setObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.beerPagingFlow.collect {
                        beerAdapter.submitData(it)
                    }
                }

                launch {
                    beerAdapter.loadStateFlow.collect { loadState ->

                        // Show loading spinner during initial load or refresh.
                        binding.progressBar.isVisible =
                            loadState.mediator?.refresh is LoadState.Loading

                    }
                }

            }
        }
    }

    fun setAdapter() {
        beerAdapter = BeerListAdapter()
        binding.beerRecyclerView.apply {
            adapter = beerAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}