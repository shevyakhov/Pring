package com.tsu.pring.application.mainFragment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.tsu.pring.R
import com.tsu.pring.application.mainFragment.MainScreenValue
import com.tsu.pring.application.mainFragment.presentation.MainFragmentViewModel
import com.tsu.pring.databinding.FragmentMainBinding
import com.tsu.pring.libraries.modules.MainNavigatorName.MAIN
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MainFragment : Fragment() {

	private val navigatorHolder: NavigatorHolder by inject(named(MAIN))
	private val viewModel by viewModel<MainFragmentViewModel>()
	private var _binding: FragmentMainBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?,
	): View {
		_binding = FragmentMainBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initCicerone()
	}

	private fun initCicerone() {
		val navigator = AppNavigator(requireActivity(), R.id.host_main, childFragmentManager)
		navigatorHolder.setNavigator(navigator)

		viewModel.mainScreenValueFlow.onEach {
			binding.bottomNavigation.selectedItemId = when (it) {
				MainScreenValue.Home       -> R.id.home
				MainScreenValue.Stats      -> R.id.stats
				MainScreenValue.Prediction -> R.id.prediction
			}
		}.launchIn(lifecycleScope)


		binding.bottomNavigation.setOnItemSelectedListener {
			if (binding.bottomNavigation.selectedItemId != it.itemId)
				when (it.itemId) {
					R.id.home       -> viewModel.navigateToHome()
					R.id.stats      -> viewModel.navigateToStats()
					R.id.prediction -> viewModel.navigateToPrediction()
				}
			true
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
		navigatorHolder.removeNavigator()
	}
}
