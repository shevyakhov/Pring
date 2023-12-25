package com.tsu.pring.features.home.coinlist

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsu.pring.R

class CoinListFragment : Fragment() {

    companion object {
        fun newInstance() = CoinListFragment()
    }

    private lateinit var viewModel: CoinListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_coin_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CoinListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}