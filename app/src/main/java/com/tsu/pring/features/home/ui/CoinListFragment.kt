package com.tsu.pring.features.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.tsu.pring.databinding.FragmentListBinding
import com.tsu.pring.features.home.presentation.ListViewModel
import com.tsu.pring.features.prediction.ui.recycler.ListListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class CoinListFragment : Fragment() {

	private var _binding: FragmentListBinding? = null
	private val binding get() = _binding!!
	private val viewModel by viewModel<ListViewModel>()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentListBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val scope = viewLifecycleOwner.lifecycleScope

		viewModel.eventFlow.onEach {
			Toast.makeText(requireContext(), "Ошибка сервера", Toast.LENGTH_SHORT).show()
		}.launchIn(scope)

		val adapter = ListListAdapter(viewModel::getPrice)
		binding.recyclerViewCoin.layoutManager = LinearLayoutManager(requireContext())
		binding.recyclerViewCoin.adapter = adapter
		viewModel.currentList.onEach {
			adapter.addAll(it)
		}.launchIn(scope)

		viewModel.getCoinList()

		binding.searchTextField.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
			override fun onQueryTextSubmit(query: String?): Boolean {
				viewModel.search(query)
				return true
			}

			override fun onQueryTextChange(p0: String?): Boolean {
				viewModel.search(p0)
				return true
			}
		})
	}
}
