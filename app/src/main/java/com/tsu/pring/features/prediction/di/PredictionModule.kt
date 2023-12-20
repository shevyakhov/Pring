package com.tsu.pring.features.prediction.di

import com.tsu.pring.features.prediction.presentation.PredictionViewModel
import com.tsu.pring.libraries.data.remote.ApiService
import com.tsu.pring.libraries.data.repository.CoinRepositoryImpl
import com.tsu.pring.libraries.domain.repository.CoinRepository
import com.tsu.pring.libraries.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val PredictionModule = module {

	single {
		OkHttpClient.Builder()
			.addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
			.build()
	}
	single<ApiService> {
		Retrofit.Builder()
			.baseUrl(Constants.BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.client(get())
			.build()
			.create()
	}
	factory<CoinRepository> { CoinRepositoryImpl(get()) }
	viewModel {
		PredictionViewModel(router = get(), repository = get(), localRepository = get())
	}
}