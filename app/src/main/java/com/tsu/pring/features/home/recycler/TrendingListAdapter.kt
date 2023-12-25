package com.tsu.pring.features.home.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsu.pring.databinding.CoinLayoutItemBinding
import com.tsu.pring.databinding.TrendingCoinItemBinding
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem

class TrendingListAdapter(
    private val coinItemClick: (id: CoinItem) -> Unit,
) : ListAdapter<CoinItem, TrendingListAdapter.CoinListHolder>(FoodListDiffCallback) {

    inner class CoinListHolder(
        private val searchBinding: CoinLayoutItemBinding,
        private val trendingBinding: TrendingCoinItemBinding,
        private val itemClick: (id: CoinItem) -> Unit,
    ) : RecyclerView.ViewHolder(searchBinding.root) {

        fun bind(item: CoinItem) {
            with(searchBinding) {
                cryptoFullName1.text = item.name
                cryptoName1.text = item.symbol

                if (item.image == null) {
                    cryptoIcon1.visibility = View.GONE
                }else{
                    Glide.with(root).load(item.image).into(cryptoIcon1)
                }
                root.setOnClickListener {
                    coinItemClick.invoke(item)
                }
            }

            with(trendingBinding) {
                cryptoFullName1.text = item.name
                cryptoName1.text = item.symbol
                cryptoPrice1.text = "${item.currentPrice}"
                cryptoBehaviour1.text = "${item.priceChangePercentage24h}"

                if (item.image == null) {
                    cryptoIcon1.visibility = View.GONE
                }else{
                    Glide.with(root).load(item.image).into(cryptoIcon1)
                }
                root.setOnClickListener {
                    coinItemClick.invoke(item)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListHolder {
        val view = LayoutInflater.from(parent.context)
        val searchBinding = CoinLayoutItemBinding.inflate(view, parent, false)
        val trendingBinding = TrendingCoinItemBinding.inflate(view, parent, false)
        return CoinListHolder(searchBinding, trendingBinding, coinItemClick)
    }

    override fun onBindViewHolder(holder: CoinListHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun addAll(it: List<CoinItem>) {
        submitList(it)
    }

    private object FoodListDiffCallback : DiffUtil.ItemCallback<CoinItem>() {

        override fun areItemsTheSame(oldItem: CoinItem, newItem: CoinItem) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CoinItem, newItem: CoinItem) = oldItem == newItem
    }
}

