package com.tsu.pring.features.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsu.pring.databinding.TrendingCoinItemBinding
import com.tsu.pring.libraries.data.remote.dto.coins.TrendingCoinItem

class TrendingListAdapter(
	private val coinItemClick: (id: TrendingCoinItem) -> Unit,
) : ListAdapter<TrendingCoinItem, TrendingListAdapter.CoinListHolder>(ListDiffCallback) {

	inner class CoinListHolder(
		private val trendingBinding: TrendingCoinItemBinding,
		private val itemClick: (id: TrendingCoinItem) -> Unit,
	) : RecyclerView.ViewHolder(trendingBinding.root) {

		fun bind(item: TrendingCoinItem) {
			with(trendingBinding) {
				cryptoFullName1.text = item.name
				cryptoName1.text = item.symbol
				cryptoBehaviour1.text = "${item.priceBtc}"

				Glide.with(root).load(item.small).into(cryptoIcon1)

				root.setOnClickListener {
					coinItemClick.invoke(item)
				}
			}

		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListHolder {
		val view = LayoutInflater.from(parent.context)
		val trendingBinding = TrendingCoinItemBinding.inflate(view, parent, false)
		return CoinListHolder(trendingBinding, coinItemClick)
	}

	override fun onBindViewHolder(holder: CoinListHolder, position: Int) {
		holder.bind(getItem(position))
	}

	fun addAll(it: List<TrendingCoinItem>) {
		submitList(it)
		notifyDataSetChanged()
	}

	private object ListDiffCallback : DiffUtil.ItemCallback<TrendingCoinItem>() {

		override fun areItemsTheSame(oldItem: TrendingCoinItem, newItem: TrendingCoinItem) = oldItem.id == newItem.id

		override fun areContentsTheSame(oldItem: TrendingCoinItem, newItem: TrendingCoinItem) = oldItem == newItem
	}
}