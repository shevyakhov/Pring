package com.tsu.pring.features.prediction.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tsu.pring.databinding.CoinLayoutItemBinding
import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem

class CoinListAdapter(
	private val foodItemClick: (id: CoinItem) -> Unit,
) : ListAdapter<CoinItem, CoinListAdapter.FoodListHolder>(FoodListDiffCallback) {

	inner class FoodListHolder(
		private val binding: CoinLayoutItemBinding,
		private val itemClick: (id: CoinItem) -> Unit,
	) : RecyclerView.ViewHolder(binding.root) {

		fun bind(item: CoinItem) {
			with(binding) {
				binding.cryptoFullName1.text = item.name
				binding.cryptoName1.text = item.symbol

				if (item.image == null) {
					binding.cryptoIcon1.visibility = View.GONE
				}else{
					Glide.with(root).load(item.image).into(cryptoIcon1)
				}
				root.setOnClickListener {
					foodItemClick.invoke(item)
				}
			}

		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListHolder {
		val view = LayoutInflater.from(parent.context)
		val binding = CoinLayoutItemBinding.inflate(view, parent, false)
		return FoodListHolder(binding, foodItemClick)
	}

	override fun onBindViewHolder(holder: FoodListHolder, position: Int) {
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