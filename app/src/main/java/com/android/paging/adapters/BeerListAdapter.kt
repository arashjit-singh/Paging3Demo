package com.android.paging.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.paging.databinding.ItemBeerBinding
import com.android.paging.domain.Beer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class BeerListAdapter : PagingDataAdapter<Beer, BeerListAdapter.ViewHolder>(BeerComparator) {

    class ViewHolder(private val binding: ItemBeerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(beer: Beer) {
            binding.apply {
                beerNameTxtVw.text = beer.beerName
                beerTaglineTxtVw.text = beer.beerTagline
                beerDescriptionTxtVw.text = beer.beerDescription
                beerBrewedDateTxtVw.text = beer.beerFirstBrewedDate
                beer.beerImageUrl.let {
                    Glide.with(binding.root.context).load(it)
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(beerImageVw)
                }
            }
        }
    }

    companion object {
        val BeerComparator = object : DiffUtil.ItemCallback<Beer>() {
            override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean {
                return oldItem.beerId == newItem.beerId
            }

            override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}