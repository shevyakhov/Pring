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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.reflect.KSuspendFunction1

class ListListAdapter(
	private val itemClick: KSuspendFunction1<CoinItem, Double>,
) : ListAdapter<CoinItem, ListListAdapter.ListHolder>(FoodListDiffCallback) {

	inner class ListHolder(
		private val binding: CoinLayoutItemBinding,
		private val itemClick: KSuspendFunction1<CoinItem, Double>,
	) : RecyclerView.ViewHolder(binding.root) {

		fun bind(item: CoinItem) {
			with(binding) {
				binding.cryptoFullName1.text = item.name
				binding.cryptoName1.text = item.symbol
				binding.cryptoPrice1.text = if (item.currentPrice.toString().toDouble() != 0.0) {
					"$ ${item.currentPrice}"
				} else
					"Нажмите чтобы узнать цену"

				if (item.image == null) {
					binding.cryptoIcon1.visibility = View.GONE
				} else {
					Glide.with(root).load(item.image).into(cryptoIcon1)
				}
				root.setOnClickListener {
					if (item.currentPrice.toString().toDouble() == 0.0)
						CoroutineScope(Dispatchers.IO).launch {
							val text = itemClick.invoke(item).toString()
							withContext(Dispatchers.Main) {
								binding.cryptoPrice1.text = "$ $text"
							}
						}
				}
			}

		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
		val view = LayoutInflater.from(parent.context)
		val binding = CoinLayoutItemBinding.inflate(view, parent, false)
		return ListHolder(binding, itemClick)
	}

	override fun onBindViewHolder(holder: ListHolder, position: Int) {
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