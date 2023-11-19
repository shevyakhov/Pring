package com.tsu.pring.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tsu.pring.data.model.CoinGet
import com.tsu.pring.data.rest.RestWorker
import com.tsu.pring.features.common.COIN_LIST_DETERMINANT
import com.tsu.pring.features.home.presentation.navigation.HomeRouter
import kotlinx.coroutines.launch

class HomeViewModel(
	private val router: HomeRouter,
	private val restWorker: RestWorker,
//	private val workManager: WorkManager,
) : ViewModel() {

	fun getCoinList()  = viewModelScope.launch {
		restWorker.doWork<CoinGet>(COIN_LIST_DETERMINANT)
//		val constraints = Constraints.Builder()
//			.setRequiredNetworkType(NetworkType.CONNECTED)
//			.setRequiresStorageNotLow(true)
//			.build()
//
//		val inputRequestItem = Data.Builder()
//			.putString(COIN_REQUEST_DETERMINANT, COIN_LIST_DETERMINANT)
//			.build()
//
//		val importWorkRequest =
//			OneTimeWorkRequestBuilder<RestWorker>()
//				.addTag(IMPORT_WORKER_TAG)
//				.setConstraints(constraints)
//				.setInputData(inputRequestItem)
//				.build()
//
//		workManager.beginUniqueWork(
//			IMPORT_WORKER_TAG,
//			ExistingWorkPolicy.KEEP,
//			importWorkRequest,
//		).enqueue()
	}
}