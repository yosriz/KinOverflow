package kin.kinoverflow.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


private const val STACKOVERFLOW_API = "https://api.stackexchange.com/2.2/"

class StackOveflowApi {
    private val retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(STACKOVERFLOW_API)
                .build()
    }



}