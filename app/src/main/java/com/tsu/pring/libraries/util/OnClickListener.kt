package com.tsu.pring.libraries.util

import com.tsu.pring.libraries.data.remote.dto.coins.CoinItem

interface OnClickListener {

    fun onItemClick(coin: CoinItem)
}