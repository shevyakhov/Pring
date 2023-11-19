package com.tsu.pring.data.rest

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiCoin(
    private val baseUrl: String,
) {

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
    private val noCacheGetInterceptor = Interceptor { chain ->
        val original = chain.request()
        val builder = original.newBuilder()
        builder.cacheControl(cacheControl = CacheControl.Builder().noCache().build())
        val newRequest =
            builder.method(original.method, original.body)
                .build()
        return@Interceptor chain.proceed(newRequest)
    }
    private val client: OkHttpClient by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(noCacheGetInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    val api: CoinRestInterface by lazy {
        retrofit.create(CoinRestInterface::class.java)
    }

    /**
     * Первичная проверка ответа от сервера.
     */
    fun <T> checkResponse(response: Response<T>): T {
        if (response.code() != 200 && response.code() != 201) {
            throw ApiExceptions.OtherException(
                "Сервер вернул ответ с ошибкой ${response.code()}: ${response.errorBody()}",
            )
        }

        if (response.body() == null) {
            throw ApiExceptions.OtherException(
                "Сервер вернул пустой ответ",
            )
        }

        return response.body()!!
    }


    sealed class ApiExceptions(msg: String) : Exception(msg) {
        class OtherException(msg: String) : ApiExceptions(msg)
    }

    companion object {
        private val TAG = ApiCoin::class.simpleName
    }
}