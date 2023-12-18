package com.example.codetest.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.codetest.data.entities.BeerEntity
import com.example.codetest.data.entities.BeerVO
import com.example.codetest.databinding.ItemBeerBinding

class BeerAdapter : PagingDataAdapter<BeerEntity, BeerAdapter.BeerViewHolder>(BeerComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerBinding.inflate(inflater, parent, false)
        return BeerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        getItem(position)?.let { holder.bindData(it) }
    }

    inner class BeerViewHolder(private val binding: ItemBeerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bindData(beer: BeerEntity) {
            binding.tvBeerName.text = beer.name
            binding.tvTagline.text = beer.tagline
            binding.tvDescription.text = beer.description

            Glide.with(itemView.context)
                .load(beer.imageUrl)
                .into(binding.ivBeerImage)
        }
    }
}

object BeerComparator : DiffUtil.ItemCallback<BeerEntity>() {
    override fun areItemsTheSame(oldItem: BeerEntity, newItem: BeerEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: BeerEntity, newItem: BeerEntity): Boolean {
        return oldItem == newItem
    }
}