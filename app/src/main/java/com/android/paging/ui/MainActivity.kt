package com.android.paging.ui

import android.os.Bundle
import android.widget.Toast
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

                        val errorState = loadState.source.append as? LoadState.Error
                            ?: loadState.source.prepend as? LoadState.Error
                            ?: loadState.append as? LoadState.Error
                            ?: loadState.prepend as? LoadState.Error
                            ?: loadState.refresh as? LoadState.Error

                        errorState?.let {
                            Toast.makeText(
                                this@MainActivity,
                                "\uD83D\uDE28 Wooops ${it.error}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

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